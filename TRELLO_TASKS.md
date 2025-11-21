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

### ✅ Navegación y UI Base
- [x] **Sistema de navegación con Compose Navigation**
  - NavGraph configurado
  - Rutas definidas (Home, Products, Cart, Account, Login, Register)
  - Navegación entre pantallas funcional

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

### ✅ Control de Versiones
- [x] **Repositorio en GitHub**
  - Repositorio configurado
  - Commits con formato `[ TIPO ]: mensaje`
  - Ramas para features (feature/login, feature/basedatos)
  - Merge a main

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

- [ ] **Crear README.md completo** ⚠️ OBLIGATORIO
  - **Ubicación**: Archivo en raíz del proyecto `README.md`
  - **Contenido requerido**:
    - Descripción del proyecto (app de pastelería e-commerce)
    - Nombres de estudiantes (completar con datos reales)
    - Funcionalidades implementadas (listar todas las pantallas y features)
    - Instrucciones de ejecución:
      - Requisitos (Android Studio, JDK, etc.)
      - Pasos para clonar y ejecutar
      - Configuración de emulador/dispositivo
    - Tecnologías utilizadas:
      - Kotlin, Jetpack Compose, Material 3
      - Room Database, MVVM Architecture
      - Navigation Compose, Coroutines, StateFlow
  - **Formato**: Markdown con secciones claras
  - **Ejemplo de estructura**: Ver README.md de proyectos similares en GitHub

- [ ] **Implementar recursos nativos - Fase mínima (Notificaciones + Cámara)** 🔴 CRÍTICO
  - **Contexto**: Requisito crítico del encargo - al menos 2 recursos nativos
  - **Recursos a implementar**:
    1. Notificaciones: Recordatorio de carrito abandonado
    2. Cámara: Foto de perfil de usuario
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

#### Cámara y Foto de Perfil
- [ ] **Configurar permisos de cámara en AndroidManifest**
  - **Archivo**: `app/src/main/AndroidManifest.xml`
  - **Permisos a agregar** dentro de `<manifest>`:
    ```xml
    <!-- Cámara -->
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" android:required="false" />
    
    <!-- Almacenamiento (para guardar imágenes) -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" 
                     android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    ```
  - **Notas**:
    - `READ_EXTERNAL_STORAGE` solo para Android 12 y anteriores
    - `READ_MEDIA_IMAGES` para Android 13+ (API 33+)
    - `android:required="false"` permite que la app funcione en dispositivos sin cámara
  - **Verificar**: Permisos antes de `<application>`

- [ ] **Modificar UserEntity para foto de perfil**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/data/local/database/UserEntity.kt`
  - **Modificación**: Agregar campo nullable:
    ```kotlin
    @Entity(tableName = "usuario")
    data class UserEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val nombre: String,
        val email: String,
        val contrasena: String,
        val fotoPerfil: String? = null  // Ruta del archivo de imagen
    )
    ```
  - **Consideraciones**:
    - Campo nullable porque usuarios existentes no tendrán foto
    - Almacenar ruta relativa o nombre de archivo, no URI completo
    - Formato sugerido: "profile_${userId}.jpg" o similar
  - **UserDao**: No requiere cambios, Room maneja automáticamente

- [ ] **Crear migración de base de datos**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/data/local/database/AppDatabase.kt`
  - **Pasos**:
    1. Incrementar `version` de 2 a 3 en `@Database`
    2. Crear objeto `Migration`:
       ```kotlin
       val MIGRATION_2_3 = object : Migration(2, 3) {
           override fun migrate(database: SupportSQLiteDatabase) {
               database.execSQL("ALTER TABLE usuario ADD COLUMN fotoPerfil TEXT")
           }
       }
       ```
    3. Agregar migración al builder:
       ```kotlin
       .addMigrations(MIGRATION_2_3)
       ```
    4. Remover `fallbackToDestructiveMigration()` o mantenerlo solo para desarrollo
  - **Testing**: Verificar que usuarios existentes no pierden datos
  - **Nota**: Si usas `fallbackToDestructiveMigration()`, la migración no se ejecutará en desarrollo

