# 📚 Guía de Estudio Técnica - Mil Sabores

## 🎯 Índice

1. [Introducción al Proyecto](#introducción-al-proyecto)
2. [Arquitectura y Patrones](#arquitectura-y-patrones)
3. [Stack Tecnológico](#stack-tecnológico)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Componentes Principales](#componentes-principales)
6. [Base de Datos con Room](#base-de-datos-con-room)
7. [Gestión de Estado con StateFlow](#gestión-de-estado-con-stateflow)
8. [Navegación con Navigation Compose](#navegación-con-navigation-compose)
9. [UI con Jetpack Compose](#ui-con-jetpack-compose)
10. [Conceptos Técnicos Avanzados](#conceptos-técnicos-avanzados)
11. [Flujos de Datos](#flujos-de-datos)
12. [Recursos Nativos Android](#recursos-nativos-android)
13. [Ejercicios Prácticos](#ejercicios-prácticos)

---

## 📖 Introducción al Proyecto

### Descripción General

**Mil Sabores** es una aplicación móvil Android de e-commerce para una pastelería, desarrollada con **Jetpack Compose** y **Kotlin**. La aplicación permite a los usuarios explorar productos, gestionar un carrito de compras y autenticarse.

### Características Principales

- ✅ **Catálogo de productos** con categorías y búsqueda
- ✅ **Carrito de compras** persistente por usuario
- ✅ **Autenticación** de usuarios con Room Database
- ✅ **Navegación** con Bottom Navigation y Drawer
- ✅ **Notificaciones** para carrito abandonado
- ✅ **Galería** para foto de perfil (Photo Picker)
- ✅ **Material Design 3** completo

### Requisitos del Sistema

- **Android Studio**: Hedgehog | 2023.1.1 o superior
- **JDK**: 11 o superior
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Kotlin**: 2.0.21

---

## 🏗️ Arquitectura y Patrones

### Arquitectura MVVM (Model-View-ViewModel)

El proyecto sigue la arquitectura **MVVM** con separación clara de responsabilidades:

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Screens    │  │  Components  │  │  ViewModels  │  │
│  │  (Compose)   │  │  (Compose)   │  │  (StateFlow) │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│                      DOMAIN LAYER                        │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │    Models    │  │  Use Cases   │  │ Repositories │  │
│  │  (Business)  │  │  (Logic)     │  │ (Interfaces) │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│                       DATA LAYER                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │  Entities    │  │   DAOs       │  │ Repositories │  │
│  │  (Room)      │  │  (Room)      │  │ (Impl)       │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### Capas de la Arquitectura

#### 1. **Presentation Layer** (`presentation/`)
- **Screens**: Pantallas principales de la aplicación
- **Components**: Componentes reutilizables de UI
- **ViewModels**: Gestión de estado y lógica de presentación
- **Navigation**: Configuración de navegación

#### 2. **Domain Layer** (`domain/`)
- **Models**: Modelos de negocio (Product, User, CartItem, Category)
- **Use Cases**: Lógica de negocio reutilizable
- **Repository Interfaces**: Contratos para repositorios

#### 3. **Data Layer** (`data/`)
- **Entities**: Entidades de Room Database
- **DAOs**: Data Access Objects para Room
- **Repositories**: Implementaciones concretas de repositorios
- **Mappers**: Conversión entre Entity ↔ Domain

### Patrones de Diseño Utilizados

1. **Repository Pattern**: Abstracción de fuentes de datos
2. **Observer Pattern**: StateFlow para observación reactiva
3. **Singleton Pattern**: AppDatabase como instancia única
4. **Factory Pattern**: ViewModelProvider para crear ViewModels
5. **Mapper Pattern**: Conversión entre capas (Entity ↔ Domain)

---

## 🛠️ Stack Tecnológico

### Lenguajes y Frameworks

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Kotlin** | 2.0.21 | Lenguaje principal |
| **Jetpack Compose** | BOM 2024.09.00 | Framework de UI declarativa |
| **Material 3** | Latest | Sistema de diseño |
| **Room Database** | 2.6.1 | Persistencia local |
| **Navigation Compose** | 2.8.4 | Navegación entre pantallas |
| **Coroutines** | 1.8.0 | Programación asíncrona |
| **Coil** | 2.5.0 | Carga de imágenes |
| **DataStore** | 1.1.1 | Almacenamiento de preferencias |

### Dependencias Principales

```kotlin
// Core Android
implementation("androidx.core:core-ktx:1.17.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
implementation("androidx.activity:activity-compose:1.11.0")

// Compose
implementation(platform("androidx.compose:compose-bom:2024.09.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.material:material-icons-extended")

// Navigation
implementation("androidx.navigation:navigation-compose:2.8.4")

// ViewModel & Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

// Room
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")

// Image Loading
implementation("io.coil-kt:coil-compose:2.5.0")

// DataStore
implementation("androidx.datastore:datastore-preferences:1.1.1")
```

---

## 📁 Estructura del Proyecto

### Organización de Carpetas

```
app/src/main/java/com/example/milsaborestest/
│
├── data/                          # Capa de Datos
│   ├── local/
│   │   └── database/              # Room Database
│   │       ├── AppDatabase.kt     # Configuración de BD
│   │       ├── *Entity.kt         # Entidades (User, Product, Cart, Category)
│   │       └── *Dao.kt            # Data Access Objects
│   ├── mapper/                    # Mappers Entity ↔ Domain
│   │   ├── ProductMapper.kt
│   │   ├── CategoryMapper.kt
│   │   └── CartMapper.kt
│   └── repository/                # Implementaciones de repositorios
│       ├── ProductRepositoryImpl.kt
│       └── CartRepositoryImpl.kt
│
├── domain/                        # Capa de Dominio
│   ├── model/                     # Modelos de negocio
│   │   ├── Product.kt
│   │   ├── User.kt
│   │   ├── CartItem.kt
│   │   └── Category.kt
│   ├── repository/                # Interfaces de repositorios
│   │   ├── ProductRepository.kt
│   │   └── CartRepository.kt
│   └── usecase/                   # Casos de uso
│       ├── GetAllProductsUseCase.kt
│       ├── GetProductByIdUseCase.kt
│       └── GetCategoriesUseCase.kt
│
├── presentation/                  # Capa de Presentación
│   ├── navigation/
│   │   ├── AppNavigation.kt      # NavGraph principal
│   │   └── Screen.kt              # Definición de rutas
│   ├── ui/
│   │   ├── screens/               # Pantallas principales
│   │   │   ├── home/HomeScreen.kt
│   │   │   ├── products/AllProductsScreen.kt
│   │   │   ├── cart/CartScreen.kt
│   │   │   ├── account/AccountScreen.kt
│   │   │   ├── login/LoginScreen.kt
│   │   │   └── splash/SplashScreen.kt
│   │   └── components/            # Componentes reutilizables
│   │       ├── ProductCard.kt
│   │       ├── CategoryCard.kt
│   │       ├── BottomNavBar.kt
│   │       └── ProductCarousel.kt
│   └── viewmodel/                 # ViewModels
│       ├── ProductViewModel.kt
│       ├── CartViewModel.kt
│       └── AuthViewModel.kt
│
├── util/                          # Utilidades
│   ├── Constants.kt
│   ├── Extensions.kt
│   ├── UiState.kt
│   ├── NotificationHelper.kt
│   └── ImageHelper.kt
│
├── ui/theme/                      # Tema y estilos
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
│
└── MainActivity.kt                # Actividad principal
```

---

## 🧩 Componentes Principales

### 1. MainActivity

**Ubicación**: `MainActivity.kt`

**Responsabilidades**:
- Punto de entrada de la aplicación
- Configuración de permisos (notificaciones)
- Inicialización de ViewModels
- Configuración de Edge-to-Edge
- Manejo de navegación desde notificaciones

**Conceptos Clave**:
```kotlin
// Edge-to-Edge: Extender UI hasta los bordes de la pantalla
enableEdgeToEdge(
    statusBarStyle = SystemBarStyle.light(...),
    navigationBarStyle = SystemBarStyle.light(...)
)

// ViewModelProvider: Crear instancias de ViewModels
val factory = AndroidViewModelFactory.getInstance(application)
cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

// ActivityResultLauncher: Solicitar permisos en tiempo de ejecución
private val requestPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestPermission()
) { isGranted -> ... }
```

### 2. AppDatabase

**Ubicación**: `data/local/database/AppDatabase.kt`

**Responsabilidades**:
- Configuración de Room Database
- Definición de entidades y DAOs
- Inicialización de datos por defecto
- Patrón Singleton para instancia única

**Conceptos Clave**:
```kotlin
@Database(
    entities = [CartEntity::class, UserEntity::class, ...],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    // ...
    
    companion object {
        // Singleton pattern
        private var database: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            if (database == null) {
                database = Room.databaseBuilder(...)
                    .fallbackToDestructiveMigration()
                    .build()
                // Insertar datos por defecto
                CoroutineScope(Dispatchers.IO).launch {
                    insertarDatosPorDefecto(database!!, context)
                }
            }
            return database!!
        }
    }
}
```

**Datos por Defecto**:
- 3 usuarios de prueba
- 8 categorías
- 15+ productos

### 3. ViewModels

#### AuthViewModel

**Responsabilidades**:
- Autenticación de usuarios (login/register)
- Gestión de sesión
- Actualización de foto de perfil
- Validación de formularios

**StateFlows**:
```kotlin
private val _user = MutableStateFlow<User?>(null)
val user: StateFlow<User?> = _user.asStateFlow()

private val _isAuthenticated = MutableStateFlow(false)
val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

private val _message = MutableStateFlow<String?>(null)
val message: StateFlow<String?> = _message.asStateFlow()
```

**Funciones Principales**:
- `login(email, password)`: Autenticar usuario
- `register(nombre, email, password)`: Registrar nuevo usuario
- `logout()`: Cerrar sesión y limpiar carrito
- `updateProfilePhoto(imagePath)`: Actualizar foto de perfil

#### CartViewModel

**Responsabilidades**:
- Gestión del carrito de compras
- Sincronización con usuario actual
- Cálculo de totales
- Operaciones CRUD del carrito

**StateFlows Reactivos**:
```kotlin
// StateFlow que reacciona a cambios de userId
val cartItems: StateFlow<List<CartItem>> = _currentUserId.flatMapLatest { userId ->
    if (userId != null) {
        cartRepository.getAllCartItems(userId)
    } else {
        flowOf(emptyList())
    }
}.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = emptyList()
)
```

**Funciones Principales**:
- `addToCart(productId, quantity)`: Agregar producto
- `updateQuantity(productId, newQuantity)`: Actualizar cantidad
- `removeFromCart(productId)`: Eliminar producto
- `clearCart()`: Vaciar carrito
- `setUserId(userId)`: Sincronizar con usuario actual

**Concepto Clave - flatMapLatest**:
- Cuando `_currentUserId` cambia, se cancela el Flow anterior y se crea uno nuevo
- Permite reaccionar automáticamente a cambios de usuario

#### ProductViewModel

**Responsabilidades**:
- Gestión de productos y categorías
- Filtrado y búsqueda
- Estados de carga (Loading, Success, Error)

**UiState Pattern**:
```kotlin
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

**StateFlows**:
```kotlin
private val _categoriesState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
val categoriesState: StateFlow<UiState<List<Category>>> = _categoriesState.asStateFlow()

private val _featuredProductsState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
val featuredProductsState: StateFlow<UiState<List<Product>>> = _featuredProductsState.asStateFlow()
```

### 4. Repositorios

#### ProductRepositoryImpl

**Patrón Repository**: Abstrae la fuente de datos (Room Database)

```kotlin
class ProductRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao
) : ProductRepository {
    
    override suspend fun getCategories(): List<Category> {
        val categoryEntities = categoryDao.obtenerTodasSuspend()
        return categoryEntities.map { categoryEntity ->
            val productos = getProductsByCategory(categoryEntity.id)
            Category(
                id = categoryEntity.id,
                nombre = categoryEntity.nombre,
                icono = categoryEntity.icono,
                productos = productos
            )
        }
    }
    
    override suspend fun getProductById(productId: String): Product? {
        val productEntity = productDao.obtenerPorId(productId)
        return productEntity?.toDomain()
    }
}
```

**Mappers**: Conversión Entity → Domain
```kotlin
// ProductMapper.kt
fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        nombre = this.nombre,
        precio = this.precio,
        // ...
    )
}
```

---

## 💾 Base de Datos con Room

### Entidades

#### UserEntity
```kotlin
@Entity(tableName = "usuario")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val contrasena: String,
    val fotoPerfil: String? = null
)
```

#### ProductEntity
```kotlin
@Entity(
    tableName = "producto",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val precio: Int,
    val imagen: String,
    val descripcion: String,
    val descripcionDetallada: String,
    val rating: Double,
    val reviews: Int,
    val porciones: String,
    val calorias: String,
    val ingredientes: String,
    val categoryId: String  // Foreign Key
)
```

#### CartEntity
```kotlin
@Entity(
    tableName = "carrito",
    primaryKeys = ["productId", "userId"]
)
data class CartEntity(
    val productId: String,
    val userId: Int,  // Foreign Key a UserEntity
    val nombre: String,
    val precio: Int,
    val cantidad: Int,
    val imagen: String,
    val descripcion: String
)
```

### DAOs (Data Access Objects)

#### ProductDao
```kotlin
@Dao
interface ProductDao {
    @Query("SELECT * FROM producto")
    suspend fun obtenerTodosSuspend(): List<ProductEntity>
    
    @Query("SELECT * FROM producto WHERE categoryId = :categoryId")
    suspend fun obtenerPorCategoriaSuspend(categoryId: String): List<ProductEntity>
    
    @Query("SELECT * FROM producto WHERE id = :id")
    suspend fun obtenerPorId(id: String): ProductEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: ProductEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(productos: List<ProductEntity>)
}
```

**Conceptos Clave**:
- `suspend fun`: Funciones de suspensión para operaciones asíncronas
- `@Query`: Consultas SQL personalizadas
- `@Insert`: Inserción con estrategia de conflicto
- `OnConflictStrategy.REPLACE`: Reemplazar si existe

#### CartDao
```kotlin
@Dao
interface CartDao {
    @Query("SELECT * FROM carrito WHERE userId = :userId")
    fun getAllCartItems(userId: Int): Flow<List<CartEntity>>
    
    @Query("SELECT COUNT(*) FROM carrito WHERE userId = :userId")
    fun getCartItemCount(userId: Int): Flow<Int>
    
    @Query("SELECT SUM(precio * cantidad) FROM carrito WHERE userId = :userId")
    fun getCartTotalPrice(userId: Int): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartEntity)
    
    @Update
    suspend fun update(cartItem: CartEntity)
    
    @Query("DELETE FROM carrito WHERE productId = :productId AND userId = :userId")
    suspend fun delete(productId: String, userId: Int)
    
    @Query("DELETE FROM carrito WHERE userId = :userId")
    suspend fun clearCart(userId: Int)
}
```

**Flow vs Suspend**:
- `Flow<List<T>>`: Emite valores reactivamente cuando cambian los datos
- `suspend fun`: Operación única que se ejecuta una vez

### Migraciones

```kotlin
.fallbackToDestructiveMigration()
```

**Nota**: En producción, usar migraciones explícitas:
```kotlin
.addMigrations(
    Migration(1, 2) { database ->
        // Código de migración
    }
)
```

---

## 🔄 Gestión de Estado con StateFlow

### StateFlow vs LiveData

**StateFlow** (usado en este proyecto):
- ✅ Type-safe
- ✅ Integración con Coroutines
- ✅ Emite valor inicial
- ✅ Operadores de Flow (map, filter, combine, etc.)

**LiveData** (alternativa):
- ✅ Lifecycle-aware automático
- ❌ Menos operadores disponibles

### Patrón StateFlow en ViewModels

```kotlin
class MyViewModel : AndroidViewModel(application) {
    // MutableStateFlow: Privado, solo modificable desde ViewModel
    private val _state = MutableStateFlow(initialValue)
    
    // StateFlow: Público, solo lectura desde UI
    val state: StateFlow<Type> = _state.asStateFlow()
    
    fun updateState(newValue: Type) {
        _state.value = newValue  // Actualizar valor
    }
}
```

### Consumo en Compose

```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel = viewModel()) {
    // collectAsState: Convierte StateFlow a State de Compose
    val state by viewModel.state.collectAsState()
    
    // La UI se recompondrá automáticamente cuando state cambie
    Text(text = state.toString())
}
```

### Operadores Avanzados

#### flatMapLatest
```kotlin
val cartItems: StateFlow<List<CartItem>> = _currentUserId.flatMapLatest { userId ->
    if (userId != null) {
        cartRepository.getAllCartItems(userId)  // Flow<List<CartItem>>
    } else {
        flowOf(emptyList())
    }
}.stateIn(...)
```

**Comportamiento**: Cuando `_currentUserId` cambia, cancela el Flow anterior y crea uno nuevo.

#### combine
```kotlin
val filteredProducts = combine(
    allProducts,
    searchTerm,
    selectedCategory
) { products, search, category ->
    products.filter { product ->
        (search.isEmpty() || product.nombre.contains(search, ignoreCase = true)) &&
        (category == null || product.categoryId == category)
    }
}.stateIn(...)
```

**Comportamiento**: Combina múltiples Flows y emite cuando cualquiera cambia.

#### stateIn
```kotlin
val state = someFlow.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = defaultValue
)
```

**Parámetros**:
- `scope`: Scope de coroutine para mantener el Flow activo
- `started`: Estrategia de inicio (WhileSubscribed, Lazily, Eagerly)
- `initialValue`: Valor inicial mientras no hay suscriptores

---

## 🧭 Navegación con Navigation Compose

### Configuración de Navegación

**AppNavigation.kt**:
```kotlin
@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController, authViewModel = authViewModel)
        }
        
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, cartViewModel = cartViewModel)
        }
        
        composable(
            route = "products?categoryId={categoryId}",
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            AllProductsScreen(
                navController = navController,
                initialCategoryId = categoryId,
                cartViewModel = cartViewModel
            )
        }
    }
}
```

### Definición de Rutas

**Screen.kt**:
```kotlin
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Products : Screen("products")
    object Cart : Screen("cart")
    object Account : Screen("account")
    object Login : Screen("login")
    object Register : Screen("register")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
}
```

### Navegación con Argumentos

```kotlin
// Navegar con argumento
navController.navigate(Screen.ProductDetail.createRoute(productId))

