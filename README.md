# 🍰 Mil Sabores - Aplicación de Pastelería

Aplicación móvil Android desarrollada con Jetpack Compose para la gestión de productos de pastelería, carrito de compras y autenticación de usuarios.

## 👥 Estudiantes

<!-- Completar con los nombres reales de los estudiantes -->
- [Nombre Estudiante 1]
- [Nombre Estudiante 2]
- [Nombre Estudiante 3]

## 📱 Descripción del Proyecto

Mil Sabores es una aplicación de e-commerce para una pastelería que permite a los usuarios:
- Explorar catálogo de productos de pastelería
- Agregar productos al carrito de compras
- Gestionar su cuenta de usuario
- Realizar compras de manera intuitiva

La aplicación está desarrollada siguiendo las mejores prácticas de Android, utilizando arquitectura MVVM y Material Design 3 para una experiencia de usuario moderna y fluida.

## ✨ Funcionalidades Implementadas

### 🏠 Pantallas Principales

- **HomeScreen**: Pantalla principal con carousel de productos destacados, categorías y grid de productos
- **AllProductsScreen**: Lista completa de productos con filtrado por categoría y búsqueda
- **ProductDetailScreen**: Detalle completo del producto con imágenes, descripción, ingredientes y opción de agregar al carrito
- **CartScreen**: Gestión del carrito de compras con control de cantidades y cálculo de totales
- **AccountScreen**: Perfil de usuario con información y opciones de configuración

### 🔐 Autenticación

- **LoginScreen**: Inicio de sesión con validación de email y contraseña
- **RegisterScreen**: Registro de nuevos usuarios con validaciones completas
- **Gestión de sesión**: Sistema de autenticación con persistencia en base de datos local
- **Logout**: Cierre de sesión funcional con actualización de UI

### 🛒 Carrito de Compras

- Agregar/eliminar productos del carrito
- Modificar cantidades
- Cálculo automático de totales
- Persistencia en base de datos local
- Contador de items en tiempo real

### 🎨 Interfaz de Usuario

- **Material 3 Design**: Implementación completa de Material Design 3
- **Bottom Navigation Bar**: Navegación principal entre secciones
- **Navigation Drawer**: Menú lateral con opciones adicionales
- **Top Navigation Bar**: Barra superior con logo, carrito y menú
- **Animaciones**: Shimmer effects en carga de datos y carousel de productos
- **Tema personalizado**: Colores y tipografía adaptados a la marca

## 🛠️ Tecnologías Utilizadas

### Lenguaje y Framework
- **Kotlin**: Lenguaje de programación principal
- **Jetpack Compose**: Framework de UI declarativa
- **Material 3**: Sistema de diseño Material Design 3

### Arquitectura
- **MVVM (Model-View-ViewModel)**: Arquitectura de la aplicación
- **StateFlow**: Gestión de estado reactiva
- **Coroutines**: Programación asíncrona

### Persistencia de Datos
- **Room Database**: Base de datos local para usuarios y carrito
- **JSON Assets**: Datos de productos desde archivos JSON

### Navegación
- **Navigation Compose**: Sistema de navegación entre pantallas
- **NavHost**: Contenedor de navegación principal

### Dependencias Principales
```kotlin
// Core Android
- androidx.core:core-ktx
- androidx.lifecycle:lifecycle-runtime-ktx
- androidx.activity:activity-compose

// Compose
- androidx.compose.ui:ui
- androidx.compose.material3:material3
- androidx.compose.ui:ui-tooling-preview

// Navigation
- androidx.navigation:navigation-compose

// Room Database
- androidx.room:room-runtime
- androidx.room:room-ktx
- kapt androidx.room:room-compiler

// Coroutines
- org.jetbrains.kotlinx:kotlinx-coroutines-android
```

## 📋 Requisitos del Sistema

### Para Desarrollo
- **Android Studio**: Hedgehog | 2023.1.1 o superior
- **JDK**: 11 o superior
- **Gradle**: 8.0 o superior
- **Kotlin**: 1.9.0 o superior

### Para Ejecución
- **Android**: 7.0 (API 24) o superior
- **Dispositivo**: Emulador o dispositivo físico con Android 7.0+

## 🚀 Instrucciones de Ejecución

### 1. Clonar el Repositorio

```bash
git clone https://github.com/NicoleChavezGomez/Pasteleria-Mil-Sabores-DesarrolloMovil.git
cd Pasteleria-Mil-Sabores-DesarrolloMovil
```

