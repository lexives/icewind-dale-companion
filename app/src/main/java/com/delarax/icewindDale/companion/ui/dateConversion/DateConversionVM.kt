package com.delarax.icewindDale.companion.ui.dateConversion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.delarax.icewindDale.companion.data.CalendarRepo
import com.delarax.icewindDale.companion.extensions.toStringOrEmpty
import com.delarax.icewindDale.companion.models.Calendar.HARPOS
import com.delarax.icewindDale.companion.models.Calendar.NUNAVUT
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.models.DateConversionMode
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.harpos.HarposMonth
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason
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
        val dayList: List<String> = listOf(),
        val monthOrSeasonList: List<String> = listOf(),
        val monthOrSeasonLabel: String = MONTH_LABEL,
        val dayIndex: Int = 0,
        val monthOrSeasonIndex: Int = 0,
        val year: Int = 1,
        val convertedDate: Date? = null
    ) {
        val day: Int = dayIndex + 1
        val month: HarposMonth? = if (conversionMode.from != HARPOS) null else {
            HarposMonth.values()[monthOrSeasonIndex]
        }
        val season: NunavutSeason? = if (conversionMode.from != NUNAVUT) null else {
            NunavutSeason.values()[monthOrSeasonIndex]
        }
    }

    init {
        getDayList()
        getMonthOrSeasonList()
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

    fun convertDate() {
        val date: Date = when (viewState.conversionMode.from) {
            HARPOS -> {
                HarposDate(day = viewState.day, month = viewState.month, year = viewState.year)
            }
            NUNAVUT -> {
                NunavutDate(day = viewState.day, season = viewState.season, year = viewState.year)
            }
        }
        val convertedDate = calendarRepo.convertDate(date, viewState.conversionMode)
        viewState = viewState.copy(convertedDate = convertedDate)
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
            monthOrSeasonIndex = 0
        )
        getDayList()
        getMonthOrSeasonList()
    }

    // TODO: error handling
    fun updateDayIndex(index: Int) {
        viewState = viewState.copy(dayIndex = index)
    }

    // TODO: error handling
    fun updateMonthOrSeasonIndex(index: Int) {
        viewState = viewState.copy(monthOrSeasonIndex = index)
        getDayList()
    }

    // TODO: error handling
    fun updateYear(yearText: String) {
        viewState = viewState.copy(year = yearText.toInt())
    }

    companion object {
        const val MONTH_LABEL = "Month"
        const val SEASON_LABEL = "Season"
    }
}