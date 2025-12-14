# Plan de Implementación: Retrofit API para Mil Sabores

## ✅ Estado Actual de la API

### API Configurada y Funcionando
- ✅ **Plataforma**: MockAPI.io
- ✅ **Proyecto**: milsabores-api
- ✅ **URL Base**: `https://693e248ef55f1be793046cd9.mockapi.io/api/v1`
- ✅ **Endpoints activos**: 
  - `GET /categories` → 8 categorías
  - `GET /products` → 16 productos
  - `GET /products/:id` → Producto por ID
  - `GET /products?categoryId=` → Productos filtrados
- ✅ **Datos importados**: 8 categorías y 16 productos
- ✅ **Documentación completa**: Ver `DOCUMENTACION_API_MOCKAPI.md`

### Archivos de Datos
- `mockapi_categories.json` - Categorías en formato array (listo para importar)
- `mockapi_products.json` - Productos en formato array (listo para importar)

---

## 📋 Análisis Comparativo

### ✅ PokeStore (Modelo a seguir)

**Estructura:**
```
remote/
  ├── RetrofitInstance.kt  (Object singleton con configuración Retrofit)
  └── ApiService.kt         (Interface con endpoints)
```

**Características:**
- ✅ Retrofit 2.9.0
- ✅ Gson Converter 2.9.0
- ✅ OkHttp 4.12.0 (implícito)
- ✅ Uso directo en ViewModels: `RetrofitInstance.api.getPosts()`
- ✅ Patrón simple: Object singleton para RetrofitInstance

**Dependencias:**
```kotlin
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

---

### ❌ MilSaboresTest (Estado Actual)

**Estructura:**
```
data/
  ├── local/
  │   └── database/        (Solo Room Database)
  ├── mapper/
  └── repository/          (Solo acceso local)
```

**Características:**
- ❌ No tiene Retrofit
- ❌ No tiene ApiService
- ❌ No tiene RetrofitInstance
- ❌ Solo usa Room Database local
- ✅ Tiene arquitectura limpia con Repositorios y Use Cases

---

## 🎯 Cambios Necesarios

### 1. **Dependencias (build.gradle.kts)**
- [ ] Agregar Retrofit 2.9.0
- [ ] Agregar Retrofit Gson Converter 2.9.0
- ~~Agregar OkHttp 4.12.0~~ (No necesario, siguiendo patrón PokeStore)
- ~~Agregar OkHttp Logging Interceptor~~ (No necesario, siguiendo patrón PokeStore)
- ~~Agregar Gson 2.10.1~~ (Incluido en converter-gson)

### 2. **Estructura de Carpetas**
- [ ] Crear carpeta `data/remote/`
- [ ] Crear `RetrofitInstance.kt` (object singleton)
- [ ] Crear `ApiService.kt` (interface con endpoints)

### 3. **DTOs (Data Transfer Objects)**
- [ ] Crear DTOs para respuestas de API (si difieren de Entities)
- [ ] Ejemplo: `ProductDto.kt`, `CategoryDto.kt`, `UserDto.kt`

### 4. **Mappers**
- [ ] Crear mappers de DTO a Domain Model
- [ ] Ejemplo: `ProductDto.toDomain()`, `CategoryDto.toDomain()`

### 5. **Remote DataSource (Opcional pero Recomendado)**
- [ ] Crear `ProductRemoteDataSource.kt`
- [ ] Crear `UserRemoteDataSource.kt`
- [ ] Encapsular llamadas a API

### 6. **Actualizar Repositorios**
- [ ] Modificar `ProductRepositoryImpl` para usar API + Room (cache)
- [ ] Implementar patrón: API primero, fallback a Room
- [ ] O: Room primero (cache), actualizar desde API en background

### 7. **Manejo de Errores**
- [ ] Agregar manejo de errores de red
- [ ] Agregar timeouts
- [ ] Agregar retry logic (opcional)

### 8. **Configuración**
- [x] BASE_URL definida: `https://693e248ef55f1be793046cd9.mockapi.io/api/v1/`
- [ ] Configurar RetrofitInstance con la URL base
- ~~Configurar interceptores~~ (No necesario, siguiendo patrón PokeStore)

---

## 📝 Plan de Implementación Detallado

### **Fase 1: Configuración Base** ⏱️ ~30 min

1. **Actualizar `gradle/libs.versions.toml`**
   ```toml
   retrofit = "2.9.0"
   ```

2. **Actualizar `app/build.gradle.kts`**
   ```kotlin
   // Network (siguiendo patrón PokeStore)
   implementation("com.squareup.retrofit2:retrofit:2.9.0")
   implementation("com.squareup.retrofit2:converter-gson:2.9.0")
   ```

3. **Crear estructura de carpetas**
   ```
   data/
     └── remote/
       ├── RetrofitInstance.kt
       └── ApiService.kt
   ```

---

### **Fase 2: Implementación de Retrofit** ⏱️ ~1 hora

1. **Crear `RetrofitInstance.kt`** (Siguiendo patrón PokeStore)
   ```kotlin
   object RetrofitInstance {
       private const val BASE_URL = "https://693e248ef55f1be793046cd9.mockapi.io/api/v1/"
       
       val api: ApiService by lazy {
           Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
               .create(ApiService::class.java)
       }
   }
   ```