// Recibir argumento
composable(
    route = Screen.ProductDetail.route,
    arguments = listOf(
        navArgument("productId") {
            type = NavType.StringType
        }
    )
) { backStackEntry ->
    val productId = backStackEntry.arguments?.getString("productId") ?: ""
    ProductDetailScreen(productId = productId)
}
```

### Navegación con Query Parameters

```kotlin
// Navegar con query parameter
navController.navigate("products?categoryId=$categoryId")

// Recibir query parameter
composable(
    route = "products?categoryId={categoryId}",
    arguments = listOf(
        navArgument("categoryId") {
            type = NavType.StringType
            nullable = false
        }
    )
) { backStackEntry ->
    val categoryId = backStackEntry.arguments?.getString("categoryId")
}
```

### Bottom Navigation Bar

```kotlin
@Composable
fun BottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination to avoid building up
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
```

---

## 🎨 UI con Jetpack Compose

### Conceptos Fundamentales

#### Composable Functions
```kotlin
@Composable
fun MyComponent(text: String) {
    Text(text = text)
}
```

**Características**:
- Función marcada con `@Composable`
- Puede llamar a otras funciones `@Composable`
- Se puede recomponer cuando cambian los parámetros

#### State en Compose
```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    
    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}
```

**remember**: Mantiene el estado durante recomposiciones
**mutableStateOf**: Crea un estado mutable

#### LaunchedEffect
```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(key1 = state) {
        // Se ejecuta cuando 'state' cambia
        // Similar a useEffect en React
        if (state.isSuccess) {
            // Hacer algo
        }
    }
}
```

### Material Design 3

#### Tema Personalizado
```kotlin
private val LightColorScheme = lightColorScheme(
    primary = BrandPink,
    secondary = BrandPinkDark,
    tertiary = Pink40,
    background = Color.White,
    surface = Color.White,
    onSurface = TextDark,
    primaryContainer = BrandPinkLight,
    onPrimaryContainer = BrandPinkDark
)

