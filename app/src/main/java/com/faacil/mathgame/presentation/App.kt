package com.faacil.mathgame.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.faacil.mathgame.enums.CalculationType
import com.faacil.mathgame.presentation.common.atoms.topAppBarBackBtn
import com.faacil.mathgame.presentation.pages.CalculationScreen
import com.faacil.mathgame.presentation.pages.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()

    // Usar el estado actual de backStackEntry para determinar la ruta activa
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Math Game") },
                navigationIcon = topAppBarBackBtn(currentRoute, navController)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("home") { HomeScreen(navController) }
                composable(
                    "calculation/{operation}",
                    arguments = listOf(navArgument("operation") { type = NavType.StringType })
                ) { backStackEntry ->
                    CalculationScreen(
                        title = backStackEntry.arguments?.getString("operation") ?: "addition",
                        action = CalculationType.valueOf(
                            backStackEntry.arguments?.getString("operation")?.uppercase()
                                ?: "ADDITION"
                        )
                    )
                }
            }
        }
    }
}
