package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.data.isLeapYear

fun HarposMonth.priorMonth() : HarposMonth = HarposMonth.values().let { months ->
    val priorMonthIndex = (this.ordinal - 1).takeIf { it >= 0 } ?: months.size - 1
    months[priorMonthIndex]
}

fun HarposMonth.nextMonth() : HarposMonth = HarposMonth.values().let { months ->
    val nextMonthIndex = (this.ordinal + 1).takeIf { it < months.size } ?: 0
    months[nextMonthIndex]
}

fun HarposMonth.lastHoliday() : HarposHoliday = when (this) {
    HarposHoliday.SHIELDMEET.nextMonth -> HarposHoliday.SHIELDMEET
    HarposHoliday.GREENGRASS.nextMonth -> HarposHoliday.GREENGRASS
    HarposHoliday.MIDSUMMER.nextMonth -> HarposHoliday.MIDSUMMER
    HarposHoliday.HIGHHARVESTTIDE.nextMonth -> HarposHoliday.HIGHHARVESTTIDE
    HarposHoliday.MOON_FEAST.nextMonth -> HarposHoliday.MOON_FEAST
    else -> this.priorMonth().lastHoliday()
}

fun HarposMonth.nextHoliday(year: Int) : HarposHoliday = when (this) {
    HarposHoliday.SHIELDMEET.priorMonth -> {
        if (year.isLeapYear()) HarposHoliday.MIDWINTER
        else HarposHoliday.SHIELDMEET
    }
    HarposHoliday.GREENGRASS.priorMonth -> HarposHoliday.GREENGRASS
    HarposHoliday.MIDSUMMER.priorMonth -> HarposHoliday.MIDSUMMER
    HarposHoliday.HIGHHARVESTTIDE.priorMonth -> HarposHoliday.HIGHHARVESTTIDE
    HarposHoliday.MOON_FEAST.priorMonth -> HarposHoliday.MOON_FEAST
    else -> {
        this.nextMonth().let {
            if (it == HarposMonth.HAMMER) {
                it.nextHoliday(year + 1)
            } else {
                it.nextHoliday(year)
            }
        }
    }
}