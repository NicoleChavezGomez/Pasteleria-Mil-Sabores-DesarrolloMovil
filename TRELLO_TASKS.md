# 📋 Planificación de Tareas - Pastelería Mil Sabores

> **Nota**: Este archivo sirve como referencia para migrar las tareas al Trello oficial del proyecto.  
> **Fecha de creación**: 10-07-2025  
> **Estado**: Referencia para planificación

---

## 📊 Columnas de Trabajo

### 🔵 Backlog
Tareas pendientes por realizar o en espera de priorización.

### 🟡 Doing
Tareas en progreso actualmente.

### 🟠 Code Review
Tareas completadas pendientes de revisión.

### 🟢 Done
Tareas completadas y validadas.

---

## 🟢 Done

### ✅ Arquitectura y Estructura Base
- [x] **Configurar proyecto Android con Compose**
  - Configuración inicial del proyecto
  - Dependencias básicas (Compose, Navigation, Hilt)
  - Estructura de carpetas (data, domain, presentation)

- [x] **Implementar arquitectura MVVM**
  - Separación de capas (data, domain, presentation)
  - ViewModels para gestión de estado
  - Repositorios para acceso a datos

- [x] **Configurar inyección de dependencias (Hilt)**
  - AppModule configurado
  - Inyección en ViewModels y repositorios

### ✅ Base de Datos y Persistencia
- [x] **Implementar Room Database**
  - AppDatabase configurado
  - Entidades: CartEntity, UserEntity
  - DAOs: CartDao, UserDao
  - Migraciones configuradas

- [x] **Sistema de autenticación con Room**
  - UserEntity y UserDao implementados
  - AuthViewModel con acceso directo a base de datos
  - Usuarios por defecto en base de datos

- [x] **Persistencia de carrito de compras**
  - CartEntity y CartDao
  - CartViewModel con operaciones CRUD
  - Sincronización con base de datos

- [x] **Persistencia de foto de perfil de usuario**
  - Campo `fotoPerfil` agregado a UserEntity (nullable String)
  - Campo `fotoPerfil` agregado al modelo de dominio User
  - Migración MIGRATION_2_3 implementada (versión 2 → 3)
  - Conversiones en AuthViewModel actualizadas para incluir fotoPerfil
  - Base de datos lista para almacenar rutas de imágenes de perfil

- [x] **Sistema de persistencia general**
  - Room Database configurado como solución de persistencia local
  - Datos persistentes: Usuarios (UserEntity), Carrito (CartEntity)
  - Migraciones de base de datos implementadas y configuradas
  - Datos no persistentes: Productos (cargados desde JSON en assets)
  - Estado de autenticación: Persistido en base de datos, se mantiene entre sesiones
  - Carrito de compras: Persistido en base de datos, se mantiene entre sesiones

### ✅ Recursos Nativos
- [x] **Sistema de notificaciones - Carrito abandonado**
  - `NotificationHelper.kt` creado e implementado (singleton)
  - Permisos `POST_NOTIFICATIONS` configurados en `AndroidManifest.xml`
  - Canal de notificaciones creado en `MainActivity.onCreate()`
  - Detección de carrito abandonado en `MainActivity.onPause()`
  - Notificación se muestra inmediatamente cuando hay items en el carrito
  - Verificación de permisos antes de mostrar notificaciones
  - Notificación incluye acción para abrir la app
  - Mensaje personalizado según cantidad de items
  - **Mejoras aplicadas**:
    - Notificación con `IMPORTANCE_HIGH` y `PRIORITY_HIGH` para mejor visibilidad
    - `BigTextStyle` implementado para mostrar contenido completo
    - `PendingIntent` corregido para evitar reinicio de app (FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE)
    - `launchMode="singleTop"` agregado a MainActivity para manejar navegación desde notificaciones
    - Lógica de navegación mejorada en `MainContent.kt` para detectar intents de notificaciones

- [x] **Sistema de galería - Foto de perfil de usuario**
  - `ImageHelper.kt` creado e implementado (object singleton) con funciones:
    - `uriToBitmap()` - Convierte URI de galería a Bitmap
    - `saveProfileImage()` - Guarda imagen en storage interno (`filesDir/profile_images/`)
    - `loadProfileImage()` - Lee imagen desde storage
    - `deleteProfileImage()` - Elimina imagen antigua al actualizar
  - Photo Picker implementado con `ActivityResultContracts.PickVisualMedia()`
  - **NO requiere permisos explícitos** - El Photo Picker moderno los maneja automáticamente
  - Imágenes por defecto creadas:
    - `ic_profile_default.xml` - Avatar por defecto para usuarios (vector drawable)
    - `ic_product_default.xml` - Imagen por defecto para productos (vector drawable)
  - `AccountScreen.kt` actualizado:
    - Componente `ProfileImage` reutilizable creado
    - FloatingActionButton para seleccionar foto de galería
    - Lógica condicional completa para cargar/mostrar foto de perfil
    - Manejo de errores completo (muestra imagen por defecto en todos los casos)
  - `MainContent.kt` (NavigationDrawerContent) actualizado:
    - Muestra foto de perfil usando componente `ProfileImage`
    - Avatar de 64.dp en sidebar
  - `AuthViewModel` actualizado:
    - Función `updateProfilePhoto(imagePath: String)` para actualizar foto en BD y StateFlow
  - Migración de base de datos `MIGRATION_2_3` implementada (versión 2 → 3)
  - Campo `fotoPerfil: String?` agregado a `UserEntity` y modelo de dominio `User`
  - **Ventajas del Photo Picker**:
    - No molesta al usuario con solicitudes de permisos
    - Más seguro (solo accede a imagen seleccionada, no a toda la galería)
    - Funciona automáticamente en Android 13+ sin permisos
    - La biblioteca de compatibilidad maneja versiones anteriores

### ✅ Navegación y UI Base
- [x] **Sistema de navegación con Compose Navigation**
  - AppNavigation configurado (renombrado desde NavGraph.kt)
  - Rutas definidas (Home, Products, Cart, Account, Login, Register)
  - Navegación entre pantallas funcional
  - AppNavigation recibe authViewModel compartido y lo pasa a pantallas que lo necesitan

- [x] **Implementar Material 3 Design**
  - Tema personalizado
  - Componentes Material 3
  - Colores y tipografía personalizados

- [x] **Bottom Navigation Bar**
  - Navegación inferior funcional
  - Iconos y labels
  - Estado activo por ruta

- [x] **Navigation Drawer (Sidebar)**
  - Drawer lateral funcional
  - Opciones de navegación
  - Información de usuario cuando está autenticado

- [x] **TopBar con acciones**
  - TopBar en pantallas principales
  - Botón de menú para abrir drawer
  - Botón de carrito con contador

### ✅ Pantallas Principales
- [x] **HomeScreen**
  - Carousel de productos destacados
  - Categorías de productos
  - Grid de productos
  - Navegación a detalles

- [x] **AllProductsScreen**
  - Lista de productos
  - Filtrado por categoría
  - Búsqueda de productos
  - Navegación a detalles

- [x] **ProductDetailScreen**
  - Detalle completo del producto
  - Imágenes del producto
  - Información (precio, descripción, ingredientes)
  - Botón agregar al carrito

- [x] **CartScreen**
  - Lista de productos en carrito
  - Controles de cantidad
  - Cálculo de total
  - Botón de checkout

- [x] **AccountScreen**
  - Información del usuario (si está autenticado)
  - Opciones de menú
  - Botón de login/logout

### ✅ Autenticación
- [x] **LoginScreen**
  - Formulario de login
  - Validaciones de email y contraseña
  - Retroalimentación visual de errores
  - Integración con AuthViewModel

- [x] **RegisterScreen**
  - Formulario de registro
  - Validaciones (nombre, email, contraseña, confirmación)
  - Retroalimentación visual
  - Creación de usuario en base de datos

- [x] **Gestión de sesión**
  - Estado de autenticación en AuthViewModel
  - Logout funcional
  - Cambio dinámico de botones (Login/Logout)
  - Navegación condicional
  - AuthViewModel compartido entre MainContent, AppNavigation y pantallas (LoginScreen, RegisterScreen, AccountScreen)
  - Estado de autenticación consistente en sidebar y pantallas individuales

### ✅ Validaciones y Lógica de Negocio
- [x] **Validaciones centralizadas en ViewModels**
  - Validación de email
  - Validación de contraseña (mínimo 6 caracteres)
  - Validación de campos requeridos
  - Mensajes de error claros

- [x] **Lógica de carrito**
  - Agregar productos
  - Actualizar cantidades
  - Eliminar productos
  - Cálculo de totales

### ✅ Componentes Reutilizables
- [x] **ProductCard**
  - Tarjeta de producto reutilizable
  - Imagen, título, precio, rating
  - Botón de acción

- [x] **CategoryCard**
  - Tarjeta de categoría
  - Icono y nombre

- [x] **ProductCarousel**
  - Carrusel horizontal de productos
  - Auto-scroll
  - Navegación manual

- [x] **Skeleton Components**
  - Shimmer effect para carga
  - Skeleton para ProductCard
  - Skeleton para CategoryCard
  - Skeleton para ProductDetail

### ✅ Animaciones Básicas
- [x] **Shimmer animations**
  - Efecto shimmer para estados de carga
  - Aplicado en skeletons

- [x] **Carousel animations**
  - Animaciones de transición en carousel
  - Auto-scroll suave

