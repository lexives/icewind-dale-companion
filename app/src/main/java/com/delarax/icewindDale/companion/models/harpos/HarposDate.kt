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

enum class HarposMonth(val commonName: String) {
    HAMMER("Deepwinter"),
    ALTURIAK("The Claw of Winter"),
    CHES("The Claw of Sunsets"),
    TARSAKH("The Claw of Storms"),
    MIRTUL("The Melting"),
    KYTHORN("The Time of Flowers"),
    FLAMERULE("Summertide"),
    ELEASIAS("Highsun"),
    ELEINT("The Fading"),
    MARPENOTH("Leaffall"),
    UKTAR("The Rotting"),
    NIGHTAL("The Drawing Down")
}

enum class HarposHoliday(
    val priorMonth: HarposMonth,
    val nextMonth: HarposMonth,
    val isQuadrennial: Boolean,
    val fullName: String
) {
    MIDWINTER(HAMMER, ALTURIAK, true, "Midwinter"),
    SHIELDMEET(HAMMER, ALTURIAK, false, "Shieldmeet"),
    GREENGRASS(TARSAKH, MIRTUL, false, "Greengrass"),
    MIDSUMMER(FLAMERULE, ELEASIAS, false, "Midsummer"),
    HIGHHARVESTTIDE(ELEINT, MARPENOTH, false, "Highharvesttide"),
    MOON_FEAST(UKTAR, NIGHTAL, false, "The Feast of the Moon")
}

