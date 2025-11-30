# 📹 INSTRUCCIONES PARA MOSTRAR CÓDIGO EN EL VIDEO

Este documento especifica exactamente qué código mostrar en Android Studio durante cada sección del video de presentación.

---

## 🎯 PREPARACIÓN PREVIA

### Archivos a tener abiertos en Android Studio (pestañas preparadas):

1. **AppDatabase.kt** - `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`
2. **UserEntity.kt** - `app/src/main/java/com/example/milsaborestest/data/local/database/UserEntity.kt`
3. **UserDao.kt** - `app/src/main/java/com/example/milsaborestest/data/local/database/UserDao.kt`
4. **ProductViewModel.kt** - `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/ProductViewModel.kt`
5. **ProductCard.kt** - `app/src/main/java/com/example/milsaborestest/presentation/ui/components/ProductCard.kt`
6. **HomeScreen.kt** - `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/home/HomeScreen.kt`
7. **LoginScreen.kt** - `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/login/LoginScreen.kt`
8. **Screen.kt** - `app/src/main/java/com/example/milsaborestest/presentation/navigation/Screen.kt`
9. **AppNavigation.kt** - `app/src/main/java/com/example/milsaborestest/presentation/navigation/AppNavigation.kt`

---

## 📋 SECCIÓN POR SECCIÓN

### 🔵 SECCIÓN 1: CONTEXTO (Nicole - 3-4 minutos)

**No se muestra código en esta sección**, solo:
- Slides con información del equipo
- Diagrama del proceso tradicional (Mermaid)
- Capturas de Trello y GitHub

---

### 🟢 SECCIÓN 2: DEMOSTRACIÓN (Nicole - 5-6 minutos)

**No se muestra código en esta sección**, solo:
- Emulador/dispositivo con la app funcionando
- Navegación entre pantallas
- Funcionalidades en acción

---

### 🟡 SECCIÓN 3: ARQUITECTURA MVVM (Nicolás - 7:00-8:30)

#### 3.1. Explicación de la Arquitectura MVVM

**Mostrar:** Diagrama de arquitectura MVVM (slide o dibujo)

#### 3.2. Capa Model - Entities Room

**Archivo:** `app/src/main/java/com/example/milsaborestest/data/local/database/UserEntity.kt`

**Líneas a destacar:** **1-15** (todo el archivo)

**Qué decir mientras muestras:**
- "En la capa Model, tenemos las Entities de Room Database"
- "UserEntity es un Data Class con anotación @Entity"
- "Room utiliza estas anotaciones para crear las tablas en SQLite"
- "Cada campo se mapea automáticamente a una columna en la base de datos"

**Zoom en:**
- Línea 6: `@Entity(tableName = "usuario")`
- Línea 7-14: La estructura del Data Class
- Línea 8-9: `@PrimaryKey(autoGenerate = true)` - explicar auto-generación de IDs

---

#### 3.3. Capa View - Composable Functions

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/home/HomeScreen.kt`

**Líneas a destacar:** **50-65** (función HomeScreen y uso de ViewModel)

**Qué decir mientras muestras:**
- "En la capa View, tenemos funciones Composable de Jetpack Compose"
- "HomeScreen es un Composable que observa el estado del ViewModel"
- "Usamos `collectAsState()` para observar los StateFlows reactivamente"
- "Cuando el estado cambia, Compose automáticamente recompone la UI"

**Zoom en:**
- Línea 52: `val productViewModel: ProductViewModel = viewModel()`
- Línea 54-55: `collectAsState()` - explicar observación reactiva
- Línea 51: `@Composable fun HomeScreen` - función Composable

**Mostrar también:** Líneas 65-69 (LazyColumn) para mostrar el uso de Material 3

---

#### 3.4. Capa ViewModel - AndroidViewModel con StateFlow

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/ProductViewModel.kt`

**Líneas a destacar:** **23-50** (clase y StateFlows)

**Qué decir mientras muestras:**
- "En la capa ViewModel, utilizamos AndroidViewModel con StateFlow"
- "ProductViewModel extiende AndroidViewModel para tener acceso al contexto"
- "Los StateFlows exponen el estado de manera reactiva"
- "Las vistas observan estos StateFlows y se actualizan automáticamente"

