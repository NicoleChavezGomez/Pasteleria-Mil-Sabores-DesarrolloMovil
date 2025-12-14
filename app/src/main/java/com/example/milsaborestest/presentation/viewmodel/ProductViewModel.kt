package com.example.milsaborestest.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborestest.data.remote.RetrofitInstance
import com.example.milsaborestest.data.remote.mapper.toDomain
import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.util.Constants
import com.example.milsaborestest.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    
    private val _categoriesState = MutableStateFlow<UiState<List<com.example.milsaborestest.domain.model.Category>>>(UiState.Loading)
    val categoriesState: StateFlow<UiState<List<com.example.milsaborestest.domain.model.Category>>> = _categoriesState.asStateFlow()
    
    private val _featuredProductsState = MutableStateFlow<UiState<List<com.example.milsaborestest.domain.model.Product>>>(UiState.Loading)
    val featuredProductsState: StateFlow<UiState<List<com.example.milsaborestest.domain.model.Product>>> = _featuredProductsState.asStateFlow()
    
    private val _productsByCategoryState = MutableStateFlow<UiState<List<com.example.milsaborestest.domain.model.Product>>>(UiState.Loading)
    val productsByCategoryState: StateFlow<UiState<List<com.example.milsaborestest.domain.model.Product>>> = _productsByCategoryState.asStateFlow()
    
    private val _productDetailState = MutableStateFlow<UiState<com.example.milsaborestest.domain.model.Product>>(UiState.Loading)
    val productDetailState: StateFlow<UiState<com.example.milsaborestest.domain.model.Product>> = _productDetailState.asStateFlow()
    
    // Estados para filtros
    private val _searchTerm = MutableStateFlow("")
    val searchTerm: StateFlow<String> = _searchTerm.asStateFlow()
    
    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()
    
    private val _minPrice = MutableStateFlow(0)
    val minPrice: StateFlow<Int> = _minPrice.asStateFlow()
    
    private val _maxPrice = MutableStateFlow(999999)
    val maxPrice: StateFlow<Int> = _maxPrice.asStateFlow()
    
    private val _minPriceDefault = MutableStateFlow(0)
    val minPriceDefault: StateFlow<Int> = _minPriceDefault.asStateFlow()
    
    private val _maxPriceDefault = MutableStateFlow(999999)
    val maxPriceDefault: StateFlow<Int> = _maxPriceDefault.asStateFlow()
    
    private val _sortBy = MutableStateFlow(com.example.milsaborestest.presentation.ui.components.SortOption.DEFAULT)
    val sortBy: StateFlow<com.example.milsaborestest.presentation.ui.components.SortOption> = _sortBy.asStateFlow()
    
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    
    // Map para relacionar productos con sus categorías (productId -> categoryId)
    private val _productToCategoryMap = MutableStateFlow<Map<String, String>>(emptyMap())
    
    // Data class para los valores combinados (type-safe)
    private data class FilterParams(
        val products: List<Product>,
        val categoryMap: Map<String, String>,
        val search: String,
        val categoryId: String?,
        val min: Int,
        val max: Int,
        val sort: com.example.milsaborestest.presentation.ui.components.SortOption
    )
    
    // Función helper para extraer los valores de forma type-safe
    private fun extractFilterParams(values: Array<*>): FilterParams {
        return FilterParams(
            products = values[0] as? List<Product> ?: emptyList(),
            categoryMap = values[1] as? Map<String, String> ?: emptyMap(),
            search = values[2] as? String ?: "",
            categoryId = values[3] as? String,
            min = values[4] as? Int ?: 0,
            max = values[5] as? Int ?: 999999,
            sort = values[6] as? com.example.milsaborestest.presentation.ui.components.SortOption 
                ?: com.example.milsaborestest.presentation.ui.components.SortOption.DEFAULT
        )
    }
    
    // Productos filtrados basados en todos los productos
    val filteredProducts: StateFlow<List<Product>> = combine(
        _allProducts,
        _productToCategoryMap,
        _searchTerm,
        _selectedCategoryId,
        _minPrice,
        _maxPrice,
        _sortBy
    ) { values ->
        val params = extractFilterParams(values)
        
        var filtered = params.products
        
        // Aplicar filtro de categoría
        if (params.categoryId != null) {
            filtered = filtered.filter { product ->
                params.categoryMap[product.id] == params.categoryId
            }
        }
        
        // Aplicar búsqueda
        if (params.search.isNotBlank()) {
            val searchLower = params.search.lowercase()
            filtered = filtered.filter {
                it.nombre.lowercase().contains(searchLower) ||
                it.descripcion.lowercase().contains(searchLower) ||
                it.ingredientes.lowercase().contains(searchLower)
            }
        }
        
        // Aplicar filtro de precio
        filtered = filtered.filter { it.precio >= params.min && it.precio <= params.max }
        
        // Aplicar ordenamiento
        filtered = when (params.sort) {
            com.example.milsaborestest.presentation.ui.components.SortOption.NAME_ASC -> filtered.sortedBy { it.nombre }
            com.example.milsaborestest.presentation.ui.components.SortOption.NAME_DESC -> filtered.sortedByDescending { it.nombre }
            com.example.milsaborestest.presentation.ui.components.SortOption.PRICE_ASC -> filtered.sortedBy { it.precio }
            com.example.milsaborestest.presentation.ui.components.SortOption.PRICE_DESC -> filtered.sortedByDescending { it.precio }
            com.example.milsaborestest.presentation.ui.components.SortOption.RATING_DESC -> filtered.sortedByDescending { it.rating }
            else -> filtered
        }
        
        filtered
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    init {
        loadCategories()
        loadFeaturedProducts()
        loadAllProducts()
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                Log.d(Constants.TAG, "ProductViewModel: Cargando categorías desde API...")
                val categoriesDto = RetrofitInstance.api.getCategories()
                Log.d(Constants.TAG, "ProductViewModel: Categorías recibidas desde API: ${categoriesDto.size}")
                val categories = categoriesDto.map { it.toDomain() }
                _categoriesState.value = UiState.Success(categories)
                Log.d(Constants.TAG, "ProductViewModel: Categorías cargadas exitosamente: ${categories.size}")
            } catch (e: Exception) {
                Log.e(Constants.TAG, "ProductViewModel: Error al cargar categorías desde API", e)
                _categoriesState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    private fun loadFeaturedProducts() {
        viewModelScope.launch {
            try {
                Log.d(Constants.TAG, "ProductViewModel: Cargando productos destacados desde API...")
                val productsDto = RetrofitInstance.api.getProducts()
                Log.d(Constants.TAG, "ProductViewModel: Productos recibidos desde API: ${productsDto.size}")
                // Productos destacados: los 4 primeros con mejor rating
                val products = productsDto
                    .map { it.toDomain() }
                    .sortedByDescending { it.rating }
                    .take(4)
                _featuredProductsState.value = UiState.Success(products)
                Log.d(Constants.TAG, "ProductViewModel: Productos destacados cargados: ${products.size}")
            } catch (e: Exception) {
                Log.e(Constants.TAG, "ProductViewModel: Error al cargar productos destacados desde API", e)
                _featuredProductsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    fun loadProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            try {
                Log.d(Constants.TAG, "ProductViewModel: Cargando productos por categoría desde API: $categoryId")
                _productsByCategoryState.value = UiState.Loading
                val productsDto = RetrofitInstance.api.getProductsByCategory(categoryId)
                Log.d(Constants.TAG, "ProductViewModel: Productos recibidos desde API para categoría $categoryId: ${productsDto.size}")
                val products = productsDto.map { it.toDomain() }
                _productsByCategoryState.value = UiState.Success(products)
                Log.d(Constants.TAG, "ProductViewModel: Productos por categoría cargados: ${products.size}")
            } catch (e: Exception) {
                Log.e(Constants.TAG, "ProductViewModel: Error al cargar productos por categoría desde API", e)
                _productsByCategoryState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    fun loadProductById(productId: String) {
        viewModelScope.launch {
            try {
                Log.d(Constants.TAG, "ProductViewModel: Cargando producto por ID desde API: $productId")
                _productDetailState.value = UiState.Loading
                val productDto = RetrofitInstance.api.getProductById(productId)
                Log.d(Constants.TAG, "ProductViewModel: Producto recibido desde API: ${productDto.nombre}")
                val product = productDto.toDomain()
                _productDetailState.value = UiState.Success(product)
                Log.d(Constants.TAG, "ProductViewModel: Producto cargado exitosamente: ${product.nombre}")
            } catch (e: Exception) {
                Log.e(Constants.TAG, "ProductViewModel: Error al cargar producto por ID desde API", e)
                _productDetailState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    private fun loadAllProducts() {
        viewModelScope.launch {
            try {
                Log.d(Constants.TAG, "ProductViewModel: Cargando todos los productos desde API...")
                val productsDto = RetrofitInstance.api.getProducts()
                Log.d(Constants.TAG, "ProductViewModel: Productos recibidos desde API: ${productsDto.size}")
                val products = productsDto.map { it.toDomain() }
                _allProducts.value = products
                Log.d(Constants.TAG, "ProductViewModel: Todos los productos cargados: ${products.size}")
                
                // Calcular rango de precios
                if (products.isNotEmpty()) {
                    val prices = products.map { it.precio }
                    val minPriceValue = prices.minOrNull() ?: 0
                    val maxPriceValue = prices.maxOrNull() ?: 999999
                    
                    _minPriceDefault.value = minPriceValue
                    _maxPriceDefault.value = maxPriceValue
                    
                    // Solo actualizar filtros activos si aún no han sido modificados por el usuario
                    if (_minPrice.value == 0 && _maxPrice.value == 999999) {
                        _minPrice.value = minPriceValue
                        _maxPrice.value = maxPriceValue
                    }
                }
                
                // Construir el mapa de producto a categoría desde los DTOs
                val categoryMap = mutableMapOf<String, String>()
                productsDto.forEach { productDto ->
                    categoryMap[productDto.id] = productDto.categoryId
                }
                
                _productToCategoryMap.value = categoryMap
                Log.d(Constants.TAG, "ProductViewModel: Mapa de categorías construido: ${categoryMap.size} productos")
            } catch (e: Exception) {
                Log.e(Constants.TAG, "ProductViewModel: Error al cargar todos los productos desde API", e)
                // Error silencioso - los productos se cargarán más tarde
            }
        }
    }
    
    fun setSearchTerm(term: String) {
        _searchTerm.value = term
    }
    
    fun setSelectedCategory(categoryId: String?) {
        _selectedCategoryId.value = categoryId
    }
    
    fun setMinPrice(price: Int) {
        _minPrice.value = price
    }
    
    fun setMaxPrice(price: Int) {
        _maxPrice.value = price
    }
    
    fun setSortBy(sort: com.example.milsaborestest.presentation.ui.components.SortOption) {
        _sortBy.value = sort
    }
    
    fun clearFilters() {
        _searchTerm.value = ""
        _selectedCategoryId.value = null
        _minPrice.value = _minPriceDefault.value
        _maxPrice.value = _maxPriceDefault.value
        _sortBy.value = com.example.milsaborestest.presentation.ui.components.SortOption.DEFAULT
    }
}

