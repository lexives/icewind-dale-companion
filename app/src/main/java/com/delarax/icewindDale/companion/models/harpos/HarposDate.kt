package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.harpos.HarposMonth.*

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

enum class HarposMonth(val num: Int, val commonName: String) {
    HAMMER(1, "Deepwinter"),
    ALTURIAK(2, "The Claw of Winter"),
    CHES(3, "The Claw of Sunsets"),
    TARSAKH(4, "The Claw of Storms"),
    MIRTUL(5, "The Melting"),
    KYTHORN(6, "The Time of Flowers"),
    FLAMERULE(7, "Summertide"),
    ELEASIAS(8, "Highsun"),
    ELEINT(9, "The Fading"),
    MARPENOTH(10, "Leaffall"),
    UKTAR(11, "The Rotting"),
    NIGHTAL(12, "The Drawing Down"),
}

enum class HarposHoliday(
    val priorMonth: HarposMonth,
    val nextMonth: HarposMonth,
    val fullName: String,
    val isQuadrennial: Boolean
) {
    MIDWINTER(HAMMER, ALTURIAK, "Midwinter", true),
    SHIELDMEET(HAMMER, ALTURIAK, "Shieldmeet", false),
    GREENGRASS(TARSAKH, MIRTUL, "Greengrass", false),
    MIDSUMMER(FLAMERULE, ELEASIAS, "Midsummer", false),
    HIGHHARVESTTIDE(ELEINT, MARPENOTH, "Highharvesttide", false),
    MOON_FEAST(UKTAR, NIGHTAL, "The Feast of the Moon", false)
}