### ✅ Animaciones Mejoradas
- [x] **Transiciones entre pantallas**
  - Implementado `AnimatedVisibility` en AllProductsScreen para panel de filtros
  - Implementado `Crossfade` en CategoriesSection para transiciones suaves entre estados
  - Implementado `AnimatedVisibility` en ExpandableSection de HomeScreen
  - Animaciones de expansión/colapso con spring animations
  - Transiciones fade-in/fade-out suaves (300ms)
  - **Archivos modificados**: AllProductsScreen.kt, HomeScreen.kt

- [x] **Animaciones de feedback**
  - ProductCard: Animación de scale al presionar (0.98f) con spring bounce
  - ProductCard: Botón "Agregar al carrito" con animación de scale (0.85f → 1.2f)
  - ProductCard: Icono de check animado con rotación 360° al agregar producto
  - ProductCard: Crossfade entre icono carrito y check de éxito
  - ProductCard: Cambio de color del botón al agregar (primary → tertiary)
  - CategoryCard: Animación de scale al presionar (0.95f)
  - CategoryCard: Ripple effect en interacciones
  - LoginScreen: Animación de shake en formulario cuando hay errores
  - LoginScreen: Efecto shake usando translationX con función seno
  - ExpandableSection: Rotación animada del icono (0° → 180°)
  - **Archivos modificados**: ProductCard.kt, CategoryCard.kt, LoginScreen.kt, HomeScreen.kt

- [x] **Animaciones de carga mejoradas**
  - Transiciones suaves entre estados Loading → Success → Error
  - Crossfade implementado en CategoriesSection (300ms)
  - AnimatedVisibility para mostrar/ocultar filtros
  - Shimmer effect ya existente en SkeletonComponents
  - **Archivos modificados**: HomeScreen.kt, AllProductsScreen.kt

- [x] **Componentes helper de animaciones**
  - Creado AnimationHelpers.kt con componentes reutilizables
  - AnimatedListItem: Animación de entrada escalonada para items de lista
  - animatedHover: Modificador para efectos hover/press
  - rememberPulseAnimation: Animación de pulsación para elementos destacados
  - **Archivo creado**: AnimationHelpers.kt

### ✅ Control de Versiones
- [x] **Repositorio en GitHub**
  - Repositorio configurado
  - Commits con formato `[ TIPO ]: mensaje`
  - Ramas para features (feature/login, feature/basedatos)
  - Merge a main

### ✅ Documentación
- [x] **Crear README.md completo** ⚠️ OBLIGATORIO
  - Archivo creado en raíz del proyecto
  - Descripción del proyecto completa
  - Nombres de estudiantes incluidos
  - Funcionalidades implementadas documentadas
  - Instrucciones de ejecución detalladas
  - Tecnologías utilizadas listadas
  - Estructura del proyecto documentada

### ✅ Mejoras de UI/UX
- [x] **Reorganizar TopNavBar: Mover hamburger menu a la derecha**
  - Menú hamburger movido a la derecha del TopNavBar
  - Posicionado después del carrito en el Row derecho
  - Funcionalidad verificada

- [x] **Aumentar ancho del Sidebar de 50% a 75%**
  - ModalDrawerSheet configurado con `0.75f` del ancho de pantalla
  - Implementado en `MainContent.kt` línea 113

- [x] **Implementar pantalla de Splash con logo de Mil Sabores**
  - Archivo `SplashScreen.kt` creado e implementado
  - Animación de scale con spring animation
  - Navegación automática según estado de autenticación
  - Ruta agregada en `AppNavigation.kt` como `startDestination`
  - Correcciones aplicadas: Animación de Compose, NavHostController

---

## 🟠 Code Review

### 🔍 Pendiente de Revisión
- [ ] **Revisar estructura de commits**
  - **Contexto**: Verificar que todos los commits sigan el formato `[ TIPO ]: mensaje`
  - **Acción**: Revisar historial de git con `git log --oneline`
  - **Tipos válidos**: FEAT, FIX, REFACTOR, DOCS, STYLE, TEST, CHORE
  - **Si hay inconsistencias**: Usar `git rebase -i` o `git commit --amend` para corregir
  - **Archivos a revisar**: Historial completo del repositorio

- [x] **Revisar código de autenticación** ✅ COMPLETADO (Revisión funcional)
  - **Contexto**: Verificar seguridad y manejo de errores en AuthViewModel
  - **Archivos revisados**: 
    - ✅ `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/AuthViewModel.kt`
    - ✅ `app/src/main/java/com/example/milsaborestest/data/local/database/UserDao.kt`
  - **Verificaciones realizadas**:
    - ✅ **Contraseñas**: Se almacenan en texto plano (documentado como "considerar hash en futuro")
      - Línea 125 en AuthViewModel: `contrasena = password`
      - Línea 26 en UserDao: Query directo con contraseña en texto plano
      - **Nota**: Para producción, implementar hash (BCrypt, Argon2, etc.)
    - ✅ **Manejo de errores**: Implementado correctamente
      - Try-catch en `login()`, `register()`, `logout()`
      - Mensajes de error apropiados en `_message.value`
      - Estado limpio en caso de error
    - ✅ **Validaciones**: Funcionan correctamente
      - Campos vacíos: Verificado en login y register
      - Email válido: Usa `Patterns.EMAIL_ADDRESS.matcher()`
      - Contraseña mínima: 6 caracteres requeridos
      - Email duplicado: Verificado en register antes de insertar
    - ✅ **Flujo de logout**: Limpia correctamente el estado
      - `_user.value = null`
      - `_isAuthenticated.value = false`
      - Mensaje de confirmación establecido
  - **Testing**: 
    - ⚠️ **No hay tests automatizados** (unit tests, integration tests)
    - ✅ **Testing manual**: Funcionalidad verificada manualmente con usuarios válidos e inválidos
    - **Recomendación**: Considerar agregar tests automatizados en el futuro
  - **Estado**: Código funcional y seguro para desarrollo. Para producción, considerar:
    - Implementar hash de contraseñas (BCrypt, Argon2)
    - Agregar tests automatizados
    - Considerar rate limiting para login/register

---

## 🟡 Doing

### 🚧 En Progreso - Tareas Críticas para Evaluación

- [x] **Implementar recursos nativos - Fase mínima (Notificaciones + Galería)** ✅ COMPLETADO
  - **Contexto**: Requisito crítico del encargo - al menos 2 recursos nativos
  - **Recursos a implementar**:
    1. ✅ **Notificaciones: Recordatorio de carrito abandonado** - COMPLETADO
    2. ✅ **Galería: Foto de perfil de usuario (seleccionar de galería)** - COMPLETADO
  - **Archivos principales a modificar/crear**:
    - ✅ `AndroidManifest.xml` (permisos de notificaciones) - COMPLETADO
    - ✅ `NotificationHelper.kt` (nuevo) - COMPLETADO
    - ✅ `ImageHelper.kt` (nuevo) - COMPLETADO
    - ✅ `UserEntity.kt` (agregar campo fotoPerfil) - COMPLETADO
    - ✅ `AppDatabase.kt` (migración) - COMPLETADO
    - ✅ `MainActivity.kt` (lógica de notificaciones) - COMPLETADO
    - ✅ `AccountScreen.kt` (UI de foto de perfil) - COMPLETADO
    - ✅ `MainContent.kt` (NavigationDrawerContent con foto) - COMPLETADO
  - **Progreso**: 2/2 recursos nativos completados (100%)
  - **Nota sobre permisos**: El Photo Picker moderno (`ActivityResultContracts.PickVisualMedia`) NO requiere permisos explícitos en Android 13+ y funciona automáticamente en versiones anteriores gracias a la biblioteca de compatibilidad.

---

## 🔵 Backlog

### 🔴 PRIORIDAD ALTA - Tareas Críticas para Evaluación

#### 📱 Recursos Nativos (CRÍTICO - Requisito del encargo) - IMPLEMENTACIÓN MÍNIMA

#### Notificaciones
- [x] **Configurar permisos de notificaciones en AndroidManifest** ✅ COMPLETADO
  - **Archivo**: `app/src/main/AndroidManifest.xml`
  - **Implementado**: Permiso `POST_NOTIFICATIONS` agregado para Android 13+
  - **Ubicación**: Antes de `<application>` tag
  - **Estado**: Funcionando correctamente

- [x] **Crear NotificationHelper/NotificationManager** ✅ COMPLETADO
  - **Ubicación**: `app/src/main/java/com/example/milsaborestest/util/NotificationHelper.kt`
  - **Implementado**:
    - Clase `object NotificationHelper` (singleton) creada
    - Función `createNotificationChannel(context: Context)` implementada
    - Función `showCartReminderNotification(context: Context, itemCount: Int)` implementada
    - Función `cancelCartReminderNotification(context: Context)` implementada
    - Verificación de permisos antes de mostrar notificaciones
    - Uso de `NotificationCompat.Builder` para compatibilidad
    - Icono: `R.drawable.ic_launcher_foreground`
  - **Canal de notificación**:
    - ID: "cart_reminder_channel"
    - Nombre: "Recordatorios de Carrito"
    - Descripción: "Notificaciones sobre productos en tu carrito"
    - Importancia: `NotificationManager.IMPORTANCE_DEFAULT`
  - **Estado**: Funcionando correctamente

