package com.delarax.icewindDale.compantion.extensions

import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.ALIANAT
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.MIDWINTER
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.MOON_FEAST
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.OMINGMAK
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.SUN_FESTIVAL
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.TUNNIQAIJUK
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.BARE_MOUNTAIN
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.BEATING_ICE
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.BERRIES
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.DENNING_POLAR_BEAR
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.ELK_HUNT
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.FALLING_STARS
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.IGLOO
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.JARLMOOT
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.NESTING_GEESE
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.RUNNING_CHAR
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.SEAL_PUPS
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.SKIN_TENTS
import com.delarax.icewindDale.companion.models.nunavut.lastHoliday
import com.delarax.icewindDale.companion.models.nunavut.nextHoliday
import com.delarax.icewindDale.companion.models.nunavut.nextSeason
import com.delarax.icewindDale.companion.models.nunavut.numDaysInSeasons
import com.delarax.icewindDale.companion.models.nunavut.numHolidaysPassed
import com.delarax.icewindDale.companion.models.nunavut.priorSeason
import org.junit.Assert.assertEquals
import org.junit.Test

class NunavutSeasonExtTests {

    @Test
    fun `priorSeason returns correct season in all cases`() {
        assertEquals(ELK_HUNT, DENNING_POLAR_BEAR.priorSeason())
        assertEquals(JARLMOOT, ELK_HUNT.priorSeason())
        assertEquals(BARE_MOUNTAIN, JARLMOOT.priorSeason())
        assertEquals(BERRIES, BARE_MOUNTAIN.priorSeason())
        assertEquals(RUNNING_CHAR, BERRIES.priorSeason())
        assertEquals(SKIN_TENTS, RUNNING_CHAR.priorSeason())
        assertEquals(NESTING_GEESE, SKIN_TENTS.priorSeason())
        assertEquals(BEATING_ICE, NESTING_GEESE.priorSeason())
        assertEquals(SEAL_PUPS, BEATING_ICE.priorSeason())
        assertEquals(IGLOO, SEAL_PUPS.priorSeason())
        assertEquals(FALLING_STARS, IGLOO.priorSeason())
        assertEquals(DENNING_POLAR_BEAR, FALLING_STARS.priorSeason())
    }

    @Test
    fun `nextSeason returns correct season in all cases`() {
        assertEquals(IGLOO, FALLING_STARS.nextSeason())
        assertEquals(SEAL_PUPS, IGLOO.nextSeason())
        assertEquals(BEATING_ICE, SEAL_PUPS.nextSeason())
        assertEquals(NESTING_GEESE, BEATING_ICE.nextSeason())
        assertEquals(SKIN_TENTS, NESTING_GEESE.nextSeason())
        assertEquals(RUNNING_CHAR, SKIN_TENTS.nextSeason())
        assertEquals(BERRIES, RUNNING_CHAR.nextSeason())
        assertEquals(BARE_MOUNTAIN, BERRIES.nextSeason())
        assertEquals(JARLMOOT, BARE_MOUNTAIN.nextSeason())
        assertEquals(ELK_HUNT, JARLMOOT.nextSeason())
        assertEquals(DENNING_POLAR_BEAR, ELK_HUNT.nextSeason())
        assertEquals(FALLING_STARS, DENNING_POLAR_BEAR.nextSeason())
    }

    @Test
    fun `lastHoliday returns correct holiday for not leap year`() {
        val year = 1001
        assertEquals(MOON_FEAST, FALLING_STARS.lastHoliday(year))
        assertEquals(MOON_FEAST, IGLOO.lastHoliday(year))
        assertEquals(OMINGMAK, SEAL_PUPS.lastHoliday(year))
        assertEquals(OMINGMAK, BEATING_ICE.lastHoliday(year))
        assertEquals(OMINGMAK, NESTING_GEESE.lastHoliday(year))
        assertEquals(SUN_FESTIVAL, SKIN_TENTS.lastHoliday(year))
        assertEquals(SUN_FESTIVAL, RUNNING_CHAR.lastHoliday(year))
        assertEquals(SUN_FESTIVAL, BERRIES.lastHoliday(year))
        assertEquals(ALIANAT, BARE_MOUNTAIN.lastHoliday(year))
        assertEquals(ALIANAT, JARLMOOT.lastHoliday(year))
        assertEquals(TUNNIQAIJUK, ELK_HUNT.lastHoliday(year))
        assertEquals(MOON_FEAST, DENNING_POLAR_BEAR.lastHoliday(year))
    }

