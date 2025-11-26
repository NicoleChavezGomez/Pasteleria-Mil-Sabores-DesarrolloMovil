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

- [ ] **Implementar recursos nativos - Fase mínima (Notificaciones + Galería)** 🔴 CRÍTICO
  - **Contexto**: Requisito crítico del encargo - al menos 2 recursos nativos
  - **Recursos a implementar**:
    1. Notificaciones: Recordatorio de carrito abandonado
    2. Galería: Foto de perfil de usuario (seleccionar de galería)
  - **Archivos principales a modificar/crear**:
    - `AndroidManifest.xml` (permisos)
    - `NotificationHelper.kt` (nuevo)
    - `ImageHelper.kt` (nuevo)
    - `UserEntity.kt` (agregar campo fotoPerfil)
    - `AppDatabase.kt` (migración)
    - `CartViewModel.kt` (lógica de notificaciones)
    - `AccountScreen.kt` (UI de foto de perfil)
    - `NavigationDrawerContent.kt` (mostrar foto)
  - **Ver tareas detalladas en Backlog** para pasos específicos

---

## 🔵 Backlog

### 🔴 PRIORIDAD ALTA - Tareas Críticas para Evaluación

#### 📱 Recursos Nativos (CRÍTICO - Requisito del encargo) - IMPLEMENTACIÓN MÍNIMA

#### Notificaciones
- [ ] **Configurar permisos de notificaciones en AndroidManifest**
  - **Archivo**: `app/src/main/AndroidManifest.xml`
  - **Acción**: Agregar dentro de `<manifest>`:
    ```xml
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    ```
  - **Nota**: Para Android 13+ (API 33+) este permiso es obligatorio
  - **Para versiones anteriores**: No se requiere permiso explícito
  - **Verificar**: El permiso debe estar antes de `<application>`

- [ ] **Crear NotificationHelper/NotificationManager**
  - **Ubicación**: `app/src/main/java/com/example/milsaborestest/util/NotificationHelper.kt`
  - **Responsabilidades**:
    - Crear canal de notificaciones (Android 8.0+)
    - Construir y mostrar notificaciones
    - Gestionar IDs de notificaciones
  - **Implementación**:
    - Clase `object NotificationHelper` (singleton)
    - Función `createNotificationChannel(context: Context)` - llamar en Application o MainActivity
    - Función `showCartReminderNotification(context: Context, itemCount: Int)`
    - Usar `NotificationCompat.Builder` para compatibilidad
    - Icono: Usar `R.drawable.ic_notification` o similar
  - **Canal de notificación**:
    - ID: "cart_reminder_channel"
    - Nombre: "Recordatorios de Carrito"
    - Descripción: "Notificaciones sobre productos en tu carrito"
    - Importancia: `NotificationManager.IMPORTANCE_DEFAULT`
  - **Dependencias**: `androidx.core:core-ktx` (ya incluida)

- [ ] **Implementar lógica de carrito abandonado**
  - **Contexto**: Detectar cuando usuario sale de la app con items en carrito y mostrar notificación inmediatamente
  - **Archivo a modificar**: `app/src/main/java/com/example/milsaborestest/MainActivity.kt`
  - **Implementación**:
    - Override `onPause()` en MainActivity
    - Verificar si hay items en carrito usando CartViewModel
    - Si `cartItems.isNotEmpty()` → Mostrar notificación inmediatamente usando `NotificationHelper`
    - No usar delay ni programación, mostrar al instante cuando se pierde el foco
  - **Lógica**:
    - Si `cartItems.isNotEmpty()` → Mostrar notificación de carrito abandonado
    - Si `cartItems.isEmpty()` → No hacer nada
  - **Consideraciones**:
    - Solo mostrar cuando la app pierde foco (onPause), no cuando está activa
    - Verificar permisos de notificación antes de mostrar
    - Mensaje amigable: "Tienes X productos en tu carrito. ¡No te los pierdas!"
    - La notificación debe tener acción para abrir la app y ir al carrito
  - **UX**: Notificación clara y útil, no intrusiva