@Composable
fun MilSaboresTestTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
```

#### Componentes Material 3

**Card**:
```kotlin
Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
) {
    // Contenido
}
```

**Button**:
```kotlin
Button(
    onClick = { /* ... */ },
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary
    )
) {
    Text("Click me")
}
```

**TextField**:
```kotlin
OutlinedTextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Label") },
    leadingIcon = { Icon(Icons.Default.Search, null) }
)
```

### Componentes Reutilizables

#### ProductCard
```kotlin
@Composable
fun ProductCard(
    product: Product,
    onProductClick: (String) -> Unit,
    onAddToCart: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick(product.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = product.imagen,
                contentDescription = product.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.precio.formatPrice(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Button(onClick = { onAddToCart(product.id) }) {
                    Text("Agregar al carrito")
                }
            }
        }
    }
}
```

### Carga de Imágenes con Coil

```kotlin
AsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
        .data(product.imagen)
        .crossfade(true)
        .build(),
    contentDescription = product.nombre,
    modifier = Modifier.size(200.dp),
    contentScale = ContentScale.Crop,
    placeholder = painterResource(R.drawable.producto_default),
    error = painterResource(R.drawable.producto_default)
)
```

**O con el DSL de Coil**:
```kotlin
AsyncImage(
    model = product.imagen,
    contentDescription = product.nombre,
    modifier = Modifier.size(200.dp),
    placeholder = painterResource(R.drawable.producto_default),
    error = painterResource(R.drawable.producto_default)
)
```

### Shimmer Effect (Loading State)

```kotlin
@Composable
fun ShimmerProductCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect()
        )
    }
}

