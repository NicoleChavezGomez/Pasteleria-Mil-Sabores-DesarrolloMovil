package com.example.milsaborestest.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.milsaborestest.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCarousel(
    items: List<ProductCarouselItem>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { items.size }, initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    
    // Auto-scroll cada 5 segundos
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % items.size
            pagerState.animateScrollToPage(nextPage)
        }
    }
    
    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp)
        ) { page ->
            val item = items[page]
            AsyncImage(
                model = item.image,
                contentDescription = item.title,
                placeholder = painterResource(R.drawable.ic_product_default),
                error = painterResource(R.drawable.ic_product_default),
                fallback = painterResource(R.drawable.ic_product_default),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item.productId) },
                contentScale = ContentScale.Fit
            )
        }
        
        // Flecha izquierda
        IconButton(
            onClick = {
                val prevPage = if (pagerState.currentPage == 0) items.size - 1 else pagerState.currentPage - 1
                coroutineScope.launch {
                    pagerState.animateScrollToPage(prevPage)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), RoundedCornerShape(50))
                .size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Anterior",
                modifier = Modifier.size(24.dp)
            )
        }
        
        // Flecha derecha
        IconButton(
            onClick = {
                val nextPage = (pagerState.currentPage + 1) % items.size
                coroutineScope.launch {
                    pagerState.animateScrollToPage(nextPage)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), RoundedCornerShape(50))
                .size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Siguiente",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

data class ProductCarouselItem(
    val productId: String,
    val image: Any, // Puede ser String (URL), Int (drawable), Painter, etc.
    val title: String
)

