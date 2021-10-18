package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.models.InvalidDateException

data class HarposDate(
    val day: Int,
    val month: HarposMonth?,
    val year: Int,
    val holiday: HarposHoliday? = null
) {
    companion object {
        @Throws(InvalidDateException::class)
        fun midwinter(year: Int) : HarposDate = getHoliday(HarposHoliday.MIDWINTER, year)
        @Throws(InvalidDateException::class)
        fun shieldmeet(year: Int) : HarposDate = getHoliday(HarposHoliday.SHIELDMEET, year)
        @Throws(InvalidDateException::class)
        fun greengrass(year: Int) : HarposDate = getHoliday(HarposHoliday.GREENGRASS, year)
        @Throws(InvalidDateException::class)
        fun midsummer(year: Int) : HarposDate = getHoliday(HarposHoliday.MIDSUMMER, year)
        @Throws(InvalidDateException::class)
        fun highharvesttide(year: Int) : HarposDate = getHoliday(HarposHoliday.HIGHHARVESTTIDE, year)
        @Throws(InvalidDateException::class)
        fun moonFeast(year: Int) : HarposDate = getHoliday(HarposHoliday.MOON_FEAST, year)

        @Throws(InvalidDateException::class)
        private fun getHoliday(holiday: HarposHoliday, year: Int) : HarposDate {
            val date = HarposDate(day = 1, month = null, year = year, holiday = holiday)
            if (!date.isValid()) { throw(InvalidDateException(date)) }
            return date
        }
    }
}

