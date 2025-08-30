package com.example.moneytracker.viewmodels.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.view.Login
import com.example.moneytracker.view.SignUp
import com.example.moneytracker.view.Start

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Start.route) {
            Start(navController)
        }

        composable(Screens.SignUp.route) {
            SignUp(navController)
        }

        composable(Screens.Login.route) {
            Login(navController)
        }
    }
}