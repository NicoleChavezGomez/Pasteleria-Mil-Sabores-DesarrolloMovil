# Documentación API - Mil Sabores (MockAPI.io)

## 📋 Información General

**Plataforma**: MockAPI.io  
**Proyecto**: milsabores-api  
**URL Base**: `https://693e248ef55f1be793046cd9.mockapi.io/api/v1`  
**Estado**: ✅ Activo y funcionando

---

## 🔗 Endpoints Disponibles

### Base URL
```
https://693e248ef55f1be793046cd9.mockapi.io/api/v1
```

---

## 📦 Recursos

### 1. Categories (Categorías)

**Endpoint Base**: `/categories`

#### Obtener todas las categorías
```
GET /api/v1/categories
```

**Respuesta** (200 OK):
```json
[
  {
    "id": "tortas-cuadradas",
    "nombre": "Tortas Cuadradas",
    "icono": "square"
  },
  {
    "id": "tortas-circulares",
    "nombre": "Tortas Circulares",
    "icono": "circle"
  },
  // ... 6 categorías más
]
```

#### Obtener una categoría por ID
```
GET /api/v1/categories/:id
```

**Ejemplo**:
```
GET /api/v1/categories/tortas-cuadradas
```

**Respuesta** (200 OK):
```json
{
  "id": "tortas-cuadradas",
  "nombre": "Tortas Cuadradas",
  "icono": "square"
}
```

#### Crear una nueva categoría
```
POST /api/v1/categories
Content-Type: application/json

{
  "id": "nueva-categoria",
  "nombre": "Nueva Categoría",
  "icono": "icon-name"
}
```

#### Actualizar una categoría
```
PUT /api/v1/categories/:id
Content-Type: application/json

{
  "id": "tortas-cuadradas",
  "nombre": "Tortas Cuadradas Actualizadas",
  "icono": "square"
}
```

#### Eliminar una categoría
```
DELETE /api/v1/categories/:id
```

---

### 2. Products (Productos)

**Endpoint Base**: `/products`

#### Obtener todos los productos
```
GET /api/v1/products
```

**Respuesta** (200 OK):
```json
[
  {
    "id": "TC001",
    "nombre": "Torta Cuadrada de Chocolate",
    "precio": 45990,
    "imagen": "https://...",
    "descripcion": "Deliciosa torta...",
    "descripcionDetallada": "Exquisita torta...",
    "rating": 4.8,
    "reviews": 24,
    "porciones": "10-15 personas",
    "calorias": "350 cal/porción",
    "ingredientes": "Chocolate premium...",
    "categoryId": "tortas-cuadradas"
  },
  // ... 15 productos más
]
```

#### Obtener un producto por ID
```
GET /api/v1/products/:id
```

**Ejemplo**:
```
GET /api/v1/products/TC001
```

**Respuesta** (200 OK):
```json
{
  "id": "TC001",
  "nombre": "Torta Cuadrada de Chocolate",
  "precio": 45990,
  "imagen": "https://delicakesysnacks.com/wp-content/uploads/2025/01/vitxekmdoeio3sgmh5dr-1.webp",
  "descripcion": "Deliciosa torta de chocolate con relleno de crema.",
  "descripcionDetallada": "Exquisita torta de chocolate premium...",
  "rating": 4.8,
  "reviews": 24,
  "porciones": "10-15 personas",
  "calorias": "350 cal/porción",
  "ingredientes": "Chocolate premium, harina, huevos, azúcar...",
  "categoryId": "tortas-cuadradas"
}
```

#### Filtrar productos por categoría
```
GET /api/v1/products?categoryId=tortas-cuadradas
```

#### Crear un nuevo producto
```
POST /api/v1/products
Content-Type: application/json

{
  "id": "PROD001",
  "nombre": "Nuevo Producto",
  "precio": 15000,
  "imagen": "https://...",
  "descripcion": "Descripción corta",
  "descripcionDetallada": "Descripción detallada...",
  "rating": 4.5,
  "reviews": 0,
  "porciones": "8-10 personas",
  "calorias": "300 cal/porción",
  "ingredientes": "Ingredientes...",
  "categoryId": "tortas-cuadradas"
}
```

#### Actualizar un producto
```
PUT /api/v1/products/:id
Content-Type: application/json

{
  "nombre": "Producto Actualizado",
  "precio": 20000,
  // ... otros campos
}
```

#### Eliminar un producto
```
DELETE /api/v1/products/:id
```

---

## 📊 Estructura de Datos

### Category (Categoría)
```typescript
{
  id: string;           // ID único de la categoría
  nombre: string;       // Nombre de la categoría
  icono: string;        // Nombre del icono
}
```

### Product (Producto)
```typescript
{
  id: string;                    // ID único del producto
  nombre: string;                 // Nombre del producto
  precio: number;                // Precio en pesos chilenos
  imagen: string;                // URL de la imagen
  descripcion: string;            // Descripción corta
  descripcionDetallada: string;   // Descripción completa
  rating: number;                // Calificación (0-5)
  reviews: number;               // Número de reseñas
  porciones: string;             // Cantidad de porciones
  calorias: string;              // Calorías por porción
  ingredientes: string;           // Lista de ingredientes
  categoryId: string;            // ID de la categoría (FK)
}
```

