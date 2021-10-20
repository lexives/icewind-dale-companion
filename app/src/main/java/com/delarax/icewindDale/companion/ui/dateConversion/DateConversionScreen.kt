package com.delarax.icewindDale.companion.ui.dateConversion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.ui.common.IcewindDaleTopAppBar
import com.delarax.icewindDale.companion.ui.common.SimpleExposedDropDownMenu
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
        vm::onSelectHoliday,
        vm::onConvertDate
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
    onSelectHoliday: (Int) -> Unit,
    onConvertDate: () -> Unit,
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

            if (viewState.holidayModeSwitchChecked) {
                HolidaySelector(
                    holidayList = viewState.holidayList,
                    onSelectHoliday = onSelectHoliday,
                    yearText = viewState.yearText,
                    onYearTextChange = onYearTextChange,
                    result = viewState.result
                )
            } else {
                DateInput(
                    dayList = viewState.dayList,
                    dayIndex = viewState.dayIndex,
                    monthOrSeasonLabel = viewState.monthOrSeasonLabel,
                    monthOrSeasonList = viewState.monthOrSeasonList,
                    monthOrSeasonIndex = viewState.monthOrSeasonIndex,
                    yearText = viewState.yearText,
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
    yearText: String,
    onSelectDay: (Int) -> Unit,
    onSelectMonthOrSeason: (Int) -> Unit,
    onYearTextChange: (String) -> Unit
) {
    Row {
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
            Text("Day")
            SimpleExposedDropDownMenu(
                values = dayList,
                selectedIndex = dayIndex,
                onChange = onSelectDay,
                modifier = Modifier.width(100.dp)
            )
        }
        YearTextField(yearText = yearText, onYearTextChange = onYearTextChange)
    }
}

@Composable
fun HolidaySelector(
    holidayList: List<String>,
    onSelectHoliday: (Int) -> Unit,
    yearText: String,
    onYearTextChange: (String) -> Unit,
    result: String
) {
    YearTextField(yearText = yearText, onYearTextChange = onYearTextChange)
    LazyColumn {
        itemsIndexed(holidayList) { i, item ->
            TextButton(onClick = { onSelectHoliday(i) }) {
                Text(item)
            }
        }
    }
    Text(
        text = result,
        modifier = Modifier.padding(vertical = 5.dp)
    )
}

@Composable
fun YearTextField(
    yearText: String,
    onYearTextChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(5.dp)
    ) {
        Text("Year")
        TextField(
            value = yearText,
            onValueChange = onYearTextChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
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
            onSelectHoliday = {},
            onConvertDate = {}
        )
    }
}

//@Composable
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//fun DateConversionScreenDarkPreview() {
//    DateConversionScreenPreview()
//}