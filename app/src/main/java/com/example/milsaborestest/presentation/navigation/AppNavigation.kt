package com.example.milsaborestest.presentation.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.milsaborestest.presentation.ui.screens.splash.SplashScreen
import com.example.milsaborestest.presentation.viewmodel.AuthViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable("products") {
            AllProductsScreen(
                navController = navController,
                initialCategoryId = null
            )
        }
        
        composable(
            route = "products?categoryId={categoryId}",
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            AllProductsScreen(
                navController = navController,
                initialCategoryId = categoryId
            )
        }
        
        composable(Screen.Cart.route) {
            CartScreen(
                navController = navController
            )
        }
        
        composable(Screen.Account.route) {
            AccountScreen(
                navController = navController,
                authViewModel = authViewModel
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
                navController = navController,
                productId = productId
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}
