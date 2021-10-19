package com.delarax.icewindDale.companion.data

import com.delarax.icewindDale.companion.exceptions.IllegalDateConversionException
import com.delarax.icewindDale.companion.models.Calendar
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRepo @Inject constructor() {

    fun getNunavutFromHarpos(harposDate: HarposDate) : NunavutDate =
        NunavutDate.fromAbsoluteDayNumber(harposDate.absoluteDayNumber(), harposDate.year)

    fun getHarposFromNunavut(nunavutDate: NunavutDate) : HarposDate =
        HarposDate.fromAbsoluteDayNumber(nunavutDate.absoluteDayNumber(), nunavutDate.year)

    @Throws(IllegalDateConversionException::class)
    fun convertDate(date: Date, from: Calendar, to: Calendar) : Date = when(from) {
        Calendar.HARPOS -> {
            (date as? HarposDate)?.let {
                when (to) {
                    Calendar.HARPOS -> date
                    Calendar.NUNAVUT -> getNunavutFromHarpos(date)                    }
            } ?: throw IllegalDateConversionException("Cannot convert $date to HarposDate")
        }
        Calendar.NUNAVUT -> (date as? NunavutDate)?.let {
            when(to) {
                Calendar.HARPOS -> getHarposFromNunavut(date)
                Calendar.NUNAVUT -> date
            }
        } ?: throw IllegalDateConversionException("Cannot convert $date to NunavutDate")
    }
}