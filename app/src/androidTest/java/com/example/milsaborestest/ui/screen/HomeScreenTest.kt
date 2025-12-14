package com.example.milsaborestest.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.milsaborestest.presentation.ui.screens.home.HomeScreen
import com.example.milsaborestest.presentation.viewmodel.CartViewModel
import org.junit.Rule
import org.junit.Test

/**
 * Tests de UI para HomeScreen
 * 
 * Estos tests verifican que los elementos principales de la pantalla
 * se muestren correctamente.
 */
class HomeScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    private fun getApplication(): android.app.Application {
        return InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as android.app.Application
    }
    
    @Test
    fun HomeScreen_MuestraProductosDestacados() {
        composeTestRule.setContent {
            HomeScreen(
                navController = rememberNavController(),
                cartViewModel = CartViewModel(getApplication())
            )
        }
        
        composeTestRule.onNodeWithText("Productos Destacados").assertIsDisplayed()
    }
    
    @Test
    fun HomeScreen_MuestraCategorias() {
        composeTestRule.setContent {
            HomeScreen(
                navController = rememberNavController(),
                cartViewModel = CartViewModel(getApplication())
            )
        }
        
        composeTestRule.onNodeWithText("Categorías").assertIsDisplayed()
    }
    
    @Test
    fun HomeScreen_MuestraBotonVerTodos() {
        composeTestRule.setContent {
            HomeScreen(
                navController = rememberNavController(),
                cartViewModel = CartViewModel(getApplication())
            )
        }
        
        composeTestRule.onNodeWithText("Ver todos").assertIsDisplayed()
    }
}

