package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.extensions.enumCaseToTitleCase
import com.delarax.icewindDale.companion.extensions.isLeapYear

fun HarposMonth.num() : Int = this.ordinal + 1

fun HarposMonth.formattedName() : String = this.name.enumCaseToTitleCase()

fun HarposMonth.priorMonth() : HarposMonth = HarposMonth.values().let { months ->
    val priorMonthIndex = (this.ordinal - 1).takeIf { it >= 0 } ?: months.lastIndex
    months[priorMonthIndex]
}

fun HarposMonth.nextMonth() : HarposMonth = HarposMonth.values().let { months ->
    val nextMonthIndex = (this.ordinal + 1).takeIf { it <= months.lastIndex } ?: 0
    months[nextMonthIndex]
}

fun HarposMonth.lastHoliday(year: Int) : HarposHoliday = HarposHoliday.values()
    .reversed()
    .find { holiday ->
        holiday.nextMonth == this && (year.isLeapYear() || !holiday.isQuadrennial)
    } ?: this.priorMonth().let { priorMonth ->
        if (priorMonth.ordinal == HarposMonth.values().lastIndex) {
            priorMonth.lastHoliday(year - 1)
        } else {
            priorMonth.lastHoliday(year)
        }
    }

fun HarposMonth.nextHoliday(year: Int) : HarposHoliday = HarposHoliday.values()
    .find { holiday ->
        holiday.priorMonth == this && (year.isLeapYear() || !holiday.isQuadrennial)
    } ?: this.nextMonth().let { nextMonth ->
    if (nextMonth.ordinal == 0) {
        nextMonth.nextHoliday(year + 1)
    } else {
        nextMonth.nextHoliday(year)
    }
}

/**
 * The number of holidays that have occurred before this month
 */
fun HarposMonth.numHolidaysPassed(year: Int) : Int {
    // find the last holiday that occurred within the SAME year (null if there are none)
    val lastHoliday = this.lastHoliday(year).takeIf { lastHoliday ->
        val finalHolidayOfTheYear = HarposHoliday.values().last()
        lastHoliday.ordinal != finalHolidayOfTheYear.ordinal ||
                this.ordinal >= finalHolidayOfTheYear.nextMonth.ordinal
    }

    return lastHoliday?.let {
        HarposHoliday.values()
            .slice(0..it.ordinal)
            .count { holiday -> year.isLeapYear() || !holiday.isQuadrennial }
    } ?: 0
}