- [x] **Implementar lógica de carrito abandonado** ✅ COMPLETADO
  - **Contexto**: Detectar cuando usuario sale de la app con items en carrito y mostrar notificación inmediatamente
  - **Archivo modificado**: `app/src/main/java/com/example/milsaborestest/MainActivity.kt`
  - **Implementado**:
    - Override `onPause()` en MainActivity
    - Verificación de items en carrito usando CartViewModel
    - Si `totalItems > 0` → Mostrar notificación inmediatamente usando `NotificationHelper`
    - Sin delay, se muestra al instante cuando se pierde el foco
  - **Lógica**:
    - Si `totalItems > 0` → Mostrar notificación de carrito abandonado
    - Si `totalItems == 0` → No hacer nada
  - **Consideraciones implementadas**:
    - Solo se muestra cuando la app pierde foco (onPause)
    - Verificación de permisos antes de mostrar
    - Mensaje amigable: "Tienes X productos en tu carrito. ¡No te los pierdas!"
    - La notificación tiene acción para abrir la app
  - **Estado**: Funcionando correctamente

- [x] **Integrar notificaciones en MainActivity** ✅ COMPLETADO
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/MainActivity.kt`
  - **Implementado**:
    - Creación de canal de notificaciones en `onCreate()`
    - Acceso a CartViewModel usando ViewModelProvider
    - Lógica de detección de carrito abandonado en `onPause()`
    - Manejo de navegación desde notificación con `onNewIntent()`
  - **Implementación**:
    - Uso de `Application` context para CartViewModel
    - Verificación de `totalItems.value > 0` antes de mostrar
    - Uso de `NotificationHelper.showCartReminderNotification()`
  - **Estado**: Funcionando correctamente

#### Galería y Foto de Perfil
- [x] **Configurar permisos de galería en AndroidManifest** ✅ COMPLETADO
  - **Archivo**: `app/src/main/AndroidManifest.xml`
  - **Implementado**: 
    - **NO se requieren permisos explícitos** - El Photo Picker moderno (`ActivityResultContracts.PickVisualMedia`) no los necesita
    - Comentario agregado en AndroidManifest explicando que no se requieren permisos
  - **Notas**:
    - El Photo Picker proporciona acceso temporal y seguro a las imágenes seleccionadas
    - No requiere `READ_EXTERNAL_STORAGE` ni `READ_MEDIA_IMAGES`
    - Funciona automáticamente en Android 13+ sin permisos
    - La biblioteca de compatibilidad maneja versiones anteriores automáticamente
    - **Ventaja**: Más simple, seguro y no molesta al usuario con solicitudes de permisos
  - **Estado**: Funcionando correctamente sin permisos explícitos

- [x] **Modificar UserEntity para foto de perfil** ✅ COMPLETADO
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/data/local/database/UserEntity.kt`
  - **Implementado**: Campo `fotoPerfil: String? = null` agregado a UserEntity
  - **Consideraciones**:
    - Campo nullable porque usuarios existentes no tendrán foto
    - Almacenar ruta relativa o nombre de archivo, no URI completo
    - Formato sugerido: "profile_${userId}.jpg" o similar
  - **UserDao**: No requiere cambios, Room maneja automáticamente

- [x] **Crear migración de base de datos** ✅ COMPLETADO
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`
  - **Implementado**:
    1. Versión incrementada de 2 a 3 en `@Database`
    2. Migración `MIGRATION_2_3` creada y agregada al builder
    3. `fallbackToDestructiveMigration()` mantenido para desarrollo
  - **Migración**: `ALTER TABLE usuario ADD COLUMN fotoPerfil TEXT` 

- [x] **Crear imágenes por defecto en drawable** ✅ COMPLETADO
  - **Ubicación**: `app/src/main/res/drawable/`
  - **Imágenes creadas**:
    - ✅ `ic_profile_default.xml` - Avatar por defecto para usuarios (vector drawable)
    - ✅ `ic_product_default.xml` - Imagen por defecto para productos (vector drawable)
  - **Diseño**: Vector drawables creados con iconos de Material Design
  - **Uso**: Se usan cuando no hay foto o falle la carga
  - **Estado**: Implementado y funcionando en AccountScreen y NavigationDrawerContent

- [x] **Implementar ImageHelper/ImageManager** ✅ COMPLETADO
  - **Ubicación**: `app/src/main/java/com/example/milsaborestest/util/ImageHelper.kt`
  - **Implementado**: Clase `object ImageHelper` con todas las funciones necesarias
  - **Funciones implementadas**:
    - ✅ `uriToBitmap(context: Context, uri: Uri): Bitmap?` - Convierte URI de galería a Bitmap
    - ✅ `saveProfileImage(context: Context, bitmap: Bitmap, userId: Int): String?` - Guarda imagen en storage interno
    - ✅ `loadProfileImage(context: Context, imagePath: String?): Bitmap?` - Lee imagen desde storage
    - ✅ `deleteProfileImage(context: Context, imagePath: String?): Boolean` - Elimina imagen antigua
  - **Storage**: Usa storage interno (`context.filesDir/profile_images/`) para privacidad
  - **Manejo de errores**: Todas las funciones manejan excepciones y retornan null/false en caso de error
  - **Logging**: Implementado con `android.util.Log` para debugging
  - **Estado**: Funcionando correctamente

- [x] **Implementar ActivityResultLauncher para galería** ✅ COMPLETADO
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/account/AccountScreen.kt`
  - **Implementado**: 
    - ✅ `rememberLauncherForActivityResult` con `ActivityResultContracts.PickVisualMedia()`
    - ✅ Uso de `PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly).build()`
    - ✅ Manejo seguro de resultados nullable con `let`/`run`
  - **Flujo implementado**:
    1. Usuario presiona FloatingActionButton para editar foto
    2. Se lanza selector de galería automáticamente (sin verificar permisos - no se requieren)
    3. En callback → Obtener URI → Convertir a Bitmap con ImageHelper → Guardar → Actualizar UserEntity
    4. Eliminación automática de imagen antigua antes de guardar nueva
  - **Manejo de permisos**: 
    - **NO se requieren permisos explícitos** - El Photo Picker los maneja automáticamente
    - Funciona en Android 13+ sin permisos
    - La biblioteca de compatibilidad maneja versiones anteriores
  - **Estado**: Funcionando correctamente

- [x] **Actualizar AccountScreen con foto de perfil y manejo de errores** ✅ COMPLETADO
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/account/AccountScreen.kt`
  - **Implementado**:
    - ✅ Componente `ProfileImage` creado con lógica condicional completa
    - ✅ Uso de `AsyncImage` de Coil con `placeholder`, `error` y `fallback` apuntando a `ic_profile_default`
    - ✅ Verificación de existencia de archivo antes de cargar
    - ✅ FloatingActionButton para editar foto (32.dp, alineado BottomEnd)
    - ✅ Avatar circular de 100.dp con `CircleShape`
  - **Lógica condicional implementada**:
    1. Si `user.fotoPerfil != null` y archivo existe → Cargar con AsyncImage
    2. Si archivo no existe o error → Mostrar `ic_profile_default`
    3. Si `user.fotoPerfil == null` → Mostrar `ic_profile_default`
  - **Casos manejados**: Todos los casos edge cubiertos (null, archivo no existe, error de lectura)
  - **Estado**: Funcionando correctamente

- [x] **Actualizar Sidebar con foto de perfil y manejo de errores** ✅ COMPLETADO
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/MainContent.kt`
  - **Función**: `NavigationDrawerContent`
  - **Implementado**:
    - ✅ Uso del componente `ProfileImage` reutilizable
    - ✅ Avatar de 64.dp (más pequeño que en AccountScreen)
    - ✅ Mostrado en `Row` junto con nombre y email del usuario
    - ✅ Misma lógica condicional que AccountScreen (usa el mismo componente)
  - **Casos manejados**: Todos los casos edge cubiertos (null, archivo no existe, error de lectura)
  - **Estado**: Funcionando correctamente

- [ ] **Actualizar componentes de productos con imágenes por defecto**
  - **Archivos a modificar**:
    - `app/src/main/java/com/example/milsaborestest/presentation/ui/components/ProductCard.kt`
    - `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/productdetail/ProductDetailScreen.kt`
    - `app/src/main/java/com/example/milsaborestest/presentation/ui/components/ProductCarousel.kt`
    - `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/cart/CartScreen.kt` (imágenes de items)
  - **Modificaciones**:
    - En todos los `AsyncImage` de productos, agregar:
      - `placeholder = painterResource(R.drawable.ic_product_default)`
      - `error = painterResource(R.drawable.ic_product_default)`
      - `fallback = painterResource(R.drawable.ic_product_default)`
  - **Casos a manejar**:
    - `product.imagen == null` o vacío → Imagen por defecto
    - URL rota o error de red → Imagen por defecto
    - Timeout de carga → Imagen por defecto
    - Formato de imagen no soportado → Imagen por defecto
  - **Ejemplo de implementación**:
    ```kotlin
    AsyncImage(
        model = product.imagen,
        contentDescription = product.nombre,
        placeholder = painterResource(R.drawable.ic_product_default),
        error = painterResource(R.drawable.ic_product_default),
        fallback = painterResource(R.drawable.ic_product_default),
        modifier = Modifier.fillMaxWidth().height(150.dp)
    )
    ```
  - **Nota**: Coil maneja automáticamente estos casos si se configuran correctamente

