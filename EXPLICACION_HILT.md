# 🔍 Explicación: ¿Qué es Hilt y cómo se usa?

## 📚 ¿Qué es Hilt?

**Hilt** (Hilt = Hilt Is Like Dagger, pero más simple) es una biblioteca de **Inyección de Dependencias (Dependency Injection)** para Android desarrollada por Google.

### Concepto Simple: Inyección de Dependencias

Imagina que tienes una clase que necesita usar otra clase. En lugar de crear la dependencia dentro de la clase, **se la "inyectas" desde afuera**.

**❌ Sin Inyección de Dependencias (Mal):**
```kotlin
class ProductViewModel {
    // Crear la dependencia dentro de la clase
    private val repository = ProductRepositoryImpl() // ❌ Acoplamiento fuerte
}
```

**✅ Con Inyección de Dependencias (Bien):**
```kotlin
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository // ✅ Se inyecta desde afuera
)
```

### ¿Por qué es útil?

1. **Desacoplamiento**: Las clases no crean sus propias dependencias
2. **Testeable**: Fácil de probar (puedes inyectar mocks)
3. **Reutilizable**: Mismo objeto puede usarse en múltiples lugares
4. **Mantenible**: Cambios en una clase no afectan a otras

---

## 🔧 ¿Cómo funciona Hilt en tu proyecto?

### 1. **Configuración Base**

**MilSaboresApplication.kt:**
```kotlin
@HiltAndroidApp  // ← Marca la aplicación para usar Hilt
class MilSaboresApplication : Application()
```

**MainActivity.kt:**
```kotlin
@AndroidEntryPoint  // ← Marca la Activity para usar Hilt
class MainActivity : ComponentActivity()
```

### 2. **Módulos de Dependencias**

**AppModule.kt:**
```kotlin
@Module
@InstallIn(SingletonComponent::class)  // ← Instala en el componente singleton
object AppModule {
    
    @Provides  // ← Proporciona una instancia
    @Singleton  // ← Una sola instancia para toda la app
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
}
```

### 3. **Inyección en ViewModels**

**ProductViewModel.kt:**
```kotlin
@HiltViewModel  // ← Marca el ViewModel para usar Hilt
class ProductViewModel @Inject constructor(  // ← @Inject indica que recibe dependencias
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel()
```

### 4. **Uso en Pantallas (Compose)**

**HomeScreen.kt:**
```kotlin
@Composable
fun HomeScreen(
    productViewModel: ProductViewModel = hiltViewModel()  // ← Hilt crea el ViewModel automáticamente
) {
    // Usar el ViewModel
}
```

---

## 🔄 Comparación: Con Hilt vs Sin Hilt

### ✅ **Con Hilt** (ProductViewModel, CartViewModel)

```kotlin
// 1. ViewModel marca con @HiltViewModel
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository  // Hilt inyecta esto automáticamente
) : ViewModel()

// 2. En la pantalla
val viewModel: ProductViewModel = hiltViewModel()  // Hilt crea y configura todo
```

**Ventajas:**
- ✅ Hilt maneja todo automáticamente
- ✅ Fácil de testear (puedes inyectar mocks)
- ✅ Dependencias compartidas (singletons)
- ✅ Menos código manual

### ❌ **Sin Hilt** (AuthViewModel - como PokeStore)

```kotlin
// 1. ViewModel crea dependencias manualmente
class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)  // Crea manualmente
    private val userDao = database.userDao()
}

// 2. En la pantalla
val viewModel: AuthViewModel = viewModel()  // ViewModel normal, sin Hilt
```

**Características:**
- ✅ Más simple y directo
- ✅ Menos configuración
- ✅ Patrón usado en PokeStore
- ❌ Más difícil de testear
- ❌ Acoplamiento más fuerte

---

## 📊 Estado Actual en tu Proyecto

### ✅ **ViewModels que SÍ usan Hilt:**

