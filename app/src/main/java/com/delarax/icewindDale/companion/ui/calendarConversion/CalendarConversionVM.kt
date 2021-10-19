package com.delarax.icewindDale.companion.ui.calendarConversion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.delarax.icewindDale.companion.data.CalendarRepo
import com.delarax.icewindDale.companion.models.Calendar.HARPOS
import com.delarax.icewindDale.companion.models.Calendar.NUNAVUT
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.models.DateConversionMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarConversionVM @Inject constructor(
    private val calendarRepo: CalendarRepo
) : ViewModel() {

    var viewState by mutableStateOf(ViewState())
        private set

    data class ViewState(
        val conversionMode: DateConversionMode = DateConversionMode(from = HARPOS, to = NUNAVUT),
        val dayList: List<String> = listOf(),
        val monthOrSeasonList: List<String> = listOf(),
        val day: Int? = null,
        val monthOrSeason: String? = null,
        val year: Int? = null
    )

    fun convertDate(date: Date) : Date =
        calendarRepo.convertDate(date, viewState.conversionMode)

    fun toggleConversionMode(toggleValue: Boolean) {
        val convertFrom = when (toggleValue) {
            true -> NUNAVUT
            false -> HARPOS
        }
        val convertTo = when (convertFrom) {
            HARPOS -> NUNAVUT
            NUNAVUT -> HARPOS
        }
        viewState = viewState.copy(conversionMode = DateConversionMode(convertFrom, convertTo))
    }

}