**Zoom en:**
- Línea 23: `class ProductViewModel(application: Application) : AndroidViewModel(application)`
- Líneas 35-36: `MutableStateFlow` y `asStateFlow()` - explicar patrón
- Líneas 25-28: Inicialización de repositorio y casos de uso

**Mostrar también:** Líneas 133-141 (función loadCategories con corrutinas)

**Qué decir:**
- "Utilizamos `viewModelScope.launch` para operaciones asíncronas"
- "Las corrutinas nos permiten realizar operaciones de base de datos sin bloquear el hilo principal"
- "Cuando la operación termina, actualizamos el StateFlow, lo que automáticamente actualiza la UI"

---

### 🟡 SECCIÓN 4: COMPONENTES REUTILIZABLES (Nicolás - 8:30-9:30)

#### 4.1. Introducción a Componentes Reutilizables

**Mostrar:** Estructura de carpetas en Android Studio
- `app/src/main/java/com/example/milsaborestest/presentation/ui/components/`

**Listar archivos visibles:**
- ProductCard.kt
- CategoryCard.kt
- ProductCarousel.kt
- SkeletonComponents.kt
- (y otros componentes)

---

#### 4.2. ProductCard - Componente Reutilizable

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/ui/components/ProductCard.kt`

**Líneas a destacar:** **31-145** (función ProductCard completa)

**Qué decir mientras muestras:**
- "ProductCard es un componente reutilizable que se usa en múltiples pantallas"
- "Recibe parámetros como el producto y callbacks para eventos"
- "Es una función Composable independiente que puede ser reutilizada"

**Zoom en:**
- Líneas 31-37: Firma de la función con parámetros
- Líneas 53-60: Uso de Card de Material 3
- Líneas 65-80: AsyncImage con Coil para carga de imágenes
- Líneas 124-141: Animación del botón de agregar al carrito

**Mostrar también:** Líneas 147-208 (AnimatedAddToCartButton) para mostrar animaciones

**Qué decir:**
- "Implementamos animaciones con `animateFloatAsState` y `spring`"
- "El botón cambia de icono cuando se agrega al carrito con una animación suave"
- "Esto mejora la experiencia del usuario con feedback visual"

---

#### 4.3. Uso de ProductCard en Diferentes Pantallas

**Archivo 1:** `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/home/HomeScreen.kt`

**Buscar:** Uso de `ProductCard` (aproximadamente línea 200-250)

**Archivo 2:** `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/products/AllProductsScreen.kt`

**Buscar:** Uso de `ProductCard` en esa pantalla

**Qué decir:**
- "ProductCard se usa tanto en HomeScreen como en AllProductsScreen"
- "El mismo componente se reutiliza con diferentes datos"
- "Esto reduce duplicación de código y facilita el mantenimiento"

---

### 🟡 SECCIÓN 5: MATERIAL DESIGN 3 (Nicolás - 9:30-10:30)

#### 5.1. Componentes Material 3 en la App

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/login/LoginScreen.kt`

**Líneas a destacar:** **100-150** (aproximadamente, donde están los TextField)

**Qué decir mientras muestras:**
- "La aplicación implementa Material Design 3"
- "Usamos TextField para inputs, Button para acciones, Card para productos"
- "LazyColumn y LazyRow para listas eficientes"

**Zoom en:**
- TextField de Material 3 con iconos
- Button con colores del tema
- Validación con `isError` y `supportingText`

**Mostrar también:** Líneas 54-62 (animación de shake)

**Qué decir:**
- "Implementamos validación con animaciones"
- "Cuando el usuario ingresa datos incorrectos, el TextField muestra un error con animación"
- "La animación de shake proporciona feedback visual claro"

---

### 🟡 SECCIÓN 6: NAVEGATION COMPOSE (Nicolás - 10:30-11:30)

#### 6.1. Definición de Rutas

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/navigation/Screen.kt`

**Líneas a destacar:** **1-23** (todo el archivo)

**Qué decir mientras muestras:**
- "Definimos nuestras rutas en un objeto Screen"
- "Cada pantalla tiene una ruta única"
- "ProductDetail tiene una ruta con parámetro para el ID del producto"

**Zoom en:**
- Líneas 6-8: `sealed class Screen`
- Líneas 9-11: `object Products` con `createRoute()` - explicar parámetros opcionales
- Líneas 16-18: `object ProductDetail` con `createRoute(productId: String)` - explicar argumentos

---

#### 6.2. Configuración del NavHost

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/navigation/AppNavigation.kt`