- [ ] **Crear imágenes por defecto en drawable**
  - **Ubicación**: `app/src/main/res/drawable/`
  - **Imágenes a crear**:
    - `ic_profile_default.xml` o `ic_profile_default.png` - Avatar por defecto para usuarios
    - `ic_product_default.xml` o `ic_product_default.png` - Imagen por defecto para productos
  - **Recomendación**: Usar vector drawable (XML) para mejor escalado
  - **Diseño**: Iconos simples y consistentes con el tema de la app
  - **Uso**: Se usarán cuando no haya foto o falle la carga

- [ ] **Implementar ImageHelper/ImageManager**
  - **Ubicación**: `app/src/main/java/com/example/milsaborestest/util/ImageHelper.kt`
  - **Responsabilidades**:
    - Guardar imagen capturada en storage interno
    - Leer imagen desde storage
    - Convertir entre Bitmap, File, y URI
    - Manejar errores y casos edge
  - **Implementación**:
    - Clase `object ImageHelper`
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
  - **Dependencias**: `android.graphics.Bitmap`, `java.io.File`

- [ ] **Implementar ActivityResultLauncher para cámara**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/screens/account/AccountScreen.kt`
  - **Implementación**:
    - Crear `rememberLauncherForActivityResult` con `ActivityResultContracts.TakePicture()`
    - Necesitarás crear un `File` temporal con URI usando `FileProvider`
    - Configurar `FileProvider` en `AndroidManifest.xml`:
      ```xml
      <provider
          android:name="androidx.core.content.FileProvider"
          android:authorities="${applicationId}.fileprovider"
          android:exported="false"
          android:grantUriPermissions="true">
          <meta-data
              android:name="android.support.FILE_PROVIDER_PATHS"
              android:resource="@xml/file_paths" />
      </provider>
      ```
    - Crear `res/xml/file_paths.xml`:
      ```xml
      <paths>
          <external-files-path name="images" path="Pictures/" />
      </paths>
      ```
  - **Flujo**:
    1. Usuario presiona botón "Tomar foto"
    2. Verificar permisos (usar `rememberPermissionState` o `ActivityResultLauncher`)
    3. Si tiene permisos → Crear File temporal → Lanzar cámara
    4. En callback → Obtener Bitmap → Guardar con ImageHelper → Actualizar UserEntity
  - **Manejo de permisos**:
    - Usar `ActivityResultContracts.RequestPermission()` para Android 13+
    - Para versiones anteriores, permisos en tiempo de instalación
  - **Dependencias**: `androidx.activity:activity-compose` (ya incluida)

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
    - Al hacer click → Lanzar ActivityResultLauncher de cámara
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
    1. Usuario toma foto en AccountScreen
    2. AccountScreen guarda imagen con ImageHelper
    3. Si guardado exitoso → AccountScreen llama `authViewModel.updateProfilePhoto(ruta)`
    4. Si guardado falla → Mostrar error, no actualizar BD
    5. AuthViewModel actualiza UserEntity en BD
    6. AuthViewModel actualiza StateFlow de user
    7. UI se actualiza automáticamente
  - **Manejo de errores**: Si la imagen no se puede guardar, mostrar mensaje de error y mantener foto anterior
  - **Testing**: Verificar que foto persiste después de logout/login, y que se muestra por defecto si falla

#### 🎨 Mejoras de Animaciones (IMPORTANTE - Mejora nota)
- [ ] **Transiciones entre pantallas**
  - **Contexto**: Mejorar fluidez visual al navegar
  - **Archivos**: Pantallas de navegación (HomeScreen, ProductDetailScreen, etc.)
  - **Implementación**:
    - Usar `AnimatedContent` para transiciones entre estados
    - Usar `Crossfade` para cambios de contenido
    - Agregar `Modifier.animateContentSize()` donde sea apropiado
  - **Ejemplo**: Transición fade cuando cambia de categoría en AllProductsScreen
  - **Dependencias**: Ya incluidas en Compose

- [ ] **Animaciones de feedback**
  - **Contexto**: Retroalimentación visual en interacciones del usuario
  - **Implementación**:
    - Botones: Usar `Modifier.scale()` con `animateFloatAsState()` al presionar
    - Formularios: Animación de error con `AnimatedVisibility`
    - Éxito: Icono de check animado al agregar al carrito
  - **Archivos**: Componentes de botones, formularios, ProductCard
  - **Ejemplo**: En `ProductCard`, animar botón "Agregar" al hacer click

- [ ] **Animaciones de carga mejoradas**
  - **Contexto**: Mejorar experiencia durante estados de carga
  - **Archivos**: `SkeletonComponents.kt`, pantallas con loading states
  - **Mejoras**:
    - Agregar transición fade-in cuando skeleton se reemplaza por contenido
    - Animación de progreso circular para operaciones largas
    - Transición suave entre estados Loading → Success → Error
  - **Implementación**: Usar `AnimatedVisibility` con transiciones personalizadas

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
- [ ] **Reorganizar TopNavBar: Mover hamburger menu a la derecha**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/MainContent.kt`
  - **Función**: `TopNavBar`
  - **Cambio actual**:
    - Menú hamburger está a la izquierda (después del logo)
    - Carrito está a la derecha
  - **Cambio deseado**:
    - Mover menú hamburger al lugar donde está el carrito (derecha)
    - El carrito puede moverse a otro lugar o mantenerse junto al menú
  - **Implementación**:
    - En el `Row` de la derecha (línea ~234), cambiar el orden de los `IconButton`
    - Opción 1: Menú hamburger primero, luego carrito
    - Opción 2: Solo menú hamburger a la derecha, carrito se puede quitar (ya está en bottom bar)
    - Mantener el precio total si se muestra
  - **Consideraciones**: Verificar que el click del menú sigue funcionando correctamente

