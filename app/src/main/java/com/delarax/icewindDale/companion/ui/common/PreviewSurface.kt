package com.delarax.icewindDale.companion.ui.common

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme

@Composable
fun PreviewSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    IcewindDaleTheme {
        Surface(modifier = modifier) {
            content()
        }
    }
}