- [x] **Actualizar AuthViewModel para manejar foto** ✅ COMPLETADO
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/AuthViewModel.kt`
  - **Implementado**:
    - ✅ Función `updateProfilePhoto(imagePath: String)` agregada
      - Actualiza `UserEntity` en base de datos con nueva ruta
      - Actualiza `_user.value` (StateFlow) con nueva información
      - Manejo completo de errores con try-catch
      - Validación de usuario autenticado y ID válido
    - ✅ Función `login()` ya incluye `fotoPerfil` al convertir UserEntity a User
    - ✅ Función `register()` ya incluye `fotoPerfil` al convertir UserEntity a User
  - **Flujo implementado**:
    1. ✅ Usuario selecciona imagen de galería en AccountScreen
    2. ✅ AccountScreen convierte URI a Bitmap y guarda imagen con ImageHelper
    3. ✅ Si guardado exitoso → AccountScreen llama `authViewModel.updateProfilePhoto(ruta)`
    4. ✅ Si guardado falla → No se actualiza BD (manejo en AccountScreen)
    5. ✅ AuthViewModel actualiza UserEntity en BD
    6. ✅ AuthViewModel actualiza StateFlow de user
    7. ✅ UI se actualiza automáticamente (Compose reacciona al StateFlow)
  - **Manejo de errores**: Implementado con try-catch, mensajes de error en `_message.value`
  - **Estado**: Funcionando correctamente, foto persiste después de logout/login

#### 📦 Migración de Productos de JSON a Room Database
- [ ] **Crear entidad CategoryEntity para categorías en base de datos**
  - **Contexto**: Migrar categorías de JSON a Room Database para tener todo centralizado
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/local/database/CategoryEntity.kt`
  - **Campos sugeridos**:
    - `id: String` (PrimaryKey) - ID de la categoría (ej: "tortas-cuadradas")
    - `nombre: String` - Nombre de la categoría (ej: "Tortas Cuadradas")
    - `icono: String` - Icono de la categoría (ej: "square")
  - **Consideraciones**:
    - ID es String porque viene del JSON como clave del map
    - No necesita autoGenerate, el ID viene del JSON

- [ ] **Crear entidad ProductEntity para productos en base de datos**
  - **Contexto**: Migrar productos de JSON a Room Database, usando la misma metodología que usuarios por defecto
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/local/database/ProductEntity.kt`
  - **Campos sugeridos**:
    - `id: String` (PrimaryKey) - ID del producto (ej: "TC001")
    - `categoryId: String` - ID de la categoría (Foreign Key a CategoryEntity)
    - `nombre: String` - Nombre del producto
    - `precio: Int` - Precio en pesos chilenos
    - `imagen: String` - URL de la imagen del producto
    - `descripcion: String` - Descripción corta
    - `descripcionDetallada: String` - Descripción completa
    - `rating: Double` - Rating promedio (1.0 - 5.0)
    - `reviews: Int` - Cantidad de reseñas
    - `porciones: String` - Información de porciones
    - `calorias: String` - Información de calorías
    - `ingredientes: String` - Lista de ingredientes
  - **Consideraciones**:
    - `categoryId` como Foreign Key a CategoryEntity
    - No incluir `reseñas` en ProductEntity (se manejará con ReviewEntity separada)
    - Rating y reviews se calcularán dinámicamente desde ReviewEntity cuando se implemente

- [ ] **Crear CategoryDao con queries necesarias**
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/local/database/CategoryDao.kt`
  - **Queries a implementar**:
    - `@Insert suspend fun insertar(category: CategoryEntity): Long` - Insertar nueva categoría
    - `@Insert suspend fun insertarTodas(categories: List<CategoryEntity>)` - Insertar múltiples categorías
    - `@Query("SELECT * FROM categorias ORDER BY nombre ASC") suspend fun obtenerTodas(): List<CategoryEntity>` - Obtener todas las categorías
    - `@Query("SELECT * FROM categorias WHERE id = :id") suspend fun obtenerPorId(id: String): CategoryEntity?` - Obtener categoría por ID
  - **Consideraciones**:
    - Usar `suspend` para operaciones asíncronas
    - Ordenar por nombre para consistencia

- [ ] **Crear ProductDao con queries necesarias**
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/local/database/ProductDao.kt`
  - **Queries a implementar**:
    - `@Insert suspend fun insertar(product: ProductEntity): Long` - Insertar nuevo producto
    - `@Insert suspend fun insertarTodos(products: List<ProductEntity>)` - Insertar múltiples productos
    - `@Query("SELECT * FROM productos ORDER BY nombre ASC") suspend fun obtenerTodos(): List<ProductEntity>` - Obtener todos los productos
    - `@Query("SELECT * FROM productos WHERE id = :id") suspend fun obtenerPorId(id: String): ProductEntity?` - Obtener producto por ID
    - `@Query("SELECT * FROM productos WHERE categoryId = :categoryId ORDER BY nombre ASC") suspend fun obtenerPorCategoria(categoryId: String): List<ProductEntity>` - Obtener productos por categoría
    - `@Query("SELECT * FROM productos WHERE nombre LIKE '%' || :searchTerm || '%' OR descripcion LIKE '%' || :searchTerm || '%'") suspend fun buscar(searchTerm: String): List<ProductEntity>` - Búsqueda de productos
  - **Consideraciones**:
    - Usar `suspend` para operaciones asíncronas
    - Búsqueda con LIKE para texto parcial
    - Ordenar por nombre para consistencia

- [ ] **Crear mappers para convertir entre Entity y Domain**
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/mapper/CategoryMapper.kt`
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/mapper/ProductMapper.kt` (actualizar existente)
  - **Funciones a implementar**:
    - `fun CategoryEntity.toDomain(products: List<Product> = emptyList()): Category` - Convertir entidad a modelo de dominio
    - `fun Category.toEntity(): CategoryEntity` - Convertir modelo a entidad
    - `fun ProductEntity.toDomain(reseñas: List<Review> = emptyList()): Product` - Actualizar mapper existente para usar Entity
    - `fun Product.toEntity(categoryId: String): ProductEntity` - Convertir modelo a entidad
  - **Consideraciones**:
    - Mantener compatibilidad con mappers existentes si es posible
    - Reseñas se cargarán por separado desde ReviewEntity cuando se implemente

- [ ] **Implementar carga de productos y categorías default desde JSON usando misma metodología que usuarios**
  - **Contexto**: Cargar productos y categorías desde `productos.json` como datos default en la base de datos, igual que se hace con usuarios
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`
  - **Metodología** (igual que usuarios por defecto):
    1. En función `insertarDatosPorDefecto()`, agregar lógica para cargar productos y categorías
    2. Leer `productos.json` usando `ProductJsonDataSource` o similar
    3. Verificar si ya existen productos/categorías para evitar duplicados
    4. Para cada categoría del JSON:
       - Crear `CategoryEntity` con id, nombre, icono
       - Insertar en base de datos
    5. Para cada producto del JSON:
       - Crear `ProductEntity` con todos los campos
       - Asociar con `categoryId` correspondiente
       - Insertar en base de datos
    6. Solo cargar una vez (verificar si ya existen datos)
  - **Consideraciones**:
    - Usar `ProductJsonDataSource` existente para leer JSON
    - Verificar existencia antes de insertar para evitar duplicados
    - Mantener la misma estructura de datos que el JSON
    - Las reseñas del JSON se manejarán por separado con ReviewEntity

- [ ] **Actualizar AppDatabase para incluir nuevas entidades y DAOs**
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`
  - **Modificaciones**:
    1. Agregar `CategoryEntity` y `ProductEntity` a la lista de entidades en `@Database`
    2. Incrementar versión de base de datos (de 3 a 4, o según corresponda)
    3. Crear migración `MIGRATION_3_4` (o la versión correspondiente):
       - Crear tabla `categorias`
       - Crear tabla `productos` con Foreign Key a `categorias`
    4. Agregar `categoryDao(): CategoryDao` al `AppDatabase`
    5. Agregar `productDao(): ProductDao` al `AppDatabase`
    6. Agregar migración al builder con `.addMigrations(MIGRATION_X_Y)`
  - **SQL sugerido**:
    ```sql
    CREATE TABLE categorias (
        id TEXT PRIMARY KEY,
        nombre TEXT NOT NULL,
        icono TEXT NOT NULL
    );
    
    CREATE TABLE productos (
        id TEXT PRIMARY KEY,
        categoryId TEXT NOT NULL,
        nombre TEXT NOT NULL,
        precio INTEGER NOT NULL,
        imagen TEXT NOT NULL,
        descripcion TEXT NOT NULL,
        descripcionDetallada TEXT NOT NULL,
        rating REAL NOT NULL,
        reviews INTEGER NOT NULL,
        porciones TEXT NOT NULL,
        calorias TEXT NOT NULL,
        ingredientes TEXT NOT NULL,
        FOREIGN KEY(categoryId) REFERENCES categorias(id)
    );
    ```

- [ ] **Actualizar ProductRepositoryImpl para usar DAO en lugar de JSON**
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/data/repository/ProductRepositoryImpl.kt`
  - **Cambios necesarios**:
    1. Inyectar `ProductDao` y `CategoryDao` en lugar de (o además de) `ProductJsonDataSource`
    2. Modificar `getCategories()` para:
       - Obtener categorías desde `CategoryDao`
       - Obtener productos de cada categoría desde `ProductDao`
       - Convertir a modelos de dominio usando mappers
    3. Modificar `getProductsByCategory()` para usar `ProductDao.obtenerPorCategoria()`
    4. Modificar `getProductById()` para usar `ProductDao.obtenerPorId()`
    5. Modificar `getAllProducts()` para usar `ProductDao.obtenerTodos()`
  - **Consideraciones**:
    - Mantener compatibilidad con la interfaz `ProductRepository`
    - Cargar reseñas desde ReviewEntity cuando se implemente (por ahora lista vacía)
    - Si no hay datos en BD, cargar desde JSON como fallback (opcional)