### 2. Abrir en Android Studio

1. Abre Android Studio
2. Selecciona **File > Open**
3. Navega a la carpeta del proyecto clonado
4. Espera a que Android Studio sincronice el proyecto y descargue las dependencias

### 3. Configurar Emulador o Dispositivo

#### Opción A: Emulador
1. Ve a **Tools > Device Manager**
2. Clic en **Create Device**
3. Selecciona un dispositivo (recomendado: Pixel 5 o superior)
4. Selecciona una imagen del sistema (recomendado: API 33 o superior)
5. Finaliza la configuración

#### Opción B: Dispositivo Físico
1. Habilita **Opciones de desarrollador** en tu dispositivo Android
2. Activa **Depuración USB**
3. Conecta el dispositivo por USB
4. Acepta la autorización de depuración en el dispositivo

### 4. Ejecutar la Aplicación

1. Selecciona el dispositivo/emulador en la barra superior
2. Clic en el botón **Run** (▶️) o presiona `Shift + F10`
3. Espera a que la aplicación se compile e instale
4. La aplicación se abrirá automáticamente

### 5. Usuarios de Prueba

La aplicación incluye usuarios por defecto para testing:

| Email | Contraseña | Rol |
|-------|------------|-----|
| admin@milsabores.com | 123456 | Administrador |
| cliente@milsabores.com | 123456 | Cliente |
| test@milsabores.com | 123456 | Usuario de prueba |

## 📁 Estructura del Proyecto

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/milsaborestest/
│   │   │   ├── data/
│   │   │   │   ├── local/
│   │   │   │   │   ├── database/          # Room Database (Entities, DAOs)
│   │   │   │   │   └── datasource/        # DataSources (JSON, etc.)
│   │   │   │   └── repository/            # Implementaciones de repositorios
│   │   │   ├── domain/
│   │   │   │   ├── model/                 # Modelos de dominio
│   │   │   │   └── usecase/               # Casos de uso
│   │   │   ├── presentation/
│   │   │   │   ├── navigation/            # Navegación (NavGraph, Screen)
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/           # Pantallas principales
│   │   │   │   │   ├── components/        # Componentes reutilizables
│   │   │   │   │   └── theme/             # Tema y estilos
│   │   │   │   └── viewmodel/             # ViewModels
│   │   │   ├── util/                      # Utilidades y helpers
│   │   │   └── MainActivity.kt            # Actividad principal
│   │   ├── res/
│   │   │   ├── drawable/                  # Imágenes y drawables
│   │   │   ├── values/                    # Colores, strings, themes
│   │   │   └── assets/                    # Archivos JSON (productos)
│   │   └── AndroidManifest.xml
│   └── test/                              # Tests unitarios
```

## 🔧 Configuración Adicional

### Base de Datos

La aplicación utiliza Room Database para persistencia local:
- **Base de datos**: `milsabores_database`
- **Entidades**: `UserEntity`, `CartEntity`
- **Versión actual**: 2

### Permisos

La aplicación requiere los siguientes permisos:
- `INTERNET`: Para futuras integraciones con API
- `ACCESS_NETWORK_STATE`: Para verificar conectividad

## 📝 Notas de Desarrollo

- El proyecto sigue el patrón de acceso directo a base de datos desde ViewModels (sin Hilt)
- Los productos se cargan desde un archivo JSON en `assets/productos.json`
- La autenticación no persiste entre sesiones (patrón in-memory como PokeStore)
- El carrito se persiste en Room Database

## 🐛 Solución de Problemas

### Error: "Couldn't delete R.jar"
- Cierra Android Studio
- Elimina la carpeta `build/` del proyecto
- Vuelve a abrir el proyecto

### Error: "Gradle sync failed"
- Verifica tu conexión a internet
- Ejecuta `./gradlew clean` en la terminal
- Sincroniza nuevamente el proyecto

### La aplicación no compila
- Verifica que tienes JDK 11 o superior instalado
- Asegúrate de tener todas las dependencias descargadas
- Limpia el proyecto: **Build > Clean Project**

## 📄 Licencia

Este proyecto es parte de un trabajo académico para el curso de Desarrollo Móvil.

## 👨‍💻 Contribuciones

Este es un proyecto académico. Para contribuciones o sugerencias, contactar a los desarrolladores.

---

**Desarrollado con ❤️ usando Jetpack Compose y Material Design 3**

