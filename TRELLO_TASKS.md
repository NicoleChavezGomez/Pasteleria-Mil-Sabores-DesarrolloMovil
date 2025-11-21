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
  - Verificar que todos los commits sigan el formato establecido
  - Consolidar commits si es necesario

- [ ] **Revisar código de autenticación**
  - Verificar manejo de errores
  - Validar flujo de login/logout
  - Revisar seguridad de contraseñas

---

## 🟡 Doing

### 🚧 En Progreso
- [ ] **Crear README.md completo**
  - Descripción del proyecto
  - Nombres de estudiantes
  - Funcionalidades implementadas
  - Instrucciones de ejecución
  - Tecnologías utilizadas

---

## 🔵 Backlog

### 📱 Recursos Nativos (CRÍTICO - Requisito del encargo)
- [ ] **Implementar acceso a Cámara**
  - Agregar permisos en AndroidManifest
  - Implementar ActivityResultLauncher para cámara
  - Crear función para tomar foto
  - Integrar en AccountScreen (foto de perfil)
  - Guardar imagen capturada

- [ ] **Implementar acceso a Almacenamiento**
  - Agregar permisos de almacenamiento
  - Guardar imágenes capturadas
  - Leer imágenes guardadas
  - Integrar con sistema de archivos

- [ ] **Alternativa: Implementar acceso a Ubicación**
  - Agregar permisos de ubicación
  - Obtener coordenadas GPS
  - Mostrar dirección en formulario de pedido
  - Integrar con mapa (opcional)

- [ ] **Alternativa: Implementar Notificaciones**
  - Configurar canal de notificaciones
  - Notificaciones de pedidos listos
  - Notificaciones de ofertas
  - Integrar con sistema de notificaciones Android

### 🎨 Mejoras de Animaciones
- [ ] **Transiciones entre pantallas**
  - Implementar AnimatedContent para transiciones
  - Transiciones suaves entre navegación
  - Efectos de fade in/out

- [ ] **Animaciones de feedback**
  - Animación en botones al presionar
  - Feedback visual en formularios
  - Animaciones de éxito/error

- [ ] **Animaciones de carga mejoradas**
  - Mejorar skeleton loaders
  - Animaciones de progreso
  - Transiciones de estado

### 🧪 Testing y Calidad
- [ ] **Tests unitarios para ViewModels**
  - Tests de AuthViewModel
  - Tests de CartViewModel
  - Tests de validaciones

- [ ] **Tests de UI**
  - Tests de componentes principales
  - Tests de navegación
  - Tests de formularios

### 📝 Documentación
- [ ] **Documentar arquitectura**
  - Diagrama de arquitectura
  - Flujo de datos
  - Decisiones de diseño

- [ ] **Documentar componentes**
  - Javadoc/KDoc en componentes principales
  - Documentación de funciones públicas
  - Ejemplos de uso

### 🚀 Funcionalidades Futuras
- [ ] **Sistema de favoritos**
  - Entity Favorito en Room
  - DAO para favoritos
  - UI para gestionar favoritos
  - Integrar en ProductDetailScreen

- [ ] **Historial de pedidos**
  - Entity Pedido en Room
  - DAO para pedidos
  - Pantalla de historial
  - Detalle de pedido

- [ ] **Sistema de direcciones**
  - Entity Direccion en Room
  - DAO para direcciones
  - Formulario de dirección
  - Selección de dirección en checkout

- [ ] **Métodos de pago**
  - Integración con pasarela de pago (simulada)
  - Formulario de pago
  - Confirmación de pago

- [ ] **Búsqueda avanzada**
  - Filtros por precio
  - Filtros por categoría
  - Ordenamiento
  - Búsqueda por texto

- [ ] **Sistema de reseñas**
  - Entity Reseña en Room
  - DAO para reseñas
  - Formulario de reseña
  - Mostrar reseñas en ProductDetail

- [ ] **Compartir productos**
  - Compartir vía Intent
  - Compartir en redes sociales
  - Generar enlace de producto

- [ ] **Modo offline**
  - Sincronización de datos
  - Cache de productos
  - Indicador de estado de conexión

- [ ] **Temas (Dark Mode)**
  - Implementar tema oscuro
  - Selector de tema
  - Persistencia de preferencia

- [ ] **Internacionalización (i18n)**
  - Strings en resources
  - Soporte para múltiples idiomas
  - Cambio de idioma en settings

### 🔧 Mejoras Técnicas
- [ ] **Optimización de imágenes**
  - Compresión de imágenes
  - Cache de imágenes
  - Lazy loading

- [ ] **Mejoras de rendimiento**
  - Optimización de queries de Room
  - Lazy loading de listas
  - Reducción de recomposiciones

- [ ] **Manejo de errores mejorado**
  - Error handling centralizado
  - Mensajes de error amigables
  - Retry logic

- [ ] **Logging y debugging**
  - Sistema de logging estructurado
  - Debug tools
  - Analytics (opcional)

### 📱 Mejoras de UX
- [ ] **Pull to refresh**
  - Implementar en listas de productos
  - Actualización de datos

- [ ] **Empty states**
  - Pantallas vacías informativas
  - Ilustraciones para estados vacíos

- [ ] **Onboarding**
  - Pantalla de bienvenida
  - Tutorial de uso
  - Primera vez que se abre la app

- [ ] **Mejoras de accesibilidad**
  - Content descriptions
  - Soporte para TalkBack
  - Contraste de colores

---

## 📊 Resumen de Estado

| Columna | Cantidad | Porcentaje |
|---------|----------|------------|
| 🟢 Done | 40+ | ~70% |
| 🟠 Code Review | 2 | ~3% |
| 🟡 Doing | 1 | ~2% |
| 🔵 Backlog | 25+ | ~25% |

---

## 🎯 Prioridades para Evaluación Parcial 2

### 🔴 CRÍTICO (Hacer primero)
1. ✅ Implementar 2 recursos nativos (Cámara + Almacenamiento o Ubicación + Notificaciones)
2. ✅ Crear README.md completo

### 🟡 IMPORTANTE (Mejorar nota)
3. ✅ Mejorar animaciones (transiciones, feedback)
4. ✅ Revisar y consolidar código

### 🟢 OPCIONAL (Para nota más alta)
5. ⚪ Tests unitarios básicos
6. ⚪ Documentación adicional

---

## 📝 Notas

- Las tareas en **Done** están completadas y funcionando
- Las tareas en **Backlog** están priorizadas según importancia para el encargo
- Las tareas futuras pueden implementarse después de la evaluación
- Este archivo debe actualizarse conforme se completen tareas
- Al migrar a Trello, mantener la misma estructura de columnas

---

**Última actualización**: 10-07-2025  
**Próxima revisión**: Al completar recursos nativos