- [ ] **Integrar notificaciones en CartViewModel**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/CartViewModel.kt`
  - **Modificaciones**:
    - Agregar función `scheduleCartReminderNotification(context: Context)`
    - Agregar función `cancelCartReminderNotification(context: Context)`
    - Llamar `cancelCartReminderNotification()` cuando `clearCart()` se ejecuta
    - Observar cambios en `cartItems` para cancelar notificación si se vacía
  - **Implementación**:
    - Usar `Application` context (no Activity context)
    - Verificar `totalItems.value > 0` antes de programar
    - Usar `NotificationHelper.showCartReminderNotification()`
  - **Testing**: Probar agregando items, saliendo de app, verificando notificación

#### Galería y Foto de Perfil
- [ ] **Configurar permisos de galería en AndroidManifest**
  - **Archivo**: `app/src/main/AndroidManifest.xml`
  - **Permisos a agregar** dentro de `<manifest>`:
    ```xml
    <!-- Almacenamiento (para leer imágenes de la galería) -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" 
                     android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    ```
  - **Notas**:
    - `READ_EXTERNAL_STORAGE` solo para Android 12 y anteriores (API 32-)
    - `READ_MEDIA_IMAGES` para Android 13+ (API 33+)
    - **NO se requiere permiso de cámara** - solo lectura de medios
    - **Ventaja**: Más simple que cámara, no requiere FileProvider
  - **Verificar**: Permisos antes de `<application>`

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

- [ ] **Crear imágenes por defecto en drawable**
  - **Ubicación**: `app/src/main/res/drawable/`
  - **Imágenes a crear**:
    - `ic_profile_default.png` - Avatar por defecto para usuarios
    - `ic_product_default.png` - Imagen por defecto para productos
  - **Diseño**: Se agregaran unas imagenes en drawable para este fin
  - **Uso**: Se usarán cuando no haya foto o falle la carga

- [ ] **Implementar ImageHelper/ImageManager**
  - **Ubicación**: `app/src/main/java/com/example/milsaborestest/util/ImageHelper.kt`
  - **Responsabilidades**:
    - Guardar imagen seleccionada de galería en storage interno
    - Leer imagen desde storage
    - Convertir entre Bitmap, File, y URI
    - Manejar errores y casos edge
  - **Implementación**:
    - Clase `object ImageHelper`
    - Función `uriToBitmap(context: Context, uri: Uri): Bitmap?`
      - Convertir URI de galería a Bitmap
      - Usar `context.contentResolver.openInputStream(uri)`
      - Usar `BitmapFactory.decodeStream()`
      - Retornar Bitmap o null si falla
      - Manejar excepciones (FileNotFoundException, IOException)
    - Función `saveProfileImage(context: Context, bitmap: Bitmap, userId: Int): String?`
      - Guardar en `context.filesDir` o `context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)`
      - Nombre: "profile_${userId}.jpg"
      - Retornar ruta del archivo guardado o null si falla
      - Manejar excepciones (IOException, SecurityException)
    - Función `loadProfileImage(context: Context, imagePath: String?): Bitmap?`
      - Leer archivo desde ruta
      - Verificar que el archivo existe antes de leer
      - Retornar Bitmap o null si no existe o hay error
      - Manejar excepciones (FileNotFoundException, IOException)
    - Función `deleteProfileImage(context: Context, imagePath: String?): Boolean`
      - Eliminar imagen antigua al actualizar
      - Retornar true si se eliminó, false si no existía o hubo error
  - **Storage interno vs externo**:
    - **Interno** (`filesDir`): Privado, se elimina con la app
    - **Externo** (`getExternalFilesDir`): Accesible por usuario, se elimina con la app
    - Recomendación: Usar storage interno para privacidad
  - **Manejo de errores**: Todas las funciones deben manejar excepciones y retornar null/false en caso de error
  - **Dependencias**: `android.graphics.Bitmap`, `android.net.Uri`, `android.content.ContentResolver`, `java.io.File`

- [ ] **Implementar ActivityResultLauncher para galería**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/account/AccountScreen.kt`
  - **Implementación**:
    - Crear `rememberLauncherForActivityResult` con `ActivityResultContracts.PickVisualMedia()`
    - **Ventaja**: No requiere FileProvider ni archivos temporales
    - Configurar para seleccionar solo imágenes:
      ```kotlin
      val pickMedia = rememberLauncherForActivityResult(
          contract = ActivityResultContracts.PickVisualMedia()
      ) { uri ->
          // uri es null si el usuario canceló
          if (uri != null) {
              // Procesar imagen seleccionada
          }
      }
      ```
  - **Flujo**:
    1. Usuario presiona botón "Seleccionar foto" o "Cambiar foto"
    2. Verificar permisos (usar `rememberPermissionState` o `ActivityResultLauncher`)
    3. Si tiene permisos → Lanzar selector de galería con `pickMedia.launch(PickVisualMediaRequest(...))`
    4. En callback → Obtener URI → Convertir a Bitmap → Guardar con ImageHelper → Actualizar UserEntity
  - **Manejo de permisos**:
    - Usar `ActivityResultContracts.RequestPermission()` para Android 13+ (READ_MEDIA_IMAGES)
    - Para Android 12 y anteriores, usar READ_EXTERNAL_STORAGE
    - **Nota**: En Android 13+, el sistema puede manejar permisos automáticamente con PickVisualMedia
  - **Dependencias**: `androidx.activity:activity-compose` (ya incluida)
  - **Código de ejemplo**:
    ```kotlin
    // Lanzar selector de galería
    pickMedia.launch(
        PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly
        )
    )
    ```

