# API REST - Mil Sabores

## 🚀 Estado

✅ **API Configurada y Funcionando**

- **Plataforma**: MockAPI.io
- **URL Base**: `https://693e248ef55f1be793046cd9.mockapi.io/api/v1`
- **Endpoints**: `/categories` y `/products`

## 📚 Documentación Completa

Ver archivo: **[DOCUMENTACION_API_MOCKAPI.md](./DOCUMENTACION_API_MOCKAPI.md)**

## 🔗 Endpoints Principales

### Categorías
```
GET /api/v1/categories          → Todas las categorías
GET /api/v1/categories/:id       → Categoría por ID
```

### Productos
```
GET /api/v1/products            → Todos los productos
GET /api/v1/products/:id         → Producto por ID
GET /api/v1/products?categoryId= → Productos por categoría
```

## 📊 Datos Disponibles

- **8 categorías** de productos
- **16 productos** distribuidos en las categorías

## 🧪 Probar la API

Abre en tu navegador:
- Categorías: https://693e248ef55f1be793046cd9.mockapi.io/api/v1/categories
- Productos: https://693e248ef55f1be793046cd9.mockapi.io/api/v1/products

## 📝 Archivos Relacionados

- `DOCUMENTACION_API_MOCKAPI.md` - Documentación completa de la API
- `PLAN_IMPLEMENTACION_RETROFIT.md` - Plan de implementación en Android
- `mockapi_categories.json` - Datos de categorías (formato array)
- `mockapi_products.json` - Datos de productos (formato array)

