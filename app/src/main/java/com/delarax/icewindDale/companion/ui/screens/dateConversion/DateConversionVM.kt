package com.delarax.icewindDale.companion.ui.screens.dateConversion

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.data.CalendarRepo
import com.delarax.icewindDale.companion.extensions.capitalize
import com.delarax.icewindDale.companion.extensions.isLeapYear
import com.delarax.icewindDale.companion.extensions.toIntOrZero
import com.delarax.icewindDale.companion.extensions.toStringOrEmpty
import com.delarax.icewindDale.companion.models.Calendar.HARPOS
import com.delarax.icewindDale.companion.models.Calendar.NUNAVUT
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.models.DateConversionMode
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.harpos.HarposDateFormat
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday
import com.delarax.icewindDale.companion.models.harpos.HarposMonth
import com.delarax.icewindDale.companion.models.harpos.toDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutDateFormat
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason
import com.delarax.icewindDale.companion.models.nunavut.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateConversionVM @Inject constructor(
    private val calendarRepo: CalendarRepo
) : ViewModel() {

    var viewState by mutableStateOf(ViewState())
        private set

    data class ViewState(
        val calendarModeSwitchChecked: Boolean = false,
        val holidayModeSwitchChecked: Boolean = false,
        val dayList: List<String> = listOf(),
        val monthOrSeasonList: List<String> = listOf(),
        val holidayList: List<String> = listOf(),
        val dayIndex: Int = 0,
        val monthOrSeasonIndex: Int = 0,
        val yearText: String = "",
        val convertedDate: Date? = null,
        val errorMessage: String? = null
    ) {
        val conversionMode: DateConversionMode = if (calendarModeSwitchChecked) {
            DateConversionMode(from = NUNAVUT, to = HARPOS)
        } else {
            DateConversionMode(from = HARPOS, to = NUNAVUT)
        }
        val monthOrSeasonLabelRes = if (conversionMode.from == HARPOS) {
            R.string.month_label
        } else {
            R.string.season_label
        }

        val calendarModeLabel: String = "Calendar mode: ${conversionMode.from.name.capitalize()} " +
                "to ${conversionMode.to.name.capitalize()}"

        val holidayModeLabel: String = if (holidayModeSwitchChecked) {
            "Holiday mode: ON"
        } else {
            "Holiday mode: OFF"
        }

        val day: Int = dayIndex + 1
        val month: HarposMonth? = if (conversionMode.from != HARPOS) null else {
            HarposMonth.values()[monthOrSeasonIndex]
        }
        val season: NunavutSeason? = if (conversionMode.from != NUNAVUT) null else {
            NunavutSeason.values()[monthOrSeasonIndex]
        }
        val year: Int = yearText.toIntOrZero()

        val standardDateString: String = when (convertedDate) {
            is HarposDate -> convertedDate.toString(HarposDateFormat.STANDARD)
            is NunavutDate -> convertedDate.toString(NunavutDateFormat.SHORT)
            else -> ""
        }

        val spokenDateString: String = when (convertedDate) {
            is HarposDate -> {
                if (!convertedDate.isHoliday) {
                    convertedDate.toString(HarposDateFormat.WRITTEN)
                } else ""
            }
            is NunavutDate -> convertedDate.toString(NunavutDateFormat.WRITTEN)
            else -> ""
        }

        val alternateSpokenDateString = when (convertedDate) {
            is HarposDate -> {
                if (!convertedDate.isHoliday) {
                    convertedDate.toString(HarposDateFormat.WRITTEN_ALTERNATE)
                } else ""
            }
            else -> ""
        }
    }

    init {
        getDayList()
        getMonthOrSeasonList()
        getHolidayList()
    }

    private fun getDayList() {
        val dayList = when(viewState.conversionMode.from) {
            HARPOS -> calendarRepo.getHarposDayList()
            NUNAVUT -> calendarRepo.getNunavutDayList(viewState.season)
        }.map {
            it.toStringOrEmpty()
        }
        viewState = viewState.copy(dayList = dayList)
    }

    private fun getMonthOrSeasonList() {
        val monthOrSeasonList = when(viewState.conversionMode.from) {
            HARPOS -> calendarRepo.getHarposMonthList()
            NUNAVUT -> calendarRepo.getNunavutSeasonList()
        }
        viewState = viewState.copy(monthOrSeasonList = monthOrSeasonList)
    }

    private fun getHolidayList() {
        viewState = if (viewState.yearText.isEmpty()) {
            if (viewState.holidayModeSwitchChecked) {
                viewState.copy(
                    holidayList = listOf(),
                    errorMessage = NEED_YEAR_FOR_HOLIDAY_MESSAGE
                )
            } else { viewState }
        } else {
            val holidayList = when (viewState.conversionMode.from) {
                HARPOS -> calendarRepo.getHarposHolidayList(viewState.year)
                NUNAVUT -> calendarRepo.getNunavutHolidayList(viewState.year)
            }
            viewState.copy(holidayList = holidayList, errorMessage = null)
        }
    }

    fun toggleConversionMode(toggleValue: Boolean) {
        viewState = viewState.copy(
            calendarModeSwitchChecked = toggleValue,
            dayIndex = 0,
            monthOrSeasonIndex = 0,
            errorMessage = null
        )
        getDayList()
        getMonthOrSeasonList()
        getHolidayList()
    }

    fun toggleHolidayMode(toggleValue: Boolean) {
        viewState = viewState.copy(holidayModeSwitchChecked = toggleValue)
        if (toggleValue) {
            getHolidayList()
        } else {
            viewState = viewState.copy(errorMessage = null)
        }
    }

    fun updateDayIndex(index: Int) {
        viewState = viewState.copy(dayIndex = index)
    }

    fun updateMonthOrSeasonIndex(index: Int) {
        viewState = viewState.copy(monthOrSeasonIndex = index)
        getDayList()
    }

    fun updateYear(yearText: String) {
        viewState = viewState.copy(yearText = yearText)
        if (viewState.holidayModeSwitchChecked) {
            getHolidayList()
        }
    }

    fun onConvertDate() {
        val date: Date = when (viewState.conversionMode.from) {
            HARPOS -> {
                HarposDate(viewState.day, viewState.month, viewState.year)
            }
            NUNAVUT -> {
                NunavutDate(viewState.day, viewState.season, viewState.year)
            }
        }
        convertDate(date)
    }

    fun onSelectHoliday(index: Int) {
        try {
            val adjustedIndex = if (viewState.year.isLeapYear()) { index } else { index + 1 }
            val date: Date = when (viewState.conversionMode.from) {
                HARPOS -> HarposHoliday.values()[adjustedIndex].toDate(viewState.year)
                NUNAVUT -> NunavutHoliday.values()[adjustedIndex].toDate(viewState.year)
            }
            convertDate(date)
        } catch(error: Throwable) {
            logError(DATE_CONVERSION_ERROR + " " + error.message)
            viewState = viewState.copy(convertedDate = null, errorMessage = DATE_CONVERSION_ERROR)
        }
    }

    private fun convertDate(date: Date) {
        viewState = try {
            val convertedDate = calendarRepo.convertDate(date, viewState.conversionMode)

            viewState.copy(convertedDate = convertedDate, errorMessage = null)
        } catch(error: Throwable) {
            logError(DATE_CONVERSION_ERROR + " " + error.message)
            viewState.copy(convertedDate = null, errorMessage = DATE_CONVERSION_ERROR)
        }
    }

    private fun logError(message: String) {
        Log.e(TAG, message)
    }

    companion object {
        const val TAG = "DATE_CONVERSION"
        const val DATE_CONVERSION_ERROR = "Error converting date."
        const val NEED_YEAR_FOR_HOLIDAY_MESSAGE = "Enter a year to see the list of holidays."
    }
}