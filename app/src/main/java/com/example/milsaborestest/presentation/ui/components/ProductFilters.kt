package com.example.milsaborestest.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceAtMost
import com.example.milsaborestest.util.Constants.Design
import com.example.milsaborestest.util.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFilters(
    searchTerm: String,
    onSearchChange: (String) -> Unit,
    categories: List<com.example.milsaborestest.domain.model.Category>,
    selectedCategoryId: String?,
    onCategoryChange: (String?) -> Unit,
    minPrice: Int,
    maxPrice: Int,
    minPriceDefault: Int,
    maxPriceDefault: Int,
    onMinPriceChange: (Int) -> Unit,
    onMaxPriceChange: (Int) -> Unit,
    sortBy: SortOption = SortOption.DEFAULT,
    onSortChange: (SortOption) -> Unit,
    productsCount: Int,
    onApplyFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Design.CARD_ELEVATION)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_STANDARD)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD)
        ) {
            // Búsqueda
            Text(
                text = "Búsqueda",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            OutlinedTextField(
                value = searchTerm,
                onValueChange = onSearchChange,
                label = { Text("Buscar") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    if (searchTerm.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(Icons.Filled.Close, contentDescription = "Limpiar")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            HorizontalDivider()
            
            // Categorías
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            var categoryExpanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCategoryId?.let { id ->
                        categories.find { it.id == id }?.nombre ?: "Todas"
                    } ?: "Todas",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Todas las Categorías") },
                        onClick = {
                            onCategoryChange(null)
                            categoryExpanded = false
                        }
                    )
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.nombre) },
                            onClick = {
                                onCategoryChange(category.id)
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }
            
            HorizontalDivider()
            
            // Rango de precio con slider
            Text(
                text = "Rango de Precio",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            // Mostrar valores actuales
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = minPrice.formatPrice(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = maxPrice.formatPrice(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            // Slider de rango con incrementos de 10
            RangeSlider(
                value = minPrice.toFloat()..maxPrice.toFloat(),
                onValueChange = { range ->
                    val min = (range.start.toInt() / 10) * 10
                    val max = ((range.endInclusive.toInt() + 9) / 10) * 10
                    onMinPriceChange(min.coerceAtLeast(minPriceDefault))
                    onMaxPriceChange(max.coerceAtMost(maxPriceDefault))
                },
                valueRange = minPriceDefault.toFloat()..maxPriceDefault.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                steps = ((maxPriceDefault - minPriceDefault) / 10 - 1).coerceAtLeast(0)
            )
            
            // Campos de texto para edición manual
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = minPrice.toString(),
                    onValueChange = { 
                        it.toIntOrNull()?.let { value ->
                            onMinPriceChange(value.coerceAtLeast(minPriceDefault).coerceAtMost(maxPrice))
                        }
                    },
                    label = { Text("Mín") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                
                OutlinedTextField(
                    value = maxPrice.toString(),
                    onValueChange = { 
                        it.toIntOrNull()?.let { value ->
                            onMaxPriceChange(value.coerceAtLeast(minPrice).coerceAtMost(maxPriceDefault))
                        }
                    },
                    label = { Text("Máx") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            
            HorizontalDivider()
            
            // Ordenar por
            Text(
                text = "Ordenar Por",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            var expanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = sortBy.label,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    SortOption.values().forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.label) },
                            onClick = {
                                onSortChange(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            HorizontalDivider()
            
            // Contador de productos
            Text(
                text = "Productos encontrados: $productsCount",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(Design.PADDING_SMALL))
            
            // Botón aplicar filtros
            Button(
                onClick = onApplyFilters,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Aplicar Filtros",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

enum class SortOption(val label: String) {
    DEFAULT("Por defecto"),
    NAME_ASC("Nombre A-Z"),
    NAME_DESC("Nombre Z-A"),
    PRICE_ASC("Precio: Menor a Mayor"),
    PRICE_DESC("Precio: Mayor a Menor"),
    RATING_DESC("Mejor Calificados")
}