**Líneas a destacar:** **21-106** (todo el archivo)

**Qué decir mientras muestras:**
- "Configuramos el NavHost con todas las pantallas de la aplicación"
- "Navigation Compose nos permite pasar argumentos entre pantallas de manera type-safe"
- "El back stack se gestiona automáticamente"

**Zoom en:**
- Líneas 26-29: `NavHost` con `startDestination`
- Líneas 30-35: `composable(Screen.Splash.route)` - rutas simples
- Líneas 48-62: `composable` con argumentos - explicar `navArgument` y `NavType`
- Líneas 77-90: `composable` con argumento requerido para ProductDetail

**Qué decir:**
- "Cuando navegamos desde HomeScreen a ProductDetailScreen, pasamos el ID del producto como argumento"
- "Navigation Compose valida los tipos en tiempo de compilación"
- "Esto previene errores de navegación"

---

### 🔴 SECCIÓN 7: ROOM DATABASE (Matías - 11:30-13:00)

#### 7.1. AppDatabase - Configuración de Room

**Archivo:** `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`

**Líneas a destacar:** **11-26** (definición de la base de datos)

**Qué decir mientras muestras:**
- "Para la persistencia de datos, utilizamos Room Database"
- "Room es una capa de abstracción sobre SQLite"
- "Nos permite trabajar con objetos Kotlin en lugar de escribir SQL directamente"

**Zoom en:**
- Líneas 11-20: `@Database` con `entities`, `version`, `exportSchema`
- Líneas 21-25: Métodos abstractos para DAOs
- Explicar que Room genera el código SQL automáticamente

---

#### 7.2. Entidades - UserEntity y ProductEntity

**Archivo 1:** `app/src/main/java/com/example/milsaborestest/data/local/database/UserEntity.kt`

**Líneas a destacar:** **1-15** (todo el archivo)

**Archivo 2:** `app/src/main/java/com/example/milsaborestest/data/local/database/ProductEntity.kt`

**Abrir y mostrar:** Estructura similar a UserEntity

**Qué decir:**
- "Definimos nuestras entidades como Data Classes con anotaciones @Entity"
- "UserEntity representa la tabla de usuarios"
- "ProductEntity representa la tabla de productos"
- "Cada entidad se mapea a una tabla en SQLite"

---

#### 7.3. DAO - Data Access Object

**Archivo:** `app/src/main/java/com/example/milsaborestest/data/local/database/UserDao.kt`

**Líneas a destacar:** **1-32** (todo el archivo)

**Qué decir mientras muestras:**
- "Para cada entidad, creamos un DAO que define las operaciones de base de datos"
- "Los DAOs utilizan funciones suspend para operaciones asíncronas"
- "Room genera el código SQL automáticamente basado en las anotaciones"

**Zoom en:**
- Línea 9: `@Dao interface UserDao`
- Líneas 11-12: `@Insert suspend fun insertar` - explicar suspend
- Líneas 20-21: `@Query("SELECT * FROM usuario")` - queries SQL
- Líneas 26-27: Query con parámetros para login

**Qué decir:**
- "Las funciones suspend se ejecutan en corrutinas"
- "Esto permite operaciones asíncronas sin bloquear el hilo principal"
- "Las queries SQL se escriben directamente, pero Room valida la sintaxis"

---

#### 7.4. Inicialización con Datos por Defecto

**Archivo:** `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`

**Líneas a destacar:** **48-260** (función insertarDatosPorDefecto)

**Qué decir mientras muestras:**
- "La base de datos se inicializa con datos por defecto"
- "Usuarios de prueba, categorías y productos se cargan automáticamente"
- "Esto se hace cuando la aplicación se ejecuta por primera vez"

**Zoom en:**
- Líneas 48-75: Inserción de usuarios por defecto
- Líneas 77-91: Inserción de categorías
- Líneas 93-257: Inserción de productos (mostrar algunos ejemplos)

**Qué decir:**
- "Verificamos si ya existen datos para no duplicar"
- "Los datos se insertan en una corrutina con Dispatchers.IO"
- "Esto asegura que la inicialización no bloquee el hilo principal"

---