fun Modifier.shimmerEffect(): Modifier {
    return this.then(
        Modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.6f),
                    Color.LightGray.copy(alpha = 0.2f),
                    Color.LightGray.copy(alpha = 0.6f)
                ),
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f)
            )
        )
    )
}
```

---

## 🚀 Conceptos Técnicos Avanzados

### Coroutines y Flows

#### Coroutines
```kotlin
// viewModelScope: Scope automático que se cancela cuando ViewModel se destruye
viewModelScope.launch {
    try {
        val result = repository.getData()  // suspend function
        _state.value = UiState.Success(result)
    } catch (e: Exception) {
        _state.value = UiState.Error(e.message ?: "Error desconocido")
    }
}
```

#### Flows
```kotlin
// Flow: Stream de datos que emite valores a lo largo del tiempo
fun getProducts(): Flow<List<Product>> = flow {
    emit(emptyList())  // Valor inicial
    delay(1000)
    emit(repository.getAllProducts())  // Valor actualizado
}

// Consumir Flow
viewModelScope.launch {
    getProducts().collect { products ->
        _products.value = products
    }
}
```

### Dependency Injection Manual

**Sin Hilt/Dagger**: El proyecto usa inyección manual

```kotlin
// En ViewModel
class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val cartDao = database.cartDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartDao)
}

