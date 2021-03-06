package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.extensions.isLeapYear
import com.delarax.icewindDale.companion.models.exceptions.InvalidDateException

@Throws(InvalidDateException::class)
fun NunavutHoliday.priorHoliday(year: Int) : NunavutHoliday =
    NunavutHoliday.values().let { holidays ->
        if (this.isQuadrennial && !year.isLeapYear()) {
            throw InvalidDateException("$year was not a leap year but $this is quadrennial.")
        }

        // Re-order the list of holidays so that the current holiday is removed and it starts with
        // the holiday immediately after the current holiday.
        // Example: this.ordinal is 3 and there are 6 holidays. Resulting list will be [4,5,6,1,2]
        val workingHolidayList = (holidays.slice((this.ordinal + 1)..holidays.lastIndex) +
                holidays.slice(0 until this.ordinal)).toMutableList()

        // Remove holidays from the list if they couldn't have occurred that year.
        // Continued example: 2 and 4 are quadrennial and current year is a leap year.
        // Resulting list will be [5,6,1,2] because 4 would have been for the prior year.
        workingHolidayList.removeAll {
            val yearForThisHoliday = if (it.ordinal > this.ordinal) { year - 1 } else year
            it.isQuadrennial && !yearForThisHoliday.isLeapYear()
        }

        // The last holiday in the list is the holiday that occurred before this one, accounting
        // for leap years
        workingHolidayList.last()
    }

@Throws(InvalidDateException::class)
fun NunavutHoliday.nextHoliday(year: Int) : NunavutHoliday =
    NunavutHoliday.values().let { holidays ->
        if (this.isQuadrennial && !year.isLeapYear()) {
            throw InvalidDateException("$year was not a leap year but $this is quadrennial.")
        }

        // Re-order the list of holidays so that the current holiday is removed and it starts with
        // the holiday immediately after the current holiday.
        // Example: this.ordinal is 3 and there are 6 holidays. Resulting list will be [4,5,6,1,2]
        val workingHolidayList = (holidays.slice((this.ordinal + 1)..holidays.lastIndex) +
                holidays.slice(0 until this.ordinal)).toMutableList()

        // Remove holidays from the list if they couldn't have occurred that year.
        // Continued example: 2 and 4 are quadrennial and current year is a leap year.
        // Resulting list will be [4,5,6,1] because 2 won't happen again until the next year
        workingHolidayList.removeAll {
            val yearForThisHoliday = if (it.ordinal < this.ordinal) { year + 1 } else year
            it.isQuadrennial && !yearForThisHoliday.isLeapYear()
        }

        // The first holiday in the list is the holiday that occurred after this one, accounting
        // for leap years
        workingHolidayList.first()
    }

/**
 * The number of holidays that have occurred in the year so far, including this holiday
 */
@Throws(InvalidDateException::class)
fun NunavutHoliday.numHolidaysPassed(year: Int) : Int {
    if (this.isQuadrennial && !year.isLeapYear()) {
        throw InvalidDateException("$year was not a leap year but $this is quadrennial.")
    }
    return NunavutHoliday.values()
        .slice(0..this.ordinal)
        .count { holiday -> year.isLeapYear() || !holiday.isQuadrennial }
}

@Throws(InvalidDateException::class)
fun NunavutHoliday.absoluteDayNumber(year: Int) : Int {
    if (this.isQuadrennial && !year.isLeapYear()) {
        throw InvalidDateException("$year was not a leap year but $this is quadrennial.")
    }
    return this.priorSeason.numDaysInSeasons() + numHolidaysPassed(year)
}

@Throws(InvalidDateException::class)
fun NunavutHoliday.toDate(year: Int) : NunavutDate {
    val date = NunavutDate(day = 1, season = null, year = year, holiday = this)
    if (!date.isValid) { throw(InvalidDateException(date)) }
    return date
}

