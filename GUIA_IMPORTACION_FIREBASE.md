# Guía: Importar Datos a Firebase Firestore con Comandos

## 🎯 Objetivo
Importar todas las categorías y productos de una vez usando comandos, sin agregar manualmente cada documento.

---

## 📋 Requisitos Previos

1. **Node.js instalado**
   - Descarga desde: https://nodejs.org/
   - Verifica instalación: `node --version`
   - Debería mostrar: `v18.x.x` o superior

2. **Cuenta de Firebase con proyecto creado**
   - Ya lo tienes: `milsabores-api`

---

## 🔑 Paso 1: Obtener Credenciales de Firebase (Service Account Key)

### Opción A: Desde Firebase Console (Recomendado)

1. Ve a Firebase Console: https://console.firebase.google.com/
2. Selecciona tu proyecto: `milsabores-api`
3. Click en el ícono de engranaje ⚙️ → "Configuración del proyecto"
4. Ve a la pestaña "Cuentas de servicio"
5. Click en "Generar nueva clave privada"
6. Se descargará un archivo JSON (ej: `milsabores-api-firebase-adminsdk-xxxxx.json`)
7. **Renombra el archivo** a: `firebase-service-account-key.json`
8. **Mueve el archivo** a la raíz de tu proyecto (donde están los archivos JSON)

### Opción B: Usar Variables de Entorno (Alternativa)

Si prefieres no tener el archivo JSON en el proyecto, puedes usar variables de entorno (más seguro pero más complejo).

---

## 📦 Paso 2: Instalar Dependencias

Abre una terminal en la raíz de tu proyecto y ejecuta:

```bash
npm install
```

Esto instalará `firebase-admin` que es necesario para el script.

---

## 🚀 Paso 3: Ejecutar el Script de Importación

### Verificar que tienes los archivos necesarios:

```
tu-proyecto/
├── firestore_import_categories.json
├── firestore_import_products.json
├── firebase-service-account-key.json  ← Debe estar aquí
├── import-firestore.js
├── package.json
└── ...
```

### Ejecutar el script:

```bash
node import-firestore.js
```

O usando npm:

```bash
npm run import
```

---

## ✅ Resultado Esperado

Deberías ver algo como:

```
🚀 Iniciando importación a Firestore...

📦 Importando categorías...
✅ 8 categorías importadas exitosamente

📦 Importando productos...
✅ Lote 1 procesado (16 productos)
✅ 16 productos importados exitosamente

✨ Importación completada exitosamente!
```

---

## 🔍 Verificar en Firebase Console

1. Ve a Firebase Console → Firestore Database
2. Deberías ver:
   - Colección `categories` con 8 documentos
   - Colección `products` con 16 documentos

---

## ⚠️ Solución de Problemas

### Error: "Cannot find module 'firebase-admin'"
**Solución**: Ejecuta `npm install` primero

### Error: "Cannot find module './firebase-service-account-key.json'"
**Solución**: 
- Asegúrate de haber descargado el archivo de credenciales
- Renómbralo a `firebase-service-account-key.json`
- Colócalo en la misma carpeta que `import-firestore.js`

### Error: "Permission denied"
**Solución**: 
- Verifica que el archivo de credenciales sea correcto
- Asegúrate de haber generado la clave desde el proyecto correcto

### Error: "Collection already exists"
**Solución**: 
- Si ya tienes datos, el script los sobrescribirá
- Si quieres evitar esto, elimina primero las colecciones desde Firebase Console

---

## 🔒 Seguridad

**IMPORTANTE**: El archivo `firebase-service-account-key.json` contiene credenciales sensibles.

**NO**:
- ❌ Subirlo a Git/GitHub
- ❌ Compartirlo públicamente
- ❌ Incluirlo en el repositorio

**SÍ**:
- ✅ Agregarlo a `.gitignore`
- ✅ Mantenerlo local
- ✅ Eliminarlo después de la importación (opcional)

---

## 📝 Agregar a .gitignore

Si tienes un archivo `.gitignore`, agrega:

```
firebase-service-account-key.json
```

---

## 🎉 ¡Listo!

Una vez que los datos estén importados, puedes:
1. Verificar en Firebase Console
2. Continuar con la configuración de Retrofit en Android
3. Obtener la URL de tu proyecto para usar en la app

---

## 📞 Siguiente Paso

Después de importar los datos, necesitarás:
1. Obtener la URL REST de tu proyecto Firebase
2. Configurar Retrofit en Android
3. Crear los DTOs necesarios

¿Necesitas ayuda con alguno de estos pasos?

