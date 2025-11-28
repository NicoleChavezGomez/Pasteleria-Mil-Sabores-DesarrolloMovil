# 📋 Tarjetas de Trello - Pastelería Mil Sabores

> **Formato**: Tarjetas de Trello con checklists, etiquetas y descripciones detalladas  
> **Fecha de creación**: 28-11-2025  
> **Total de tarjetas**: 15

---

## 🟢 DONE (Completadas)

### 📱 Tarjeta 1: Configuración Inicial del Proyecto
**Etiquetas**: `✅ Done` `🏗️ Setup` `📱 Android`

**Descripción:**
Configuración base del proyecto Android con Jetpack Compose, estructura de carpetas y dependencias esenciales.

**Checklist:**
- [x] Crear proyecto Android con Compose
- [x] Configurar dependencias básicas (Compose, Navigation, Room, Coil)
- [x] Establecer estructura de carpetas (data, domain, presentation)
- [x] Configurar build.gradle.kts con versiones correctas
- [x] Configurar AndroidManifest.xml con permisos necesarios

**Archivos principales:**
- `app/build.gradle.kts`
- `app/src/main/AndroidManifest.xml`
- Estructura de carpetas: `data/`, `domain/`, `presentation/`

**Notas técnicas:**
- Proyecto configurado con Material 3 Design
- Compose BOM para gestión de versiones
- Gestión manual de dependencias (sin inyección de dependencias)

---

### 🏗️ Tarjeta 2: Arquitectura MVVM
**Etiquetas**: `✅ Done` `🏗️ Arquitectura`

**Descripción:**
Implementación de arquitectura MVVM con separación de capas y gestión manual de dependencias.

**Checklist:**
- [x] Implementar separación de capas (data, domain, presentation)
- [x] Crear ViewModels para gestión de estado
- [x] Crear Repositorios para acceso a datos
- [x] Configurar gestión manual de dependencias en ViewModels y repositorios

**Archivos principales:**
- `presentation/viewmodel/` (AuthViewModel, CartViewModel, etc.)
- `data/repository/` (CartRepositoryImpl, ProductRepositoryImpl, etc.)
- `domain/repository/` (interfaces)

**Notas técnicas:**
- MVVM con StateFlow para estado reactivo
- Repositorios como capa de abstracción
- Gestión manual de dependencias en ViewModels (AndroidViewModel)

---

### 💾 Tarjeta 3: Room Database y Sistema de Persistencia
**Etiquetas**: `✅ Done` `💾 Database` `🗄️ Room`

**Descripción:**
Implementación de Room Database para persistencia local con entidades, DAOs y datos por defecto.

**Checklist:**
- [x] Configurar AppDatabase con Room
- [x] Crear UserEntity y UserDao (autenticación)
- [x] Crear CartEntity y CartDao (carrito de compras)
- [x] Crear CategoryEntity y CategoryDao (categorías de productos)
- [x] Crear ProductEntity y ProductDao (productos)
- [x] Configurar fallbackToDestructiveMigration para desarrollo
- [x] Configurar datos por defecto (usuarios, categorías y productos)
- [x] Asociar carrito a usuarios (userId en CartEntity con ForeignKey)
- [x] Asociar productos a categorías (categoryId en ProductEntity con ForeignKey)

**Archivos principales:**
- `data/local/database/AppDatabase.kt`
- `data/local/database/UserEntity.kt`, `UserDao.kt`
- `data/local/database/CartEntity.kt`, `CartDao.kt`
- `data/local/database/CategoryEntity.kt`, `CategoryDao.kt`
- `data/local/database/ProductEntity.kt`, `ProductDao.kt`

**Notas técnicas:**
- Versión actual de BD: 2
- Usa fallbackToDestructiveMigration() para simplificar desarrollo
- Foreign Keys configuradas con CASCADE DELETE
- Carrito asociado a usuarios (userId en CartEntity)
- Productos y categorías cargados directamente en base de datos (no desde JSON)

---

### 🔐 Tarjeta 4: Sistema de Autenticación
**Etiquetas**: `✅ Done` `🔐 Auth` `👤 Usuario`

**Descripción:**
Sistema completo de autenticación con login, registro, gestión de sesión y persistencia de usuarios.

