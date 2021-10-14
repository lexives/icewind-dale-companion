package com.delarax.icewindDale.compantion.models

import com.delarax.icewindDale.companion.models.*
import com.delarax.icewindDale.companion.models.HarposHoliday.*
import com.delarax.icewindDale.companion.models.HarposMonth.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HarposDateExtTests {

    @Test
    fun `date with no month or holiday is not valid`() {
        assertFalse(HarposDate(day = 20, month = null, year = 1234, holiday = null).isValid())
    }

    @Test
    fun `date with both a month and a holiday is not valid`() {
        assertFalse(
            HarposDate(day = 20, month = MIRTUL, year = 1234, holiday = MIDSUMMER).isValid()
        )
    }

    @Test
    fun `date with a year less than 1 is not valid`() {
        assertFalse(HarposDate(day = 20, month = MIRTUL, year = 0).isValid())
        assertFalse(HarposDate(day = 20, month = MIRTUL, year = -1).isValid())
    }

    @Test
    fun `date with day less than 1 is not valid`() {
        assertFalse(HarposDate(day = 0, month = MIRTUL, year = 1234).isValid())
        assertFalse(HarposDate(day = -1, month = MIRTUL, year = 1234).isValid())
    }

    @Test
    fun `date with day greater than 30 is not valid`() {
        assertFalse(HarposDate(day = 31, month = MIRTUL, year = 1234).isValid())
    }

    @Test
    fun `date with day other than 1 on a holiday is not valid`() {
        assertFalse(HarposDate(day = 20, month = null, year = 1234, holiday = MIDSUMMER).isValid())
    }

    @Test
    fun `midwinter on a non-leap year is not valid`() {
        assertFalse(HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).isValid())
    }

    @Test
    fun `valid date within a month`() {
        assertTrue(HarposDate(day = 20, month = MIRTUL, year = 1234).isValid())
        assertTrue(HarposDate(day = 30, month = MIRTUL, year = 1234).isValid())
    }

    @Test
    fun `valid date on a holiday`() {
        assertTrue(HarposDate(day = 1, month = null, year = 1234, holiday = MIDSUMMER).isValid())
    }

    @Test
    fun `valid midwinter`() {
        assertTrue(HarposDate(day = 1, month = null, year = 1004, holiday = MIDWINTER).isValid())
    }

    @Test
    fun `priorMonth returns correct month in all cases`() {
        assertEquals(UKTAR, NIGHTAL.priorMonth())
        assertEquals(MARPENOTH, UKTAR.priorMonth())
        assertEquals(ELEINT, MARPENOTH.priorMonth())
        assertEquals(ELEASIAS, ELEINT.priorMonth())
        assertEquals(FLAMERULE, ELEASIAS.priorMonth())
        assertEquals(KYTHORN, FLAMERULE.priorMonth())
        assertEquals(MIRTUL, KYTHORN.priorMonth())
        assertEquals(TARSAKH, MIRTUL.priorMonth())
        assertEquals(CHES, TARSAKH.priorMonth())
        assertEquals(ALTURIAK, CHES.priorMonth())
        assertEquals(HAMMER, ALTURIAK.priorMonth())
        assertEquals(NIGHTAL, HAMMER.priorMonth())
    }

    @Test
    fun `nextMonth returns correct month in all cases`() {
        assertEquals(ALTURIAK, HAMMER.nextMonth())
        assertEquals(CHES, ALTURIAK.nextMonth())
        assertEquals(TARSAKH, CHES.nextMonth())
        assertEquals(MIRTUL, TARSAKH.nextMonth())
        assertEquals(KYTHORN, MIRTUL.nextMonth())
        assertEquals(FLAMERULE, KYTHORN.nextMonth())
        assertEquals(ELEASIAS, FLAMERULE.nextMonth())
        assertEquals(ELEINT, ELEASIAS.nextMonth())
        assertEquals(MARPENOTH, ELEINT.nextMonth())
        assertEquals(UKTAR, MARPENOTH.nextMonth())
        assertEquals(NIGHTAL, UKTAR.nextMonth())
        assertEquals(HAMMER, NIGHTAL.nextMonth())
    }

    @Test
    fun `lastHoliday returns correct holiday in all cases`() {
        assertEquals(MOON_FEAST, HAMMER.lastHoliday())
        assertEquals(SHIELDMEET, ALTURIAK.lastHoliday())
        assertEquals(SHIELDMEET, CHES.lastHoliday())
        assertEquals(SHIELDMEET, TARSAKH.lastHoliday())
        assertEquals(GREENGRASS, MIRTUL.lastHoliday())
        assertEquals(GREENGRASS, KYTHORN.lastHoliday())
        assertEquals(GREENGRASS, FLAMERULE.lastHoliday())
        assertEquals(MIDSUMMER, ELEASIAS.lastHoliday())
        assertEquals(MIDSUMMER, ELEINT.lastHoliday())
        assertEquals(HIGHHARVESTTIDE, MARPENOTH.lastHoliday())
        assertEquals(HIGHHARVESTTIDE, UKTAR.lastHoliday())
        assertEquals(MOON_FEAST, NIGHTAL.lastHoliday())
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        val year = 1001
        assertEquals(SHIELDMEET, HAMMER.nextHoliday(year))
        assertEquals(GREENGRASS, ALTURIAK.nextHoliday(year))
        assertEquals(GREENGRASS, CHES.nextHoliday(year))
        assertEquals(GREENGRASS, TARSAKH.nextHoliday(year))
        assertEquals(MIDSUMMER, MIRTUL.nextHoliday(year))
        assertEquals(MIDSUMMER, KYTHORN.nextHoliday(year))
        assertEquals(MIDSUMMER, FLAMERULE.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        assertEquals(MOON_FEAST, MARPENOTH.nextHoliday(year))
        assertEquals(MOON_FEAST, UKTAR.nextHoliday(year))
        assertEquals(SHIELDMEET, NIGHTAL.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        val year = 1003
        assertEquals(SHIELDMEET, HAMMER.nextHoliday(year))
        assertEquals(GREENGRASS, ALTURIAK.nextHoliday(year))
        assertEquals(GREENGRASS, CHES.nextHoliday(year))
        assertEquals(GREENGRASS, TARSAKH.nextHoliday(year))
        assertEquals(MIDSUMMER, MIRTUL.nextHoliday(year))
        assertEquals(MIDSUMMER, KYTHORN.nextHoliday(year))
        assertEquals(MIDSUMMER, FLAMERULE.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        assertEquals(MOON_FEAST, MARPENOTH.nextHoliday(year))
        assertEquals(MOON_FEAST, UKTAR.nextHoliday(year))
        assertEquals(MIDWINTER, NIGHTAL.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when leap year`() {
        val year = 1004
        assertEquals(MIDWINTER, HAMMER.nextHoliday(year))
        assertEquals(GREENGRASS, ALTURIAK.nextHoliday(year))
        assertEquals(GREENGRASS, CHES.nextHoliday(year))
        assertEquals(GREENGRASS, TARSAKH.nextHoliday(year))
        assertEquals(MIDSUMMER, MIRTUL.nextHoliday(year))
        assertEquals(MIDSUMMER, KYTHORN.nextHoliday(year))
        assertEquals(MIDSUMMER, FLAMERULE.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        assertEquals(HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        assertEquals(MOON_FEAST, MARPENOTH.nextHoliday(year))
        assertEquals(MOON_FEAST, UKTAR.nextHoliday(year))
        assertEquals(SHIELDMEET, NIGHTAL.nextHoliday(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `numHolidaysPassed throws error if date is not valid`() {
        HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).numHolidaysPassed()
    }

    @Test
    fun `numHolidaysPassed returns correct number on non-holiday when not leap year`() {
        assertEquals(0, HarposDate(day = 1, month = HAMMER, year = 1001).numHolidaysPassed())
        assertEquals(1, HarposDate(day = 1, month = ALTURIAK, year = 1001).numHolidaysPassed())
        assertEquals(1, HarposDate(day = 1, month = CHES, year = 1001).numHolidaysPassed())
        assertEquals(1, HarposDate(day = 1, month = TARSAKH, year = 1001).numHolidaysPassed())
        assertEquals(2, HarposDate(day = 1, month = MIRTUL, year = 1001).numHolidaysPassed())
        assertEquals(2, HarposDate(day = 1, month = KYTHORN, year = 1001).numHolidaysPassed())
        assertEquals(2, HarposDate(day = 1, month = FLAMERULE, year = 1001).numHolidaysPassed())
        assertEquals(3, HarposDate(day = 1, month = ELEASIAS, year = 1001).numHolidaysPassed())
        assertEquals(3, HarposDate(day = 1, month = ELEINT, year = 1001).numHolidaysPassed())
        assertEquals(4, HarposDate(day = 1, month = MARPENOTH, year = 1001).numHolidaysPassed())
        assertEquals(4, HarposDate(day = 1, month = UKTAR, year = 1001).numHolidaysPassed())
        assertEquals(5, HarposDate(day = 1, month = NIGHTAL, year = 1001).numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on non-holiday when leap year`() {
        assertEquals(1, HarposDate(day = 1, month = null, year = 1001, holiday = SHIELDMEET)
            .numHolidaysPassed())
        assertEquals(2, HarposDate(day = 1, month = null, year = 1001, holiday = GREENGRASS)
            .numHolidaysPassed())
        assertEquals(3, HarposDate(day = 1, month = null, year = 1001, holiday = MIDSUMMER)
            .numHolidaysPassed())
        assertEquals(4, HarposDate(day = 1, month = null, year = 1001, holiday = HIGHHARVESTTIDE)
            .numHolidaysPassed())
        assertEquals(5, HarposDate(day = 1, month = null, year = 1001, holiday = MOON_FEAST)
            .numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when not leap year`() {
        assertEquals(0, HarposDate(day = 1, month = HAMMER, year = 1004).numHolidaysPassed())
        assertEquals(2, HarposDate(day = 1, month = ALTURIAK, year = 1004).numHolidaysPassed())
        assertEquals(2, HarposDate(day = 1, month = CHES, year = 1004).numHolidaysPassed())
        assertEquals(2, HarposDate(day = 1, month = TARSAKH, year = 1004).numHolidaysPassed())
        assertEquals(3, HarposDate(day = 1, month = MIRTUL, year = 1004).numHolidaysPassed())
        assertEquals(3, HarposDate(day = 1, month = KYTHORN, year = 1004).numHolidaysPassed())
        assertEquals(3, HarposDate(day = 1, month = FLAMERULE, year = 1004).numHolidaysPassed())
        assertEquals(4, HarposDate(day = 1, month = ELEASIAS, year = 1004).numHolidaysPassed())
        assertEquals(4, HarposDate(day = 1, month = ELEINT, year = 1004).numHolidaysPassed())
        assertEquals(5, HarposDate(day = 1, month = MARPENOTH, year = 1004).numHolidaysPassed())
        assertEquals(5, HarposDate(day = 1, month = UKTAR, year = 1004).numHolidaysPassed())
        assertEquals(6, HarposDate(day = 1, month = NIGHTAL, year = 1004).numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when leap year`() {
        assertEquals(1, HarposDate(day = 1, month = null, year = 1004, holiday = MIDWINTER)
            .numHolidaysPassed())
        assertEquals(2, HarposDate(day = 1, month = null, year = 1004, holiday = SHIELDMEET)
            .numHolidaysPassed())
        assertEquals(3, HarposDate(day = 1, month = null, year = 1004, holiday = GREENGRASS)
            .numHolidaysPassed())
        assertEquals(4, HarposDate(day = 1, month = null, year = 1004, holiday = MIDSUMMER)
            .numHolidaysPassed())
        assertEquals(5, HarposDate(day = 1, month = null, year = 1004, holiday = HIGHHARVESTTIDE)
            .numHolidaysPassed())
        assertEquals(6, HarposDate(day = 1, month = null, year = 1004, holiday = MOON_FEAST)
            .numHolidaysPassed())
    }
}