- [ ] **Aumentar ancho del Sidebar de 50% a 75%**
  - **Archivo**: `app/src/main/java/com/example/milsaborestest/presentation/ui/MainContent.kt`
  - **Línea actual**: `Modifier.width((screenWidthDp * 0.5f).dp)` (línea ~113)
  - **Cambio**: Cambiar a `Modifier.width((screenWidthDp * 0.75f).dp)`
  - **Implementación**:
    - Modificar `ModalDrawerSheet` dentro de `ModalNavigationDrawer`
    - Cambiar el multiplicador de `0.5f` a `0.75f`
  - **Consideraciones**:
    - Verificar que el contenido del drawer se vea bien con más espacio
    - Ajustar padding/spacing si es necesario
    - Probar en diferentes tamaños de pantalla

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
    - Modificar `NavGraph.kt` para que `startDestination` sea `Screen.Splash.route`
    - Agregar ruta `Splash` en el enum `Screen` (si no existe)
    - En `SplashScreen`, después del delay, navegar a `Screen.Login.route` o `Screen.Home.route`
    - Usar `navController.navigate()` con `popUpTo(Screen.Splash.route) { inclusive = true }` para remover splash del back stack
  - **Archivos a modificar**:
    - `app/src/main/java/com/example/milsaborestest/presentation/navigation/NavGraph.kt` (agregar ruta Splash)
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

### 🟢 PRIORIDAD BAJA - Tareas Post-Evaluación (No críticas para el encargo)

#### 📱 Recursos Nativos - Funcionalidades Futuras (Post-evaluación)
- [ ] **Implementar acceso a Galería**
  - **Contexto**: Para seleccionar imágenes existentes en lugar de tomar foto
  - **Archivo**: Similar a implementación de cámara
  - **Permisos**: Mismos que cámara (READ_MEDIA_IMAGES)
  - **ActivityResultLauncher**: Usar `ActivityResultContracts.PickVisualMedia()`
  - **Integración**: Agregar opción en AccountScreen: "Tomar foto" o "Seleccionar de galería"
  - **Uso futuro**: Para reseñas con imágenes

- [ ] **Implementar reseñas con imágenes**
  - **Contexto**: Permitir que usuarios suban fotos con sus reseñas
  - **Modificaciones**:
    - `Review` domain model: Agregar `imagenUri: String?`
    - Crear `ReviewEntity` en Room (migrar de JSON a BD)
    - `ReviewDao` con operaciones CRUD
    - Formulario de reseña con opción de subir imagen
  - **UI**: Mostrar imágenes en `ReviewItem` en ProductDetailScreen
  - **Storage**: Guardar imágenes de reseñas en carpeta separada

