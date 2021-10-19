package com.delarax.icewindDale.companion.ui.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun ScreenOrientationHandler(
    landscapeContent: @Composable () -> Unit = {},
    portraitContent: @Composable () -> Unit = {}
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> portraitContent()
        else -> landscapeContent()
    }
}