**Checklist:**
- [x] Implementar LoginScreen con validaciones
- [x] Implementar RegisterScreen con validaciones
- [x] Crear AuthViewModel con login(), register(), logout()
- [x] Validaciones de email y contraseña (mínimo 6 caracteres)
- [x] Gestión de sesión con StateFlow
- [x] Persistencia de estado de autenticación
- [x] Navegación condicional según autenticación
- [x] Usuarios por defecto en base de datos

**Archivos principales:**
- `presentation/ui/screens/login/LoginScreen.kt`
- `presentation/ui/screens/register/RegisterScreen.kt`
- `presentation/viewmodel/AuthViewModel.kt`
- `data/local/database/UserEntity.kt`, `UserDao.kt`

**Notas técnicas:**
- Contraseñas en texto plano (considerar hash en producción)
- Validación de email con Patterns.EMAIL_ADDRESS
- Estado de autenticación persistido en BD
- AuthViewModel compartido entre pantallas

---

### 🛒 Tarjeta 5: Carrito de Compras
**Etiquetas**: `✅ Done` `🛒 Cart` `💾 Persistencia`

**Descripción:**
Sistema completo de carrito de compras con persistencia, asociado a usuarios y sincronización en tiempo real.

**Checklist:**
- [x] Crear CartEntity con userId (ForeignKey a UserEntity)
- [x] Implementar CartDao con queries filtradas por userId
- [x] Crear CartViewModel con operaciones CRUD
- [x] Implementar CartScreen con lista de items
- [x] Agregar/eliminar productos del carrito
- [x] Actualizar cantidades
- [x] Calcular totales (items y precio)
- [x] Sincronizar carrito con usuario autenticado (setUserId)
- [x] Limpiar carrito al hacer logout
- [x] Persistencia entre sesiones

**Archivos principales:**
- `data/local/database/CartEntity.kt`, `CartDao.kt`
- `presentation/viewmodel/CartViewModel.kt`
- `presentation/ui/screens/cart/CartScreen.kt`
- `data/repository/CartRepositoryImpl.kt`

**Notas técnicas:**
- Carrito asociado a usuarios (cada usuario tiene su propio carrito)
- CartViewModel usa `_currentUserId.flatMapLatest` para filtrar por usuario
- MainContent sincroniza userId con CartViewModel
- Carrito se limpia automáticamente en logout()

---

### 📱 Tarjeta 6: Recursos Nativos (Notificaciones + Galería)
**Etiquetas**: `✅ Done` `📱 Recursos Nativos` `🔔 Notificaciones` `📷 Galería`

**Descripción:**
Implementación de recursos nativos Android: sistema de notificaciones para carrito abandonado y galería para foto de perfil.

**Checklist:**
- [x] Crear NotificationHelper.kt (singleton)
- [x] Configurar permisos POST_NOTIFICATIONS en AndroidManifest
- [x] Crear canal de notificaciones en MainActivity
- [x] Detectar carrito abandonado en MainActivity.onPause()
- [x] Mostrar notificación inmediata cuando hay items en carrito
- [x] Crear ImageHelper.kt para gestión de imágenes
- [x] Implementar Photo Picker con ActivityResultContracts.PickVisualMedia()
- [x] Guardar foto de perfil en storage interno
- [x] Actualizar UserEntity con campo fotoPerfil
- [x] Migración MIGRATION_2_3 para fotoPerfil
- [x] Crear imágenes por defecto (producto_default.png, user_default.png)
- [x] Actualizar AccountScreen con selector de galería
- [x] Mostrar foto de perfil en NavigationDrawer

**Archivos principales:**
- `util/NotificationHelper.kt`
- `util/ImageHelper.kt`
- `MainActivity.kt` (onPause, onCreate)
- `presentation/ui/screens/account/AccountScreen.kt`
- `data/local/database/UserEntity.kt` (campo fotoPerfil)

**Notas técnicas:**
- Photo Picker NO requiere permisos explícitos (Android 13+)
- Notificaciones con IMPORTANCE_HIGH y BigTextStyle
- Imágenes guardadas en `filesDir/profile_images/`
- Imágenes por defecto usadas en todos los componentes

---

### 🧭 Tarjeta 7: Navegación y UI Base
**Etiquetas**: `✅ Done` `🧭 Navigation` `🎨 UI`

