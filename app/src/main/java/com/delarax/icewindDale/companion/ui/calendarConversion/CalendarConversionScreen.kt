package com.delarax.icewindDale.companion.ui.calendarConversion

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.models.Calendar
import com.delarax.icewindDale.companion.ui.components.IcewindDaleTopAppBar
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme

@Composable
fun CalendarConversionScreen() {
    val calendarConversionScreenVM: CalendarConversionVM = hiltViewModel()
    CalendarConversionScreenContent(
        calendarConversionScreenVM.viewState,
        calendarConversionScreenVM::toggleConversionMode
    )
}

@Composable
fun CalendarConversionScreenContent(
    viewState: CalendarConversionVM.ViewState,
    onToggleConversionMode: (Boolean) -> Unit
) {
    Scaffold (
        topBar = {
            IcewindDaleTopAppBar(
                title = stringResource(id = R.string.app_name)
            )
        }
    ) {
        Column {
            Switch(
                checked = viewState.convertFrom == Calendar.NUNAVUT,
                onCheckedChange = onToggleConversionMode
            )
            Text(text = "Calendar mode: ${viewState.convertFrom.fullName} " +
                    "to ${viewState.convertTo.fullName}")
        }
    }
}

/**
 * Previews
 */

@Composable
@Preview
fun CalendarConversionScreenPreview() {
    IcewindDaleTheme {
        CalendarConversionScreen()
    }
}

//@Composable
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//fun CalendarConversionScreenDarkPreview() {
//    CalendarConversionScreenPreview()
//}