- [ ] **Actualizar AppModule para inyectar nuevos DAOs**
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/di/AppModule.kt`
  - **Modificaciones**:
    1. Agregar `@Provides fun provideCategoryDao(database: AppDatabase) = database.categoryDao()`
    2. Agregar `@Provides fun provideProductDao(database: AppDatabase) = database.productDao()`
  - **Consideraciones**:
    - Mantener inyección de `ProductJsonDataSource` si se usa como fallback o para carga inicial
    - Los DAOs se inyectarán en `ProductRepositoryImpl`

- [ ] **Eliminar o deprecar ProductJsonDataSource**
  - **Contexto**: Una vez migrado todo a Room, el JSON solo se usará para carga inicial
    - **Opción B**: Eliminar completamente y cargar datos directamente en `insertarDatosPorDefecto()` sin DataSource

  - **Archivos afectados**:
    - `ProductJsonDataSource.kt` - Mantener o eliminar según opción
    - `AppModule.kt` - Actualizar inyección si se elimina
    - `ProductRepositoryImpl.kt` - Ya no usaría JSON para consultas

#### 🛒 Checkout e Historial de Compras
- [x] **Crear entidad PurchaseEntity/OrderEntity para compras** ✅ COMPLETADO
  - **Contexto**: Necesitamos almacenar el historial de compras de los usuarios
  - **Archivo creado**: `app/src/main/java/com/example/milsaborestest/data/local/database/PurchaseEntity.kt`
  - **Archivo creado**: `app/src/main/java/com/example/milsaborestest/data/local/database/PurchaseItemEntity.kt`
  - **Implementación**:
    - ✅ `PurchaseEntity`: id (String UUID), userId (FK), fecha (ISO 8601), total, estado
    - ✅ `PurchaseItemEntity`: id (autoincrement), purchaseId (FK), productId, nombre, precio, cantidad, imagen
    - ✅ Tabla separada para items (más normalizado)
    - ✅ Foreign Keys configuradas con CASCADE DELETE
    - ✅ Snapshot de productos al momento de compra
  - **Estado**: Implementado con tabla separada para mejor normalización

- [x] **Crear PurchaseDao con queries necesarias** ✅ COMPLETADO
  - **Archivo creado**: `app/src/main/java/com/example/milsaborestest/data/local/database/PurchaseDao.kt`
  - **Queries implementadas**:
    - ✅ `insertarCompra(purchase: PurchaseEntity): Long` - Insertar nueva compra
    - ✅ `insertarItems(items: List<PurchaseItemEntity>)` - Insertar items de compra
    - ✅ `obtenerComprasPorUsuario(userId: Int): List<PurchaseEntity>` - Obtener compras ordenadas por fecha DESC
    - ✅ `obtenerCompraPorId(purchaseId: String): PurchaseEntity?` - Obtener compra específica
    - ✅ `obtenerItemsPorCompra(purchaseId: String): List<PurchaseItemEntity>` - Obtener items de una compra
    - ✅ `contarComprasPorUsuario(userId: Int): Int` - Estadística de compras
    - ✅ `obtenerTotalGastadoPorUsuario(userId: Int): Int` - Total gastado por usuario
  - **Estado**: Implementado con queries adicionales para estadísticas

- [x] **Crear modelo de dominio Purchase** ✅ COMPLETADO
  - **Archivo creado**: `app/src/main/java/com/example/milsaborestest/domain/model/Purchase.kt`
  - **Implementación**:
    - ✅ `Purchase`: id, userId, fecha, total, estado, items (List<PurchaseItem>)
    - ✅ `PurchaseItem`: id, productId, nombre, precio, cantidad, imagen
    - ✅ Propiedad calculada `subtotal` en PurchaseItem (precio * cantidad)
  - **Mapper**: Conversión implementada directamente en PurchaseViewModel
  - **Estado**: Modelos de dominio completos con documentación

- [x] **Implementar PurchaseViewModel para gestionar compras** ✅ COMPLETADO
  - **Archivo creado**: `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/PurchaseViewModel.kt`
  - **Funcionalidades implementadas**:
    - ✅ `StateFlow<List<Purchase>>` - purchaseHistory
    - ✅ `StateFlow<Purchase?>` - currentPurchase
    - ✅ `StateFlow<Boolean>` - isLoading
    - ✅ `StateFlow<String?>` - message
    - ✅ `realizarCompra(cartItems: List<CartItem>, userId: Int): String?` - Checkout completo
    - ✅ `obtenerHistorialCompras(userId: Int)` - Cargar historial con items
    - ✅ `obtenerCompraPorId(purchaseId: String)` - Obtener compra específica
    - ✅ `formatearFecha()` - Formato legible de fechas (ISO -> "dd MMM yyyy, HH:mm")
  - **Lógica de checkout implementada**:
    - ✅ Validación de carrito vacío
    - ✅ Generación de UUID para ID de compra
    - ✅ Cálculo de total automático
    - ✅ Creación de PurchaseEntity y PurchaseItemEntity
    - ✅ Inserción en base de datos
    - ✅ Manejo de errores con try-catch
    - ✅ Estados de carga y mensajes
  - **Estado**: ViewModel completo con todas las funcionalidades

- [x] **Implementar función de checkout en CartViewModel o crear función separada** ✅ COMPLETADO
  - **Contexto**: Simular el proceso de compra desde el carrito
  - **Opción implementada**: Opción B - Función en `PurchaseViewModel` (separación de responsabilidades)
  - **Flujo implementado**:
    1. ✅ Usuario presiona botón "Comprar" en `CartScreen`
    2. ✅ Validación de autenticación (si no está autenticado, muestra mensaje)
    3. ✅ `CartScreen` llama a `PurchaseViewModel.realizarCompra(cartItems, userId)`
    4. ✅ `PurchaseViewModel` crea PurchaseEntity y PurchaseItemEntity
    5. ✅ Inserción en base de datos
    6. ✅ `CartScreen` llama a `CartViewModel.clearCart()` después de compra exitosa
    7. ✅ Diálogo de éxito con opciones de navegación
    8. ✅ Navegación a historial o home
  - **Estado**: Implementado con separación de responsabilidades

- [x] **Crear pantalla de Historial de Compras (PurchaseHistoryScreen)** ✅ COMPLETADO
  - **Archivo creado**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/purchasehistory/PurchaseHistoryScreen.kt`
  - **Funcionalidades implementadas**:
    - ✅ Lista de compras del usuario autenticado
    - ✅ Ordenadas por fecha (más recientes primero)
    - ✅ Información de cada compra: fecha, total, cantidad de items, estado
    - ✅ Lista expandible de items con animaciones
    - ✅ Imágenes de productos en items
    - ✅ Cálculo de subtotales por item
  - **UI implementada**:
    - ✅ `LazyColumn` con `animateItemPlacement()`
    - ✅ `PurchaseCard` con información resumida
    - ✅ Botón expandir/colapsar con animación de rotación
    - ✅ `StatusChip` con colores según estado
    - ✅ Estado vacío con icono y mensaje
    - ✅ Estado de carga con CircularProgressIndicator
    - ✅ Manejo de usuario no autenticado
  - **Integración**:
    - ✅ PurchaseViewModel para datos
    - ✅ AuthViewModel para usuario autenticado
    - ✅ Ruta agregada en `Screen.kt` (Screen.PurchaseHistory)
    - ✅ Ruta agregada en `AppNavigation.kt`
    - ✅ Opción en NavigationDrawer (solo usuarios autenticados)
  - **Estado**: Pantalla completa con animaciones y estados

- [x] **Actualizar CartScreen con botón de checkout funcional** ✅ COMPLETADO
  - **Archivo modificado**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/cart/CartScreen.kt`
  - **Modificaciones implementadas**:
    - ✅ Función signature actualizada: recibe `AuthViewModel` y `PurchaseViewModel`
    - ✅ Validación de autenticación antes de comprar
    - ✅ Texto dinámico en botón: "Comprar" si autenticado, "Iniciar Sesión" si no
    - ✅ Snackbar para mensajes de error/información
    - ✅ Función `onCheckout` con lógica completa:
      1. ✅ Validar autenticación (mensaje si no está autenticado)
      2. ✅ Validar carrito no vacío
      3. ✅ Llamar a `PurchaseViewModel.realizarCompra(cartItems, userId)`
      4. ✅ Limpiar carrito con `CartViewModel.clearCart()` si exitoso
      5. ✅ Mostrar diálogo de éxito con opciones de navegación
    - ✅ Diálogo de éxito (`AlertDialog`) con:
      - ✅ Icono de check animado
      - ✅ Mensaje de confirmación
      - ✅ Botón "Ver Historial" (navega a purchase_history)
      - ✅ Botón "Volver al Inicio" (popBackStack)
    - ✅ Botón deshabilitado si `isLoading` o carrito vacío
    - ✅ CircularProgressIndicator en botón durante carga
    - ✅ Scaffold con SnackbarHost para mensajes
  - **Estado**: CartScreen completamente funcional con checkout

- [x] **Agregar migración de base de datos para PurchaseEntity** ✅ COMPLETADO
  - **Archivo modificado**: `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`
  - **Implementación**:
    1. ✅ Versión incrementada de 3 a 4
    2. ✅ Migración `MIGRATION_3_4` creada con SQL:
       - ✅ Tabla `compras` con: id (PK), userId (FK), fecha, total, estado
       - ✅ Tabla `purchase_items` con: id (PK autoincrement), purchaseId (FK), productId, nombre, precio, cantidad, imagen
       - ✅ Foreign Keys con CASCADE DELETE
    3. ✅ `PurchaseEntity` y `PurchaseItemEntity` agregadas a `@Database`
    4. ✅ `purchaseDao(): PurchaseDao` agregado al AppDatabase
    5. ✅ Migración agregada al builder: `.addMigrations(MIGRATION_2_3, MIGRATION_3_4)`
  - **SQL implementado**:
    ```sql
    CREATE TABLE IF NOT EXISTS compras (
        id TEXT PRIMARY KEY NOT NULL,
        userId INTEGER NOT NULL,
        fecha TEXT NOT NULL,
        total INTEGER NOT NULL,
        estado TEXT NOT NULL,
        FOREIGN KEY(userId) REFERENCES usuario(id) ON DELETE CASCADE
    );
    
    CREATE TABLE IF NOT EXISTS purchase_items (
        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        purchaseId TEXT NOT NULL,
        productId TEXT NOT NULL,
        nombre TEXT NOT NULL,
        precio INTEGER NOT NULL,
        cantidad INTEGER NOT NULL,
        imagen TEXT NOT NULL,
        FOREIGN KEY(purchaseId) REFERENCES compras(id) ON DELETE CASCADE
    );
    ```
  - **Estado**: Migración completa y funcional

#### ⭐ Sistema de Reseñas
- [ ] **Crear entidad ReviewEntity para reseñas en base de datos**
  - **Contexto**: Necesitamos almacenar reseñas de productos asociadas a usuarios, permitiendo que usuarios agreguen sus propias reseñas
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/local/database/ReviewEntity.kt`
  - **Campos sugeridos**:
    - `id: Int` (PrimaryKey, autoGenerate = true) - ID único de la reseña
    - `productId: String` - ID del producto (no FK, productos vienen de JSON)
    - `userId: Int?` - ID del usuario que escribió la reseña (nullable, null para reseñas default)
    - `autor: String` - Nombre del autor (para reseñas default, usar nombre del JSON)
    - `fecha: String` - Fecha de la reseña
    - `rating: Int` - Calificación (1-5 estrellas)
    - `comentario: String` - Texto de la reseña
  - **Consideraciones**:
    - `userId` nullable porque las reseñas default del JSON no tienen usuario asociado
    - `autor` siempre presente (para reseñas default viene del JSON, para nuevas viene del UserEntity)
    - Almacenar `productId` como String (productos vienen de JSON, no de BD)

