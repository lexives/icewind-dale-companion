package com.delarax.icewindDale.companion.models

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.NunavutHoliday.*

fun NunavutDate.isLeapYear() : Boolean = this.year.isLeapYear()

fun NunavutDate.isValid() : Boolean {
    return !(
        (this.season == null && this.holiday == null) ||
        (this.season != null && this.holiday != null) ||
        (this.year < 1) ||
        (this.season != null && this.day !in (1..this.season.numDays)) ||
        (this.holiday != null && this.day != 1)
    )
}

fun NunavutSeason.priorSeason() : NunavutSeason = NunavutSeason.values().let { seasons ->
    val priorSeasonIndex = (this.ordinal - 1).takeIf { it >= 0 } ?: seasons.size - 1
    seasons[priorSeasonIndex]
}

fun NunavutSeason.nextSeason(): NunavutSeason = NunavutSeason.values().let { seasons ->
    val nextSeasonIndex = (this.ordinal + 1).takeIf { it < seasons.size } ?: 0
    seasons[nextSeasonIndex]
}

fun NunavutSeason.lastHoliday(year: Int) : NunavutHoliday = when (this) {
    MIDWINTER.nextSeason -> {
        if (year.isLeapYear()) MIDWINTER
        else this.priorSeason().lastHoliday(year)
    }
    OMINGMAK.nextSeason -> OMINGMAK
    SUN_FESTIVAL.nextSeason -> SUN_FESTIVAL
    ALIANAT.nextSeason -> ALIANAT
    MOON_FEAST.nextSeason -> MOON_FEAST
    else -> this.priorSeason().lastHoliday(year)
}

fun NunavutSeason.nextHoliday(year: Int): NunavutHoliday = when (this) {
    MIDWINTER.priorSeason -> {
        if (year.isLeapYear()) MIDWINTER
        else this.nextSeason().nextHoliday(year)
    }
    OMINGMAK.priorSeason -> OMINGMAK
    SUN_FESTIVAL.priorSeason -> SUN_FESTIVAL
    ALIANAT.priorSeason -> ALIANAT
    MOON_FEAST.priorSeason -> MOON_FEAST
    else -> this.nextSeason().nextHoliday(year)
}

