# Instrucciones para Importar Datos a Firebase Firestore

## 📁 Archivos Generados

Se han creado dos archivos JSON con todos tus datos:

1. **`firestore_import_categories.json`** - 8 categorías
2. **`firestore_import_products.json`** - 16 productos

---

## 🔄 Opción 1: Importación Manual (Recomendada - Más Rápida)

### Para Categorías:

1. Abre el archivo `firestore_import_categories.json` en un editor de texto
2. En Firebase Console, crea la colección `categories` (si no la creaste)
3. Para cada categoría en el JSON:
   - Click en "Agregar documento"
   - ID del documento: usa la clave del JSON (ej: `tortas-cuadradas`)
   - Agrega los campos uno por uno:
     - `id`: string → valor del JSON
     - `nombre`: string → valor del JSON
     - `icono`: string → valor del JSON
   - Click en "Guardar"

### Para Productos:

1. Abre el archivo `firestore_import_products.json` en un editor de texto
2. En Firebase Console, crea la colección `products`
3. Para cada producto en el JSON:
   - Click en "Agregar documento"
   - ID del documento: usa la clave del JSON (ej: `TC001`)
   - Agrega los campos:
     - **Importante**: `precio`, `rating`, `reviews` son tipo **number**
     - Todos los demás son tipo **string**
   - Click en "Guardar"

**Tiempo estimado**: 20-30 minutos para agregar todos los documentos manualmente.

---

## 🔄 Opción 2: Usar Script de Importación (Más Rápida pero Requiere Node.js)

### Requisitos:
- Node.js instalado
- Firebase CLI instalado: `npm install -g firebase-tools`

### Pasos:

1. **Instalar Firebase CLI** (si no lo tienes):
   ```bash
   npm install -g firebase-tools
   ```

2. **Iniciar sesión en Firebase**:
   ```bash
   firebase login
   ```

3. **Inicializar Firebase en tu proyecto**:
   ```bash
   firebase init firestore
   ```
   - Selecciona tu proyecto `milsabores-api`
   - Usa las reglas por defecto
   - No sobrescribas los archivos existentes

4. **Convertir JSON a formato Firestore**:
   Necesitarás crear un script Node.js para convertir los JSON a formato de importación de Firestore.

5. **Importar datos**:
   ```bash
   firebase firestore:import firestore_import.json
   ```

**Nota**: Esta opción requiere más configuración pero es más rápida si tienes muchos datos.

---

## 🔄 Opción 3: Usar Firebase Admin SDK (Para Desarrolladores)

Si tienes experiencia con Node.js, puedes crear un script que use Firebase Admin SDK para importar los datos directamente.

---

## ✅ Recomendación

Para tu caso (presentación rápida), usa la **Opción 1 (Importación Manual)**:
- No requiere instalaciones adicionales
- Es más directa y visual
- Puedes verificar cada documento mientras lo agregas
- Tiempo: ~20-30 minutos

---

## 📝 Notas Importantes

1. **Tipos de datos**:
   - `precio`, `rating`, `reviews` → **number**
   - Todos los demás → **string**

2. **IDs de documentos**:
   - Usa exactamente los mismos IDs que están en los archivos JSON
   - Esto asegura que las relaciones (`categoryId`) funcionen correctamente

3. **Verificación**:
   - Después de importar, verifica que:
     - Colección `categories` tiene 8 documentos
     - Colección `products` tiene 16 documentos
     - Los `categoryId` en productos coinciden con IDs de categorías

---

## 🚀 Siguiente Paso

Una vez que tengas los datos importados, necesitarás:
1. Obtener la URL de tu proyecto Firebase
2. Configurar Retrofit en Android para consumir la API REST de Firestore
3. Crear los DTOs que coincidan con la estructura de Firestore