- [ ] **Crear ReviewDao con queries necesarias**
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/local/database/ReviewDao.kt`
  - **Queries a implementar**:
    - `@Insert suspend fun insertar(review: ReviewEntity): Long` - Insertar nueva reseña
    - `@Query("SELECT * FROM reseñas WHERE productId = :productId ORDER BY fecha DESC") suspend fun obtenerPorProducto(productId: String): List<ReviewEntity>` - Obtener reseñas de un producto
    - `@Query("SELECT * FROM reseñas WHERE userId = :userId") suspend fun obtenerPorUsuario(userId: Int): List<ReviewEntity>` - Obtener reseñas de un usuario
    - `@Query("SELECT * FROM reseñas WHERE productId = :productId AND userId = :userId") suspend fun obtenerPorProductoYUsuario(productId: String, userId: Int): ReviewEntity?` - Verificar si usuario ya reseñó un producto
    - `@Query("SELECT AVG(rating) FROM reseñas WHERE productId = :productId") suspend fun obtenerRatingPromedio(productId: String): Double?` - Calcular rating promedio
    - `@Query("SELECT COUNT(*) FROM reseñas WHERE productId = :productId") suspend fun obtenerCantidadReseñas(productId: String): Int` - Contar reseñas de un producto
  - **Consideraciones**:
    - Usar `suspend` para operaciones asíncronas
    - Ordenar por fecha descendente (más recientes primero)

- [ ] **Actualizar modelo de dominio Review para incluir userId opcional**
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/domain/model/Review.kt`
  - **Modificaciones**:
    - Agregar campo `userId: String?` (nullable) para identificar si la reseña es de un usuario registrado
    - Mantener compatibilidad con reseñas existentes del JSON
  - **Estructura actualizada sugerida**:
    ```kotlin
    data class Review(
        val id: String? = null, // ID de la reseña en BD (null para reseñas del JSON)
        val autor: String,
        val fecha: String,
        val rating: Int,
        val comentario: String,
        val userId: String? = null // ID del usuario (null para reseñas default)
    )
    ```

- [ ] **Crear ReviewMapper para convertir entre ReviewEntity y Review**
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/data/mapper/ReviewMapper.kt`
  - **Funciones a implementar**:
    - `fun ReviewEntity.toDomain(): Review` - Convertir entidad a modelo de dominio
    - `fun Review.toEntity(productId: String, userId: Int?): ReviewEntity` - Convertir modelo a entidad
    - `fun ReviewDto.toDomain(): Review` - Mantener conversión de DTO (para reseñas del JSON)
  - **Consideraciones**:
    - Manejar conversión de `Int` (userId en Entity) a `String` (userId en Domain)
    - Manejar valores null para reseñas default

- [ ] **Implementar carga de reseñas default desde JSON usando misma metodología que usuarios**
  - **Contexto**: Cargar las reseñas que vienen en `productos.json` como reseñas default en la base de datos
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`
  - **Metodología** (igual que usuarios por defecto):
    1. En función `insertarDatosPorDefecto()`, agregar lógica para cargar reseñas
    2. Leer `productos.json` usando `ProductJsonDataSource` o similar
    3. Para cada producto, extraer sus reseñas del JSON
    4. Convertir cada reseña a `ReviewEntity` con:
       - `userId = null` (reseñas default no tienen usuario)
       - `autor = review.autor` (del JSON)
       - `productId = product.id` (del producto actual)
       - Resto de campos del JSON
    5. Verificar si ya existen reseñas para evitar duplicados
    6. Insertar todas las reseñas default en la base de datos
  - **Consideraciones**:
    - Solo cargar reseñas default una vez (verificar si ya existen)
    - Asociar cada reseña con su `productId` correspondiente
    - Mantener las reseñas del JSON como "default" (userId = null)

- [ ] **Crear ReviewViewModel para gestionar reseñas**
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/ReviewViewModel.kt`
  - **Funcionalidades**:
    - `StateFlow<List<Review>>` para reseñas de un producto
    - `StateFlow<Double>` para rating promedio
    - `StateFlow<Int>` para cantidad de reseñas
    - Función `obtenerReseñasPorProducto(productId: String)` - Cargar reseñas de un producto
    - Función `agregarReseña(productId: String, userId: Int, rating: Int, comentario: String)` - Agregar nueva reseña
    - Función `verificarUsuarioYaReseñó(productId: String, userId: Int): Boolean` - Verificar si usuario ya reseñó
    - Función `calcularRatingPromedio(productId: String)` - Calcular rating promedio
  - **Lógica de agregar reseña**:
    1. Validar que usuario está autenticado
    2. Validar que rating está entre 1-5
    3. Validar que comentario no está vacío
    4. Verificar si usuario ya reseñó este producto (opcional: permitir solo una reseña por producto)
    5. Obtener nombre del usuario de `UserEntity`
    6. Crear `ReviewEntity` con fecha actual
    7. Insertar en base de datos
    8. Actualizar rating promedio y cantidad de reseñas
    9. Recargar lista de reseñas
  - **Consideraciones**:
    - Usar `viewModelScope.launch` para operaciones asíncronas
    - Manejar errores con try-catch
    - Actualizar StateFlow después de operaciones exitosas

- [ ] **Actualizar ProductDetailScreen para mostrar reseñas desde base de datos y permitir agregar nuevas**
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/productdetail/ProductDetailScreen.kt`
  - **Modificaciones**:
    - Integrar `ReviewViewModel` para cargar reseñas desde BD (no solo del JSON)
    - Mostrar reseñas combinadas: default (del JSON o BD) + nuevas (de usuarios)
    - Agregar sección para que usuario autenticado agregue reseña:
      - Formulario con rating (1-5 estrellas) y campo de texto para comentario
      - Botón "Agregar Reseña"
      - Validación: Solo mostrar si usuario está autenticado
      - Validación: No permitir agregar si usuario ya reseñó (opcional)
    - Actualizar rating promedio y cantidad de reseñas mostrados
    - Mostrar indicador visual si reseña es del usuario actual
  - **UI sugerida**:
    - Sección "Agregar tu reseña" (solo si autenticado)
    - Input para rating (selector de estrellas)
    - TextField para comentario
    - Botón "Enviar Reseña"
    - Lista de reseñas con indicador de "Tu reseña" si es del usuario actual

- [ ] **Actualizar ProductViewModel o crear lógica para combinar reseñas del JSON con reseñas de BD**
  - **Contexto**: Los productos vienen del JSON con reseñas, pero ahora también tenemos reseñas en BD
  - **Opciones**:
    - **Opción A**: Modificar `ProductViewModel` para cargar reseñas desde BD y combinarlas con las del JSON
    - **Opción B**: Cargar reseñas solo desde BD (migrar todas las del JSON a BD al inicio)
  - **Recomendación**: Opción B (más limpio, todas las reseñas en un solo lugar)
  - **Implementación**:
    - Al cargar productos, no usar reseñas del JSON directamente
    - Cargar reseñas desde `ReviewViewModel` usando `productId`
    - Si no hay reseñas en BD para un producto, cargar las default del JSON y guardarlas en BD
  - **Archivos a modificar**:
    - `ProductViewModel.kt` - Modificar lógica de carga de productos
    - `ProductMapper.kt` - Actualizar para no incluir reseñas del JSON directamente

