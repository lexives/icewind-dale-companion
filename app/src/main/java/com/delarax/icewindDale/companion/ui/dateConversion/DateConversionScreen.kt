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
import com.delarax.icewindDale.companion.extensions.toStringOrEmpty
import com.delarax.icewindDale.companion.ui.components.IcewindDaleTopAppBar
import com.delarax.icewindDale.companion.ui.components.SimpleExposedDropDownMenu
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme

@Composable
fun DateConversionScreen() {
    val vm: DateConversionVM = hiltViewModel()
    DateConversionScreenContent(
        vm.viewState,
        vm::toggleConversionMode,
        vm::toggleHolidayMode,
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
    onToggleHolidayMode: (Boolean) -> Unit,
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
            SwitchWithLabel(
                checked = viewState.calendarModeSwitchChecked,
                onCheckedChange = onToggleConversionMode,
                labelText = viewState.calendarModeLabel
            )
            Divider(modifier = Modifier.padding(vertical = 10.dp))

            SwitchWithLabel(
                checked = viewState.holidayModeSwitchChecked,
                onCheckedChange = onToggleHolidayMode,
                labelText = viewState.holidayModeLabel
            )
            Divider(modifier = Modifier.padding(vertical = 10.dp))

            DateInput(
                dayList = viewState.dayList,
                dayIndex = viewState.dayIndex,
                monthOrSeasonLabel = viewState.monthOrSeasonLabel,
                monthOrSeasonList = viewState.monthOrSeasonList,
                monthOrSeasonIndex = viewState.monthOrSeasonIndex,
                yearString = viewState.year.toStringOrEmpty(),
                onSelectDay = onSelectDay,
                onSelectMonthOrSeason = onSelectMonthOrSeason,
                onYearTextChange = onYearTextChange
            )

            ConversionResult(
                onConvertDate = onConvertDate,
                result = viewState.result
            )
        }
    }
}

@Composable
fun SwitchWithLabel(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    labelText: String = ""
) {
    Column(modifier = modifier) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(text = labelText)
    }
}

@Composable
fun DateInput(
    dayList: List<String>,
    dayIndex: Int,
    monthOrSeasonLabel: String,
    monthOrSeasonList: List<String>,
    monthOrSeasonIndex: Int,
    yearString: String,
    onSelectDay: (Int) -> Unit,
    onSelectMonthOrSeason: (Int) -> Unit,
    onYearTextChange: (String) -> Unit
) {
    Row {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text("Day")
            SimpleExposedDropDownMenu(
                values = dayList,
                selectedIndex = dayIndex,
                onChange = onSelectDay
            )
        }
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(monthOrSeasonLabel)
            SimpleExposedDropDownMenu(
                values = monthOrSeasonList,
                selectedIndex = monthOrSeasonIndex,
                onChange = onSelectMonthOrSeason
            )
        }
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text("Year")
            TextField(
                value = yearString,
                onValueChange = onYearTextChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
fun ConversionResult(
    onConvertDate: () -> Unit,
    result: String
) {
    Row {
        Column {
            Button(onClick = onConvertDate) {
                Text("Convert")
            }
            Text(
                text = result,
                modifier = Modifier.padding(vertical = 5.dp)
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
        DateConversionScreenContent(
            viewState = DateConversionVM.ViewState(),
            onToggleConversionMode = {},
            onToggleHolidayMode = {},
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