package com.example.moneytracker.viewmodels.navigation

sealed class Screens(val route: String) {
    data object SignUp: Screens("signUpScreen")
    data object Login: Screens("loginScreen")
    data object Start: Screens("startScreen")
    data object Commute : Screens("commuteScreen")
    data object Items : Screens("itemsScreen")
}