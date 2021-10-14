package com.delarax.icewindDale.compantion.models

import com.delarax.icewindDale.companion.models.*
import com.delarax.icewindDale.companion.models.NunavutHoliday.*
import com.delarax.icewindDale.companion.models.NunavutSeason.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NunavutDateExtTests {
    
    @Test
    fun `date with no season or holiday is not valid`() {
        assertFalse(NunavutDate(day = 20, season = null, year = 1234, holiday = null).isValid())
    }

    @Test
    fun `date with both a season and a holiday is not valid`() {
        assertFalse(NunavutDate(day = 20, season = IGLOO, year = 1234, holiday = ALIANAT).isValid())
    }

    @Test
    fun `date with a year less than 1 is not valid`() {
        assertFalse(NunavutDate(day = 20, season = IGLOO, year = 0).isValid())
        assertFalse(NunavutDate(day = 20, season = IGLOO, year = -1).isValid())
    }

    @Test
    fun `date with day less than 1 is not valid`() {
        assertFalse(NunavutDate(day = 0, season = IGLOO, year = 1234).isValid())
        assertFalse(NunavutDate(day = -1, season = IGLOO, year = 1234).isValid())
    }

    @Test
    fun `date with day greater than the max for that season is not valid`() {
        assertFalse(NunavutDate(day = 21, season = DENNING_POLAR_BEAR, year = 1234).isValid())
        assertFalse(NunavutDate(day = 31, season = FALLING_STARS, year = 1234).isValid())
        assertFalse(NunavutDate(day = 41, season = IGLOO, year = 1234).isValid())
        assertFalse(NunavutDate(day = 41, season = SEAL_PUPS, year = 1234).isValid())
        assertFalse(NunavutDate(day = 21, season = BEATING_ICE, year = 1234).isValid())
        assertFalse(NunavutDate(day = 31, season = NESTING_GEESE, year = 1234).isValid())
        assertFalse(NunavutDate(day = 41, season = SKIN_TENTS, year = 1234).isValid())
        assertFalse(NunavutDate(day = 31, season = RUNNING_CHAR, year = 1234).isValid())
        assertFalse(NunavutDate(day = 21, season = BERRIES, year = 1234).isValid())
        assertFalse(NunavutDate(day = 41, season = BARE_MOUNTAIN, year = 1234).isValid())
        assertFalse(NunavutDate(day = 21, season = JARLMOOT, year = 1234).isValid())
        assertFalse(NunavutDate(day = 31, season = ELK_HUNT, year = 1234).isValid())
    }

    @Test
    fun `date with day other than 1 on a holiday is not valid`() {
        assertFalse(NunavutDate(day = 20, season = null, year = 1234, holiday = ALIANAT).isValid())
    }

    @Test
    fun `midwinter on a non-leap year is not valid`() {
        assertFalse(NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).isValid())
    }

    @Test
    fun `valid date within each season`() {
        assertTrue(NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = 1234).isValid())
        assertTrue(NunavutDate(day = 30, season = FALLING_STARS, year = 1234).isValid())
        assertTrue(NunavutDate(day = 40, season = IGLOO, year = 1234).isValid())
        assertTrue(NunavutDate(day = 40, season = SEAL_PUPS, year = 1234).isValid())
        assertTrue(NunavutDate(day = 20, season = BEATING_ICE, year = 1234).isValid())
        assertTrue(NunavutDate(day = 30, season = NESTING_GEESE, year = 1234).isValid())
        assertTrue(NunavutDate(day = 40, season = SKIN_TENTS, year = 1234).isValid())
        assertTrue(NunavutDate(day = 30, season = RUNNING_CHAR, year = 1234).isValid())
        assertTrue(NunavutDate(day = 20, season = BERRIES, year = 1234).isValid())
        assertTrue(NunavutDate(day = 40, season = BARE_MOUNTAIN, year = 1234).isValid())
        assertTrue(NunavutDate(day = 20, season = JARLMOOT, year = 1234).isValid())
        assertTrue(NunavutDate(day = 30, season = ELK_HUNT, year = 1234).isValid())
    }

    @Test
    fun `valid date on a holiday`() {
        assertTrue(NunavutDate(day = 1, season = null, year = 1234, holiday = ALIANAT).isValid())
    }

    @Test
    fun `valid midwinter`() {
        assertTrue(NunavutDate(day = 1, season = null, year = 1004, holiday = MIDWINTER).isValid())
    }
    
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

    @Test
    fun `numHolidaysPassed returns correct number on non-holiday when not leap year`() {
        assertEquals(0, NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1001)
            .numHolidaysPassed())
        assertEquals(0, NunavutDate(day = 1, season = FALLING_STARS, year = 1001)
            .numHolidaysPassed())
        assertEquals(0, NunavutDate(day = 1, season = IGLOO, year = 1001)
            .numHolidaysPassed())
        assertEquals(1, NunavutDate(day = 1, season = SEAL_PUPS, year = 1001)
            .numHolidaysPassed())
        assertEquals(1, NunavutDate(day = 1, season = BEATING_ICE, year = 1001)
            .numHolidaysPassed())
        assertEquals(1, NunavutDate(day = 1, season = NESTING_GEESE, year = 1001)
            .numHolidaysPassed())
        assertEquals(2, NunavutDate(day = 1, season = SKIN_TENTS, year = 1001)
            .numHolidaysPassed())
        assertEquals(2, NunavutDate(day = 1, season = RUNNING_CHAR, year = 1001)
            .numHolidaysPassed())
        assertEquals(2, NunavutDate(day = 1, season = BERRIES, year = 1001)
            .numHolidaysPassed())
        assertEquals(3, NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1001)
            .numHolidaysPassed())
        assertEquals(3, NunavutDate(day = 1, season = JARLMOOT, year = 1001)
            .numHolidaysPassed())
        assertEquals(3, NunavutDate(day = 1, season = ELK_HUNT, year = 1001)
            .numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on non-holiday when leap year`() {
        assertEquals(1, NunavutDate(day = 1, season = null, year = 1001, holiday = OMINGMAK)
            .numHolidaysPassed())
        assertEquals(2, NunavutDate(day = 1, season = null, year = 1001, holiday = SUN_FESTIVAL)
            .numHolidaysPassed())
        assertEquals(3, NunavutDate(day = 1, season = null, year = 1001, holiday = ALIANAT)
            .numHolidaysPassed())
        assertEquals(4, NunavutDate(day = 1, season = null, year = 1001, holiday = MOON_FEAST)
            .numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when not leap year`() {
        assertEquals(0, NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1004)
            .numHolidaysPassed())
        assertEquals(0, NunavutDate(day = 1, season = FALLING_STARS, year = 1004)
            .numHolidaysPassed())
        assertEquals(1, NunavutDate(day = 1, season = IGLOO, year = 1004)
            .numHolidaysPassed())
        assertEquals(2, NunavutDate(day = 1, season = SEAL_PUPS, year = 1004)
            .numHolidaysPassed())
        assertEquals(2, NunavutDate(day = 1, season = BEATING_ICE, year = 1004)
            .numHolidaysPassed())
        assertEquals(2, NunavutDate(day = 1, season = NESTING_GEESE, year = 1004)
            .numHolidaysPassed())
        assertEquals(3, NunavutDate(day = 1, season = SKIN_TENTS, year = 1004)
            .numHolidaysPassed())
        assertEquals(3, NunavutDate(day = 1, season = RUNNING_CHAR, year = 1004)
            .numHolidaysPassed())
        assertEquals(3, NunavutDate(day = 1, season = BERRIES, year = 1004)
            .numHolidaysPassed())
        assertEquals(4, NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1004)
            .numHolidaysPassed())
        assertEquals(4, NunavutDate(day = 1, season = JARLMOOT, year = 1004)
            .numHolidaysPassed())
        assertEquals(4, NunavutDate(day = 1, season = ELK_HUNT, year = 1004)
            .numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when leap year`() {
        assertEquals(1, NunavutDate(day = 1, season = null, year = 1004, holiday = MIDWINTER)
            .numHolidaysPassed())
        assertEquals(2, NunavutDate(day = 1, season = null, year = 1004, holiday = OMINGMAK)
            .numHolidaysPassed())
        assertEquals(3, NunavutDate(day = 1, season = null, year = 1004, holiday = SUN_FESTIVAL)
            .numHolidaysPassed())
        assertEquals(4, NunavutDate(day = 1, season = null, year = 1004, holiday = ALIANAT)
            .numHolidaysPassed())
        assertEquals(5, NunavutDate(day = 1, season = null, year = 1004, holiday = MOON_FEAST)
            .numHolidaysPassed())
    }
}