- [ ] **Implementar acceso a Ubicación**
  - **Contexto**: Para direcciones de entrega y pastelerías cercanas
  - **Permisos**: `ACCESS_FINE_LOCATION` o `ACCESS_COARSE_LOCATION`
  - **Implementación**: Usar Fused Location Provider API
  - **UI**: Formulario de dirección con botón "Usar mi ubicación actual"
  - **Consideraciones**: Solicitar permisos en runtime, manejar denegación

### 🧪 Testing y Calidad (Post-evaluación)
- [ ] **Tests unitarios para ViewModels**
  - **Contexto**: Validar lógica de negocio sin UI
  - **Archivos a crear**:
    - `AuthViewModelTest.kt`
    - `CartViewModelTest.kt`
    - `ProductViewModelTest.kt`
  - **Implementación**:
    - Usar `androidx.arch.core:core-testing` para LiveData/StateFlow
    - Usar `org.jetbrains.kotlinx:kotlinx-coroutines-test` para coroutines
    - Mock de repositorios y use cases
  - **Casos de prueba**:
    - Login exitoso/fallido
    - Agregar/eliminar del carrito
    - Validaciones de formularios
  - **Ubicación**: `app/src/test/java/com/example/milsaborestest/`

- [ ] **Tests de UI**
  - **Contexto**: Validar comportamiento de componentes Compose
  - **Archivos**: Tests para pantallas principales
  - **Implementación**:
    - Usar `androidx.compose.ui:ui-test-junit4`
    - Usar `createComposeRule()` para tests
    - Verificar que elementos se muestran correctamente
    - Simular interacciones (click, scroll)
  - **Casos**: Navegación, formularios, listas

### 📝 Documentación Técnica (Post-evaluación)
- [ ] **Documentar arquitectura**
  - **Formato**: Diagrama o documento Markdown
  - **Contenido**:
    - Diagrama de capas (Data, Domain, Presentation)
    - Flujo de datos (UI → ViewModel → Repository → DataSource)
    - Decisiones de diseño (por qué MVVM, por qué Room, etc.)
  - **Ubicación**: `docs/ARCHITECTURE.md` o sección en README
  - **Herramientas**: Mermaid para diagramas, o imágenes

- [ ] **Documentar componentes**
  - **Contexto**: KDoc/Javadoc para funciones y clases públicas
  - **Archivos**: Todos los componentes principales
  - **Formato**:
    ```kotlin
    /**
     * Componente que muestra una tarjeta de producto.
     * 
     * @param product El producto a mostrar
     * @param onItemClick Callback cuando se hace click en el producto
     */
    @Composable
    fun ProductCard(...)
    ```
  - **Prioridad**: ViewModels, Repositories, Componentes reutilizables

### 🚀 Funcionalidades Futuras
- [ ] **Sistema de favoritos**
  - **Entidad**: `FavoriteEntity` en Room (userId, productId)
  - **DAO**: `FavoriteDao` con queries para obtener favoritos de usuario
  - **UI**: Botón de corazón en ProductCard y ProductDetailScreen
  - **Integración**: Agregar a AccountScreen como opción de menú
  - **Persistencia**: Guardar en Room, sincronizar con estado de UI

- [ ] **Historial de pedidos**
  - **Entidad**: `OrderEntity` en Room (id, userId, fecha, total, items)
  - **DAO**: `OrderDao` con queries por usuario y fecha
  - **Pantalla**: `OrderHistoryScreen` con lista de pedidos
  - **Detalle**: `OrderDetailScreen` mostrando items del pedido
  - **Integración**: Agregar a AccountScreen → "Mis Pedidos"

- [ ] **Sistema de direcciones**
  - **Entidad**: `AddressEntity` en Room (id, userId, calle, ciudad, etc.)
  - **DAO**: `AddressDao` para gestionar direcciones de usuario
  - **UI**: Formulario de dirección, lista de direcciones guardadas
  - **Integración**: Selección de dirección en checkout (futuro)
  - **Opcional**: Integrar con API de geocoding para validar direcciones

