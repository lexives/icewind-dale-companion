package com.delarax.icewindDale.companion.ui

import android.content.res.Configuration
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.ui.components.IcewindDaleTopAppBar
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme

@Composable
fun CalendarScreen() {
    CalendarScreenContent()
}

@Composable
fun CalendarScreenContent() {
    Scaffold (
        topBar = {
            IcewindDaleTopAppBar(
                title = stringResource(id = R.string.app_name)
            )
        }
    ) {
        Text(text = "Calendar")
    }
}

/**
 * Previews
 */

@Composable
@Preview
fun CalendarScreenPreview() {
    IcewindDaleTheme {
        CalendarScreen()
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CalendarScreenDarkPreview() {
    IcewindDaleTheme {
        CalendarScreen()
    }
}