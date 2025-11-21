package com.example.milsaborestest.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.milsaborestest.presentation.navigation.Screen
import com.example.milsaborestest.ui.theme.BrandPink
import com.example.milsaborestest.ui.theme.BrandPinkLight
import com.example.milsaborestest.ui.theme.CardWhite
import com.example.milsaborestest.ui.theme.TextMedium
import com.example.milsaborestest.util.Constants.Design

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            icon = Icons.Filled.Home,
            label = "Inicio"
        ),
        BottomNavItem(
            route = Screen.Products.route,
            icon = Icons.Filled.ShoppingBag,
            label = "Productos"
        ),
        BottomNavItem(
            route = Screen.Cart.route,
            icon = Icons.Filled.ShoppingCart,
            label = "Carrito"
        ),
        BottomNavItem(
            route = Screen.Account.route,
            icon = Icons.Filled.AccountCircle,
            label = "Cuenta"
        )
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = Design.TOPBAR_ELEVATION
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { 
                    Icon(item.icon, contentDescription = item.label)
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Pop everything off the back stack before navigating
                            popUpTo(Screen.Home.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrandPink,
                    selectedTextColor = BrandPink,
                    indicatorColor = BrandPinkLight,
                    unselectedIconColor = TextMedium,
                    unselectedTextColor = TextMedium
                )
            )
        }
    }
}

