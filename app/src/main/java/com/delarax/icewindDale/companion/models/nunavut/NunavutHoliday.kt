package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.*

enum class NunavutHoliday(
    val priorSeason: NunavutSeason,
    val nextSeason: NunavutSeason,
    val isQuadrennial: Boolean,
    val abbreviation: NunavutAbbreviation,
    val fullName: String,
    val animal: String
) {
    MIDWINTER(NunavutSeason.FALLING_STARS, NunavutSeason.IGLOO, true, M, "Midwinter", "Auril"),
    OMINGMAK(NunavutSeason.IGLOO, NunavutSeason.SEAL_PUPS, false, O, "Omingmak", "Narwhal"),
    SUN_FESTIVAL(
        NunavutSeason.NESTING_GEESE,
        NunavutSeason.SKIN_TENTS, false, FS, "The Festival of the Sun", "Griffon"
    ),
    ALIANAT(NunavutSeason.BERRIES, NunavutSeason.BARE_MOUNTAIN, false,  A, "Alianat","Tiger"),
    TUNNIQAIJUK(NunavutSeason.JARLMOOT, NunavutSeason.ELK_HUNT, false, T, "Tunniqaijuk", "Mammoth"),
    MOON_FEAST(
        NunavutSeason.ELK_HUNT,
        NunavutSeason.DENNING_POLAR_BEAR, false, FM, "Feast of the Moon", "Hippogryf"
    )
}