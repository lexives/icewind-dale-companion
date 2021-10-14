package com.delarax.icewindDale.companion.models

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.HarposHoliday.*

fun HarposMonth.priorMonth() : HarposMonth = HarposMonth.values().let { months ->
    val priorMonthIndex = (this.ordinal - 1).takeIf { it >= 0 } ?: months.size - 1
    months[priorMonthIndex]
}

fun HarposMonth.nextMonth() : HarposMonth = HarposMonth.values().let { months ->
    val nextMonthIndex = (this.ordinal + 1).takeIf { it < months.size } ?: 0
    months[nextMonthIndex]
}

fun HarposMonth.lastHoliday() : HarposHoliday = when (this) {
    SHIELDMEET.nextMonth -> SHIELDMEET
    GREENGRASS.nextMonth -> GREENGRASS
    MIDSUMMER.nextMonth -> MIDSUMMER
    HIGHHARVESTTIDE.nextMonth -> HIGHHARVESTTIDE
    MOON_FEAST.nextMonth -> MOON_FEAST
    else -> this.priorMonth().lastHoliday()
}

fun HarposMonth.nextHoliday(year: Int) : HarposHoliday = when (this) {
    SHIELDMEET.priorMonth -> {
        if (year.isLeapYear()) MIDWINTER
        else SHIELDMEET
    }
    GREENGRASS.priorMonth -> GREENGRASS
    MIDSUMMER.priorMonth -> MIDSUMMER
    HIGHHARVESTTIDE.priorMonth -> HIGHHARVESTTIDE
    MOON_FEAST.priorMonth -> MOON_FEAST
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
