package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.data.isLeapYear

fun NunavutSeason.priorSeason() : NunavutSeason = NunavutSeason.values().let { seasons ->
    val priorSeasonIndex = (this.ordinal - 1).takeIf { it >= 0 } ?: seasons.size - 1
    seasons[priorSeasonIndex]
}

fun NunavutSeason.nextSeason(): NunavutSeason = NunavutSeason.values().let { seasons ->
    val nextSeasonIndex = (this.ordinal + 1).takeIf { it < seasons.size } ?: 0
    seasons[nextSeasonIndex]
}

fun NunavutSeason.lastHoliday(year: Int) : NunavutHoliday = when (this) {
    NunavutHoliday.MIDWINTER.nextSeason -> {
        if (year.isLeapYear()) NunavutHoliday.MIDWINTER
        else this.priorSeason().lastHoliday(year)
    }
    NunavutHoliday.OMINGMAK.nextSeason -> NunavutHoliday.OMINGMAK
    NunavutHoliday.SUN_FESTIVAL.nextSeason -> NunavutHoliday.SUN_FESTIVAL
    NunavutHoliday.ALIANAT.nextSeason -> NunavutHoliday.ALIANAT
    NunavutHoliday.MOON_FEAST.nextSeason -> NunavutHoliday.MOON_FEAST
    else -> this.priorSeason().lastHoliday(year)
}

fun NunavutSeason.nextHoliday(year: Int): NunavutHoliday = when (this) {
    NunavutHoliday.MIDWINTER.priorSeason -> {
        if (year.isLeapYear()) NunavutHoliday.MIDWINTER
        else this.nextSeason().nextHoliday(year)
    }
    NunavutHoliday.OMINGMAK.priorSeason -> NunavutHoliday.OMINGMAK
    NunavutHoliday.SUN_FESTIVAL.priorSeason -> NunavutHoliday.SUN_FESTIVAL
    NunavutHoliday.ALIANAT.priorSeason -> NunavutHoliday.ALIANAT
    NunavutHoliday.MOON_FEAST.priorSeason -> NunavutHoliday.MOON_FEAST
    else -> this.nextSeason().nextHoliday(year)
}