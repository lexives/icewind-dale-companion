package com.delarax.icewindDale.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.delarax.icewindDale.companion.ui.CalendarScreen
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO
        setContent {
            IcewindDaleContent {
                CalendarScreen()
            }
        }
    }
}

@Composable
fun IcewindDaleContent(content: @Composable () -> Unit) {
    IcewindDaleTheme {
        content()
    }
}