- [ ] **Agregar migración de base de datos para ReviewEntity**
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`
  - **Pasos**:
    1. Incrementar versión de base de datos (de 3 a 4, o de 4 a 5 si ya se agregó PurchaseEntity)
    2. Crear migración correspondiente:
       - Crear tabla `reseñas` con campos necesarios
    3. Agregar `ReviewEntity` a la lista de entidades en `@Database`
    4. Agregar `reviewDao(): ReviewDao` al `AppDatabase`
    5. Agregar migración al builder con `.addMigrations(MIGRATION_X_Y)`
  - **SQL sugerido**:
    ```sql
    CREATE TABLE reseñas (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        productId TEXT NOT NULL,
        userId INTEGER,
        autor TEXT NOT NULL,
        fecha TEXT NOT NULL,
        rating INTEGER NOT NULL,
        comentario TEXT NOT NULL
    );
    ```
  - **Consideraciones**:
    - `userId` es nullable (para reseñas default)
    - No hay Foreign Key a `usuario` porque las reseñas default no tienen usuario
    - `productId` es String (productos vienen de JSON, no de BD)

#### 📋 Planificación y Documentación (IMPORTANTE - Mejora nota)
- [ ] **Verificar y documentar Trello**
  - **Contexto**: Requisito de la rúbrica - planificación visible
  - **Acción**: 
    - Verificar si existe Trello del equipo
    - Si existe: Documentar link en README.md
    - Si no existe: Crear Trello básico con tareas principales
    - Migrar tareas de este archivo a Trello
  - **Documentación**: Agregar sección en README sobre planificación

#### 🎨 Mejoras de UI/UX (Opcional - Mejora experiencia)
- [x] **Implementar pantalla de Splash con logo de Mil Sabores** ✅ COMPLETADO
  - **Contexto**: Pantalla inicial que se muestra al abrir la app, con el logo de la pastelería
  - **Archivo creado**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/splash/SplashScreen.kt`
  - **Logo**: El logo ya existe en `app/src/main/res/drawable/logo_milsabores.png`
  - **Implementado**:
    - Composable `SplashScreen` creado con diseño centrado
    - Logo mostrado usando `Image(painter = painterResource(id = R.drawable.logo_milsabores), ...)`
    - Animación de scale implementada con `spring` animation (DampingRatioMediumBouncy)
    - `LaunchedEffect` con `delay(2000ms)` para mostrar la pantalla por 2 segundos
    - Navegación automática a Login o Home según estado de autenticación
    - Texto "Mil Sabores" y "Pastelería" agregado debajo del logo
  - **Diseño**:
    - Fondo: `CardWhite`
    - Logo: Centrado vertical y horizontalmente, tamaño 250.dp
    - Texto con estilos Material3 (headlineMedium y titleMedium)
  - **Navegación**:
    - `AppNavigation.kt` configurado con `startDestination = Screen.Splash.route`
    - Ruta `Splash` agregada en `Screen.kt`
    - Navegación con `popUpTo(Screen.Splash.route) { inclusive = true }` para remover splash del back stack
  - **Archivos modificados**:
    - ✅ `app/src/main/java/com/example/milsaborestest/presentation/navigation/AppNavigation.kt` (ruta Splash agregada)
    - ✅ `app/src/main/java/com/example/milsaborestest/presentation/navigation/Screen.kt` (objeto Splash agregado)
    - ✅ `app/src/main/java/com/example/milsaborestest/presentation/ui/MainContent.kt` (lógica para ocultar bottom bar en splash)
  - **Correcciones aplicadas**:
    - Animación corregida: Reemplazado `OvershootInterpolator` (Android View) por `spring` de Compose
    - Tipo de parámetro corregido: `NavHostController` en lugar de `NavController`
  - **Estado**: Funcionando correctamente

---


## 📊 Resumen de Estado

| Columna | Cantidad | Porcentaje |
|---------|----------|------------|
| 🟢 Done | 50+ | ~60% |
| 🟠 Code Review | 1 | ~1% |
| 🟡 Doing | 0 | ~0% |
| 🔵 Backlog (Crítico) | 28 | ~33% |
| 🔵 Backlog (Post-Evaluación) | 5+ | ~6% |

### 📈 Progreso para Evaluación

**Tareas Críticas Completadas:**
- ✅ Recursos Nativos: 12/13 tareas (92%) - **CASI COMPLETADO** (Notificaciones ✅✅, Galería ✅✅, solo falta imágenes por defecto en productos)
- ✅ README.md: 1/1 tarea (100%) - **COMPLETADO**
- ✅ Animaciones: 4/4 tareas (100%) - **COMPLETADO** ✨
- ✅ Splash Screen: 1/1 tarea (100%) - **COMPLETADO**

**Tareas Críticas Pendientes:**
- ❌ Trello: 0/1 tarea (0%) - **PENDIENTE**
- ❌ Migración de Productos (JSON → Room): 0/9 tareas (0%) - **PENDIENTE**
- ❌ Checkout e Historial de Compras: 0/8 tareas (0%) - **PENDIENTE**
- ❌ Sistema de Reseñas: 0/9 tareas (0%) - **PENDIENTE**
- ⚠️ Imágenes por defecto en productos: 0/1 tarea (0%) - **PENDIENTE** (no crítico)

**Total crítico pendiente: 27 tareas** (1 Trello + 9 Migración + 8 Checkout + 9 Reseñas)

---

## 🎯 Prioridades para Evaluación Parcial 2

### 🔴 CRÍTICO (Hacer primero - Bloqueadores)
1. **Implementar recursos nativos - Implementación Mínima** ⚠️ REQUISITO OBLIGATORIO
   - Notificaciones (carrito abandonado)
   - Galería (foto de perfil - seleccionar de galería)
   - Modificar UserEntity y migración de BD
   - UI para foto de perfil
   - **Sin esto: 0% en IE 2.4.1 (15% de la nota)**

2. ~~**Crear README.md completo**~~ ✅ **COMPLETADO**
   - Descripción, nombres, funcionalidades, instrucciones
   - **Estado**: Implementado y actualizado

### 🟡 IMPORTANTE (Mejorar nota significativamente)
3. ~~**Mejorar animaciones** (transiciones, feedback)~~ ✅ **COMPLETADO**
   - Estado anterior: 60% en IE 2.2.2
   - **Estado actual: 100% - Animaciones implementadas completamente**
   - Impacto: +4% en nota final alcanzado
   - **Implementaciones**:
     - ✅ Transiciones entre pantallas con AnimatedVisibility y Crossfade
     - ✅ Animaciones de feedback en ProductCard, CategoryCard y LoginScreen
     - ✅ Animaciones de carga mejoradas con transiciones suaves
     - ✅ Componentes helper reutilizables (AnimationHelpers.kt)

4. **Verificar y documentar Trello**
   - Actualmente: 60% en IE 2.3.2
   - Con Trello visible: Puede llegar a 100% (20% de la nota)
   - Impacto: +8% en nota final


---

## 📝 Notas

- Las tareas en **Done** están completadas y funcionando
- Las tareas en **Backlog** están priorizadas según importancia para el encargo
- Las tareas futuras pueden implementarse después de la evaluación
- Este archivo debe actualizarse conforme se completen tareas
- Al migrar a Trello, mantener la misma estructura de columnas
- **Todas las tareas incluyen contexto técnico y pasos específicos** para facilitar implementación con IA o desarrollo manual

---

**Última actualización**: 26-11-2025  
**Próxima revisión**: Al completar mejoras pendientes (imágenes por defecto en productos, etc.)

### 🎉 Actualizaciones Recientes

**26-11-2025 - Sistema de Galería y Foto de Perfil Completado**
- ✅ Implementado `ImageHelper.kt` con funciones completas (uriToBitmap, saveProfileImage, loadProfileImage, deleteProfileImage)
- ✅ Photo Picker implementado con `ActivityResultContracts.PickVisualMedia()` (NO requiere permisos explícitos)
- ✅ Imágenes por defecto creadas (`ic_profile_default.xml`, `ic_product_default.xml`)
- ✅ `AccountScreen.kt` actualizado con selector de galería y componente `ProfileImage` reutilizable
- ✅ `MainContent.kt` (NavigationDrawerContent) actualizado para mostrar foto de perfil
- ✅ `AuthViewModel` con función `updateProfilePhoto()` para actualizar foto en BD
- ✅ Migración de BD `MIGRATION_2_3` implementada (versión 2 → 3)
- ✅ Manejo completo de errores (muestra imagen por defecto en todos los casos)
- ✅ Correcciones aplicadas: Imports, parámetros no utilizados, uso correcto de Photo Picker
- 📝 Commits: "[ FEAT ]: Implementar sistema de foto de perfil con galería", "[ FIX ]: Corregir errores en AccountScreen y MainContent"

**25-11-2025 - Splash Screen y Mejoras de Notificaciones**
- ✅ Implementada pantalla de Splash con logo de Mil Sabores
- ✅ Animación de scale con spring animation (DampingRatioMediumBouncy)
- ✅ Navegación automática según estado de autenticación
- ✅ Correcciones aplicadas: Animación de Compose, NavHostController
- ✅ Mejoras en sistema de notificaciones:
  - Notificación con contenido completo (BigTextStyle)
  - IMPORTANCE_HIGH y PRIORITY_HIGH para mejor visibilidad
  - PendingIntent corregido para evitar reinicio de app
  - launchMode="singleTop" en MainActivity
  - Navegación mejorada desde notificaciones
- ✅ Agregado `enableOnBackInvokedCallback="true"` al AndroidManifest
- 📝 Commits: "[ FEAT ]: Implementar pantalla de Splash" y "[ FIX ]: Corregir errores de compilación y mejorar sistema de notificaciones"