    @Test
    fun `lastHoliday returns correct holiday for leap year`() {
        val year = 1004
        assertEquals(MOON_FEAST, FALLING_STARS.lastHoliday(year))
        assertEquals(MIDWINTER, IGLOO.lastHoliday(year))
        assertEquals(OMINGMAK, SEAL_PUPS.lastHoliday(year))
        assertEquals(OMINGMAK, BEATING_ICE.lastHoliday(year))
        assertEquals(OMINGMAK, NESTING_GEESE.lastHoliday(year))
        assertEquals(SUN_FESTIVAL, SKIN_TENTS.lastHoliday(year))
        assertEquals(SUN_FESTIVAL, RUNNING_CHAR.lastHoliday(year))
        assertEquals(SUN_FESTIVAL, BERRIES.lastHoliday(year))
        assertEquals(ALIANAT, BARE_MOUNTAIN.lastHoliday(year))
        assertEquals(ALIANAT, JARLMOOT.lastHoliday(year))
        assertEquals(TUNNIQAIJUK, ELK_HUNT.lastHoliday(year))
        assertEquals(MOON_FEAST, DENNING_POLAR_BEAR.lastHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        val year = 1001
        assertEquals(OMINGMAK, FALLING_STARS.nextHoliday(year))
        assertEquals(OMINGMAK, IGLOO.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, SEAL_PUPS.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, BEATING_ICE.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, NESTING_GEESE.nextHoliday(year))
        assertEquals(ALIANAT, SKIN_TENTS.nextHoliday(year))
        assertEquals(ALIANAT, RUNNING_CHAR.nextHoliday(year))
        assertEquals(ALIANAT, BERRIES.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, BARE_MOUNTAIN.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, JARLMOOT.nextHoliday(year))
        assertEquals(MOON_FEAST, ELK_HUNT.nextHoliday(year))
        assertEquals(OMINGMAK, DENNING_POLAR_BEAR.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        val year = 1003
        assertEquals(OMINGMAK, FALLING_STARS.nextHoliday(year))
        assertEquals(OMINGMAK, IGLOO.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, SEAL_PUPS.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, BEATING_ICE.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, NESTING_GEESE.nextHoliday(year))
        assertEquals(ALIANAT, SKIN_TENTS.nextHoliday(year))
        assertEquals(ALIANAT, RUNNING_CHAR.nextHoliday(year))
        assertEquals(ALIANAT, BERRIES.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, BARE_MOUNTAIN.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, JARLMOOT.nextHoliday(year))
        assertEquals(MOON_FEAST, ELK_HUNT.nextHoliday(year))
        assertEquals(MIDWINTER, DENNING_POLAR_BEAR.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday for leap year`() {
        val year = 1004
        assertEquals(MIDWINTER, FALLING_STARS.nextHoliday(year))
        assertEquals(OMINGMAK, IGLOO.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, SEAL_PUPS.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, BEATING_ICE.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, NESTING_GEESE.nextHoliday(year))
        assertEquals(ALIANAT, SKIN_TENTS.nextHoliday(year))
        assertEquals(ALIANAT, RUNNING_CHAR.nextHoliday(year))
        assertEquals(ALIANAT, BERRIES.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, BARE_MOUNTAIN.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, JARLMOOT.nextHoliday(year))
        assertEquals(MOON_FEAST, ELK_HUNT.nextHoliday(year))
        assertEquals(OMINGMAK, DENNING_POLAR_BEAR.nextHoliday(year))
    }

    @Test
    fun `numDaysInSeasons returns correct number of days in call cases`() {
        assertEquals(30, FALLING_STARS.numDaysInSeasons())
        assertEquals(70, IGLOO.numDaysInSeasons())
        assertEquals(110, SEAL_PUPS.numDaysInSeasons())
        assertEquals(130, BEATING_ICE.numDaysInSeasons())
        assertEquals(160, NESTING_GEESE.numDaysInSeasons())
        assertEquals(200, SKIN_TENTS.numDaysInSeasons())
        assertEquals(230, RUNNING_CHAR.numDaysInSeasons())
        assertEquals(250, BERRIES.numDaysInSeasons())
        assertEquals(290, BARE_MOUNTAIN.numDaysInSeasons())
        assertEquals(310, JARLMOOT.numDaysInSeasons())
        assertEquals(340, ELK_HUNT.numDaysInSeasons())
        assertEquals(360, DENNING_POLAR_BEAR.numDaysInSeasons())
    }

    @Test
    fun `numHolidaysPassed returns correct number for non-leap year`() {
        val year = 1001
        assertEquals(0, FALLING_STARS.numHolidaysPassed(year))
        assertEquals(0, IGLOO.numHolidaysPassed(year))
        assertEquals(1, SEAL_PUPS.numHolidaysPassed(year))
        assertEquals(1, BEATING_ICE.numHolidaysPassed(year))
        assertEquals(1, NESTING_GEESE.numHolidaysPassed(year))
        assertEquals(2, SKIN_TENTS.numHolidaysPassed(year))
        assertEquals(2, RUNNING_CHAR.numHolidaysPassed(year))
        assertEquals(2, BERRIES.numHolidaysPassed(year))
        assertEquals(3, BARE_MOUNTAIN.numHolidaysPassed(year))
        assertEquals(3, JARLMOOT.numHolidaysPassed(year))
        assertEquals(4, ELK_HUNT.numHolidaysPassed(year))
        assertEquals(5, DENNING_POLAR_BEAR.numHolidaysPassed(year))
    }

    @Test
    fun `numHolidaysPassed returns correct number for leap year`() {
        val year = 1004
        assertEquals(0, FALLING_STARS.numHolidaysPassed(year))
        assertEquals(1, IGLOO.numHolidaysPassed(year))
        assertEquals(2, SEAL_PUPS.numHolidaysPassed(year))
        assertEquals(2, BEATING_ICE.numHolidaysPassed(year))
        assertEquals(2, NESTING_GEESE.numHolidaysPassed(year))
        assertEquals(3, SKIN_TENTS.numHolidaysPassed(year))
        assertEquals(3, RUNNING_CHAR.numHolidaysPassed(year))
        assertEquals(3, BERRIES.numHolidaysPassed(year))
        assertEquals(4, BARE_MOUNTAIN.numHolidaysPassed(year))
        assertEquals(4, JARLMOOT.numHolidaysPassed(year))
        assertEquals(5, ELK_HUNT.numHolidaysPassed(year))
        assertEquals(6, DENNING_POLAR_BEAR.numHolidaysPassed(year))
    }
}