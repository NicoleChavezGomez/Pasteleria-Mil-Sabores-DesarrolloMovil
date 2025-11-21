package com.example.milsaborestest.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.milsaborestest.presentation.ui.screens.account.AccountScreen
import com.example.milsaborestest.presentation.ui.screens.cart.CartScreen
import com.example.milsaborestest.presentation.ui.screens.home.HomeScreen
import com.example.milsaborestest.presentation.ui.screens.login.LoginScreen
import com.example.milsaborestest.presentation.ui.screens.productdetail.ProductDetailScreen
import com.example.milsaborestest.presentation.ui.screens.products.AllProductsScreen
import com.example.milsaborestest.presentation.ui.screens.register.RegisterScreen
import com.example.milsaborestest.presentation.viewmodel.CartViewModel

@Composable
fun MilSaboresNavGraph(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onCategoryClick = { categoryId ->
                        navController.navigate(Screen.Products.createRoute(categoryId))
                    },
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onNavigateToCart = {
                        navController.navigate(Screen.Cart.route)
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    },
                    onNavigateToAllProducts = {
                        navController.navigate(Screen.Products.createRoute())
                    },
                    cartViewModel = cartViewModel
                )
            }
            
            // Ruta para productos sin categoría específica (más general PRIMERO)
            composable("products") { backStackEntry ->
                AllProductsScreen(
                    initialCategoryId = null,
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onNavigateToCart = {
                        navController.navigate(Screen.Cart.route)
                    },
                    cartViewModel = cartViewModel
                )
            }
            
            // Ruta para productos con categoría específica (más específica DESPUÉS)
            composable(
                route = "products?categoryId={categoryId}",
                arguments = listOf(
                    navArgument("categoryId") {
                        type = NavType.StringType
                        nullable = false  // NO nullable - siempre debe haber un valor
                    }
                )
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId")
                AllProductsScreen(
                    initialCategoryId = categoryId,
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onNavigateToCart = {
                        navController.navigate(Screen.Cart.route)
                    },
                    cartViewModel = cartViewModel
                )
            }
            
            composable(Screen.Cart.route) {
                CartScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    viewModel = cartViewModel
                )
            }
            
            composable(Screen.Account.route) {
                AccountScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
            
            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ProductDetailScreen(
                    productId = productId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onNavigateToCart = {
                        navController.navigate(Screen.Cart.route)
                    },
                    cartViewModel = cartViewModel
                )
            }
            
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.popBackStack()
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
            
            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.popBackStack()
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
        }
    }
}
