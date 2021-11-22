package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.B
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.BI
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.BM
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.DP
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.EH
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.FS
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.I
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.J
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.NG
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.RC
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.SP
import com.delarax.icewindDale.companion.models.nunavut.NunavutAbbreviation.ST

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