- [ ] **Métodos de pago**
  - **Contexto**: Simulación de proceso de pago
  - **Entidad**: `PaymentMethodEntity` (tipo, último4, expiración)
  - **UI**: Formulario de tarjeta, lista de métodos guardados
  - **Integración**: Selección en checkout
  - **Nota**: NO implementar pago real, solo simulación

- [ ] **Búsqueda avanzada**
  - **Contexto**: Ya existe búsqueda básica, mejorar con filtros
  - **Mejoras**:
    - Filtros por rango de precio (slider)
    - Filtros por categoría múltiple
    - Ordenamiento avanzado (ya existe parcialmente)
    - Búsqueda por ingredientes
  - **UI**: Dialog o BottomSheet con opciones de filtro
  - **Archivo**: Mejorar `ProductFilters.kt`

- [ ] **Sistema de reseñas**
  - **Contexto**: Permitir que usuarios escriban reseñas (actualmente solo se muestran)
  - **Entidad**: `ReviewEntity` en Room (migrar de JSON)
  - **DAO**: `ReviewDao` con queries por producto
  - **UI**: Formulario de reseña en ProductDetailScreen
  - **Validación**: Usuario debe haber comprado el producto (futuro)
  - **Integración**: Mostrar reseñas propias del usuario

- [ ] **Compartir productos**
  - **Implementación**: Usar `ShareCompat` o `Intent.ACTION_SEND`
  - **Contenido**: Texto con nombre, precio, y link (si hay web)
  - **UI**: Botón de compartir en ProductDetailScreen
  - **Opcional**: Generar imagen compartible con información del producto

- [ ] **Modo offline**
  - **Contexto**: Funcionar sin conexión a internet
  - **Implementación**:
    - Cache de productos en Room (ya existe parcialmente)
    - Sincronización cuando hay conexión
    - Indicador de estado de conexión
  - **UI**: Banner o icono mostrando estado offline
  - **Consideraciones**: Productos y carrito ya funcionan offline

- [ ] **Temas (Dark Mode)**
  - **Implementación**: Crear `DarkColorScheme` en `Theme.kt`
  - **UI**: Switch en AccountScreen o Settings
  - **Persistencia**: Guardar preferencia en DataStore o SharedPreferences
  - **Material 3**: Ya tiene soporte nativo para dark mode

- [ ] **Internacionalización (i18n)**
  - **Contexto**: Soporte para múltiples idiomas
  - **Implementación**:
    - Mover todos los strings a `res/values/strings.xml`
    - Crear `res/values-es/strings.xml` para español
    - Usar `stringResource()` en lugar de strings hardcodeados
  - **UI**: Selector de idioma en settings
  - **Archivos**: Todas las pantallas necesitan refactorización

### 🔧 Mejoras Técnicas
- [ ] **Optimización de imágenes**
  - **Contexto**: Reducir uso de memoria y mejorar rendimiento
  - **Implementación**:
    - Compresión de imágenes antes de guardar (foto de perfil)
    - Usar `BitmapFactory.Options` con `inSampleSize`
    - Cache de imágenes con Coil (ya configurado)
  - **Archivos**: `ImageHelper.kt`, componentes que cargan imágenes

- [ ] **Mejoras de rendimiento**
  - **Contexto**: Optimizar queries y recomposiciones
  - **Implementación**:
    - Agregar índices en Room para queries frecuentes
    - Usar `remember` y `derivedStateOf` donde sea apropiado
    - LazyColumn con `key()` para mejor performance
  - **Archivos**: DAOs, ViewModels, pantallas con listas

- [ ] **Manejo de errores mejorado**
  - **Contexto**: Centralizar y mejorar mensajes de error
  - **Implementación**:
    - Crear `ErrorHandler` o `ExceptionMapper`
    - Mapear excepciones a mensajes amigables
    - Mostrar errores consistentemente (Snackbar, Dialog)
  - **Archivos**: ViewModels, Repositories

- [ ] **Logging y debugging**
  - **Contexto**: Mejorar debugging y monitoreo
  - **Implementación**:
    - Usar `Timber` o `Log` de forma estructurada
    - Agregar logs en puntos clave (login, errores, navegación)
    - Configurar niveles de log (DEBUG, RELEASE)
  - **Archivos**: Todos los ViewModels y Repositories

