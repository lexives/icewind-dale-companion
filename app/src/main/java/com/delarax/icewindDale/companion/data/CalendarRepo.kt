package com.delarax.icewindDale.companion.data

import com.delarax.icewindDale.companion.exceptions.IllegalDateConversionException
import com.delarax.icewindDale.companion.extensions.capitalize
import com.delarax.icewindDale.companion.extensions.isLeapYear
import com.delarax.icewindDale.companion.models.Calendar
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.models.DateConversionMode
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday
import com.delarax.icewindDale.companion.models.harpos.HarposMonth
import com.delarax.icewindDale.companion.models.harpos.formattedName
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason
import com.delarax.icewindDale.companion.models.nunavut.formattedName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRepo @Inject constructor() {

    fun getHarposDayList() : List<Int> = (1..30).toList()

    fun getNunavutDayList(season: NunavutSeason?) : List<Int> = season?.let {
        (1..it.numDays).toList()
    } ?: listOf()

    fun getHarposMonthList() : List<String> = HarposMonth.values().toList().map {
        it.formattedName()
    }

    fun getNunavutSeasonList() : List<String> = NunavutSeason.values().toList().map {
        it.formattedName()
    }

    fun getHarposHolidayList(year: Int) : List<String> = HarposHoliday.values().toList()
        .filter {
            !it.isQuadrennial || year.isLeapYear()
        }.map {
            it.fullName
        }

    fun getNunavutHolidayList(year: Int) : List<String> = NunavutHoliday.values().toList()
        .filter {
            !it.isQuadrennial || year.isLeapYear()
        }.map {
            it.fullName
        }


    fun getNunavutFromHarpos(harposDate: HarposDate) : NunavutDate =
        NunavutDate.fromAbsoluteDayNumber(harposDate.absoluteDayNumber(), harposDate.year)

    fun getHarposFromNunavut(nunavutDate: NunavutDate) : HarposDate =
        HarposDate.fromAbsoluteDayNumber(nunavutDate.absoluteDayNumber(), nunavutDate.year)

    @Throws(IllegalDateConversionException::class)
    fun convertDate(date: Date, conversionMode: DateConversionMode) : Date =
        when(conversionMode.from) {
            Calendar.HARPOS -> {
                (date as? HarposDate)?.let {
                    when (conversionMode.to) {
                        Calendar.HARPOS -> date
                        Calendar.NUNAVUT -> getNunavutFromHarpos(date)                    }
                } ?: throw IllegalDateConversionException("Cannot convert $date to HarposDate")
            }
            Calendar.NUNAVUT -> (date as? NunavutDate)?.let {
                when(conversionMode.to) {
                    Calendar.HARPOS -> getHarposFromNunavut(date)
                    Calendar.NUNAVUT -> date
                }
            } ?: throw IllegalDateConversionException("Cannot convert $date to NunavutDate")
        }
}