**Descripción:**
Sistema de navegación con Compose Navigation, Material 3 Design y componentes de navegación (BottomBar, Drawer, TopBar).

**Checklist:**
- [x] Configurar AppNavigation con NavHost
- [x] Definir rutas en Screen.kt
- [x] Implementar Bottom Navigation Bar
- [x] Implementar Navigation Drawer (Sidebar) con ancho 75%
- [x] Implementar TopBar con logo, carrito y menú hamburger
- [x] Mover menú hamburger a la derecha del TopBar
- [x] Navegación entre pantallas funcional
- [x] Implementar Material 3 Design
- [x] Tema personalizado con colores y tipografía

**Archivos principales:**
- `presentation/navigation/AppNavigation.kt`
- `presentation/navigation/Screen.kt`
- `presentation/ui/MainContent.kt` (TopNavBar, NavigationDrawerContent, BottomNavBar)

**Notas técnicas:**
- Navigation con type-safe arguments
- Drawer con ModalDrawerSheet (ancho 75%)
- TopBar con BadgedBox para contador de carrito
- Navegación condicional según autenticación

---

### 📺 Tarjeta 8: Pantallas Principales
**Etiquetas**: `✅ Done` `📺 Screens` `🎨 UI`

**Descripción:**
Implementación de todas las pantallas principales de la aplicación con sus funcionalidades completas.

**Checklist:**
- [x] HomeScreen (carousel de productos, categorías, grid de productos)
- [x] AllProductsScreen (lista de productos, filtrado por categoría, búsqueda)
- [x] ProductDetailScreen (detalle completo, imágenes, información, agregar al carrito)
- [x] CartScreen (lista de items, controles de cantidad, total)
- [x] AccountScreen (información de usuario, foto de perfil, opciones)
- [x] LoginScreen (formulario, validaciones, retroalimentación)
- [x] RegisterScreen (formulario, validaciones, creación de usuario)
- [x] SplashScreen (logo, animación, navegación automática)

**Archivos principales:**
- `presentation/ui/screens/home/HomeScreen.kt`
- `presentation/ui/screens/products/AllProductsScreen.kt`
- `presentation/ui/screens/productdetail/ProductDetailScreen.kt`
- `presentation/ui/screens/cart/CartScreen.kt`
- `presentation/ui/screens/account/AccountScreen.kt`
- `presentation/ui/screens/login/LoginScreen.kt`
- `presentation/ui/screens/register/RegisterScreen.kt`
- `presentation/ui/screens/splash/SplashScreen.kt`

**Notas técnicas:**
- Todas las pantallas usan Material 3
- Imágenes con AsyncImage de Coil y fallbacks
- Validaciones con retroalimentación visual
- Navegación integrada con AppNavigation

---

### 🧩 Tarjeta 9: Componentes Reutilizables
**Etiquetas**: `✅ Done` `🧩 Components` `♻️ Reusable`

**Descripción:**
Componentes UI reutilizables para mantener consistencia y reducir duplicación de código.

**Checklist:**
- [x] ProductCard (tarjeta de producto con imagen, título, precio, rating)
- [x] CategoryCard (tarjeta de categoría con icono y nombre)
- [x] ProductCarousel (carrusel horizontal de productos con auto-scroll)
- [x] Skeleton Components (shimmer effect para estados de carga)
- [x] ProfileImage (componente reutilizable para foto de perfil)

**Archivos principales:**
- `presentation/ui/components/ProductCard.kt`
- `presentation/ui/components/CategoryCard.kt`
- `presentation/ui/components/ProductCarousel.kt`
- `presentation/ui/components/SkeletonComponents.kt`

**Notas técnicas:**
- Componentes con parámetros configurables
- Skeleton components para mejor UX durante carga
- ProductCarousel con auto-scroll y navegación manual

---

### ✨ Tarjeta 10: Animaciones
**Etiquetas**: `✅ Done` `✨ Animations` `🎨 UI/UX`

**Descripción:**
Sistema completo de animaciones para mejorar la experiencia de usuario: transiciones, feedback y carga.

**Checklist:**
- [x] Shimmer animations para estados de carga
- [x] Carousel animations (transiciones suaves)
- [x] Transiciones entre pantallas (AnimatedVisibility, Crossfade)
- [x] Animaciones de feedback (scale, rotation en ProductCard, CategoryCard)
- [x] Animaciones de carga mejoradas (transiciones entre estados)
- [x] Crear AnimationHelpers.kt con componentes reutilizables
- [x] Animación de shake en LoginScreen para errores
- [x] Animación de scale en SplashScreen