### 📱 Mejoras de UX
- [ ] **Pull to refresh**
  - **Contexto**: Actualizar datos deslizando hacia abajo
  - **Implementación**: Usar `SwipeRefresh` de Material 3
  - **Archivos**: `AllProductsScreen.kt`, `HomeScreen.kt`
  - **Funcionalidad**: Recargar productos desde JSON/BD

- [ ] **Empty states**
  - **Contexto**: Mostrar mensajes cuando no hay datos
  - **Implementación**: Crear componentes para estados vacíos
  - **Casos**: Carrito vacío, sin productos, sin favoritos
  - **UI**: Ilustración + mensaje + acción sugerida

- [ ] **Onboarding**
  - **Contexto**: Guía para nuevos usuarios
  - **Implementación**: Pantalla de bienvenida con pasos
  - **Persistencia**: Guardar en SharedPreferences si ya se mostró
  - **UI**: Usar `HorizontalPager` para pasos del tutorial

- [ ] **Mejoras de accesibilidad**
  - **Contexto**: Hacer la app accesible para todos
  - **Implementación**:
    - Agregar `contentDescription` a todos los iconos e imágenes
    - Verificar contraste de colores (WCAG AA mínimo)
    - Soporte para TalkBack (screen reader)
  - **Archivos**: Todos los componentes de UI
  - **Testing**: Usar Accessibility Scanner de Android

---

## 📊 Resumen de Estado

| Columna | Cantidad | Porcentaje |
|---------|----------|------------|
| 🟢 Done | 40+ | ~70% |
| 🟠 Code Review | 2 | ~3% |
| 🟡 Doing | 2 | ~3% |
| 🔵 Backlog (Crítico) | 12 | ~2% |
| 🟢 Post-Evaluación | 25+ | ~23% |

### 📈 Progreso para Evaluación

**Tareas Críticas Restantes:**
- ✅ Recursos Nativos: 0/12 tareas (0%)
- ✅ README.md: 0/1 tarea (0%)
- ✅ Animaciones: 0/3 tareas (0%)
- ✅ Trello: 0/1 tarea (0%)

**Total crítico pendiente: 17 tareas**

---

## 🎯 Prioridades para Evaluación Parcial 2

### 🔴 CRÍTICO (Hacer primero - Bloqueadores)
1. **Implementar recursos nativos - Implementación Mínima** ⚠️ REQUISITO OBLIGATORIO
   - Notificaciones (carrito abandonado)
   - Cámara (foto de perfil)
   - Modificar UserEntity y migración de BD
   - UI para foto de perfil
   - **Sin esto: 0% en IE 2.4.1 (15% de la nota)**

2. **Crear README.md completo** ⚠️ REQUISITO OBLIGATORIO
   - Descripción, nombres, funcionalidades, instrucciones
   - **Sin esto: No se puede entregar el proyecto**

### 🟡 IMPORTANTE (Mejorar nota significativamente)
3. **Mejorar animaciones** (transiciones, feedback)
   - Actualmente: 60% en IE 2.2.2
   - Con mejoras: Puede llegar a 100% (10% de la nota)
   - Impacto: +4% en nota final

4. **Verificar y documentar Trello**
   - Actualmente: 60% en IE 2.3.2
   - Con Trello visible: Puede llegar a 100% (20% de la nota)
   - Impacto: +8% en nota final

### 🟢 OPCIONAL (Post-evaluación)
- Tests unitarios
- Documentación técnica detallada
- Funcionalidades futuras
- Mejoras técnicas avanzadas

---

## 📝 Notas

- Las tareas en **Done** están completadas y funcionando
- Las tareas en **Backlog** están priorizadas según importancia para el encargo
- Las tareas futuras pueden implementarse después de la evaluación
- Este archivo debe actualizarse conforme se completen tareas
- Al migrar a Trello, mantener la misma estructura de columnas
- **Todas las tareas incluyen contexto técnico y pasos específicos** para facilitar implementación con IA o desarrollo manual

---

**Última actualización**: 10-07-2025  
**Próxima revisión**: Al completar recursos nativos