**25-11-2025 - Animaciones Mejoradas Completadas**
- ✅ Implementadas todas las animaciones de feedback en componentes
- ✅ Transiciones suaves entre estados en pantallas principales
- ✅ Creado archivo AnimationHelpers.kt con componentes reutilizables
- ✅ Animaciones de scale, rotation, fade, y slide implementadas
- 📝 Commit: "[ FEAT ]: Implementar animaciones mejoradas en toda la aplicación"
- 🌿 Rama: feature/animaciones-mejoradas

---

## 📋 Estado Actual de Implementación (Revisión de Codebase)

### ✅ Tareas Completadas (Verificadas en Codebase)

1. **README.md**: ✅ Completado
   - Archivo existe en raíz del proyecto
   - Contiene toda la información requerida
   - Nombres de estudiantes incluidos

2. **TopNavBar - Hamburger a la derecha**: ✅ Completado
   - Implementado en `MainContent.kt` línea 271-277
   - Menú hamburger posicionado a la derecha después del carrito

3. **Sidebar - Ancho 75%**: ✅ Completado
   - Implementado en `MainContent.kt` línea 113
   - `ModalDrawerSheet` configurado con `0.75f` del ancho de pantalla

4. **Gestión de sesión - AuthViewModel compartido**: ✅ Completado
   - AuthViewModel compartido desde MainContent a AppNavigation
   - AuthViewModel pasado a LoginScreen, RegisterScreen y AccountScreen
   - Estado de autenticación consistente entre sidebar y pantallas
   - Implementado en `MainContent.kt`, `AppNavigation.kt` y pantallas de autenticación

5. **Modelo de datos - Foto de perfil**: ✅ Completado
   - Campo `fotoPerfil` agregado a `UserEntity.kt`
   - Campo `fotoPerfil` agregado al modelo de dominio `User.kt`
   - Migración `MIGRATION_2_3` implementada en `AppDatabase.kt`
   - Versión de BD actualizada de 2 a 3
   - Conversiones en `AuthViewModel.kt` actualizadas

6. **Sistema de notificaciones - Carrito abandonado**: ✅ Completado
   - `NotificationHelper.kt` creado con todas las funciones necesarias
   - Permisos de notificaciones configurados en `AndroidManifest.xml`
   - Canal de notificaciones creado en `MainActivity.onCreate()`
   - Lógica de detección de carrito abandonado en `MainActivity.onPause()`
   - Notificación se muestra inmediatamente cuando hay items en el carrito
   - Notificación incluye acción para abrir la app
   - Verificación de permisos antes de mostrar notificaciones

### ❌ Tareas Pendientes (Verificadas en Codebase)

1. **SplashScreen**: ✅ COMPLETADO
   - ✅ Archivo `SplashScreen.kt` creado e implementado
   - ✅ Ruta `Screen.Splash` agregada en `Screen.kt`
   - ✅ `AppNavigation.kt` tiene ruta de Splash configurada como `startDestination`
   - ✅ Animación de scale con spring animation implementada
   - ✅ Navegación automática según estado de autenticación
   - ✅ Correcciones aplicadas: Animación de Compose, NavHostController

2. **Recursos Nativos - Notificaciones**: ✅ COMPLETADO Y MEJORADO
   - ✅ `NotificationHelper.kt` creado e implementado
   - ✅ Permisos de notificaciones agregados en `AndroidManifest.xml`
   - ✅ `MainActivity.kt` tiene lógica de `onPause()` para notificaciones
   - ✅ Canal de notificaciones creado en `onCreate()`
   - ✅ Detección de carrito abandonado funcionando
   - ✅ Notificación se muestra inmediatamente al perder foco de la app
   - ✅ **Mejoras aplicadas**:
     - Notificación con contenido completo (título y cuerpo) usando `BigTextStyle`
     - `IMPORTANCE_HIGH` y `PRIORITY_HIGH` para mejor visibilidad
     - `PendingIntent` corregido para evitar reinicio de app
     - `launchMode="singleTop"` en MainActivity
     - Navegación mejorada desde notificaciones en `MainContent.kt`

3. **Recursos Nativos - Galería**: ✅ COMPLETADO
   - ✅ `UserEntity.kt` tiene campo `fotoPerfil` (completado)
   - ✅ Migración de BD implementada (MIGRATION_2_3, versión 2 → 3)
   - ✅ `ImageHelper.kt` creado e implementado con todas las funciones necesarias
   - ✅ Photo Picker implementado (NO requiere permisos explícitos - comportamiento correcto)
   - ✅ `AccountScreen.kt` tiene selector de galería y muestra foto de perfil
   - ✅ `MainContent.kt` (NavigationDrawerContent) muestra foto de perfil
   - ✅ Imágenes por defecto creadas (`ic_profile_default.xml`, `ic_product_default.xml`)
   - ✅ `AuthViewModel` tiene función `updateProfilePhoto()` para actualizar foto
   - ✅ Componente `ProfileImage` reutilizable creado

4. **Imágenes por defecto en productos**: ✅ COMPLETADO
   - ✅ Drawables `ic_product_default` y `ic_profile_default` creados
   - ✅ `ProductCard.kt` tiene `placeholder`, `error` y `fallback` en `AsyncImage`
   - ✅ `ProductCarousel.kt` tiene `placeholder`, `error` y `fallback` en `AsyncImage`
   - ✅ `ProductDetailScreen.kt` tiene `placeholder`, `error` y `fallback` en `AsyncImage`
   - ✅ `CartScreen.kt` tiene `placeholder`, `error` y `fallback` en `AsyncImage`
   - ✅ Todos los componentes manejan correctamente:
     - Imagen por defecto mientras carga (placeholder)
     - Imagen por defecto si falla la carga (error)
     - Imagen por defecto si URL es null/vacía (fallback)
   - ✅ Rama `feature/imagenes-por-defecto-productos` creada
   - ✅ Commit: `[ FEAT ]: Implementar imágenes por defecto en componentes de productos`

5. **Foto de perfil en AccountScreen**: ✅ COMPLETADO
   - ✅ `AccountScreen.kt` muestra foto de perfil con componente `ProfileImage`
   - ✅ Lógica condicional completa para cargar foto desde storage
   - ✅ FloatingActionButton para seleccionar foto de galería
   - ✅ Manejo de errores completo (muestra imagen por defecto en todos los casos)

6. **Migración de Productos de JSON a Room Database**: ❌ PENDIENTE
   - ❌ `CategoryEntity.kt` no existe (entidad para categorías)
   - ❌ `ProductEntity.kt` no existe (entidad para productos)
   - ❌ `CategoryDao.kt` no existe (DAO para categorías)
   - ❌ `ProductDao.kt` no existe (DAO para productos)
   - ❌ `CategoryMapper.kt` no existe (mapper para categorías)
   - ❌ `ProductMapper.kt` no está actualizado para usar Entity (solo tiene mapper de DTO)
   - ❌ `AppDatabase.kt` no tiene tablas `categorias` ni `productos`
   - ❌ `AppDatabase.kt` no carga productos/categorías default desde JSON (solo carga usuarios)
   - ❌ `ProductRepositoryImpl.kt` usa `ProductJsonDataSource` para todas las consultas (no usa DAO)
   - ❌ `AppModule.kt` no inyecta `CategoryDao` ni `ProductDao`
   - ⚠️ **Estado actual**: Productos se cargan completamente desde JSON (`productos.json` en assets)
   - ⚠️ **Objetivo**: Migrar a Room Database usando misma metodología que usuarios por defecto

7. **Checkout e Historial de Compras**: ✅ COMPLETADO
   - ✅ `PurchaseEntity.kt` y `PurchaseItemEntity.kt` creados e implementados
   - ✅ `PurchaseDao.kt` creado con todas las queries necesarias
   - ✅ Modelos de dominio `Purchase.kt` y `PurchaseItem.kt` creados
   - ✅ `PurchaseViewModel.kt` implementado con lógica de checkout y gestión de historial
   - ✅ `PurchaseHistoryScreen.kt` creada con UI completa y animaciones
   - ✅ `CartScreen.kt` actualizado con botón de checkout funcional y validaciones
   - ✅ `AppDatabase.kt` actualizado con nuevas tablas y migración `MIGRATION_3_4`
   - ✅ Funcionalidad de checkout simula compra, guarda en BD y limpia carrito
   - ✅ Persistencia de historial de compras por usuario implementada
   - ✅ Navegación integrada en `AppNavigation.kt` y `MainContent.kt` (Drawer)

8. **Sistema de Reseñas**: ✅ COMPLETADO
   - ✅ `ReviewEntity.kt` definida con índices y FK a usuarios (tabla `reseñas`)
   - ✅ `ReviewDao.kt` creado con inserciones masivas, conteos y queries por producto/usuario
   - ✅ Modelo `Review.kt` ahora incluye `id` y `userId` para identificar al autor
   - ✅ `ReviewMapper.kt` agrega conversiones entre DTO ↔ Entity ↔ Domain
   - ✅ `ReviewViewModel.kt` gestiona carga, validaciones y envío de reseñas con `StateFlow`
   - ✅ `AppDatabase.kt` versión 5 con `ReviewEntity`, `reviewDao()` y migración `MIGRATION_4_5`
   - ✅ Carga inicial de reseñas desde `productos.json` en `insertarDatosPorDefecto`
   - ✅ `ProductDetailScreen.kt` consume `ReviewViewModel`, muestra reseñas de Room y formulario
   - ✅ Usuarios autenticados pueden agregar una reseña por producto (validaciones y feedback)
   - ✅ Reseñas se persisten en Room y actualizan rating/cantidad en tiempo real