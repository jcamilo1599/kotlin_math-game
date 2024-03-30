package com.faacil.mathgame.presentation.common.atoms

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun topAppBarBackBtn(
    currentRoute: String?,
    navController: NavHostController,
): @Composable () -> Unit {
    if (currentRoute?.startsWith("calculation") == true) {
        return {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
            }
        }
    } else {
        return {}
    }
}