package com.delarax.icewindDale.companion.ui.calendarConversion

import androidx.lifecycle.ViewModel
import com.delarax.icewindDale.companion.data.CalendarRepo
import com.delarax.icewindDale.companion.models.Calendar
import com.delarax.icewindDale.companion.models.Calendar.HARPOS
import com.delarax.icewindDale.companion.models.Calendar.NUNAVUT
import com.delarax.icewindDale.companion.models.Date
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarConversionVM @Inject constructor(
    private val calendarRepo: CalendarRepo
) : ViewModel() {

    data class ViewState(
        val convertFrom: Calendar = HARPOS,
        val convertTo: Calendar = NUNAVUT,
        val dayList: List<Int> = listOf(),
        val monthOrSeasonList: List<String> = listOf(),
        val day: Int? = null,
        val monthOrSeason: String? = null,
        val year: Int? = null
    )

    fun convertDate(date: Date, from: Calendar, to: Calendar) : Date =
        calendarRepo.convertDate(date, from, to)
}