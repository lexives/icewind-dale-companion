package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.*

data class NunavutDate(
    val day: Int,
    val season: NunavutSeason?,
    val year: Int,
    val holiday: NunavutHoliday? = null
) {
    companion object {
        @Throws(InvalidDateException::class)
        fun midwinter(year: Int) : NunavutDate = getHoliday(NunavutHoliday.MIDWINTER, year)
        @Throws(InvalidDateException::class)
        fun omingmak(year: Int) : NunavutDate = getHoliday(NunavutHoliday.OMINGMAK, year)
        @Throws(InvalidDateException::class)
        fun sunFestival(year: Int) : NunavutDate = getHoliday(NunavutHoliday.SUN_FESTIVAL, year)
        @Throws(InvalidDateException::class)
        fun alianat(year: Int) : NunavutDate = getHoliday(NunavutHoliday.ALIANAT, year)
        @Throws(InvalidDateException::class)
        fun tunniqaijuk(year: Int) : NunavutDate = getHoliday(NunavutHoliday.TUNNIQAIJUK, year)
        @Throws(InvalidDateException::class)
        fun moonFeast(year: Int) : NunavutDate = getHoliday(NunavutHoliday.MOON_FEAST, year)

        @Throws(InvalidDateException::class)
        private fun getHoliday(holiday: NunavutHoliday, year: Int) : NunavutDate {
            val date = NunavutDate(day = 1, season = null, year = year, holiday = holiday)
            if (!date.isValid()) { throw(InvalidDateException(date)) }
            return date
        }
    }
}

enum class NunavutSeason(
    val numDays: Int,
    val abbreviation: NunavutAbbreviation,
    val fullName: String,
    val animal: String
) {
    FALLING_STARS(30, FS, "Season of the Falling Stars", "Owl"),
    IGLOO(40, I, "Season of the Igloo", "Yeti"),
    SEAL_PUPS(40, SP, "Season of the Seal Pups", "Seal"),
    BEATING_ICE(20, BI, "Season of the Beating Ice", "Knucklehead Trout"),
    NESTING_GEESE(30, NG, "Season of the Nesting Geese", "Fox"),
    SKIN_TENTS(40, ST, "Season of the Skin Tents", "Eagle"),
    RUNNING_CHAR(30, RC, "Season of the Running Char", "Whale"),
    BERRIES(20, B, "Season of the Berries", "Hare"),
    BARE_MOUNTAIN(40, BM, "Season of the Bare Mountain", "Goat"),
    JARLMOOT(20, J, "Season of the Jarlmoot", "Wolf"),
    ELK_HUNT(30, EH, "Season of the Elk Hunt", "Elk"),
    DENNING_POLAR_BEAR(20, DP, "Season of the Denning Polar Bear", "Polar Bear")
}

enum class NunavutHoliday(
    val priorSeason: NunavutSeason,
    val nextSeason: NunavutSeason,
    val isQuadrennial: Boolean,
    val abbreviation: NunavutAbbreviation,
    val fullName: String,
    val animal: String
) {
    MIDWINTER(FALLING_STARS, IGLOO, true, M, "Midwinter", "Auril"),
    OMINGMAK(IGLOO, SEAL_PUPS, false, O, "Omingmak", "Narwhal"),
    SUN_FESTIVAL(NESTING_GEESE, SKIN_TENTS, false, FS, "The Festival of the Sun", "Griffon"),
    ALIANAT(BERRIES, BARE_MOUNTAIN, false,  A, "Alianat","Tiger"),
    TUNNIQAIJUK(JARLMOOT, ELK_HUNT, false, T, "Tunniqaijuk", "Mammoth"),
    MOON_FEAST(ELK_HUNT, DENNING_POLAR_BEAR, false, FM, "Feast of the Moon", "Hippogryf")
}

enum class NunavutAbbreviation {
    DP, FS, I, SP, BI, NG, ST, RC, B, BM, J, EH, // Seasons
    M, O, A, T, FM // Holidays (FS is a duplicate)
}