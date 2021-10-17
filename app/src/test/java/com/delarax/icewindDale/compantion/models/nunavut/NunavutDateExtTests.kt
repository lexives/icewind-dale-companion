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
        assertFalse(NunavutDate(day = 21, season = DENNING_POLAR_BEAR, year = 1234).isValid())
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
        assertTrue(NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = 1234).isValid())
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
            ELK_HUNT,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1234).priorSeason()
        )
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
        assertEquals(FALLING_STARS, NunavutDate.midwinter(1004).priorSeason())
        assertEquals(IGLOO, NunavutDate.omingmak(1234).priorSeason())
        assertEquals(NESTING_GEESE, NunavutDate.sunFestival(1234).priorSeason())
        assertEquals(BERRIES, NunavutDate.alianat(1234).priorSeason())
        assertEquals(JARLMOOT, NunavutDate.tunniqaijuk(1234).priorSeason())
        assertEquals(ELK_HUNT, NunavutDate.moonFeast(1234).priorSeason())
    }

    @Test(expected = InvalidDateException::class)
    fun `nextSeason throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).nextSeason()
    }

    @Test
    fun `nextSeason returns correct season in all cases`() {
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
        assertEquals(
            FALLING_STARS,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1234).nextSeason()
        )
        assertEquals(IGLOO, NunavutDate.midwinter(1004).nextSeason())
        assertEquals(SEAL_PUPS, NunavutDate.omingmak(1234).nextSeason())
        assertEquals(SKIN_TENTS, NunavutDate.sunFestival(1234).nextSeason())
        assertEquals(BARE_MOUNTAIN, NunavutDate.alianat(1234).nextSeason())
        assertEquals(ELK_HUNT, NunavutDate.tunniqaijuk(1234).nextSeason())
        assertEquals(DENNING_POLAR_BEAR, NunavutDate.moonFeast(1234).nextSeason())
    }

    @Test(expected = InvalidDateException::class)
    fun `lastHoliday throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).lastHoliday()
    }

    @Test
    fun `lastHoliday returns correct holiday for not leap year`() {
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
            TUNNIQAIJUK,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1001).lastHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1001).lastHoliday()
        )
        assertEquals(OMINGMAK, NunavutDate.omingmak(1001).lastHoliday())
        assertEquals(SUN_FESTIVAL, NunavutDate.sunFestival(1001).lastHoliday())
        assertEquals(ALIANAT, NunavutDate.alianat(1001).lastHoliday())
        assertEquals(TUNNIQAIJUK, NunavutDate.tunniqaijuk(1001).lastHoliday())
        assertEquals(MOON_FEAST, NunavutDate.moonFeast(1001).lastHoliday())
    }

    @Test
    fun `lastHoliday returns correct holiday for leap year`() {
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
            TUNNIQAIJUK,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1004).lastHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1004).lastHoliday()
        )
        assertEquals(MIDWINTER, NunavutDate.midwinter(1004).lastHoliday())
        assertEquals(OMINGMAK, NunavutDate.omingmak(1004).lastHoliday())
        assertEquals(SUN_FESTIVAL, NunavutDate.sunFestival(1004).lastHoliday())
        assertEquals(ALIANAT, NunavutDate.alianat(1004).lastHoliday())
        assertEquals(TUNNIQAIJUK, NunavutDate.tunniqaijuk(1004).lastHoliday())
        assertEquals(MOON_FEAST, NunavutDate.moonFeast(1004).lastHoliday())
    }

    @Test(expected = InvalidDateException::class)
    fun `nextHoliday throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).nextHoliday()
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
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
            TUNNIQAIJUK,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1001).nextHoliday()
        )
        assertEquals(
            TUNNIQAIJUK,
            NunavutDate(day = 1, season = JARLMOOT, year = 1001).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1001).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1001).nextHoliday()
        )
        assertEquals(SUN_FESTIVAL, NunavutDate.omingmak(1001).nextHoliday())
        assertEquals(ALIANAT, NunavutDate.sunFestival(1001).nextHoliday())
        assertEquals(TUNNIQAIJUK, NunavutDate.alianat(1001).nextHoliday())
        assertEquals(MOON_FEAST, NunavutDate.tunniqaijuk(1001).nextHoliday())
        assertEquals(OMINGMAK, NunavutDate.moonFeast(1001).nextHoliday())
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
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
            TUNNIQAIJUK,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1003).nextHoliday()
        )
        assertEquals(
            TUNNIQAIJUK,
            NunavutDate(day = 1, season = JARLMOOT, year = 1003).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1003).nextHoliday()
        )
        assertEquals(
            MIDWINTER,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1003).nextHoliday()
        )
        assertEquals(SUN_FESTIVAL, NunavutDate.omingmak(1003).nextHoliday())
        assertEquals(ALIANAT, NunavutDate.sunFestival(1003).nextHoliday())
        assertEquals(TUNNIQAIJUK, NunavutDate.alianat(1003).nextHoliday())
        assertEquals(MOON_FEAST, NunavutDate.tunniqaijuk(1003).nextHoliday())
        assertEquals(MIDWINTER, NunavutDate.moonFeast(1003).nextHoliday())
    }

    @Test
    fun `nextHoliday returns correct holiday for leap year`() {
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
            TUNNIQAIJUK,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1004).nextHoliday()
        )
        assertEquals(
            TUNNIQAIJUK,
            NunavutDate(day = 1, season = JARLMOOT, year = 1004).nextHoliday()
        )
        assertEquals(
            MOON_FEAST,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1004).nextHoliday()
        )
        assertEquals(
            OMINGMAK,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1004).nextHoliday()
        )
        assertEquals(OMINGMAK, NunavutDate.midwinter(1004).nextHoliday())
        assertEquals(SUN_FESTIVAL, NunavutDate.omingmak(1004).nextHoliday())
        assertEquals(ALIANAT, NunavutDate.sunFestival(1004).nextHoliday())
        assertEquals(TUNNIQAIJUK, NunavutDate.alianat(1004).nextHoliday())
        assertEquals(MOON_FEAST, NunavutDate.tunniqaijuk(1004).nextHoliday())
        assertEquals(OMINGMAK, NunavutDate.moonFeast(1004).nextHoliday())
    }

    @Test
    fun `numHolidaysPassed returns correct number on non-holiday when not leap year`() {
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
        assertEquals(4, NunavutDate(day = 1, season = ELK_HUNT, year = 1001)
            .numHolidaysPassed())
        assertEquals(5, NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1001)
            .numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on non-holiday when leap year`() {
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
        assertEquals(5, NunavutDate(day = 1, season = ELK_HUNT, year = 1004)
            .numHolidaysPassed())
        assertEquals(6, NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1004)
            .numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when not leap year`() {
        assertEquals(1, NunavutDate.omingmak(1001).numHolidaysPassed())
        assertEquals(2, NunavutDate.sunFestival(1001).numHolidaysPassed())
        assertEquals(3, NunavutDate.alianat(1001).numHolidaysPassed())
        assertEquals(4, NunavutDate.tunniqaijuk(1001).numHolidaysPassed())
        assertEquals(5, NunavutDate.moonFeast(1001).numHolidaysPassed())
    }
    
    @Test
    fun `numHolidaysPassed returns correct number on holiday when leap year`() {
        assertEquals(1, NunavutDate.midwinter(1004).numHolidaysPassed())
        assertEquals(2, NunavutDate.omingmak(1004).numHolidaysPassed())
        assertEquals(3, NunavutDate.sunFestival(1004).numHolidaysPassed())
        assertEquals(4, NunavutDate.alianat(1004).numHolidaysPassed())
        assertEquals(5, NunavutDate.tunniqaijuk(1004).numHolidaysPassed())
        assertEquals(6, NunavutDate.moonFeast(1004).numHolidaysPassed())
    }

    @Test(expected = InvalidDateException::class)
    fun `absoluteDayNumber throws error if date is not valid`() {
        NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).absoluteDayNumber()
    }

    @Test
    fun `absoluteDayNumber for first day of each month, non-leap year`() {
        assertEquals(
            1,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            31,
            NunavutDate(day = 1, season = IGLOO, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            72,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            112,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            132,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            163,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            203,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            233,
            NunavutDate(day = 1, season = BERRIES, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            254,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            294,
            NunavutDate(day = 1, season = JARLMOOT, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            315,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1001).absoluteDayNumber()
        )
        assertEquals(
            346,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1001).absoluteDayNumber()
        )
    }

    @Test
    fun `absoluteDayNumber for first day of each month, leap year`() {
        assertEquals(
            1,
            NunavutDate(day = 1, season = FALLING_STARS, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            32,
            NunavutDate(day = 1, season = IGLOO, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            73,
            NunavutDate(day = 1, season = SEAL_PUPS, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            113,
            NunavutDate(day = 1, season = BEATING_ICE, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            133,
            NunavutDate(day = 1, season = NESTING_GEESE, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            164,
            NunavutDate(day = 1, season = SKIN_TENTS, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            204,
            NunavutDate(day = 1, season = RUNNING_CHAR, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            234,
            NunavutDate(day = 1, season = BERRIES, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            255,
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            295,
            NunavutDate(day = 1, season = JARLMOOT, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            316,
            NunavutDate(day = 1, season = ELK_HUNT, year = 1004).absoluteDayNumber()
        )
        assertEquals(
            347,
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1004).absoluteDayNumber()
        )
    }

    @Test
    fun `absoluteDayNumber for last day of each month, non-leap year`() {
        assertEquals(30, NunavutDate(
            day = FALLING_STARS.numDays,
            season = FALLING_STARS,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(70, NunavutDate(
            day = IGLOO.numDays,
            season = IGLOO,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(111, NunavutDate(
            day = SEAL_PUPS.numDays,
            season = SEAL_PUPS,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(131, NunavutDate(
            day = BEATING_ICE.numDays,
            season = BEATING_ICE,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(161, NunavutDate(
            day = NESTING_GEESE.numDays,
            season = NESTING_GEESE,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(202, NunavutDate(
            day = SKIN_TENTS.numDays,
            season = SKIN_TENTS,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(232, NunavutDate(
            day = RUNNING_CHAR.numDays,
            season = RUNNING_CHAR,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(252, NunavutDate(
            day = BERRIES.numDays,
            season = BERRIES,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(293, NunavutDate(
            day = BARE_MOUNTAIN.numDays,
            season = BARE_MOUNTAIN,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(313, NunavutDate(
            day = JARLMOOT.numDays,
            season = JARLMOOT,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(344, NunavutDate(
            day = ELK_HUNT.numDays,
            season = ELK_HUNT,
            year = 1001).absoluteDayNumber()
        )
        assertEquals(365, NunavutDate(
            day = DENNING_POLAR_BEAR.numDays,
            season = DENNING_POLAR_BEAR,
            year = 1001).absoluteDayNumber()
        )
    }

    @Test
    fun `absoluteDayNumber for last day of each month, leap year`() {
        assertEquals(30, NunavutDate(
            day = FALLING_STARS.numDays,
            season = FALLING_STARS,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(71, NunavutDate(
            day = IGLOO.numDays,
            season = IGLOO,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(112, NunavutDate(
            day = SEAL_PUPS.numDays,
            season = SEAL_PUPS,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(132, NunavutDate(
            day = BEATING_ICE.numDays,
            season = BEATING_ICE,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(162, NunavutDate(
            day = NESTING_GEESE.numDays,
            season = NESTING_GEESE,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(203, NunavutDate(
            day = SKIN_TENTS.numDays,
            season = SKIN_TENTS,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(233, NunavutDate(
            day = RUNNING_CHAR.numDays,
            season = RUNNING_CHAR,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(253, NunavutDate(
            day = BERRIES.numDays,
            season = BERRIES,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(294, NunavutDate(
            day = BARE_MOUNTAIN.numDays,
            season = BARE_MOUNTAIN,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(314, NunavutDate(
            day = JARLMOOT.numDays,
            season = JARLMOOT,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(345, NunavutDate(
            day = ELK_HUNT.numDays,
            season = ELK_HUNT,
            year = 1004).absoluteDayNumber()
        )
        assertEquals(366, NunavutDate(
            day = DENNING_POLAR_BEAR.numDays,
            season = DENNING_POLAR_BEAR,
            year = 1004).absoluteDayNumber()
        )
    }

    @Test
    fun `absoluteDayNumber for each holiday, non-leap year`() {
        assertEquals(71, NunavutDate.omingmak(1001).absoluteDayNumber())
        assertEquals(162, NunavutDate.sunFestival(1001).absoluteDayNumber())
        assertEquals(253, NunavutDate.alianat(1001).absoluteDayNumber())
        assertEquals(314, NunavutDate.tunniqaijuk(1001).absoluteDayNumber())
        assertEquals(345, NunavutDate.moonFeast(1001).absoluteDayNumber())
    }

    @Test
    fun `absoluteDayNumber for each holiday, leap year`() {
        assertEquals(31, NunavutDate.midwinter(1004).absoluteDayNumber())
        assertEquals(72, NunavutDate.omingmak(1004).absoluteDayNumber())
        assertEquals(163, NunavutDate.sunFestival(1004).absoluteDayNumber())
        assertEquals(254, NunavutDate.alianat(1004).absoluteDayNumber())
        assertEquals(315, NunavutDate.tunniqaijuk(1004).absoluteDayNumber())
        assertEquals(346, NunavutDate.moonFeast(1004).absoluteDayNumber())
    }
}