2. **Crear `ApiService.kt`**
   ```kotlin
   interface ApiService {
       @GET("categories")
       suspend fun getCategories(): List<CategoryDto>
       
       @GET("products")
       suspend fun getProducts(): List<ProductDto>
       
       @GET("products")
       suspend fun getProductsByCategory(
           @Query("categoryId") categoryId: String
       ): List<ProductDto>
       
       @GET("products/{id}")
       suspend fun getProductById(@Path("id") id: String): ProductDto
   }
   ```

3. **Crear DTOs**
   - `CategoryDto.kt` (coincide con estructura de API)
   - `ProductDto.kt` (coincide con estructura de API)

---

### **Fase 3: Integración con ViewModels** ⏱️ ~1.5 horas

**Siguiendo patrón PokeStore (uso directo en ViewModels):**

1. **Actualizar `ProductViewModel.kt`**
   ```kotlin
   class ProductViewModel : ViewModel() {
       val categories = MutableStateFlow(emptyList<Category>())
       val products = MutableStateFlow(emptyList<Product>())
       
       fun loadCategories() {
           viewModelScope.launch {
               try {
                   val categoriesDto = RetrofitInstance.api.getCategories()
                   categories.value = categoriesDto.map { it.toDomain() }
               } catch (e: Exception) {
                   // Manejo de errores
               }
           }
       }
       
       fun loadProducts() {
           viewModelScope.launch {
               try {
                   val productsDto = RetrofitInstance.api.getProducts()
                   products.value = productsDto.map { it.toDomain() }
               } catch (e: Exception) {
                   // Manejo de errores
               }
           }
       }
   }
   ```

2. **Crear Mappers**
   - `CategoryDto.toDomain()` → `Category`
   - `ProductDto.toDomain()` → `Product`

---

### **Fase 4: Testing y Ajustes** ⏱️ ~1 hora

1. **Probar endpoints**
   - ✅ API ya está funcionando en MockAPI.io
   - Verificar respuestas en navegador/Postman
   - Validar mapeo DTO → Domain

2. **Ajustar ViewModels**
   - Reemplazar llamadas a repositorios por llamadas directas a API
   - Mantener Room solo para datos locales (carrito, usuario)

3. **Manejo de errores**
   - Agregar try-catch en todas las llamadas
   - Mostrar mensajes de error al usuario

---

## 🔄 Estrategia de Migración

### **Enfoque Recomendado: Híbrido (API + Room Cache)**

1. **Ventajas:**
   - ✅ Funciona offline (usa cache de Room)
   - ✅ Datos siempre actualizados desde API
   - ✅ Mejor experiencia de usuario
   - ✅ Fallback automático si API falla

2. **Flujo:**
   ```
   ViewModel → UseCase → Repository
                              ↓
                    ┌─────────┴─────────┐
                    ↓                   ↓
                 API (Retrofit)      Room (Cache)
                    ↓                   ↓
              Guardar en Room    Retornar desde Room
                    ↓
              Retornar a ViewModel
   ```

---

## 📦 Archivos a Crear/Modificar

### **Nuevos Archivos:**
1. `data/remote/RetrofitInstance.kt`
2. `data/remote/ApiService.kt`
3. `data/remote/dto/ProductDto.kt` (si es necesario)
4. `data/remote/dto/CategoryDto.kt` (si es necesario)
5. `data/remote/dto/UserDto.kt` (si es necesario)
6. `data/mapper/ProductDtoMapper.kt` (si se usan DTOs)

### **Archivos a Modificar:**
1. `gradle/libs.versions.toml` - Agregar dependencias
2. `app/build.gradle.kts` - Agregar implementaciones
3. `data/repository/ProductRepositoryImpl.kt` - Integrar API
4. `data/repository/CartRepositoryImpl.kt` - (Si aplica)
5. `MainActivity.kt` o donde se inicialice - Verificar permisos INTERNET

---

## ⚠️ Consideraciones Importantes

1. **Permisos AndroidManifest.xml**
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```

2. **BASE_URL**
   - Usar BuildConfig para diferentes ambientes (dev, prod)
   - O constante en RetrofitInstance

3. **Manejo de Errores**
   - Timeout de red
   - Errores HTTP (404, 500, etc.)
   - Excepciones de red (sin conexión)

4. **Threading**
   - Retrofit ya maneja corrutinas (suspend functions)
   - No necesita Dispatchers.IO explícito

5. **Testing**
   - Mock de ApiService para tests
   - Usar MockWebServer para tests de integración

---

## 🎯 Próximos Pasos

1. ✅ Crear rama `feature/implementar-retrofit-api` (YA HECHO)
2. ✅ Configurar API en MockAPI.io (YA HECHO)
3. ✅ Importar datos (8 categorías, 16 productos) (YA HECHO)
4. ✅ Documentar API (DOCUMENTACION_API_MOCKAPI.md) (YA HECHO)
5. ⏳ Implementar Fase 1 (Configuración Base - Dependencias)
6. ⏳ Implementar Fase 2 (Retrofit - RetrofitInstance y ApiService)
7. ⏳ Implementar Fase 3 (Integración - ViewModels y Mappers)
8. ⏳ Implementar Fase 4 (Testing y Ajustes)
9. ⏳ Merge a `dev`

---

## 📚 Referencias

- **PokeStore:** `E:\DOWNLOADS\PokeStore-API\PokeStore`
- **Retrofit Docs:** https://square.github.io/retrofit/
- **Gson Docs:** https://github.com/google/gson

