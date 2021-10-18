package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.models.InvalidDateException

data class NunavutDate(
    val day: Int,
    val season: NunavutSeason?,
    val year: Int,
    val holiday: NunavutHoliday? = null
) {
    companion object {
        @Throws(InvalidDateException::class)
        fun midwinter(year: Int) : NunavutDate = getHoliday(NunavutHoliday.MIDWINTER, year)
        @Throws(InvalidDateException::class)
        fun omingmak(year: Int) : NunavutDate = getHoliday(NunavutHoliday.OMINGMAK, year)
        @Throws(InvalidDateException::class)
        fun sunFestival(year: Int) : NunavutDate = getHoliday(NunavutHoliday.SUN_FESTIVAL, year)
        @Throws(InvalidDateException::class)
        fun alianat(year: Int) : NunavutDate = getHoliday(NunavutHoliday.ALIANAT, year)
        @Throws(InvalidDateException::class)
        fun tunniqaijuk(year: Int) : NunavutDate = getHoliday(NunavutHoliday.TUNNIQAIJUK, year)
        @Throws(InvalidDateException::class)
        fun moonFeast(year: Int) : NunavutDate = getHoliday(NunavutHoliday.MOON_FEAST, year)

        @Throws(InvalidDateException::class)
        private fun getHoliday(holiday: NunavutHoliday, year: Int) : NunavutDate {
            val date = NunavutDate(day = 1, season = null, year = year, holiday = holiday)
            if (!date.isValid()) { throw(InvalidDateException(date)) }
            return date
        }
    }
}