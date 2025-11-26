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

- [ ] **Revisar código de autenticación**
  - **Contexto**: Verificar seguridad y manejo de errores en AuthViewModel
  - **Archivos a revisar**: 
    - `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/AuthViewModel.kt`
    - `app/src/main/java/com/example/milsaborestest/data/local/database/UserDao.kt`
  - **Verificar**:
    - Las contraseñas NO se almacenan en texto plano (actualmente sí, considerar hash en futuro)
    - Manejo de errores en login/register
    - Validaciones funcionan correctamente
    - Flujo de logout limpia correctamente el estado
  - **Testing**: Probar con usuarios válidos e inválidos

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
| 🟢 Done | 45+ | ~75% |
| 🟠 Code Review | 2 | ~3% |
| 🟡 Doing | 1 | ~2% |
| 🔵 Backlog (Crítico) | 10 | ~17% |
| 🟢 Post-Evaluación | 25+ | ~2% |

### 📈 Progreso para Evaluación

**Tareas Críticas Restantes:**
- ⚠️ Recursos Nativos: 4/10 tareas (40%) - **EN PROGRESO** (Notificaciones ✅✅, Galería ⏳)
- ✅ README.md: 1/1 tarea (100%) - **COMPLETADO**
- ✅ Animaciones: 4/4 tareas (100%) - **COMPLETADO** ✨
- ✅ Splash Screen: 1/1 tarea (100%) - **COMPLETADO**
- ❌ Trello: 0/1 tarea (0%) - **PENDIENTE**

**Total crítico pendiente: 6 tareas** (Notificaciones completadas y mejoradas, 6 de galería pendientes)

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

4. **Imágenes por defecto en productos**: ⚠️ Parcialmente implementado
   - ✅ Drawables `ic_product_default` y `ic_profile_default` creados
   - ❌ `ProductCard.kt` no tiene `placeholder`, `error`, ni `fallback` en `AsyncImage` (tarea independiente, no crítica)

5. **Foto de perfil en AccountScreen**: ✅ COMPLETADO
   - ✅ `AccountScreen.kt` muestra foto de perfil con componente `ProfileImage`
   - ✅ Lógica condicional completa para cargar foto desde storage
   - ✅ FloatingActionButton para seleccionar foto de galería
   - ✅ Manejo de errores completo (muestra imagen por defecto en todos los casos)