**Archivos principales:**
- `presentation/ui/components/AnimationHelpers.kt`
- `presentation/ui/screens/home/HomeScreen.kt` (AnimatedVisibility)
- `presentation/ui/screens/products/AllProductsScreen.kt` (Crossfade)
- `presentation/ui/components/ProductCard.kt` (animaciones de feedback)

**Notas técnicas:**
- Spring animations para efectos naturales
- AnimatedVisibility para mostrar/ocultar elementos
- Crossfade para transiciones suaves entre estados
- Animaciones de feedback en interacciones del usuario

---


### 🎨 Tarjeta 12: Mejoras de UI/UX
**Etiquetas**: `✅ Done` `🎨 UI/UX` `✨ Mejoras`

**Descripción:**
Mejoras de interfaz y experiencia de usuario implementadas durante el desarrollo.

**Checklist:**
- [x] Reorganizar TopNavBar (mover hamburger menu a la derecha)
- [x] Aumentar ancho del Sidebar de 50% a 75%
- [x] Implementar pantalla de Splash con logo y animación
- [x] Actualizar componentes de productos con imágenes por defecto
- [x] Mejorar sistema de notificaciones (BigTextStyle, importancia alta)
- [x] Implementar manejo de errores con imágenes por defecto
- [x] Agregar validaciones visuales en formularios

**Archivos principales:**
- `presentation/ui/MainContent.kt` (TopNavBar, NavigationDrawerContent)
- `presentation/ui/screens/splash/SplashScreen.kt`
- `presentation/ui/components/ProductCard.kt`, `ProductCarousel.kt`, etc. (imágenes por defecto)

**Notas técnicas:**
- Imágenes por defecto en todos los AsyncImage (placeholder, error, fallback)
- Splash con animación de scale y navegación automática
- Notificaciones mejoradas para mejor visibilidad

---

### 📚 Tarjeta 13: Documentación y Control de Versiones
**Etiquetas**: `✅ Done` `📚 Docs` `🔀 Git`

**Descripción:**
Documentación del proyecto y configuración de control de versiones con Git y GitHub.

**Checklist:**
- [x] Crear README.md completo con:
  - Descripción del proyecto
  - Nombres de estudiantes
  - Funcionalidades implementadas
  - Instrucciones de ejecución
  - Tecnologías utilizadas
  - Estructura del proyecto
- [x] Configurar repositorio en GitHub
- [x] Establecer formato de commits: `[ TIPO ]: mensaje`
- [x] Crear ramas para features (feature/login, feature/basedatos, etc.)
- [x] Merge a main después de revisión

**Archivos principales:**
- `README.md`
- `.git/` (configuración de Git)
- Ramas: `main`, `dev`, `feature/*`

**Notas técnicas:**
- README con toda la información requerida para evaluación
- Commits con formato estándar (FEAT, FIX, REFACTOR, etc.)
- Ramas de feature para desarrollo paralelo

---

## 🔵 BACKLOG (Pendientes)

### 📦 Tarjeta 14: Migración de Productos de JSON a Room Database
**Etiquetas**: `✅ Done` `💾 Database` `📦 Productos`

**Descripción:**
Migración completa de productos y categorías desde archivo JSON (assets) a Room Database. Todos los datos ahora se cargan desde la base de datos local.

**Checklist:**
- [x] Crear CategoryEntity para categorías en base de datos
- [x] Crear ProductEntity para productos en base de datos
- [x] Crear CategoryDao con queries necesarias (obtenerTodasSuspend, insertar, insertarTodas, contar)
- [x] Crear ProductDao con queries necesarias (obtenerTodosSuspend, obtenerPorId, obtenerPorCategoriaSuspend, insertar, insertarTodos)
- [x] Crear mappers para convertir entre Entity y Domain (CategoryMapper, ProductMapper)
- [x] Implementar carga de productos y categorías default directamente en insertarDatosPorDefecto()
- [x] Actualizar AppDatabase para incluir CategoryEntity y ProductEntity
- [x] Actualizar ProductRepositoryImpl para usar CategoryDao y ProductDao directamente
- [x] Eliminar ProductJsonDataSource (ya no se usa)
- [x] Eliminar DTOs obsoletos (ProductDto, CategoryDto, ProductosResponseDto)