### 🔴 SECCIÓN 8: VIEWMODEL Y CORRUTINAS (Matías - 13:00-14:00)

#### 8.1. ViewModel con Corrutinas

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/ProductViewModel.kt`

**Líneas a destacar:** **133-141** (loadCategories con corrutinas)

**Qué decir mientras muestras:**
- "Los ViewModels utilizan corrutinas para realizar operaciones asíncronas"
- "viewModelScope.launch crea una corrutina que se cancela automáticamente cuando el ViewModel se destruye"
- "Utilizamos Dispatchers.IO para operaciones de base de datos"

**Zoom en:**
- Línea 134: `viewModelScope.launch {`
- Líneas 136-137: Llamada a caso de uso (operación suspend)
- Línea 137: Actualización de StateFlow
- Líneas 138-140: Manejo de errores con try-catch

**Mostrar también:** Líneas 183-220 (loadAllProducts) para mostrar operación más compleja

**Qué decir:**
- "Cuando el usuario busca productos, lanzamos una corrutina"
- "La corrutina consulta la base de datos y actualiza el StateFlow"
- "Esto automáticamente actualiza la UI de manera reactiva"

---

#### 8.2. StateFlow y Observación Reactiva

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/ProductViewModel.kt`

**Líneas a destacar:** **35-36, 75-125** (StateFlow y combine)

**Qué decir mientras muestras:**
- "StateFlow nos permite tener un estado observable"
- "Las vistas observan estos StateFlows con collectAsState()"
- "Cuando el estado cambia, Compose automáticamente recompone las partes de la UI que dependen de ese estado"

**Zoom en:**
- Líneas 35-36: `MutableStateFlow` y `asStateFlow()`
- Líneas 75-125: `combine` para combinar múltiples StateFlows
- Explicar cómo `filteredProducts` se actualiza automáticamente cuando cambian los filtros

**Mostrar también:** En HomeScreen.kt, líneas 54-55 para mostrar cómo se observa

---

### 🔴 SECCIÓN 9: RECURSOS NATIVOS (Matías - 14:00-14:30)

#### 9.1. Notificaciones

**Archivo:** `app/src/main/java/com/example/milsaborestest/util/NotificationHelper.kt`

**Abrir y mostrar:** Código de notificaciones

**Qué decir:**
- "Implementamos notificaciones para recordar al usuario sobre su carrito abandonado"
- "Usamos NotificationManager y NotificationChannel de Android"
- "Las notificaciones mejoran la experiencia del usuario"

---

#### 9.2. Photo Picker

**Archivo:** `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/account/AccountScreen.kt`

**Buscar:** Uso de Photo Picker (aproximadamente donde se selecciona la foto)

**Qué decir:**
- "Utilizamos el Photo Picker moderno para seleccionar la foto de perfil"
- "No requiere permisos explícitos de almacenamiento"
- "Es la forma recomendada por Google para seleccionar imágenes"

---

## ✅ CHECKLIST ANTES DE GRABAR

- [ ] Todos los archivos mencionados están abiertos en Android Studio
- [ ] El proyecto compila sin errores
- [ ] La app funciona correctamente en el emulador
- [ ] Los números de línea son correctos (verificar antes de grabar)
- [ ] El código está formateado correctamente
- [ ] Android Studio tiene el tema claro para mejor visibilidad en video
- [ ] El zoom está configurado para que el código sea legible
- [ ] Se practicó la navegación entre archivos

---

## 💡 TIPS PARA LA GRABACIÓN

1. **Navegación rápida:** Usa `Ctrl+Shift+N` (Windows) o `Cmd+Shift+O` (Mac) para buscar archivos rápidamente
2. **Zoom:** Ajusta el tamaño de fuente antes de grabar (Settings > Editor > Font)
3. **Resaltado:** Usa el mouse para resaltar líneas específicas mientras hablas
4. **Transiciones:** Practica cambiar entre archivos rápidamente
5. **Código limpio:** Asegúrate de que el código esté bien formateado (Ctrl+Alt+L / Cmd+Option+L)

---

## 📝 NOTAS ADICIONALES

- Si algún número de línea cambia, actualiza este documento antes de grabar
- Practica cada sección antes de grabar para fluidez
- Mantén el código visible y legible en todo momento
- Explica el código mientras lo muestras, no solo lo leas

