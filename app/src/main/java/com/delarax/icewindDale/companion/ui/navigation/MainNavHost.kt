package com.delarax.icewindDale.companion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.delarax.icewindDale.companion.ui.screens.dateConversion.DateConversionScreen
import com.delarax.icewindDale.companion.ui.screens.settings.SettingsScreen

@Composable
fun MainNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.DATE_CONVERSION.route
    ) {
        composable(Screen.DATE_CONVERSION.route) {
            DateConversionScreen()
        }
        composable(Screen.SETTINGS.route) {
            SettingsScreen()
        }
    }
}