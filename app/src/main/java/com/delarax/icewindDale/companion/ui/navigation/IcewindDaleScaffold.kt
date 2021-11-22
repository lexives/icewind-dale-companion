package com.delarax.icewindDale.companion.ui.navigation

import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.ui.common.ActionItem
import com.delarax.icewindDale.companion.ui.common.DrawerMenuItem
import com.delarax.icewindDale.companion.ui.common.IcewindDaleSideDrawerContent
import com.delarax.icewindDale.companion.ui.common.IcewindDaleTopAppBar
import com.delarax.icewindDale.companion.ui.screens.dateConversion.DateConversionScreen
import kotlinx.coroutines.launch

@Composable
fun IcewindDaleScaffold() {
    val navController = rememberNavController()
    val navActions = remember(navController) { NavActions(navController) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = Screen.values().find { it.route == currentDestination?.route }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val unknownScreenToast = Toast.makeText(
        LocalContext.current,
        R.string.screen_does_not_exist,
        Toast.LENGTH_SHORT
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            IcewindDaleTopAppBar(
                title = stringResource(id = currentScreen?.titleRes ?: R.string.app_name),
                leftActionItem = ActionItem(
                    name = if (scaffoldState.drawerState.isOpen) {
                        stringResource(id = R.string.close_left_drawer_menu)
                    } else {
                        stringResource(id = R.string.open_left_drawer_menu)
                    },
                    icon = Icons.Rounded.Menu,
                    onClick = {
                        scope.launch {
                            if (scaffoldState.drawerState.isOpen) {
                                scaffoldState.drawerState.close()
                            } else {
                                scaffoldState.drawerState.open()
                            }
                        }
                    }
                ),
                actionItems = currentScreen?.topBarActionItems ?: listOf()
            )
        },
        drawerContent = {
            IcewindDaleSideDrawerContent(Screen.values().map { screen ->
                DrawerMenuItem(
                    nameRes = screen.titleRes,
                    icon = screen.icon,
                    onClick = {
                        try {
                            navActions.popUpToScreen(screen)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        } catch (e: IllegalArgumentException) {
                            // This exception occurs if the destination does not exist
                            unknownScreenToast.show()
                        }
                    }
                )
            })
        },
        drawerBackgroundColor = MaterialTheme.colors.surface
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.DATE_CONVERSION.route
        ) {
            composable(Screen.DATE_CONVERSION.route) {
                DateConversionScreen()
            }
        }
    }
}