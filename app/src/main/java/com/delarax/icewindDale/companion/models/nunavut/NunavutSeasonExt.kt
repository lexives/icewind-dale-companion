package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.data.isLeapYear

fun NunavutSeason.num() :Int = this.ordinal + 1

fun NunavutSeason.priorSeason() : NunavutSeason = NunavutSeason.values().let { seasons ->
    val priorSeasonIndex = (this.ordinal - 1).takeIf { it >= 0 } ?: seasons.size - 1
    seasons[priorSeasonIndex]
}

fun NunavutSeason.nextSeason(): NunavutSeason = NunavutSeason.values().let { seasons ->
    val nextSeasonIndex = (this.ordinal + 1).takeIf { it < seasons.size } ?: 0
    seasons[nextSeasonIndex]
}

fun NunavutSeason.lastHoliday(year: Int) : NunavutHoliday = NunavutHoliday.values()
    .reversed()
    .find { holiday ->
        holiday.nextSeason == this && (year.isLeapYear() || !holiday.isQuadrennial)
    } ?: this.priorSeason().let { priorSeason ->
    if (priorSeason.ordinal == NunavutSeason.values().lastIndex) {
        priorSeason.lastHoliday(year - 1)
    } else {
        priorSeason.lastHoliday(year)
    }
}

fun NunavutSeason.nextHoliday(year: Int): NunavutHoliday = NunavutHoliday.values()
    .find { holiday ->
        holiday.priorSeason == this && (year.isLeapYear() || !holiday.isQuadrennial)
    } ?: this.nextSeason().let { nextSeason ->
        if (nextSeason.ordinal == 0) {
            nextSeason.nextHoliday(year + 1)
        } else {
            nextSeason.nextHoliday(year)
        }
    }

/**
 * Returns the sum of all the days in the seasons up to and including the given season.
 * Does not include holidays.
 *
 * @sample
 * FALLING_STARS has 30 days, IGLOO has 40 days
 * so IGLOO.numDaysInSeasons == 70 days
 */
fun NunavutSeason.numDaysInSeasons(): Int = NunavutSeason.values()
    .slice(0..this.ordinal)
    .map { it.numDays }
    .sum()