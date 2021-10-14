package com.delarax.icewindDale.companion.models

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.HarposHoliday.*

fun HarposDate.isLeapYear() : Boolean = this.year.isLeapYear()

fun HarposDate.isValid() : Boolean {
    return !(
        (this.month == null && this.holiday == null) ||
        (this.month != null && this.holiday != null) ||
        (this.year < 1) ||
        (this.day !in (1..30)) ||
        (this.holiday != null && this.day != 1) ||
        (this.holiday == MIDWINTER && !this.isLeapYear())
    )
}

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

/**
 * The number of holidays that have occurred so far, including the current day if it's a holiday.
 */
@Throws(InvalidDateException::class)
fun HarposDate.numHolidaysPassed() : Int {
    if (!this.isValid()) {
        throw InvalidDateException(this)
    }

    val lastHoliday = this.month?.lastHoliday() ?: this.holiday!!
    val numYearlyHolidaysPassed =
        if (lastHoliday == MOON_FEAST && this.month == HarposMonth.HAMMER) {
            0
        } else {
            lastHoliday.ordinal
        }

    return if (this.isLeapYear() && this.month != HarposMonth.HAMMER) {
        numYearlyHolidaysPassed + 1
    } else {
        numYearlyHolidaysPassed
    }
}