// En MainContent
@Composable
fun MainContent(...) {
    val cartViewModel: CartViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    
    AppNavigation(
        navController = navController,
        authViewModel = authViewModel,
        cartViewModel = cartViewModel
    )
}
```

### Edge-to-Edge

```kotlin
enableEdgeToEdge(
    statusBarStyle = SystemBarStyle.light(
        scrim = 0xFFD63384.toInt(),  // Color de status bar
        darkScrim = 0xFFD63384.toInt()
    ),
    navigationBarStyle = SystemBarStyle.light(
        scrim = 0xFFFFFFFF.toInt(),  // Color de navigation bar
        darkScrim = 0xFFFFFFFF.toInt()
    )
)
```

**En Compose**:
```kotlin
Surface(
    modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()  // Padding para status bar
        .navigationBarsPadding()  // Padding para navigation bar
) {
    // Contenido
}
```

### Photo Picker (Android 13+)

```kotlin
val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
) { uri ->
    uri?.let {
        // Procesar imagen seleccionada
        val imagePath = saveImageToInternalStorage(it)
        viewModel.updateProfilePhoto(imagePath)
    }
}

Button(onClick = {
    photoPickerLauncher.launch(
        PickVisualMediaRequest(PickVisualMedia.ImageOnly)
    )
}) {
    Text("Seleccionar foto")
}
```

**No requiere permisos** en Android 13+ (API 33+)

---

## 📊 Flujos de Datos

### Flujo de Autenticación

```
1. Usuario ingresa email/password
   ↓
