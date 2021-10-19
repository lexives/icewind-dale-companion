package com.delarax.icewindDale.compantion.models.nunavut

import com.delarax.icewindDale.companion.exceptions.InvalidDateException
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.*
import com.delarax.icewindDale.companion.models.nunavut.toDate
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NunavutDateTests {

    @Test
    fun `isLeapYear is true every fourth year`() {
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 1).isLeapYear)
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 2).isLeapYear)
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 3).isLeapYear)
        assertTrue(NunavutDate(day = 1, season = IGLOO, year = 4).isLeapYear)
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 5).isLeapYear)
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 6).isLeapYear)
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 7).isLeapYear)
        assertTrue(NunavutDate(day = 1, season = IGLOO, year = 8).isLeapYear)
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 9).isLeapYear)
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 10).isLeapYear)
        assertFalse(NunavutDate(day = 1, season = IGLOO, year = 11).isLeapYear)
        assertTrue(NunavutDate(day = 1, season = IGLOO, year = 12).isLeapYear)
    }

    @Test
    fun `isValid for date with no season or holiday is not valid`() {
        assertFalse(NunavutDate(day = 20, season = null, year = 1234, holiday = null).isValid)
    }

    @Test
    fun `isValid for date with both a season and a holiday is not valid`() {
        assertFalse(NunavutDate(day = 20, season = IGLOO, year = 1234, holiday = ALIANAT).isValid)
    }

    @Test
    fun `isValid for date with a year less than 1 is not valid`() {
        assertFalse(NunavutDate(day = 20, season = IGLOO, year = 0).isValid)
        assertFalse(NunavutDate(day = 20, season = IGLOO, year = -1).isValid)
    }

    @Test
    fun `isValid for date with day less than 1 is not valid`() {
        assertFalse(NunavutDate(day = 0, season = IGLOO, year = 1234).isValid)
        assertFalse(NunavutDate(day = -1, season = IGLOO, year = 1234).isValid)
    }

    @Test
    fun `isValid for date with day greater than the max for that season is not valid`() {
        assertFalse(NunavutDate(day = 31, season = FALLING_STARS, year = 1234).isValid)
        assertFalse(NunavutDate(day = 41, season = IGLOO, year = 1234).isValid)
        assertFalse(NunavutDate(day = 41, season = SEAL_PUPS, year = 1234).isValid)
        assertFalse(NunavutDate(day = 21, season = BEATING_ICE, year = 1234).isValid)
        assertFalse(NunavutDate(day = 31, season = NESTING_GEESE, year = 1234).isValid)
        assertFalse(NunavutDate(day = 41, season = SKIN_TENTS, year = 1234).isValid)
        assertFalse(NunavutDate(day = 31, season = RUNNING_CHAR, year = 1234).isValid)
        assertFalse(NunavutDate(day = 21, season = BERRIES, year = 1234).isValid)
        assertFalse(NunavutDate(day = 41, season = BARE_MOUNTAIN, year = 1234).isValid)
        assertFalse(NunavutDate(day = 21, season = JARLMOOT, year = 1234).isValid)
        assertFalse(NunavutDate(day = 31, season = ELK_HUNT, year = 1234).isValid)
        assertFalse(NunavutDate(day = 21, season = DENNING_POLAR_BEAR, year = 1234).isValid)
    }

    @Test
    fun `isValid for date with day other than 1 on a holiday is not valid`() {
        assertFalse(NunavutDate(day = 20, season = null, year = 1234, holiday = ALIANAT).isValid)
    }

    @Test
    fun `isValid for midwinter on a non-leap year is not valid`() {
        assertFalse(NunavutDate(day = 1, season = null, year = 1001, holiday = MIDWINTER).isValid)
    }

    @Test
    fun `isValid for valid date within each season`() {
        assertTrue(NunavutDate(day = 30, season = FALLING_STARS, year = 1234).isValid)
        assertTrue(NunavutDate(day = 40, season = IGLOO, year = 1234).isValid)
        assertTrue(NunavutDate(day = 40, season = SEAL_PUPS, year = 1234).isValid)
        assertTrue(NunavutDate(day = 20, season = BEATING_ICE, year = 1234).isValid)
        assertTrue(NunavutDate(day = 30, season = NESTING_GEESE, year = 1234).isValid)
        assertTrue(NunavutDate(day = 40, season = SKIN_TENTS, year = 1234).isValid)
        assertTrue(NunavutDate(day = 30, season = RUNNING_CHAR, year = 1234).isValid)
        assertTrue(NunavutDate(day = 20, season = BERRIES, year = 1234).isValid)
        assertTrue(NunavutDate(day = 40, season = BARE_MOUNTAIN, year = 1234).isValid)
        assertTrue(NunavutDate(day = 20, season = JARLMOOT, year = 1234).isValid)
        assertTrue(NunavutDate(day = 30, season = ELK_HUNT, year = 1234).isValid)
        assertTrue(NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = 1234).isValid)
    }

    @Test
    fun `isValid for valid date on a holiday`() {
        assertTrue(NunavutDate(day = 1, season = null, year = 1234, holiday = ALIANAT).isValid)
    }

    @Test
    fun `isValid for valid midwinter`() {
        assertTrue(NunavutDate(day = 1, season = null, year = 1004, holiday = MIDWINTER).isValid)
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
        assertEquals(FALLING_STARS, MIDWINTER.toDate(1004).priorSeason())
        assertEquals(IGLOO, OMINGMAK.toDate(1234).priorSeason())
        assertEquals(NESTING_GEESE, SUN_FESTIVAL.toDate(1234).priorSeason())
        assertEquals(BERRIES, ALIANAT.toDate(1234).priorSeason())
        assertEquals(JARLMOOT, TUNNIQAIJUK.toDate(1234).priorSeason())
        assertEquals(ELK_HUNT, MOON_FEAST.toDate(1234).priorSeason())
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
        assertEquals(IGLOO, MIDWINTER.toDate(1004).nextSeason())
        assertEquals(SEAL_PUPS, OMINGMAK.toDate(1234).nextSeason())
        assertEquals(SKIN_TENTS, SUN_FESTIVAL.toDate(1234).nextSeason())
        assertEquals(BARE_MOUNTAIN, ALIANAT.toDate(1234).nextSeason())
        assertEquals(ELK_HUNT, TUNNIQAIJUK.toDate(1234).nextSeason())
        assertEquals(DENNING_POLAR_BEAR, MOON_FEAST.toDate(1234).nextSeason())
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
        assertEquals(OMINGMAK, OMINGMAK.toDate(1001).lastHoliday())
        assertEquals(SUN_FESTIVAL, SUN_FESTIVAL.toDate(1001).lastHoliday())
        assertEquals(ALIANAT, ALIANAT.toDate(1001).lastHoliday())
        assertEquals(TUNNIQAIJUK, TUNNIQAIJUK.toDate(1001).lastHoliday())
        assertEquals(MOON_FEAST, MOON_FEAST.toDate(1001).lastHoliday())
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
        assertEquals(MIDWINTER, MIDWINTER.toDate(1004).lastHoliday())
        assertEquals(OMINGMAK, OMINGMAK.toDate(1004).lastHoliday())
        assertEquals(SUN_FESTIVAL, SUN_FESTIVAL.toDate(1004).lastHoliday())
        assertEquals(ALIANAT, ALIANAT.toDate(1004).lastHoliday())
        assertEquals(TUNNIQAIJUK, TUNNIQAIJUK.toDate(1004).lastHoliday())
        assertEquals(MOON_FEAST, MOON_FEAST.toDate(1004).lastHoliday())
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
        assertEquals(SUN_FESTIVAL, OMINGMAK.toDate(1001).nextHoliday())
        assertEquals(ALIANAT, SUN_FESTIVAL.toDate(1001).nextHoliday())
        assertEquals(TUNNIQAIJUK, ALIANAT.toDate(1001).nextHoliday())
        assertEquals(MOON_FEAST, TUNNIQAIJUK.toDate(1001).nextHoliday())
        assertEquals(OMINGMAK, MOON_FEAST.toDate(1001).nextHoliday())
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
        assertEquals(SUN_FESTIVAL, OMINGMAK.toDate(1003).nextHoliday())
        assertEquals(ALIANAT, SUN_FESTIVAL.toDate(1003).nextHoliday())
        assertEquals(TUNNIQAIJUK, ALIANAT.toDate(1003).nextHoliday())
        assertEquals(MOON_FEAST, TUNNIQAIJUK.toDate(1003).nextHoliday())
        assertEquals(MIDWINTER, MOON_FEAST.toDate(1003).nextHoliday())
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
        assertEquals(OMINGMAK, MIDWINTER.toDate(1004).nextHoliday())
        assertEquals(SUN_FESTIVAL, OMINGMAK.toDate(1004).nextHoliday())
        assertEquals(ALIANAT, SUN_FESTIVAL.toDate(1004).nextHoliday())
        assertEquals(TUNNIQAIJUK, ALIANAT.toDate(1004).nextHoliday())
        assertEquals(MOON_FEAST, TUNNIQAIJUK.toDate(1004).nextHoliday())
        assertEquals(OMINGMAK, MOON_FEAST.toDate(1004).nextHoliday())
    }

    @Test
    fun `numHolidaysPassed returns correct number on non-holiday when not leap year`() {
        assertEquals(
            0, NunavutDate(day = 1, season = FALLING_STARS, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            0, NunavutDate(day = 1, season = IGLOO, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            1, NunavutDate(day = 1, season = SEAL_PUPS, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            1, NunavutDate(day = 1, season = BEATING_ICE, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            1, NunavutDate(day = 1, season = NESTING_GEESE, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            2, NunavutDate(day = 1, season = SKIN_TENTS, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            2, NunavutDate(day = 1, season = RUNNING_CHAR, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            2, NunavutDate(day = 1, season = BERRIES, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            3, NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            3, NunavutDate(day = 1, season = JARLMOOT, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            4, NunavutDate(day = 1, season = ELK_HUNT, year = 1001)
                .numHolidaysPassed()
        )
        assertEquals(
            5, NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1001)
                .numHolidaysPassed()
        )
    }

    @Test
    fun `numHolidaysPassed returns correct number on non-holiday when leap year`() {
        assertEquals(
            0, NunavutDate(day = 1, season = FALLING_STARS, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            1, NunavutDate(day = 1, season = IGLOO, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            2, NunavutDate(day = 1, season = SEAL_PUPS, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            2, NunavutDate(day = 1, season = BEATING_ICE, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            2, NunavutDate(day = 1, season = NESTING_GEESE, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            3, NunavutDate(day = 1, season = SKIN_TENTS, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            3, NunavutDate(day = 1, season = RUNNING_CHAR, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            3, NunavutDate(day = 1, season = BERRIES, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            4, NunavutDate(day = 1, season = BARE_MOUNTAIN, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            4, NunavutDate(day = 1, season = JARLMOOT, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            5, NunavutDate(day = 1, season = ELK_HUNT, year = 1004)
                .numHolidaysPassed()
        )
        assertEquals(
            6, NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = 1004)
                .numHolidaysPassed()
        )
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when not leap year`() {
        assertEquals(1, OMINGMAK.toDate(1001).numHolidaysPassed())
        assertEquals(2, SUN_FESTIVAL.toDate(1001).numHolidaysPassed())
        assertEquals(3, ALIANAT.toDate(1001).numHolidaysPassed())
        assertEquals(4, TUNNIQAIJUK.toDate(1001).numHolidaysPassed())
        assertEquals(5, MOON_FEAST.toDate(1001).numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when leap year`() {
        assertEquals(1, MIDWINTER.toDate(1004).numHolidaysPassed())
        assertEquals(2, OMINGMAK.toDate(1004).numHolidaysPassed())
        assertEquals(3, SUN_FESTIVAL.toDate(1004).numHolidaysPassed())
        assertEquals(4, ALIANAT.toDate(1004).numHolidaysPassed())
        assertEquals(5, TUNNIQAIJUK.toDate(1004).numHolidaysPassed())
        assertEquals(6, MOON_FEAST.toDate(1004).numHolidaysPassed())
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
        assertEquals(
            30, NunavutDate(
                day = FALLING_STARS.numDays,
                season = FALLING_STARS,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            70, NunavutDate(
                day = IGLOO.numDays,
                season = IGLOO,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            111, NunavutDate(
                day = SEAL_PUPS.numDays,
                season = SEAL_PUPS,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            131, NunavutDate(
                day = BEATING_ICE.numDays,
                season = BEATING_ICE,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            161, NunavutDate(
                day = NESTING_GEESE.numDays,
                season = NESTING_GEESE,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            202, NunavutDate(
                day = SKIN_TENTS.numDays,
                season = SKIN_TENTS,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            232, NunavutDate(
                day = RUNNING_CHAR.numDays,
                season = RUNNING_CHAR,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            252, NunavutDate(
                day = BERRIES.numDays,
                season = BERRIES,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            293, NunavutDate(
                day = BARE_MOUNTAIN.numDays,
                season = BARE_MOUNTAIN,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            313, NunavutDate(
                day = JARLMOOT.numDays,
                season = JARLMOOT,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            344, NunavutDate(
                day = ELK_HUNT.numDays,
                season = ELK_HUNT,
                year = 1001
            ).absoluteDayNumber()
        )
        assertEquals(
            365, NunavutDate(
                day = DENNING_POLAR_BEAR.numDays,
                season = DENNING_POLAR_BEAR,
                year = 1001
            ).absoluteDayNumber()
        )
    }

    @Test
    fun `absoluteDayNumber for last day of each month, leap year`() {
        assertEquals(
            30, NunavutDate(
                day = FALLING_STARS.numDays,
                season = FALLING_STARS,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            71, NunavutDate(
                day = IGLOO.numDays,
                season = IGLOO,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            112, NunavutDate(
                day = SEAL_PUPS.numDays,
                season = SEAL_PUPS,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            132, NunavutDate(
                day = BEATING_ICE.numDays,
                season = BEATING_ICE,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            162, NunavutDate(
                day = NESTING_GEESE.numDays,
                season = NESTING_GEESE,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            203, NunavutDate(
                day = SKIN_TENTS.numDays,
                season = SKIN_TENTS,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            233, NunavutDate(
                day = RUNNING_CHAR.numDays,
                season = RUNNING_CHAR,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            253, NunavutDate(
                day = BERRIES.numDays,
                season = BERRIES,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            294, NunavutDate(
                day = BARE_MOUNTAIN.numDays,
                season = BARE_MOUNTAIN,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            314, NunavutDate(
                day = JARLMOOT.numDays,
                season = JARLMOOT,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            345, NunavutDate(
                day = ELK_HUNT.numDays,
                season = ELK_HUNT,
                year = 1004
            ).absoluteDayNumber()
        )
        assertEquals(
            366, NunavutDate(
                day = DENNING_POLAR_BEAR.numDays,
                season = DENNING_POLAR_BEAR,
                year = 1004
            ).absoluteDayNumber()
        )
    }

    @Test
    fun `absoluteDayNumber for each holiday, non-leap year`() {
        assertEquals(71, OMINGMAK.toDate(1001).absoluteDayNumber())
        assertEquals(162, SUN_FESTIVAL.toDate(1001).absoluteDayNumber())
        assertEquals(253, ALIANAT.toDate(1001).absoluteDayNumber())
        assertEquals(314, TUNNIQAIJUK.toDate(1001).absoluteDayNumber())
        assertEquals(345, MOON_FEAST.toDate(1001).absoluteDayNumber())
    }

    @Test
    fun `absoluteDayNumber for each holiday, leap year`() {
        assertEquals(31, MIDWINTER.toDate(1004).absoluteDayNumber())
        assertEquals(72, OMINGMAK.toDate(1004).absoluteDayNumber())
        assertEquals(163, SUN_FESTIVAL.toDate(1004).absoluteDayNumber())
        assertEquals(254, ALIANAT.toDate(1004).absoluteDayNumber())
        assertEquals(315, TUNNIQAIJUK.toDate(1004).absoluteDayNumber())
        assertEquals(346, MOON_FEAST.toDate(1004).absoluteDayNumber())
    }

    @Test
    fun `daysInLeapYear returns correct number of days`() {
        assertEquals(366, NunavutDate.daysInLeapYear)
    }

    @Test
    fun `daysInNonLeapYear returns correct number of days`() {
        assertEquals(365, NunavutDate.daysInNonLeapYear)
    }

    @Test(expected = InvalidDateException::class)
    fun `fromAbsoluteDayNumber throws error if day number is less than 1`() {
        NunavutDate.fromAbsoluteDayNumber(0, 1004)
    }

    @Test(expected = InvalidDateException::class)
    fun `fromAbsoluteDayNumber throws error if day number is greater than max for leap year`() {
        NunavutDate.fromAbsoluteDayNumber(367, 1004)
    }

    @Test(expected = InvalidDateException::class)
    fun `fromAbsoluteDayNumber throws error if day number is greater than max for non-leap year`() {
        NunavutDate.fromAbsoluteDayNumber(366, 1001)
    }

    @Test
    fun `fromAbsoluteDayNumber converts all holidays correctly for non-leap year`() {
        val year = 1001
        assertEquals(OMINGMAK.toDate(year), NunavutDate.fromAbsoluteDayNumber(71, year))
        assertEquals(SUN_FESTIVAL.toDate(year), NunavutDate.fromAbsoluteDayNumber(162, year))
        assertEquals(ALIANAT.toDate(year), NunavutDate.fromAbsoluteDayNumber(253, year))
        assertEquals(TUNNIQAIJUK.toDate(year), NunavutDate.fromAbsoluteDayNumber(314, year))
        assertEquals(MOON_FEAST.toDate(year), NunavutDate.fromAbsoluteDayNumber(345, year))
    }

    @Test
    fun `fromAbsoluteDayNumber converts all holidays correctly for leap year`() {
        val year = 1004
        assertEquals(MIDWINTER.toDate(year), NunavutDate.fromAbsoluteDayNumber(31, year))
        assertEquals(OMINGMAK.toDate(year), NunavutDate.fromAbsoluteDayNumber(72, year))
        assertEquals(SUN_FESTIVAL.toDate(year), NunavutDate.fromAbsoluteDayNumber(163, year))
        assertEquals(ALIANAT.toDate(year), NunavutDate.fromAbsoluteDayNumber(254, year))
        assertEquals(TUNNIQAIJUK.toDate(year), NunavutDate.fromAbsoluteDayNumber(315, year))
        assertEquals(MOON_FEAST.toDate(year), NunavutDate.fromAbsoluteDayNumber(346, year))
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of FS`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = FALLING_STARS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(1, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = FALLING_STARS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(11, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 30, season = FALLING_STARS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(30, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = FALLING_STARS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(1, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = FALLING_STARS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(11, leapYear)
        )
        assertEquals(
            NunavutDate(day = 30, season = FALLING_STARS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(30, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of I`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = IGLOO, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(31, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = IGLOO, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(41, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 40, season = IGLOO, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(70, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = IGLOO, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(32, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = IGLOO, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(42, leapYear)
        )
        assertEquals(
            NunavutDate(day = 40, season = IGLOO, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(71, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of SP`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = SEAL_PUPS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(72, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = SEAL_PUPS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(82, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 40, season = SEAL_PUPS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(111, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = SEAL_PUPS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(73, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = SEAL_PUPS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(83, leapYear)
        )
        assertEquals(
            NunavutDate(day = 40, season = SEAL_PUPS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(112, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of BI`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = BEATING_ICE, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(112, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = BEATING_ICE, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(122, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 20, season = BEATING_ICE, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(131, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = BEATING_ICE, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(113, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = BEATING_ICE, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(123, leapYear)
        )
        assertEquals(
            NunavutDate(day = 20, season = BEATING_ICE, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(132, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of NG`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = NESTING_GEESE, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(132, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = NESTING_GEESE, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(142, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 30, season = NESTING_GEESE, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(161, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = NESTING_GEESE, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(133, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = NESTING_GEESE, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(143, leapYear)
        )
        assertEquals(
            NunavutDate(day = 30, season = NESTING_GEESE, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(162, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of ST`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = SKIN_TENTS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(163, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = SKIN_TENTS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(173, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 40, season = SKIN_TENTS, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(202, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = SKIN_TENTS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(164, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = SKIN_TENTS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(174, leapYear)
        )
        assertEquals(
            NunavutDate(day = 40, season = SKIN_TENTS, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(203, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of RC`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = RUNNING_CHAR, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(203, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = RUNNING_CHAR, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(213, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 30, season = RUNNING_CHAR, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(232, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = RUNNING_CHAR, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(204, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = RUNNING_CHAR, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(214, leapYear)
        )
        assertEquals(
            NunavutDate(day = 30, season = RUNNING_CHAR, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(233, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of B`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = BERRIES, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(233, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = BERRIES, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(243, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 20, season = BERRIES, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(252, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = BERRIES, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(234, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = BERRIES, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(244, leapYear)
        )
        assertEquals(
            NunavutDate(day = 20, season = BERRIES, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(253, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of BM`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(254, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = BARE_MOUNTAIN, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(264, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 40, season = BARE_MOUNTAIN, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(293, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = BARE_MOUNTAIN, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(255, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = BARE_MOUNTAIN, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(265, leapYear)
        )
        assertEquals(
            NunavutDate(day = 40, season = BARE_MOUNTAIN, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(294, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of J`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = JARLMOOT, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(294, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = JARLMOOT, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(304, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 20, season = JARLMOOT, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(313, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = JARLMOOT, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(295, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = JARLMOOT, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(305, leapYear)
        )
        assertEquals(
            NunavutDate(day = 20, season = JARLMOOT, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(314, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of EH`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = ELK_HUNT, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(315, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = ELK_HUNT, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(325, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 30, season = ELK_HUNT, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(344, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = ELK_HUNT, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(316, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = ELK_HUNT, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(326, leapYear)
        )
        assertEquals(
            NunavutDate(day = 30, season = ELK_HUNT, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(345, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of DP`() {
        val nonLeapYear = 1001
        assertEquals(
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(346, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = DENNING_POLAR_BEAR, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(356, nonLeapYear)
        )
        assertEquals(
            NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = nonLeapYear),
            NunavutDate.fromAbsoluteDayNumber(365, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(347, leapYear)
        )
        assertEquals(
            NunavutDate(day = 11, season = DENNING_POLAR_BEAR, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(357, leapYear)
        )
        assertEquals(
            NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = leapYear),
            NunavutDate.fromAbsoluteDayNumber(366, leapYear)
        )
    }
}
