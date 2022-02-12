package com.wkk.wanandroid

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wkk.wanandroid.ui.MainScreen
import com.wkk.wanandroid.ui.login.LoginScreen
import com.wkk.wanandroid.ui.login.RegisterScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.MAIN,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = startDestination, modifier) {
        composable(AppDestinations.MAIN) { MainScreen() }
        composable(AppDestinations.LOGIN) { LoginScreen() }
        composable(AppDestinations.REGISTER) { RegisterScreen() }
    }
}