2. LoginScreen llama a authViewModel.login()
   ↓
3. AuthViewModel valida y consulta UserDao
   ↓
4. Si existe, actualiza _user y _isAuthenticated
   ↓
5. MainContent observa isAuthenticated
   ↓
6. Si autenticado, actualiza userId en CartViewModel
   ↓
7. CartViewModel reacciona y carga carrito del usuario
```

### Flujo de Agregar al Carrito

```
1. Usuario hace clic en "Agregar al carrito"
   ↓
2. ProductCard llama a cartViewModel.addToCart(productId)
   ↓
3. CartViewModel obtiene userId actual
   ↓
4. Si userId es null, muestra error
   ↓
5. Si userId existe, obtiene producto de ProductRepository
   ↓
6. Crea CartItem y llama a cartRepository.addToCart()
   ↓
7. CartRepository.insert() en CartDao
   ↓
8. Room emite nuevo valor en Flow
   ↓
9. CartViewModel.cartItems se actualiza automáticamente
   ↓
10. UI se recompone y muestra nuevo item
```

### Flujo de Carga de Productos

```
1. HomeScreen se compone
   ↓
2. Llama a productViewModel.loadFeaturedProducts()
   ↓
3. ProductViewModel consulta ProductRepository
   ↓
4. ProductRepository consulta ProductDao
   ↓
