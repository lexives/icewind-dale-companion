package com.delarax.icewindDale.compantion.models.nunavut

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.nunavut.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NunavutHolidayExtTests {

    @Test(expected = InvalidDateException::class)
    fun `priorHoliday throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.priorHoliday(1001)
    }

    @Test
    fun `priorHoliday returns correct holiday when not leap year and last year was not leap year`() {
        val year = 1003
        assertEquals(MOON_FEAST, OMINGMAK.priorHoliday(year))
        assertEquals(OMINGMAK, SUN_FESTIVAL.priorHoliday(year))
        assertEquals(SUN_FESTIVAL, ALIANAT.priorHoliday(year))
        assertEquals(ALIANAT, TUNNIQAIJUK.priorHoliday(year))
        assertEquals(TUNNIQAIJUK, MOON_FEAST.priorHoliday(year))
    }

    @Test
    fun `priorHoliday returns correct holiday when not leap year and last year was leap year`() {
        val year = 1001
        assertEquals(MOON_FEAST, OMINGMAK.priorHoliday(year))
        assertEquals(OMINGMAK, SUN_FESTIVAL.priorHoliday(year))
        assertEquals(SUN_FESTIVAL, ALIANAT.priorHoliday(year))
        assertEquals(ALIANAT, TUNNIQAIJUK.priorHoliday(year))
        assertEquals(TUNNIQAIJUK, MOON_FEAST.priorHoliday(year))
    }

    @Test
    fun `priorHoliday returns correct holiday when leap year`() {
        val year = 1004
        assertEquals(MOON_FEAST, MIDWINTER.priorHoliday(year))
        assertEquals(MIDWINTER, OMINGMAK.priorHoliday(year))
        assertEquals(OMINGMAK, SUN_FESTIVAL.priorHoliday(year))
        assertEquals(SUN_FESTIVAL, ALIANAT.priorHoliday(year))
        assertEquals(ALIANAT, TUNNIQAIJUK.priorHoliday(year))
        assertEquals(TUNNIQAIJUK, MOON_FEAST.priorHoliday(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `nextHoliday throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.nextHoliday(1001)
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        val year = 1001
        assertEquals(SUN_FESTIVAL, OMINGMAK.nextHoliday(year))
        assertEquals(ALIANAT, SUN_FESTIVAL.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, ALIANAT.nextHoliday(year))
        assertEquals(MOON_FEAST, TUNNIQAIJUK.nextHoliday(year))
        assertEquals(OMINGMAK, MOON_FEAST.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        val year = 1003
        assertEquals(SUN_FESTIVAL, OMINGMAK.nextHoliday(year))
        assertEquals(ALIANAT, SUN_FESTIVAL.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, ALIANAT.nextHoliday(year))
        assertEquals(MOON_FEAST, TUNNIQAIJUK.nextHoliday(year))
        assertEquals(MIDWINTER, MOON_FEAST.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when leap year`() {
        val year = 1004
        assertEquals(OMINGMAK, MIDWINTER.nextHoliday(year))
        assertEquals(SUN_FESTIVAL, OMINGMAK.nextHoliday(year))
        assertEquals(ALIANAT, SUN_FESTIVAL.nextHoliday(year))
        assertEquals(TUNNIQAIJUK, ALIANAT.nextHoliday(year))
        assertEquals(MOON_FEAST, TUNNIQAIJUK.nextHoliday(year))
        assertEquals(OMINGMAK, MOON_FEAST.nextHoliday(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `numHolidaysPassed throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.numHolidaysPassed(1001)
    }

    @Test
    fun `numHolidaysPassed returns correct number for non-leap year`() {
        val year = 1001
        assertEquals(1, OMINGMAK.numHolidaysPassed(year))
        assertEquals(2, SUN_FESTIVAL.numHolidaysPassed(year))
        assertEquals(3, ALIANAT.numHolidaysPassed(year))
        assertEquals(4, TUNNIQAIJUK.numHolidaysPassed(year))
        assertEquals(5, MOON_FEAST.numHolidaysPassed(year))
    }

    @Test
    fun `numHolidaysPassed returns correct number for leap year`() {
        val year = 1004
        assertEquals(1, MIDWINTER.numHolidaysPassed(year))
        assertEquals(2, OMINGMAK.numHolidaysPassed(year))
        assertEquals(3, SUN_FESTIVAL.numHolidaysPassed(year))
        assertEquals(4, ALIANAT.numHolidaysPassed(year))
        assertEquals(5, TUNNIQAIJUK.numHolidaysPassed(year))
        assertEquals(6, MOON_FEAST.numHolidaysPassed(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `absoluteDayNumber throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.absoluteDayNumber(1001)
    }

    @Test
    fun `absoluteDayNumber returns correct number for non-leap year`() {
        val year = 1001
        assertEquals(71, OMINGMAK.absoluteDayNumber(year))
        assertEquals(162, SUN_FESTIVAL.absoluteDayNumber(year))
        assertEquals(253, ALIANAT.absoluteDayNumber(year))
        assertEquals(314, TUNNIQAIJUK.absoluteDayNumber(year))
        assertEquals(345, MOON_FEAST.absoluteDayNumber(year))
    }

    @Test
    fun `absoluteDayNumber for each holiday for leap year`() {
        val year = 1004
        assertEquals(31, MIDWINTER.absoluteDayNumber(year))
        assertEquals(72, OMINGMAK.absoluteDayNumber(year))
        assertEquals(163, SUN_FESTIVAL.absoluteDayNumber(year))
        assertEquals(254, ALIANAT.absoluteDayNumber(year))
        assertEquals(315, TUNNIQAIJUK.absoluteDayNumber(year))
        assertEquals(346, MOON_FEAST.absoluteDayNumber(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `toDate throws error if date is not valid`() {
        MIDWINTER.toDate(1001)
    }

    @Test
    fun `toDate returns correct date in all cases`() {
        assertEquals(
            NunavutDate(day = 1, season = null, year = 1004, holiday = MIDWINTER),
            MIDWINTER.toDate(1004)
        )
        assertEquals(
            NunavutDate(day = 1, season = null, year = 1004, holiday = OMINGMAK),
            OMINGMAK.toDate(1004)
        )
        assertEquals(
            NunavutDate(day = 1, season = null, year = 1004, holiday = SUN_FESTIVAL),
            SUN_FESTIVAL.toDate(1004)
        )
        assertEquals(
            NunavutDate(day = 1, season = null, year = 1004, holiday = ALIANAT),
            ALIANAT.toDate(1004)
        )
        assertEquals(
            NunavutDate(day = 1, season = null, year = 1004, holiday = TUNNIQAIJUK),
            TUNNIQAIJUK.toDate(1004)
        )
        assertEquals(
            NunavutDate(day = 1, season = null, year = 1004, holiday = MOON_FEAST),
            MOON_FEAST.toDate(1004)
        )
    }
}