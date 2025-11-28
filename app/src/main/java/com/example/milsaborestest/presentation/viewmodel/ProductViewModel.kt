package com.example.milsaborestest.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborestest.data.local.database.AppDatabase
import com.example.milsaborestest.data.repository.ProductRepositoryImpl
import com.example.milsaborestest.domain.model.Product
import com.example.milsaborestest.domain.usecase.GetAllProductsUseCase
import com.example.milsaborestest.domain.usecase.GetCategoriesUseCase
import com.example.milsaborestest.domain.usecase.GetFeaturedProductsUseCase
import com.example.milsaborestest.domain.usecase.GetProductsByCategoryUseCase
import com.example.milsaborestest.domain.usecase.GetProductByIdUseCase
import com.example.milsaborestest.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val categoryDao = database.categoryDao()
    private val productDao = database.productDao()
    private val productRepository = ProductRepositoryImpl(categoryDao, productDao)
    private val getCategoriesUseCase = GetCategoriesUseCase(productRepository)
    private val getProductsByCategoryUseCase = GetProductsByCategoryUseCase(productRepository)
    private val getProductByIdUseCase = GetProductByIdUseCase(productRepository)
    private val getFeaturedProductsUseCase = GetFeaturedProductsUseCase(productRepository)
    private val getAllProductsUseCase = GetAllProductsUseCase(productRepository)
    
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
        val products: List<Product> = values[0] as List<Product>
        val categoryMap: Map<String, String> = values[1] as Map<String, String>
        val search: String = values[2] as String
        val categoryId: String? = values[3] as? String
        val min: Int = values[4] as Int
        val max: Int = values[5] as Int
        val sort: com.example.milsaborestest.presentation.ui.components.SortOption = values[6] as com.example.milsaborestest.presentation.ui.components.SortOption
        
        var filtered = products
        
        // Aplicar filtro de categoría
        if (categoryId != null) {
            filtered = filtered.filter { product ->
                categoryMap[product.id] == categoryId
            }
        }
        
        // Aplicar búsqueda
        if (search.isNotBlank()) {
            val searchLower = search.lowercase()
            filtered = filtered.filter {
                it.nombre.lowercase().contains(searchLower) ||
                it.descripcion.lowercase().contains(searchLower) ||
                it.ingredientes.lowercase().contains(searchLower)
            }
        }
        
        // Aplicar filtro de precio
        filtered = filtered.filter { it.precio >= min && it.precio <= max }
        
        // Aplicar ordenamiento
        filtered = when (sort) {
            com.example.milsaborestest.presentation.ui.components.SortOption.NAME_ASC -> filtered.sortedBy { it.nombre }
            com.example.milsaborestest.presentation.ui.components.SortOption.NAME_DESC -> filtered.sortedByDescending { it.nombre }
            com.example.milsaborestest.presentation.ui.components.SortOption.PRICE_ASC -> filtered.sortedBy { it.precio }
            com.example.milsaborestest.presentation.ui.components.SortOption.PRICE_DESC -> filtered.sortedByDescending { it.precio }
            com.example.milsaborestest.presentation.ui.components.SortOption.RATING_DESC -> filtered.sortedByDescending { it.rating }
            com.example.milsaborestest.presentation.ui.components.SortOption.REVIEWS_DESC -> filtered.sortedByDescending { it.reviews }
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
                val categories = getCategoriesUseCase()
                _categoriesState.value = UiState.Success(categories)
            } catch (e: Exception) {
                _categoriesState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    private fun loadFeaturedProducts() {
        viewModelScope.launch {
            try {
                val products = getFeaturedProductsUseCase()
                _featuredProductsState.value = UiState.Success(products)
            } catch (e: Exception) {
                _featuredProductsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    fun loadProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            try {
                _productsByCategoryState.value = UiState.Loading
                val products = getProductsByCategoryUseCase(categoryId)
                _productsByCategoryState.value = UiState.Success(products)
            } catch (e: Exception) {
                _productsByCategoryState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    fun loadProductById(productId: String) {
        viewModelScope.launch {
            try {
                _productDetailState.value = UiState.Loading
                val product = getProductByIdUseCase(productId)
                if (product != null) {
                    _productDetailState.value = UiState.Success(product)
                } else {
                    _productDetailState.value = UiState.Error("Producto no encontrado")
                }
            } catch (e: Exception) {
                _productDetailState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    private fun loadAllProducts() {
        viewModelScope.launch {
            try {
                val products = getAllProductsUseCase()
                _allProducts.value = products
                
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
                
                // Construir el mapa de producto a categoría
                val categoryMap = mutableMapOf<String, String>()
                val categories = getCategoriesUseCase()
                
                categories.forEach { category ->
                    val productsInCategory = getProductsByCategoryUseCase(category.id)
                    productsInCategory.forEach { product ->
                        categoryMap[product.id] = category.id
                    }
                }
                
                _productToCategoryMap.value = categoryMap
            } catch (e: Exception) {
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