---

## 🔍 Categorías Disponibles

| ID | Nombre | Icono |
|----|--------|-------|
| `tortas-cuadradas` | Tortas Cuadradas | square |
| `tortas-circulares` | Tortas Circulares | circle |
| `postres-individuales` | Postres Individuales | cookie |
| `productos-sin-azucar` | Productos Sin Azúcar | favorite |
| `pasteleria-tradicional` | Pastelería Tradicional | home |
| `productos-sin-gluten` | Productos Sin Gluten | eco |
| `productos-veganos` | Productos Veganos | park |
| `tortas-especiales` | Tortas Especiales | star |

**Total**: 8 categorías

---

## 🍰 Productos Disponibles

**Total**: 16 productos distribuidos en las siguientes categorías:

- **Tortas Cuadradas**: 2 productos (TC001, TC002)
- **Tortas Circulares**: 3 productos (TT001, TT002, TT003)
- **Postres Individuales**: 2 productos (PI001, PI002)
- **Productos Sin Azúcar**: 2 productos (PSA001, PSA002)
- **Pastelería Tradicional**: 2 productos (PT001, PT002)
- **Productos Sin Gluten**: 2 productos (PG001, PG002)
- **Productos Veganos**: 2 productos (PV001, PV002)
- **Tortas Especiales**: 2 productos (TE001, TE002)

---

## 🧪 Ejemplos de Uso

### Ejemplo 1: Obtener todas las categorías
```bash
curl https://693e248ef55f1be793046cd9.mockapi.io/api/v1/categories
```

### Ejemplo 2: Obtener un producto específico
```bash
curl https://693e248ef55f1be793046cd9.mockapi.io/api/v1/products/TC001
```

### Ejemplo 3: Filtrar productos por categoría
```bash
curl "https://693e248ef55f1be793046cd9.mockapi.io/api/v1/products?categoryId=tortas-cuadradas"
```

### Ejemplo 4: Crear un nuevo producto
```bash
curl -X POST https://693e248ef55f1be793046cd9.mockapi.io/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "id": "NUEVO001",
    "nombre": "Nuevo Producto",
    "precio": 20000,
    "imagen": "https://ejemplo.com/imagen.jpg",
    "descripcion": "Descripción corta",
    "descripcionDetallada": "Descripción detallada",
    "rating": 4.5,
    "reviews": 0,
    "porciones": "8-10 personas",
    "calorias": "300 cal/porción",
    "ingredientes": "Ingredientes del producto",
    "categoryId": "tortas-cuadradas"
  }'
```

---

## 📱 Integración con Android (Retrofit)

### URL Base para Retrofit
```kotlin
private const val BASE_URL = "https://693e248ef55f1be793046cd9.mockapi.io/api/v1/"
```

### Ejemplo de ApiService
```kotlin
interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>
    
    @GET("categories/{id}")
    suspend fun getCategoryById(@Path("id") id: String): CategoryDto
    
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
    
    @GET("products")
    suspend fun getProductsByCategory(
        @Query("categoryId") categoryId: String
    ): List<ProductDto>
    
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): ProductDto
}
```

---

## ⚠️ Notas Importantes

1. **Límites del Plan Gratuito**:
   - Hasta 4 proyectos
   - Límite de requests (verificar en MockAPI.io)
   - Los datos persisten mientras el proyecto esté activo

2. **Autenticación**:
   - No se requiere autenticación para las operaciones GET
   - Las operaciones POST/PUT/DELETE pueden requerir autenticación según la configuración

3. **CORS**:
   - MockAPI.io permite CORS por defecto
   - No hay problemas para consumir desde aplicaciones web o móviles

4. **Rate Limiting**:
   - Verificar límites en el plan gratuito
   - Considerar implementar caché en la aplicación

---

## 🔄 Sincronización con Datos Locales

Los datos en MockAPI.io están sincronizados con los datos por defecto de la aplicación:

- ✅ 8 categorías (mismas que en `AppDatabase.kt`)
- ✅ 16 productos (mismos que en `AppDatabase.kt`)
- ✅ Estructura de datos idéntica a `ProductEntity` y `CategoryEntity`

---

## 📝 Archivos Relacionados

- `mockapi_categories.json` - Datos de categorías en formato array
- `mockapi_products.json` - Datos de productos en formato array
- `firestore_import_categories.json` - Formato original (objeto)
- `firestore_import_products.json` - Formato original (objeto)

---

## 🚀 Próximos Pasos

1. ✅ API configurada y funcionando
2. ⏳ Implementar Retrofit en Android
3. ⏳ Crear DTOs para mapear respuestas de API
4. ⏳ Actualizar ViewModels para usar API
5. ⏳ Implementar caché local (Room) como fallback

---

## 📞 Soporte

- **MockAPI.io Dashboard**: https://mockapi.io/projects
- **Documentación MockAPI.io**: https://mockapi.io/docs
- **Proyecto**: milsabores-api

---

**Última actualización**: Enero 2025  
**Versión API**: v1