**Archivos creados/modificados:**
- `data/local/database/CategoryEntity.kt` ✅
- `data/local/database/ProductEntity.kt` ✅
- `data/local/database/CategoryDao.kt` ✅
- `data/local/database/ProductDao.kt` ✅
- `data/mapper/CategoryMapper.kt` ✅
- `data/mapper/ProductMapper.kt` ✅ (actualizado)
- `data/local/database/AppDatabase.kt` ✅ (actualizado)
- `data/repository/ProductRepositoryImpl.kt` ✅ (actualizado)

**Notas técnicas:**
- Productos y categorías cargados directamente en base de datos (hardcoded en insertarDatosPorDefecto)
- Foreign Key de ProductEntity a CategoryEntity configurada
- ProductRepositoryImpl ahora usa DAOs en lugar de JSON
- Código simplificado: eliminado ProductJsonDataSource y DTOs obsoletos
- Base de datos versión 2 con fallbackToDestructiveMigration()

---

### 📋 Tarjeta 15: Planificación y Documentación en Trello
**Etiquetas**: `🔵 Backlog` `📋 Planificación` `📚 Docs` `🟡 Importante`

**Descripción:**
Verificar y documentar planificación en Trello según requisitos de la rúbrica.

**Checklist:**
- [ ] Verificar si existe Trello del equipo
- [ ] Si no existe: Crear Trello básico con columnas (Backlog, Doing, Code Review, Done)
- [ ] Migrar tareas de TRELLO_TASKS.md a Trello
- [ ] Crear tarjetas con checklists y descripciones
- [ ] Agregar etiquetas y miembros del equipo
- [ ] Documentar link de Trello en README.md
- [ ] Agregar sección sobre planificación en README.md

**Archivos a modificar:**
- `README.md` (agregar sección de planificación con link a Trello)

**Notas técnicas:**
- Requisito de la rúbrica para evaluación
- Mejora nota en planificación (IE 2.3.2)
- Impacto: +8% en nota final potencial

**Prioridad:** 🟡 Importante - Requisito de la rúbrica

---

## 📊 Resumen de Tarjetas

| Estado | Cantidad | Porcentaje |
|--------|----------|------------|
| 🟢 Done | 14 | ~93% |
| 🔵 Backlog | 1 | ~7% |
| **TOTAL** | **15** | **100%** |

### 📈 Progreso General

- **Tarjetas Completadas**: 14/15 (93%)
- **Tarjetas Pendientes**: 1/15 (7%)
- **Funcionalidades Críticas**: ✅ Completadas
- **Recursos Nativos**: ✅ Completados (Notificaciones + Galería)
- **Migración de Productos a Room**: ✅ Completada
- **Carrito por Usuario**: ✅ Completado
- **Limpieza de Código**: ✅ Completada (eliminado código sin usar)

---

## 🎯 Próximas Acciones

1. **Planificación en Trello** (Tarjeta #15)
   - Contexto: Requisito de la rúbrica
   - Impacto: Mejora nota en planificación
   - Prioridad: 🟡 Importante

---

## 🧹 Limpieza de Código Completada

**Eliminaciones realizadas:**
- ✅ ProductJsonDataSource.kt (ya no se usa, productos en Room)
- ✅ Resource.kt (reemplazado por UiState)
- ✅ ProductDto.kt, CategoryDto.kt, ProductosResponseDto.kt (DTOs obsoletos)
- ✅ Sistema de reviews eliminado (no necesario)
- ✅ Sistema de compras eliminado (solo carrito)
- ✅ Dependencias sin usar: Retrofit, OkHttp, Gson
- ✅ Métodos sin usar en DAOs (Flow methods, métodos de búsqueda no utilizados)
- ✅ Opción REVIEWS_DESC eliminada de filtros

**Arquitectura actualizada:**
- ✅ Sin Hilt (gestión manual de dependencias)
- ✅ Room Database simplificado (fallbackToDestructiveMigration)
- ✅ Productos y categorías en Room Database
- ✅ Código limpio y optimizado

---

**Última actualización**: Diciembre 2025  
**Formato**: Tarjetas de Trello con checklists y descripciones detalladas

