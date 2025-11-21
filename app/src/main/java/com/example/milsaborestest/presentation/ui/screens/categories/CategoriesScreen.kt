package com.example.milsaborestest.presentation.ui.screens.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.milsaborestest.presentation.ui.components.CategoryCard
import com.example.milsaborestest.presentation.ui.components.LoadingIndicator
import com.example.milsaborestest.presentation.viewmodel.ProductViewModel
import com.example.milsaborestest.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    productViewModel: ProductViewModel = viewModel()
) {
    val categoriesState by productViewModel.categoriesState.collectAsState()
    val currentState = categoriesState
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categorías") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = { /* TODO: Implementar búsqueda */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (currentState) {
            is UiState.Loading -> {
                LoadingIndicator(modifier = Modifier.padding(paddingValues))
            }
            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(currentState.data) { category ->
                        CategoryCard(
                            category = category,
                            onCategoryClick = onCategoryClick
                        )
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = "Error: ${currentState.message}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