5. ProductDao ejecuta query SQL
   ↓
6. Room retorna List<ProductEntity>
   ↓
7. ProductRepository mapea Entity → Domain (Product)
   ↓
8. ProductViewModel actualiza _featuredProductsState
   ↓
9. HomeScreen observa featuredProductsState
   ↓
10. UI se recompone y muestra productos
```

---

## 📱 Recursos Nativos Android

### Notificaciones

**NotificationHelper.kt**:
```kotlin
object NotificationHelper {
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Carrito Abandonado",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificaciones de recordatorio de carrito"
            }
            
            val notificationManager = context.getSystemService<NotificationManager>()
            notificationManager?.createNotificationChannel(channel)
        }
    }
    
    fun showCartReminderNotification(context: Context, itemCount: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("navigate_to", "cart")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("¡No olvides tu carrito!")
            .setContentText("Tienes $itemCount items esperando")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }
}
```

**Permisos** (Android 13+):
```kotlin
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    when {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED -> {
            // Permiso concedido
        }
        else -> {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
```

### Photo Picker

```kotlin
val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
) { uri ->
    uri?.let {
        // Guardar imagen en almacenamiento interno
        val imagePath = saveImageToInternalStorage(context, it)
        authViewModel.updateProfilePhoto(imagePath)
    }
}

fun saveImageToInternalStorage(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, "profile_${System.currentTimeMillis()}.jpg")
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return file.absolutePath
}
```

---

## 🎯 Ejercicios Prácticos

### Ejercicio 1: Agregar Filtro de Precio

**Objetivo**: Implementar filtro por rango de precios en AllProductsScreen

**Pasos**:
1. Agregar `minPrice` y `maxPrice` a ProductViewModel
2. Crear UI con Slider para seleccionar rango
3. Filtrar productos usando `combine` de Flows
4. Actualizar UI cuando cambie el filtro

**Código de Referencia**:
```kotlin
// En ProductViewModel
private val _minPrice = MutableStateFlow(0)
val minPrice: StateFlow<Int> = _minPrice.asStateFlow()

private val _maxPrice = MutableStateFlow(999999)
val maxPrice: StateFlow<Int> = _maxPrice.asStateFlow()

