package com.delarax.icewindDale.companion.ui.dateConversion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.delarax.icewindDale.companion.data.CalendarRepo
import com.delarax.icewindDale.companion.extensions.capitalize
import com.delarax.icewindDale.companion.extensions.toStringOrEmpty
import com.delarax.icewindDale.companion.models.Calendar.HARPOS
import com.delarax.icewindDale.companion.models.Calendar.NUNAVUT
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.models.DateConversionMode
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday
import com.delarax.icewindDale.companion.models.harpos.HarposMonth
import com.delarax.icewindDale.companion.models.harpos.toDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
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
        val conversionMode: DateConversionMode = DateConversionMode(from = HARPOS, to = NUNAVUT),
        val holidayModeSwitchChecked: Boolean = false,
        val dayList: List<String> = listOf(),
        val monthOrSeasonList: List<String> = listOf(),
        val holidayList: List<String> = listOf(),
        val monthOrSeasonLabel: String = MONTH_LABEL,
        val dayIndex: Int = 0,
        val monthOrSeasonIndex: Int = 0,
        val year: Int? = null,
        val convertedDate: Date? = null,
        val result: String = ""
    ) {
        val day: Int = dayIndex + 1
        val month: HarposMonth? = if (conversionMode.from != HARPOS) null else {
            HarposMonth.values()[monthOrSeasonIndex]
        }
        val season: NunavutSeason? = if (conversionMode.from != NUNAVUT) null else {
            NunavutSeason.values()[monthOrSeasonIndex]
        }

        val calendarModeSwitchChecked: Boolean = conversionMode.from == NUNAVUT
        val calendarModeLabel: String = "Calendar mode: ${conversionMode.from.name.capitalize()} " +
                "to ${conversionMode.to.name.capitalize()}"

        val holidayModeLabel: String = if (holidayModeSwitchChecked) {
            "Holiday mode: ON"
        } else {
            "Holiday mode: OFF"
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
        viewState = viewState.year?.let {
            val holidayList = when (viewState.conversionMode.from) {
                HARPOS -> calendarRepo.getHarposHolidayList(it)
                NUNAVUT -> calendarRepo.getNunavutHolidayList(it)
            }
            viewState.copy(holidayList = holidayList, result = "")
        } ?: if (viewState.holidayModeSwitchChecked) {
            viewState.copy(
                holidayList = listOf(),
                result = "Enter a year to see the list of holidays."
            )
        } else { viewState }
    }

    fun onConvertDate() {
        val date: Date = when (viewState.conversionMode.from) {
            HARPOS -> {
                HarposDate(viewState.day, viewState.month, viewState.year ?: 0)
            }
            NUNAVUT -> {
                NunavutDate(viewState.day, viewState.season, viewState.year ?: 0)
            }
        }
        convertDate(date)
    }

    fun onSelectHoliday(index: Int) {
        try {
            val date: Date = when (viewState.conversionMode.from) {
                HARPOS -> HarposHoliday.values()[index].toDate(viewState.year ?: 0)
                NUNAVUT -> NunavutHoliday.values()[index].toDate(viewState.year ?: 0)
            }
            convertDate(date)
        } catch(error: Exception) {
            viewState = viewState.copy(convertedDate = null, result = "Error converting date.")
        }
    }

    fun toggleConversionMode(toggleValue: Boolean) {
        val convertFrom = when (toggleValue) {
            true -> NUNAVUT
            false -> HARPOS
        }
        val convertTo = when (convertFrom) {
            HARPOS -> NUNAVUT
            NUNAVUT -> HARPOS
        }
        viewState = viewState.copy(
            conversionMode = DateConversionMode(convertFrom, convertTo),
            monthOrSeasonLabel = if (convertFrom == HARPOS) MONTH_LABEL else SEASON_LABEL,
            dayIndex = 0,
            monthOrSeasonIndex = 0,
            result = ""
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
            viewState = viewState.copy(result = "")
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
        viewState = viewState.copy(year = yearText.toIntOrNull())
        if (viewState.holidayModeSwitchChecked) {
            getHolidayList()
        }
    }

    private fun convertDate(date: Date) {
        viewState = try {
            val convertedDate = calendarRepo.convertDate(date, viewState.conversionMode)
            viewState.copy(convertedDate = convertedDate, result = convertedDate.toStringOrEmpty())
        } catch(error: Exception) {
            viewState.copy(convertedDate = null, result = "Error converting date.")
        }
    }

    companion object {
        const val MONTH_LABEL = "Month"
        const val SEASON_LABEL = "Season"
    }
}