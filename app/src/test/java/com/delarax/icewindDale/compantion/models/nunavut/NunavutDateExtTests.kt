package com.delarax.icewindDale.compantion.models.nunavut

import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.*
import com.delarax.icewindDale.companion.models.nunavut.isValid
import com.delarax.icewindDale.companion.models.nunavut.numHolidaysPassed
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