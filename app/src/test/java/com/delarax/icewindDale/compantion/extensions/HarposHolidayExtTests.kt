package com.delarax.icewindDale.compantion.extensions

import com.delarax.icewindDale.companion.models.exceptions.InvalidDateException
import com.delarax.icewindDale.companion.models.harpos.*
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HarposHolidayExtTests {

    @Test(expected = InvalidDateException::class)
    fun `priorHoliday throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.priorHoliday(1001)
    }

    @Test
    fun `priorHoliday returns correct holiday when not leap year and last year was not leap year`() {
        val year = 1003
        assertEquals(MOON_FEAST, SHIELDMEET.priorHoliday(year))
        assertEquals(SHIELDMEET, GREENGRASS.priorHoliday(year))
        assertEquals(GREENGRASS, MIDSUMMER.priorHoliday(year))
        assertEquals(MIDSUMMER, HIGHHARVESTTIDE.priorHoliday(year))
        assertEquals(HIGHHARVESTTIDE, MOON_FEAST.priorHoliday(year))
    }

    @Test
    fun `priorHoliday returns correct holiday when not leap year and last year was leap year`() {
        val year = 1001
        assertEquals(MOON_FEAST, SHIELDMEET.priorHoliday(year))
        assertEquals(SHIELDMEET, GREENGRASS.priorHoliday(year))
        assertEquals(GREENGRASS, MIDSUMMER.priorHoliday(year))
        assertEquals(MIDSUMMER, HIGHHARVESTTIDE.priorHoliday(year))
        assertEquals(HIGHHARVESTTIDE, MOON_FEAST.priorHoliday(year))
    }

    @Test
    fun `priorHoliday returns correct holiday when leap year`() {
        val year = 1004
        assertEquals(MOON_FEAST, MIDWINTER.priorHoliday(year))
        assertEquals(MIDWINTER, SHIELDMEET.priorHoliday(year))
        assertEquals(SHIELDMEET, GREENGRASS.priorHoliday(year))
        assertEquals(GREENGRASS, MIDSUMMER.priorHoliday(year))
        assertEquals(MIDSUMMER, HIGHHARVESTTIDE.priorHoliday(year))
        assertEquals(HIGHHARVESTTIDE, MOON_FEAST.priorHoliday(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `nextHoliday throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.nextHoliday(1001)
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        val year = 1001
        assertEquals(GREENGRASS, SHIELDMEET.nextHoliday(year))
        assertEquals(MIDSUMMER, GREENGRASS.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, MIDSUMMER.nextHoliday(year))
        assertEquals(MOON_FEAST, HIGHHARVESTTIDE.nextHoliday(year))
        assertEquals(SHIELDMEET, MOON_FEAST.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        val year = 1003
        assertEquals(GREENGRASS, SHIELDMEET.nextHoliday(year))
        assertEquals(MIDSUMMER, GREENGRASS.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, MIDSUMMER.nextHoliday(year))
        assertEquals(MOON_FEAST, HIGHHARVESTTIDE.nextHoliday(year))
        assertEquals(MIDWINTER, MOON_FEAST.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when leap year`() {
        val year = 1004
        assertEquals(SHIELDMEET, MIDWINTER.nextHoliday(year))
        assertEquals(GREENGRASS, SHIELDMEET.nextHoliday(year))
        assertEquals(MIDSUMMER, GREENGRASS.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, MIDSUMMER.nextHoliday(year))
        assertEquals(MOON_FEAST, HIGHHARVESTTIDE.nextHoliday(year))
        assertEquals(SHIELDMEET, MOON_FEAST.nextHoliday(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `numHolidaysPassed throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.numHolidaysPassed(1001)
    }

    @Test
    fun `numHolidaysPassed returns correct number for non-leap year`() {
        val year = 1001
        assertEquals(1, SHIELDMEET.numHolidaysPassed(year))
        assertEquals(2, GREENGRASS.numHolidaysPassed(year))
        assertEquals(3, MIDSUMMER.numHolidaysPassed(year))
        assertEquals(4, HIGHHARVESTTIDE.numHolidaysPassed(year))
        assertEquals(5, MOON_FEAST.numHolidaysPassed(year))
    }

    @Test
    fun `numHolidaysPassed returns correct number for leap year`() {
        val year = 1004
        assertEquals(1, MIDWINTER.numHolidaysPassed(year))
        assertEquals(2, SHIELDMEET.numHolidaysPassed(year))
        assertEquals(3, GREENGRASS.numHolidaysPassed(year))
        assertEquals(4, MIDSUMMER.numHolidaysPassed(year))
        assertEquals(5, HIGHHARVESTTIDE.numHolidaysPassed(year))
        assertEquals(6, MOON_FEAST.numHolidaysPassed(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `absoluteDayNumber throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.absoluteDayNumber(1001)
    }

    @Test
    fun `absoluteDayNumber returns correct number for non-leap year`() {
        val year = 1001
        assertEquals(31, SHIELDMEET.absoluteDayNumber(year))
        assertEquals(122, GREENGRASS.absoluteDayNumber(year))
        assertEquals(213, MIDSUMMER.absoluteDayNumber(year))
        assertEquals(274, HIGHHARVESTTIDE.absoluteDayNumber(year))
        assertEquals(335, MOON_FEAST.absoluteDayNumber(year))
    }

    @Test
    fun `absoluteDayNumber returns correct number for leap year`() {
        val year = 1004
        assertEquals(31, MIDWINTER.absoluteDayNumber(year))
        assertEquals(32, SHIELDMEET.absoluteDayNumber(year))
        assertEquals(123, GREENGRASS.absoluteDayNumber(year))
        assertEquals(214, MIDSUMMER.absoluteDayNumber(year))
        assertEquals(275, HIGHHARVESTTIDE.absoluteDayNumber(year))
        assertEquals(336, MOON_FEAST.absoluteDayNumber(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `toDate throws error if date is not valid`() {
        MIDWINTER.toDate(1001)
    }

    @Test
    fun `toDate returns correct date in all cases`() {
        assertEquals(
            HarposDate(day = 1, month = null, year = 1004, holiday = MIDWINTER),
            MIDWINTER.toDate(1004)
        )
        assertEquals(
            HarposDate(day = 1, month = null, year = 1004, holiday = SHIELDMEET),
            SHIELDMEET.toDate(1004)
        )
        assertEquals(
            HarposDate(day = 1, month = null, year = 1004, holiday = GREENGRASS),
            GREENGRASS.toDate(1004)
        )
        assertEquals(
            HarposDate(day = 1, month = null, year = 1004, holiday = MIDSUMMER),
            MIDSUMMER.toDate(1004)
        )
        assertEquals(
            HarposDate(day = 1, month = null, year = 1004, holiday = HIGHHARVESTTIDE),
            HIGHHARVESTTIDE.toDate(1004)
        )
        assertEquals(
            HarposDate(day = 1, month = null, year = 1004, holiday = MOON_FEAST),
            MOON_FEAST.toDate(1004)
        )
    }
}