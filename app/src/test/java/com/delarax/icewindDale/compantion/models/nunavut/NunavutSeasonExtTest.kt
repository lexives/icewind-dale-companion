package com.delarax.icewindDale.compantion.models.nunavut

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.nunavut.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.*
import org.junit.Assert.assertEquals
import org.junit.Test

class NunavutSeasonExtTest {

    @Test
    fun `priorSeason returns correct season in all cases`() {
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
        assertEquals(ELK_HUNT, DENNING_POLAR_BEAR.priorSeason())
    }

    @Test
    fun `nextSeason returns correct season in all cases`() {
        assertEquals(FALLING_STARS, DENNING_POLAR_BEAR.nextSeason())
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
    }

    @Test
    fun `lastHoliday returns correct holiday for not leap year`() {
        val year = 1001
        assertEquals(MOON_FEAST, DENNING_POLAR_BEAR.lastHoliday(year))
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
        assertEquals(ALIANAT, ELK_HUNT.lastHoliday(year))
    }

    @Test
    fun `lastHoliday returns correct holiday for leap year`() {
        val year = 1004
        assertEquals(MOON_FEAST, DENNING_POLAR_BEAR.lastHoliday(year))
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
        assertEquals(ALIANAT, ELK_HUNT.lastHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday for not leap year`() {
        val year = 1001
        assertEquals(OMINGMAK, DENNING_POLAR_BEAR.nextHoliday(year))
        assertEquals(OMINGMAK, FALLING_STARS.nextHoliday(year))
        assertEquals(OMINGMAK, IGLOO.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, SEAL_PUPS.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, BEATING_ICE.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, NESTING_GEESE.nextHoliday(year))
        assertEquals(ALIANAT, SKIN_TENTS.nextHoliday(year))
        assertEquals(ALIANAT, RUNNING_CHAR.nextHoliday(year))
        assertEquals(ALIANAT, BERRIES.nextHoliday(year))
        assertEquals(MOON_FEAST, BARE_MOUNTAIN.nextHoliday(year))
        assertEquals(MOON_FEAST, JARLMOOT.nextHoliday(year))
        assertEquals(MOON_FEAST, ELK_HUNT.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday for leap year`() {
        val year = 1004
        assertEquals(MIDWINTER, DENNING_POLAR_BEAR.nextHoliday(year))
        assertEquals(MIDWINTER, FALLING_STARS.nextHoliday(year))
        assertEquals(OMINGMAK, IGLOO.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, SEAL_PUPS.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, BEATING_ICE.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, NESTING_GEESE.nextHoliday(year))
        assertEquals(ALIANAT, SKIN_TENTS.nextHoliday(year))
        assertEquals(ALIANAT, RUNNING_CHAR.nextHoliday(year))
        assertEquals(ALIANAT, BERRIES.nextHoliday(year))
        assertEquals(MOON_FEAST, BARE_MOUNTAIN.nextHoliday(year))
        assertEquals(MOON_FEAST, JARLMOOT.nextHoliday(year))
        assertEquals(MOON_FEAST, ELK_HUNT.nextHoliday(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `numHolidaysPassed throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).numHolidaysPassed()
    }
}