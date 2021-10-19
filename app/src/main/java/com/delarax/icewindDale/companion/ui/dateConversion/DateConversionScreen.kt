package com.delarax.icewindDale.companion.ui.dateConversion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.extensions.capitalize
import com.delarax.icewindDale.companion.models.Calendar
import com.delarax.icewindDale.companion.ui.components.IcewindDaleTopAppBar
import com.delarax.icewindDale.companion.ui.components.SimpleExposedDropDownMenu
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme

@Composable
fun DateConversionScreen() {
    val dateConversionScreenVM: DateConversionVM = hiltViewModel()
    DateConversionScreenContent(
        dateConversionScreenVM.viewState,
        dateConversionScreenVM::toggleConversionMode
    )
}

@Composable
fun DateConversionScreenContent(
    viewState: DateConversionVM.ViewState,
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
                checked = viewState.conversionMode.from == Calendar.NUNAVUT,
                onCheckedChange = onToggleConversionMode
            )
            Text(text = "Calendar mode: ${viewState.conversionMode.from.name.capitalize()} " +
                    "to ${viewState.conversionMode.to.name.capitalize()}")
            SimpleExposedDropDownMenu(
                values = viewState.dayList,
                selectedIndex = 0,
                onChange = { },
                label = {},
                modifier = Modifier.width(200.dp),
            )
        }
    }
}

/**
 * Previews
 */

@Composable
@Preview
fun DateConversionScreenPreview() {
    IcewindDaleTheme {
        DateConversionScreen()
    }
}

//@Composable
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//fun DateConversionScreenDarkPreview() {
//    DateConversionScreenPreview()
//}