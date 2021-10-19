package com.delarax.icewindDale.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.delarax.icewindDale.companion.ui.calendarConversion.CalendarConversionScreen
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IcewindDaleContent {
                CalendarConversionScreen()
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