- [ ] **Actualizar AccountScreen con foto de perfil y manejo de errores**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/account/AccountScreen.kt`
  - **Modificaciones**:
    - Reemplazar `Image(painterResource(R.drawable.logo_milsabores))` con lógica condicional
    - **Lógica condicional para foto de perfil**:
      1. Si `user.fotoPerfil != null` → Intentar cargar desde storage
      2. Si carga exitosa → Mostrar imagen con Coil o ImageHelper
      3. Si falla carga (archivo no existe, error) → Mostrar imagen por defecto `R.drawable.ic_profile_default`
      4. Si `user.fotoPerfil == null` → Mostrar imagen por defecto `R.drawable.ic_profile_default`
    - Usar `AsyncImage` de Coil con `placeholder` y `error`:
      ```kotlin
      AsyncImage(
          model = File(user.fotoPerfil),
          contentDescription = "Foto de perfil",
          placeholder = painterResource(R.drawable.ic_profile_default),
          error = painterResource(R.drawable.ic_profile_default),
          fallback = painterResource(R.drawable.ic_profile_default)
      )
      ```
    - Agregar botón "Editar foto" o hacer el avatar clickeable
    - Al hacer click → Lanzar ActivityResultLauncher de galería
  - **UI**:
    - Avatar circular de 100.dp
    - Botón flotante pequeño para editar (opcional)
    - Mostrar loading mientras se procesa imagen (placeholder)
  - **Estado**:
    - Manejar estados: Loading, Success, Error
    - En caso de error, siempre mostrar imagen por defecto
  - **Casos a manejar**:
    - `fotoPerfil == null` → Imagen por defecto
    - `fotoPerfil != null` pero archivo no existe → Imagen por defecto
    - Error al leer archivo → Imagen por defecto
    - Timeout de carga → Imagen por defecto

- [ ] **Actualizar Sidebar con foto de perfil y manejo de errores**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/MainContent.kt`
  - **Función**: `NavigationDrawerContent`
  - **Modificaciones**:
    - En la sección de información de usuario (cuando `isAuthenticated && user != null`)
    - Reemplazar o agregar avatar con foto de perfil
    - **Misma lógica condicional que AccountScreen**:
      - Si hay foto y carga exitosa → Mostrar foto
      - Si no hay foto o falla carga → Mostrar `R.drawable.ic_profile_default`
    - Tamaño sugerido: 64.dp (más pequeño que en AccountScreen)
  - **Implementación**:
    - Pasar `user?.fotoPerfil` como parámetro
    - Usar `AsyncImage` de Coil con `placeholder` y `error` apuntando a imagen por defecto
    - Mostrar en `Row` o `Column` junto con nombre y email
  - **Casos a manejar**: Igual que AccountScreen (null, archivo no existe, error de lectura)

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

