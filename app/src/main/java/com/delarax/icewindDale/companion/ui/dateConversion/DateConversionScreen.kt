package com.delarax.icewindDale.companion.ui.dateConversion

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.delarax.icewindDale.companion.data.PreferencesRepo
import com.delarax.icewindDale.companion.extensions.enumCaseToTitleCase
import com.delarax.icewindDale.companion.ui.theme.ThemeVM
import com.delarax.icewindDale.companion.ui.common.IcewindDaleTopAppBar
import com.delarax.icewindDale.companion.ui.common.SimpleExposedDropDownMenu
import com.delarax.icewindDale.companion.ui.common.SwitchWithLabel
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme

@Composable
fun DateConversionScreen() {
    val vm: DateConversionVM = hiltViewModel()
    val themeVM: ThemeVM = hiltViewModel()
    DateConversionScreenContent(
        themeVM.viewState,
        themeVM::setNightModePreference,
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
    themeViewState: ThemeVM.ViewState,
    onSelectNightModeOption: (PreferencesRepo.DarkThemePreference) -> Unit,
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
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // TODO: make the radio buttons their own component
            Text("Night Mode")
            Row(modifier = Modifier.padding(vertical = 5.dp)) {
                themeViewState.darkThemePreferences.forEach {
                    RadioButton(
                        selected = (it == themeViewState.currentDarkThemePreference),
                        onClick = { onSelectNightModeOption(it) }
                    )
                    Text(
                        text = it.name.enumCaseToTitleCase(),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
            Divider(modifier = Modifier.padding(vertical = 10.dp))

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
                    onYearTextChange = onYearTextChange
                )
            } else {
                DateInput(
                    dayList = viewState.dayList,
                    dayIndex = viewState.dayIndex,
                    monthOrSeasonLabel = stringResource(viewState.monthOrSeasonLabelRes),
                    monthOrSeasonList = viewState.monthOrSeasonList,
                    monthOrSeasonIndex = viewState.monthOrSeasonIndex,
                    yearText = viewState.yearText,
                    onSelectDay = onSelectDay,
                    onSelectMonthOrSeason = onSelectMonthOrSeason,
                    onYearTextChange = onYearTextChange
                )
                Button(
                    onClick = onConvertDate,
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text("Convert")
                }
            }
            ConversionResult(
                errorMessage = viewState.errorMessage,
                dateStringShort = viewState.standardDateString,
                dateStringLong = viewState.spokenDateString,
                dateStringLongAlternate = viewState.alternateSpokenDateString,
            )
        }
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
    onYearTextChange: (String) -> Unit
) {
    Column {
        YearTextField(yearText = yearText, onYearTextChange = onYearTextChange)
        Spacer(modifier = Modifier.height(5.dp))
        Column {
            holidayList.forEachIndexed { i, item ->
                Button(
                    onClick = { onSelectHoliday(i) },
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Text(item)
                }
            }
        }
    }
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
    errorMessage: String?,
    dateStringShort: String,
    dateStringLong: String,
    dateStringLongAlternate: String,
) {
    Row {
        Column {
            errorMessage?.let {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            } ?: run {
                Text(
                    text = dateStringShort,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = dateStringLong,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = dateStringLongAlternate,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
    }
}

/**
 * Previews
 */

@Preview
@Composable
fun DateConversionScreenPreview() {
    IcewindDaleTheme {
        DateConversionScreenContent(
            viewState = DateConversionVM.ViewState(),
            themeViewState = ThemeVM.ViewState(),
            onSelectNightModeOption = {},
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DateConversionScreenDarkPreview() {
    DateConversionScreenPreview()
}