val filteredProducts = combine(
    allProducts,
    searchTerm,
    selectedCategoryId,
    minPrice,
    maxPrice
) { products, search, category, min, max ->
    products.filter { product ->
        (search.isEmpty() || product.nombre.contains(search, ignoreCase = true)) &&
        (category == null || product.categoryId == category) &&
        (product.precio >= min && product.precio <= max)
    }
}.stateIn(...)
```

### Ejercicio 2: Implementar Favoritos

**Objetivo**: Agregar funcionalidad de favoritos

**Pasos**:
1. Crear `FavoriteEntity` en Room
2. Crear `FavoriteDao` con operaciones CRUD
3. Agregar `FavoriteRepository`
4. Crear `FavoriteViewModel`
5. Agregar botón de favorito en ProductCard
6. Crear pantalla de favoritos

### Ejercicio 3: Mejorar Manejo de Errores

**Objetivo**: Implementar manejo robusto de errores

**Pasos**:
1. Crear `sealed class Result<T>`
2. Actualizar repositorios para retornar `Result<T>`
3. Manejar errores en ViewModels
4. Mostrar mensajes de error en UI
5. Implementar retry logic

**Código de Referencia**:
```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

// En Repository
suspend fun getProductById(id: String): Result<Product> {
    return try {
        val product = productDao.obtenerPorId(id)
        if (product != null) {
            Result.Success(product.toDomain())
        } else {
            Result.Error(NoSuchElementException("Product not found"))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}
```

### Ejercicio 4: Implementar Paginación

**Objetivo**: Cargar productos de forma paginada

**Pasos**:
1. Modificar `ProductDao` para soportar LIMIT/OFFSET
2. Agregar estado de paginación en ViewModel
3. Implementar `LazyColumn` con `onScrollStateChanged`
4. Cargar más productos cuando se llegue al final

### Ejercicio 5: Agregar Tests Unitarios

**Objetivo**: Escribir tests para ViewModels

**Pasos**:
1. Configurar dependencias de testing
2. Crear tests para `AuthViewModel`
3. Crear tests para `CartViewModel`
4. Usar `TestCoroutineDispatcher` para tests de coroutines

**Código de Referencia**:
```kotlin
class CartViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: CartRepository
    
    @Before
    fun setup() {
        cartRepository = mockk()
        viewModel = CartViewModel(Application())
    }
    
    @Test
    fun `addToCart should add product when user is authenticated`() = runTest {
        // Given
        val productId = "TC001"
        val userId = 1
        viewModel.setUserId(userId)
        
        // When
        viewModel.addToCart(productId)
        
        // Then
        verify { cartRepository.addToCart(any(), userId) }
    }
}
```

---

## 📝 Resumen de Conceptos Clave

### 1. Arquitectura MVVM
- Separación de responsabilidades en 3 capas
- ViewModels gestionan estado y lógica de presentación
- Repositorios abstraen fuentes de datos

### 2. StateFlow
- Gestión reactiva de estado
- Integración con Compose mediante `collectAsState()`
- Operadores avanzados: `flatMapLatest`, `combine`, `stateIn`

### 3. Room Database
- Persistencia local con SQLite
- Entidades, DAOs y Database
- Flows para observación reactiva

### 4. Jetpack Compose
- UI declarativa
- Recomposition automática
- Material Design 3

### 5. Navigation Compose
- Navegación type-safe
- Argumentos y query parameters
- Deep linking

### 6. Coroutines
- Programación asíncrona
- `viewModelScope` para lifecycle-aware
- `suspend` functions

### 7. Flows
- Streams de datos reactivos
- Operadores funcionales
- Integración con Room

---

## 🔗 Recursos Adicionales

### Documentación Oficial
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- [Coroutines](https://developer.android.com/kotlin/coroutines)

### Conceptos Avanzados
- **Dependency Injection**: Considerar migrar a Hilt
- **Testing**: Unit tests, UI tests, Integration tests
- **Performance**: Profiling, optimización de recomposición
- **Accessibility**: Contenido descriptivo, navegación por teclado

---

## ✅ Checklist de Aprendizaje

- [ ] Entender arquitectura MVVM y separación de capas
- [ ] Comprender StateFlow y su uso en ViewModels
- [ ] Dominar Room Database (Entities, DAOs, Queries)
- [ ] Aprender Jetpack Compose básico (Composables, State, LaunchedEffect)
- [ ] Entender Navigation Compose (Rutas, Argumentos, Deep Linking)
- [ ] Dominar Coroutines y Flows
- [ ] Aprender Material Design 3
- [ ] Entender Photo Picker y Notificaciones
- [ ] Practicar con ejercicios propuestos
- [ ] Leer y entender el código del proyecto completo

---

**¡Buena suerte con tu aprendizaje! 🚀**

