package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.data.isLeapYear

fun HarposMonth.num() : Int = this.ordinal + 1

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