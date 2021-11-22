package com.delarax.icewindDale.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.delarax.icewindDale.companion.data.PreferencesRepo.DarkThemePreference.OFF
import com.delarax.icewindDale.companion.data.PreferencesRepo.DarkThemePreference.ON
import com.delarax.icewindDale.companion.ui.navigation.IcewindDaleScaffold
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme
import com.delarax.icewindDale.companion.ui.theme.ThemeVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IcewindDaleContent()
        }
    }
}

@Composable
fun IcewindDaleContent() {
    val themeVM: ThemeVM = hiltViewModel()
    val darkTheme: Boolean = when (themeVM.viewState.currentDarkThemePreference) {
        OFF -> false
        ON -> true
        else -> isSystemInDarkTheme()
    }
    IcewindDaleTheme(darkTheme = darkTheme) {
        IcewindDaleScaffold()
    }
}