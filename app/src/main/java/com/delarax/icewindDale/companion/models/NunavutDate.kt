package com.delarax.icewindDale.companion.models

import com.delarax.icewindDale.companion.models.NunavutAbbreviation.*
import com.delarax.icewindDale.companion.models.NunavutSeason.*

data class NunavutDate(
    val day: Int,
    val season: NunavutSeason?,
    val year: Int,
    val holiday: NunavutHoliday? = null
)

enum class NunavutSeason(
    val num: Int,
    val fullName: String,
    val abbreviation: NunavutAbbreviation,
    val animal: String,
    val numDays: Int
) {
    DENNING_POLAR_BEAR(1, "Season of the Denning Polar Bear", DP, "Polar Bear", 20),
    FALLING_STARS(2, "Season of the Falling Stars", FS, "Owl", 30),
    IGLOO(3, "Season of the Igloo", I, "Yeti", 40),
    SEAL_PUPS(4, "Season of the Seal Pups", SP, "Seal", 40),
    BEATING_ICE(5, "Season of the Beating Ice", BI, "Knucklehead Trout", 20),
    NESTING_GEESE(6, "Season of the Nesting Geese", NG, "Fox", 30),
    SKIN_TENTS(7, "Season of the Skin Tents", ST, "Eagle", 40),
    RUNNING_CHAR(8, "Season of the Running Char", RC, "Whale", 30),
    BERRIES(9, "Season of the Berries", B, "Hare", 20),
    BARE_MOUNTAIN(10, "Season of the Bare Mountain", BM, "Goat", 40),
    JARLMOOT(11, "Season of the Jarlmoot", J, "Wolf", 20),
    ELK_HUNT(12, "Season of the Elk Hunt", EH, "Elk", 30),
}

enum class NunavutHoliday(
    val priorSeason: NunavutSeason,
    val nextSeason: NunavutSeason,
    val fullName: String,
    val abbreviation: NunavutAbbreviation,
    val animal: String,
    val isQuadrennial: Boolean
) {
    MIDWINTER(FALLING_STARS, IGLOO, "Midwinter", M, "Auril", true),
    OMINGMAK(IGLOO, SEAL_PUPS, "Omingmak", O, "Narwhal", false),
    SUN_FESTIVAL(NESTING_GEESE, SKIN_TENTS, "The Festival of the Sun", FS, "Griffon", false),
    ALIANAT(BERRIES, BARE_MOUNTAIN, "Alianat", A, "Tiger", false),
    MOON_FEAST(ELK_HUNT, DENNING_POLAR_BEAR, "Feast of the Moon", FM, "Hippogryf", false)
}

enum class NunavutAbbreviation {
    DP, FS, I, SP, BI, NG, ST, RC, B, BM, J, EH, // Seasons
    M, O, A, FM // Holidays (FS is a duplicate)
}