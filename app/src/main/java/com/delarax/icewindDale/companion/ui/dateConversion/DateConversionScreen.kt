package com.delarax.icewindDale.companion.ui.dateConversion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.extensions.capitalize
import com.delarax.icewindDale.companion.extensions.toStringOrEmpty
import com.delarax.icewindDale.companion.models.Calendar
import com.delarax.icewindDale.companion.ui.components.IcewindDaleTopAppBar
import com.delarax.icewindDale.companion.ui.components.SimpleExposedDropDownMenu
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme

@Composable
fun DateConversionScreen() {
    val vm: DateConversionVM = hiltViewModel()
    DateConversionScreenContent(
        vm.viewState,
        vm::toggleConversionMode,
        vm::updateDayIndex,
        vm::updateMonthOrSeasonIndex,
        vm::updateYear,
        vm::convertDate
    )
}

@Composable
fun DateConversionScreenContent(
    viewState: DateConversionVM.ViewState,
    onToggleConversionMode: (Boolean) -> Unit,
    onSelectDay: (Int) -> Unit,
    onSelectMonthOrSeason: (Int) -> Unit,
    onYearTextChange: (String) -> Unit,
    onConvertDate: () -> Unit
) {
    Scaffold (
        topBar = {
            IcewindDaleTopAppBar(
                title = stringResource(id = R.string.app_name)
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            /**
             * Conversion Mode Selector Section
             */
            Switch(
                checked = viewState.conversionMode.from == Calendar.NUNAVUT,
                onCheckedChange = onToggleConversionMode
            )
            Text(text = "Calendar mode: ${viewState.conversionMode.from.name.capitalize()} " +
                    "to ${viewState.conversionMode.to.name.capitalize()}")

            Divider(
                modifier = Modifier.padding(vertical = 10.dp)
            )

            /**
             * Date Input Section
             */
            Row {
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text("Day")
                    SimpleExposedDropDownMenu(
                        values = viewState.dayList,
                        selectedIndex = viewState.dayIndex,
                        onChange = onSelectDay
                    )
                }
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(viewState.monthOrSeasonLabel)
                    SimpleExposedDropDownMenu(
                        values = viewState.monthOrSeasonList,
                        selectedIndex = viewState.monthOrSeasonIndex,
                        onChange = onSelectMonthOrSeason
                    )
                }
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text("Year")
                    TextField(
                        value = viewState.year.toStringOrEmpty(),
                        onValueChange = onYearTextChange,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            /**
             * Conversion Section
             */
            Row {
                Column {
                    Button(onClick = onConvertDate) {
                        Text("Convert")
                    }
                    Text(
                        text = viewState.result,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
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
        DateConversionScreenContent(
            viewState = DateConversionVM.ViewState(),
            onToggleConversionMode = {},
            onSelectDay = {},
            onSelectMonthOrSeason = {},
            onYearTextChange = {},
            onConvertDate = {}
        )
    }
}

//@Composable
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//fun DateConversionScreenDarkPreview() {
//    DateConversionScreenPreview()
//}