- [ ] **Actualizar AuthViewModel para manejar foto**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/viewmodel/AuthViewModel.kt`
  - **Modificaciones**:
    - Agregar función `updateProfilePhoto(imagePath: String)`
      - Actualizar `UserEntity` con nueva ruta
      - Actualizar `_user.value` con nueva información
      - Manejar errores: Si falla guardar, no actualizar BD
    - Modificar `login()` para cargar foto de perfil al iniciar sesión
    - Considerar función `loadUserProfile()` que carga foto desde storage
    - Validar que la ruta existe antes de guardar en BD
  - **Flujo**:
    1. Usuario selecciona imagen de galería en AccountScreen
    2. AccountScreen convierte URI a Bitmap y guarda imagen con ImageHelper
    3. Si guardado exitoso → AccountScreen llama `authViewModel.updateProfilePhoto(ruta)`
    4. Si guardado falla → Mostrar error, no actualizar BD
    5. AuthViewModel actualiza UserEntity en BD
    6. AuthViewModel actualiza StateFlow de user
    7. UI se actualiza automáticamente
  - **Manejo de errores**: Si la imagen no se puede guardar, mostrar mensaje de error y mantener foto anterior
  - **Testing**: Verificar que foto persiste después de logout/login, y que se muestra por defecto si falla



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
- [ ] **Implementar pantalla de Splash con logo de Mil Sabores**
  - **Contexto**: Pantalla inicial que se muestra al abrir la app, con el logo de la pastelería
  - **Archivo a crear**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/splash/SplashScreen.kt`
  - **Logo**: El logo ya existe en `app/src/main/res/drawable/logo_milsabores.png`
  - **Implementación**:
    - Crear composable `SplashScreen` con diseño centrado
    - Mostrar el logo usando `Image(painter = painterResource(id = R.drawable.logo_milsabores), ...)`
    - Agregar animación de fade-in o scale para el logo (opcional pero recomendado)
    - Usar `LaunchedEffect` con `delay(2000-3000ms)` para mostrar la pantalla por 2-3 segundos
    - Después del delay, navegar a la pantalla principal (Login o Home según estado de autenticación)
  - **Diseño**:
    - Fondo: Usar `CardWhite` o color primario del tema
    - Logo: Centrado vertical y horizontalmente
    - Tamaño del logo: Aproximadamente 200-250dp de ancho (ajustar según necesidad)
    - Opcional: Agregar texto "Mil Sabores" o "Pastelería" debajo del logo
  - **Navegación**:
    - Modificar `AppNavigation.kt` para que `startDestination` sea `Screen.Splash.route`
    - Agregar ruta `Splash` en el enum `Screen` (si no existe)
    - En `SplashScreen`, después del delay, navegar a `Screen.Login.route` o `Screen.Home.route`
    - Usar `navController.navigate()` con `popUpTo(Screen.Splash.route) { inclusive = true }` para remover splash del back stack
  - **Archivos a modificar**:
    - `app/src/main/java/com/example/milsaborestest/presentation/navigation/AppNavigation.kt` (agregar ruta Splash)
    - `app/src/main/java/com/example/milsaborestest/presentation/navigation/Screen.kt` (agregar objeto Splash si no existe)
    - `app/src/main/java/com/example/milsaborestest/presentation/ui/MainContent.kt` (cambiar startDestination si es necesario)
  - **Consideraciones**:
    - La pantalla debe ser simple y rápida (no bloquear el inicio de la app)
    - Si el usuario ya está autenticado, navegar directamente a Home
    - Si no está autenticado, navegar a Login
    - Usar `rememberCoroutineScope()` para manejar la coroutine del delay
    - Considerar usar `AnimatedVisibility` o `AnimatedContent` para transiciones suaves
  - **Testing**: Verificar que la pantalla se muestra correctamente y navega después del delay

---


## 📊 Resumen de Estado

| Columna | Cantidad | Porcentaje |
|---------|----------|------------|
| 🟢 Done | 43+ | ~73% |
| 🟠 Code Review | 2 | ~3% |
| 🟡 Doing | 1 | ~2% |
| 🔵 Backlog (Crítico) | 12 | ~20% |
| 🟢 Post-Evaluación | 25+ | ~2% |

### 📈 Progreso para Evaluación

**Tareas Críticas Restantes:**
- ❌ Recursos Nativos: 0/12 tareas (0%) - **PENDIENTE**
- ✅ README.md: 1/1 tarea (100%) - **COMPLETADO**
- ✅ Animaciones: 4/4 tareas (100%) - **COMPLETADO** ✨
- ❌ Trello: 0/1 tarea (0%) - **PENDIENTE**

**Total crítico pendiente: 12 tareas**

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

**Última actualización**: 25-11-2025  
**Próxima revisión**: Al completar recursos nativos

### 🎉 Actualizaciones Recientes

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

### ❌ Tareas Pendientes (Verificadas en Codebase)

1. **SplashScreen**: ❌ No implementado
   - No existe archivo `SplashScreen.kt`
   - No existe ruta `Screen.Splash` en `Screen.kt`
   - `AppNavigation.kt` no tiene ruta de Splash
   - `startDestination` sigue siendo `Screen.Login.route`

2. **Recursos Nativos - Notificaciones**: ❌ No implementado
   - No existe `NotificationHelper.kt`
   - No hay permisos de notificaciones en `AndroidManifest.xml`
   - `MainActivity.kt` no tiene lógica de `onPause()` para notificaciones

3. **Recursos Nativos - Galería**: ⚠️ Parcialmente implementado
   - ✅ `UserEntity.kt` tiene campo `fotoPerfil` (completado)
   - ✅ Migración de BD implementada (completado)
   - ❌ No existe `ImageHelper.kt`
   - ❌ No hay permisos de galería en `AndroidManifest.xml`
   - ❌ `AccountScreen.kt` no tiene selector de galería
   - ❌ No hay imágenes por defecto (`ic_profile_default`, `ic_product_default`)

4. **Imágenes por defecto en productos**: ❌ No implementado
   - `ProductCard.kt` no tiene `placeholder`, `error`, ni `fallback` en `AsyncImage`
   - No existen drawables `ic_product_default` ni `ic_profile_default`

5. **Foto de perfil en AccountScreen**: ❌ No implementado
   - `AccountScreen.kt` muestra `logo_milsabores` en lugar de foto de perfil
   - No hay lógica condicional para cargar foto desde storage
   - No hay botón para seleccionar foto de galería
