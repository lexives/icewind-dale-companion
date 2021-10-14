package com.delarax.icewindDale.compantion.models.nunavut

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.nunavut.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.*
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

    @Test(expected = InvalidDateException::class)
    fun `priorSeason throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).priorSeason()
    }

    @Test
    fun `priorSeason returns correct season in all cases`() {
        assertEquals(
            JARLMOOT,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1234).priorSeason()
        )
        assertEquals(
            BARE_MOUNTAIN,
            NunavutDate(day = 1, season = JARLMOOT, year = 1234).priorSeason()
        )
        assertEquals(
            BERRIES,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1234).priorSeason()
        )
        assertEquals(
            RUNNING_CHAR,
            NunavutDate(day = 1, season = BERRIES, year = 1234).priorSeason()
        )
        assertEquals(
            SKIN_TENTS,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1234).priorSeason()
        )
        assertEquals(
            NESTING_GEESE,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1234).priorSeason()
        )
        assertEquals(
            BEATING_ICE,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1234).priorSeason()
        )
        assertEquals(
            SEAL_PUPS,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1234).priorSeason()
        )
        assertEquals(
            IGLOO,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1234).priorSeason()
        )
        assertEquals(
            FALLING_STARS,
            NunavutDate(day = 1, season = IGLOO, year = 1234).priorSeason()
        )
        assertEquals(
            DENNING_POLAR_BEAR,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1234).priorSeason()
        )
        assertEquals(
            ELK_HUNT,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1234).priorSeason()
        )
    }

    @Test(expected = InvalidDateException::class)
    fun `nextSeason throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).nextSeason()
    }

    @Test
    fun `nextSeason returns correct season in all cases`() {
        assertEquals(
            FALLING_STARS,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1234).nextSeason()
        )
        assertEquals(
            IGLOO,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1234).nextSeason()
        )
        assertEquals(
            SEAL_PUPS,
            NunavutDate(day = 1, season = IGLOO, year = 1234).nextSeason()
        )
        assertEquals(
            BEATING_ICE,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1234).nextSeason()
        )
        assertEquals(
            NESTING_GEESE,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1234).nextSeason()
        )
        assertEquals(
            SKIN_TENTS,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1234).nextSeason()
        )
        assertEquals(
            RUNNING_CHAR,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1234).nextSeason()
        )
        assertEquals(
            BERRIES,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1234).nextSeason()
        )
        assertEquals(
            BARE_MOUNTAIN,
            NunavutDate(day = 1, season = BERRIES, year = 1234).nextSeason()
        )
        assertEquals(
            JARLMOOT,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1234).nextSeason()
        )
        assertEquals(
            ELK_HUNT,
        NunavutDate(day = 1, season = JARLMOOT, year = 1234).nextSeason()
        )
        assertEquals(
            DENNING_POLAR_BEAR,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1234).nextSeason()
        )
    }

    @Test(expected = InvalidDateException::class)
    fun `lastHoliday throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).lastHoliday()
    }

    @Test
    fun `lastHoliday returns correct holiday for not leap year`() {
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1001).lastHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1001).lastHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = IGLOO, year = 1001).lastHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1001).lastHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1001).lastHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1001).lastHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1001).lastHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1001).lastHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = BERRIES, year = 1001).lastHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1001).lastHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = JARLMOOT, year = 1001).lastHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1001).lastHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = null, year = 1001, holiday = OMINGMAK).lastHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = null, year = 1001, holiday = SUN_FESTIVAL).lastHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = null, year = 1001, holiday = ALIANAT).lastHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = null, year = 1001, holiday = MOON_FEAST).lastHoliday()
        )
    }

    @Test
    fun `lastHoliday returns correct holiday for leap year`() {
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1004).lastHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1004).lastHoliday()
        )
        assertEquals(
            MIDWINTER,
            NunavutDate(day = 1, season = IGLOO, year = 1004).lastHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1004).lastHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1004).lastHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1004).lastHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1004).lastHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1004).lastHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = BERRIES, year = 1004).lastHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1004).lastHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = JARLMOOT, year = 1004).lastHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1004).lastHoliday()
        )
        assertEquals(
            MIDWINTER,
            NunavutDate(day = 1, season = null, year = 1004, holiday = MIDWINTER).lastHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = null, year = 1004, holiday = OMINGMAK).lastHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = null, year = 1004, holiday = SUN_FESTIVAL).lastHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = null, year = 1004, holiday = ALIANAT).lastHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = null, year = 1004, holiday = MOON_FEAST).lastHoliday()
        )
    }

    @Test(expected = InvalidDateException::class)
    fun `nextHoliday throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).nextHoliday()
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1001).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1001).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = IGLOO, year = 1001).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1001).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1001).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1001).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1001).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1001).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = BERRIES, year = 1001).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1001).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = JARLMOOT, year = 1001).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1001).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = null, year = 1001, holiday = OMINGMAK).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = null, year = 1001, holiday = SUN_FESTIVAL).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = null, year = 1001, holiday = ALIANAT).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = null, year = 1001, holiday = MOON_FEAST).nextHoliday()
        )
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1003).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1003).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = IGLOO, year = 1003).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1003).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1003).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1003).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1003).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1003).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = BERRIES, year = 1003).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1003).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = JARLMOOT, year = 1003).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1003).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = null, year = 1003, holiday = OMINGMAK).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = null, year = 1003, holiday = SUN_FESTIVAL).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = null, year = 1003, holiday = ALIANAT).nextHoliday()
        )
        assertEquals(
            MIDWINTER,
            NunavutDate(day = 1, season = null, year = 1003, holiday = MOON_FEAST).nextHoliday()
        )
    }

    @Test
    fun `nextHoliday returns correct holiday for leap year`() {
        assertEquals(
            MIDWINTER,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1004).nextHoliday()
        )
        assertEquals(
            MIDWINTER,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1004).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = IGLOO, year = 1004).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1004).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1004).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1004).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1004).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1004).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = BERRIES, year = 1004).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1004).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = JARLMOOT, year = 1004).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1004).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = null, year = 1004, holiday = MIDWINTER).nextHoliday()
        )
        assertEquals(
            SUN_FESTIVAL,
            NunavutDate(day = 1, season = null, year = 1004, holiday = OMINGMAK).nextHoliday()
        )
        assertEquals(
            ALIANAT,
            NunavutDate(day = 1, season = null, year = 1004, holiday = SUN_FESTIVAL).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = null, year = 1004, holiday = ALIANAT).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = null, year = 1004, holiday = MOON_FEAST).nextHoliday()
        )
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