1. **ProductViewModel** ✅
   - `@HiltViewModel`
   - Usa `hiltViewModel()` en pantallas
   - Dependencias inyectadas: UseCases, Repository

2. **CartViewModel** ✅
   - `@HiltViewModel`
   - Usa `hiltViewModel()` en pantallas
   - Dependencias inyectadas: CartRepository

### ❌ **ViewModels que NO usan Hilt:**

1. **AuthViewModel** ❌
   - NO tiene `@HiltViewModel`
   - Usa `viewModel()` (normal) en pantallas
   - Crea dependencias manualmente: `AppDatabase.getDatabase(application)`
   - **Razón**: Seguimos el patrón de PokeStore (acceso directo)

---

## 🤔 ¿Qué usa PokeStore?

Basado en tu descripción y el código de AuthViewModel, **PokeStore probablemente NO usa Hilt**. En su lugar:

- ✅ Acceso directo a la base de datos desde ViewModels
- ✅ Creación manual de dependencias
- ✅ Patrón más simple y directo

**Por eso AuthViewModel sigue ese patrón:**
```kotlin
class AuthViewModel(application: Application) : AndroidViewModel(application) {
    // Acceso directo, sin Hilt
    private val database = AppDatabase.getDatabase(application)
}
```

---

## 📋 ¿Se pide Hilt en la Rúbrica?

**Revisando la rúbrica del encargo:**

La rúbrica menciona:
- ✅ **IE 2.3.1**: "Estructura el proyecto aplicando principios de modularidad"
- ✅ **IE 2.3.1**: "separando responsabilidades lógicas, visuales y funcionales"

**Hilt NO es explícitamente requerido**, pero:
- ✅ **SÍ ayuda** a cumplir con "modularidad" y "separación de responsabilidades"
- ✅ **SÍ es una buena práctica** en Android moderno
- ✅ **SÍ está implementado** en tu proyecto (parcialmente)

---

## 🎯 Recomendación

### Estado Actual: ✅ **CORRECTO**

Tu proyecto tiene:
- ✅ Hilt implementado para la mayoría de componentes (ProductViewModel, CartViewModel)
- ✅ Patrón simple (sin Hilt) para AuthViewModel (como PokeStore)
- ✅ Mejor de ambos mundos: modularidad donde se necesita, simplicidad donde conviene

### ¿Debes cambiar algo?

**NO es necesario**, pero si quieres ser consistente:

**Opción 1: Mantener como está** ✅ (Recomendado)
- ProductViewModel y CartViewModel con Hilt (complejo, necesita inyección)
- AuthViewModel sin Hilt (simple, acceso directo como PokeStore)

**Opción 2: Migrar AuthViewModel a Hilt**
- Agregar `@HiltViewModel` a AuthViewModel
- Cambiar `viewModel()` por `hiltViewModel()` en pantallas
- Más consistente, pero más complejo

---

## 📝 Resumen

| Concepto | Explicación |
|----------|-------------|
| **Hilt** | Biblioteca de inyección de dependencias para Android |
| **¿Para qué?** | Inyectar dependencias automáticamente sin crear objetos manualmente |
| **¿Está en tu proyecto?** | ✅ SÍ, parcialmente (ProductViewModel, CartViewModel) |
| **¿Está en PokeStore?** | ❌ Probablemente NO (usa acceso directo) |
| **¿Se pide en la rúbrica?** | ⚠️ No explícitamente, pero ayuda con modularidad |
| **¿Debes cambiarlo?** | ❌ NO, está bien como está |

---

## 🔗 Referencias

- **Hilt**: Biblioteca oficial de Google para DI en Android
- **Dagger**: La biblioteca base de la que Hilt es un wrapper
- **Inyección de Dependencias**: Patrón de diseño para desacoplar código

---

**Conclusión**: Hilt está implementado correctamente en tu proyecto. No es necesario cambiarlo, y el uso parcial (algunos ViewModels con Hilt, otros sin Hilt) es válido y sigue buenas prácticas.

