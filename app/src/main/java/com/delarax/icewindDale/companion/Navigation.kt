package com.delarax.icewindDale.companion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.delarax.icewindDale.companion.ui.dateConversion.DateConversionScreen

@Composable
fun IcewindDaleNavigation() {
    val navController = rememberNavController()
    val navActions = remember(navController) { NavActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Routes.DATE_CONVERSION
    ) {
        composable(Routes.DATE_CONVERSION) {
            DateConversionScreen()
        }
    }
}

object Routes {
    const val DATE_CONVERSION = "dateConversion"
}

object RouteArgs {

}

class NavActions(navController: NavController) {
    val back: () -> Unit = { navController.popBackStack() }
}