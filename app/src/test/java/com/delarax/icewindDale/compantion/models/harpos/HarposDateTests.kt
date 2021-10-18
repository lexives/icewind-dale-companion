package com.delarax.icewindDale.compantion.models.harpos

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.*
import com.delarax.icewindDale.companion.models.harpos.HarposMonth.*
import com.delarax.icewindDale.companion.models.harpos.toDate
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HarposDateTests {

    @Test
    fun `isLeapYear is true every fourth year`() {
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 1).isLeapYear)
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 2).isLeapYear)
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 3).isLeapYear)
        assertTrue(HarposDate(day = 1, month = HAMMER, year = 4).isLeapYear)
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 5).isLeapYear)
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 6).isLeapYear)
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 7).isLeapYear)
        assertTrue(HarposDate(day = 1, month = HAMMER, year = 8).isLeapYear)
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 9).isLeapYear)
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 10).isLeapYear)
        assertFalse(HarposDate(day = 1, month = HAMMER, year = 11).isLeapYear)
        assertTrue(HarposDate(day = 1, month = HAMMER, year = 12).isLeapYear)
    }

    @Test
    fun `isValid for date with no month or holiday is not valid`() {
        assertFalse(HarposDate(day = 20, month = null, year = 1234, holiday = null).isValid)
    }

    @Test
    fun `isValid for date with both a month and a holiday is not valid`() {
        assertFalse(
            HarposDate(day = 20, month = MIRTUL, year = 1234, holiday = MIDSUMMER).isValid
        )
    }

    @Test
    fun `isValid for date with a year less than 1 is not valid`() {
        assertFalse(HarposDate(day = 20, month = MIRTUL, year = 0).isValid)
        assertFalse(HarposDate(day = 20, month = MIRTUL, year = -1).isValid)
    }

    @Test
    fun `isValid for date with day less than 1 is not valid`() {
        assertFalse(HarposDate(day = 0, month = MIRTUL, year = 1234).isValid)
        assertFalse(HarposDate(day = -1, month = MIRTUL, year = 1234).isValid)
    }

    @Test
    fun `isValid for date with day greater than 30 is not valid`() {
        assertFalse(HarposDate(day = 31, month = MIRTUL, year = 1234).isValid)
    }

    @Test
    fun `isValid for date with day other than 1 on a holiday is not valid`() {
        assertFalse(HarposDate(day = 20, month = null, year = 1234, holiday = MIDSUMMER).isValid)
    }

    @Test
    fun `isValid for midwinter on a non-leap year is not valid`() {
        assertFalse(HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).isValid)
    }

    @Test
    fun `isValid for valid date within a month`() {
        assertTrue(HarposDate(day = 20, month = MIRTUL, year = 1234).isValid)
        assertTrue(HarposDate(day = 30, month = MIRTUL, year = 1234).isValid)
    }

    @Test
    fun `isValid for valid date on a holiday`() {
        assertTrue(HarposDate(day = 1, month = null, year = 1234, holiday = MIDSUMMER).isValid)
    }

    @Test
    fun `isValid for valid midwinter`() {
        assertTrue(HarposDate(day = 1, month = null, year = 1004, holiday = MIDWINTER).isValid)
    }

    @Test(expected = InvalidDateException::class)
    fun `priorMonth throws error if date is not valid`() {
        HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).priorMonth()
    }

    @Test
    fun `priorMonth returns correct month in all cases`() {
        assertEquals(UKTAR, HarposDate(day = 1, month = NIGHTAL, year = 1234).priorMonth())
        assertEquals(MARPENOTH, HarposDate(day = 1, month = UKTAR, year = 1234).priorMonth())
        assertEquals(ELEINT, HarposDate(day = 1, month = MARPENOTH, year = 1234).priorMonth())
        assertEquals(ELEASIAS, HarposDate(day = 1, month = ELEINT, year = 1234).priorMonth())
        assertEquals(FLAMERULE, HarposDate(day = 1, month = ELEASIAS, year = 1234).priorMonth())
        assertEquals(KYTHORN, HarposDate(day = 1, month = FLAMERULE, year = 1234).priorMonth())
        assertEquals(MIRTUL, HarposDate(day = 1, month = KYTHORN, year = 1234).priorMonth())
        assertEquals(TARSAKH, HarposDate(day = 1, month = MIRTUL, year = 1234).priorMonth())
        assertEquals(CHES, HarposDate(day = 1, month = TARSAKH, year = 1234).priorMonth())
        assertEquals(ALTURIAK, HarposDate(day = 1, month = CHES, year = 1234).priorMonth())
        assertEquals(HAMMER, HarposDate(day = 1, month = ALTURIAK, year = 1234).priorMonth())
        assertEquals(NIGHTAL, HarposDate(day = 1, month = HAMMER, year = 1234).priorMonth())
        assertEquals(HAMMER, MIDWINTER.toDate(1004).priorMonth())
        assertEquals(HAMMER, SHIELDMEET.toDate(1234).priorMonth())
        assertEquals(TARSAKH, GREENGRASS.toDate(1234).priorMonth())
        assertEquals(FLAMERULE, MIDSUMMER.toDate(1234).priorMonth())
        assertEquals(ELEINT, HIGHHARVESTTIDE.toDate(1234).priorMonth())
        assertEquals(UKTAR, MOON_FEAST.toDate(1234).priorMonth())
    }

    @Test(expected = InvalidDateException::class)
    fun `nextMonth throws error if date is not valid`() {
        HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).nextMonth()
    }

    @Test
    fun `nextMonth returns correct month in all cases`() {
        assertEquals(ALTURIAK, HarposDate(day = 1, month = HAMMER, year = 1234).nextMonth())
        assertEquals(CHES, HarposDate(day = 1, month = ALTURIAK, year = 1234).nextMonth())
        assertEquals(TARSAKH, HarposDate(day = 1, month = CHES, year = 1234).nextMonth())
        assertEquals(MIRTUL, HarposDate(day = 1, month = TARSAKH, year = 1234).nextMonth())
        assertEquals(KYTHORN, HarposDate(day = 1, month = MIRTUL, year = 1234).nextMonth())
        assertEquals(FLAMERULE, HarposDate(day = 1, month = KYTHORN, year = 1234).nextMonth())
        assertEquals(ELEASIAS, HarposDate(day = 1, month = FLAMERULE, year = 1234).nextMonth())
        assertEquals(ELEINT, HarposDate(day = 1, month = ELEASIAS, year = 1234).nextMonth())
        assertEquals(MARPENOTH, HarposDate(day = 1, month = ELEINT, year = 1234).nextMonth())
        assertEquals(UKTAR, HarposDate(day = 1, month = MARPENOTH, year = 1234).nextMonth())
        assertEquals(NIGHTAL, HarposDate(day = 1, month = UKTAR, year = 1234).nextMonth())
        assertEquals(HAMMER, HarposDate(day = 1, month = NIGHTAL, year = 1234).nextMonth())
        assertEquals(ALTURIAK, MIDWINTER.toDate(1004).nextMonth())
        assertEquals(ALTURIAK, SHIELDMEET.toDate(1234).nextMonth())
        assertEquals(MIRTUL, GREENGRASS.toDate(1234).nextMonth())
        assertEquals(ELEASIAS, MIDSUMMER.toDate(1234).nextMonth())
        assertEquals(MARPENOTH, HIGHHARVESTTIDE.toDate(1234).nextMonth())
        assertEquals(NIGHTAL, MOON_FEAST.toDate(1234).nextMonth())
    }

    @Test(expected = InvalidDateException::class)
    fun `lastHoliday throws error if date is not valid`() {
        HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).lastHoliday()
    }

    @Test
    fun `lastHoliday returns correct holiday in all cases`() {
        assertEquals(MOON_FEAST, HarposDate(day = 1, month = HAMMER, year = 1234).lastHoliday())
        assertEquals(SHIELDMEET, HarposDate(day = 1, month = ALTURIAK, year = 1234).lastHoliday())
        assertEquals(SHIELDMEET, HarposDate(day = 1, month = CHES, year = 1234).lastHoliday())
        assertEquals(SHIELDMEET, HarposDate(day = 1, month = TARSAKH, year = 1234).lastHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = MIRTUL, year = 1234).lastHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = KYTHORN, year = 1234).lastHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = FLAMERULE, year = 1234).lastHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = ELEASIAS, year = 1234).lastHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = ELEINT, year = 1234).lastHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate(day = 1, month = MARPENOTH, year = 1234).lastHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate(day = 1, month = UKTAR, year = 1234).lastHoliday())
        assertEquals(MOON_FEAST, HarposDate(day = 1, month = NIGHTAL, year = 1234).lastHoliday())
        assertEquals(MIDWINTER, MIDWINTER.toDate(1004).lastHoliday())
        assertEquals(SHIELDMEET, SHIELDMEET.toDate(1234).lastHoliday())
        assertEquals(GREENGRASS, GREENGRASS.toDate(1234).lastHoliday())
        assertEquals(MIDSUMMER, MIDSUMMER.toDate(1234).lastHoliday())
        assertEquals(HIGHHARVESTTIDE, HIGHHARVESTTIDE.toDate(1234).lastHoliday())
        assertEquals(MOON_FEAST, MOON_FEAST.toDate(1234).lastHoliday())
    }

    @Test(expected = InvalidDateException::class)
    fun `nextHoliday throws error if date is not valid`() {
        HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).nextHoliday()
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        assertEquals(SHIELDMEET, HarposDate(day = 1, month = HAMMER, year = 1001).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = ALTURIAK, year = 1001).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = CHES, year = 1001).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = TARSAKH, year = 1001).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = MIRTUL, year = 1001).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = KYTHORN, year = 1001).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = FLAMERULE, year = 1001).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate(day = 1, month = ELEASIAS, year = 1001).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate(day = 1, month = ELEINT, year = 1001).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate(day = 1, month = MARPENOTH, year = 1001).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate(day = 1, month = UKTAR, year = 1001).nextHoliday())
        assertEquals(SHIELDMEET, HarposDate(day = 1, month = NIGHTAL, year = 1001).nextHoliday())
        assertEquals(GREENGRASS, SHIELDMEET.toDate(1001).nextHoliday())
        assertEquals(MIDSUMMER, GREENGRASS.toDate(1001).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, MIDSUMMER.toDate(1001).nextHoliday())
        assertEquals(MOON_FEAST, HIGHHARVESTTIDE.toDate(1001).nextHoliday())
        assertEquals(SHIELDMEET, MOON_FEAST.toDate(1001).nextHoliday())
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        assertEquals(SHIELDMEET, HarposDate(day = 1, month = HAMMER, year = 1003).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = ALTURIAK, year = 1003).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = CHES, year = 1003).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = TARSAKH, year = 1003).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = MIRTUL, year = 1003).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = KYTHORN, year = 1003).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = FLAMERULE, year = 1003).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate(day = 1, month = ELEASIAS, year = 1003).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate(day = 1, month = ELEINT, year = 1003).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate(day = 1, month = MARPENOTH, year = 1003).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate(day = 1, month = UKTAR, year = 1003).nextHoliday())
        assertEquals(MIDWINTER, HarposDate(day = 1, month = NIGHTAL, year = 1003).nextHoliday())
        assertEquals(GREENGRASS, SHIELDMEET.toDate(1003).nextHoliday())
        assertEquals(MIDSUMMER, GREENGRASS.toDate(1003).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, MIDSUMMER.toDate(1003).nextHoliday())
        assertEquals(MOON_FEAST, HIGHHARVESTTIDE.toDate(1003).nextHoliday())
        assertEquals(MIDWINTER, MOON_FEAST.toDate(1003).nextHoliday())
    }

    @Test
    fun `nextHoliday returns correct holiday when leap year`() {
        assertEquals(MIDWINTER, HarposDate(day = 1, month = HAMMER, year = 1004).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = ALTURIAK, year = 1004).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = CHES, year = 1004).nextHoliday())
        assertEquals(GREENGRASS, HarposDate(day = 1, month = TARSAKH, year = 1004).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = MIRTUL, year = 1004).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = KYTHORN, year = 1004).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate(day = 1, month = FLAMERULE, year = 1004).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate(day = 1, month = ELEASIAS, year = 1004).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate(day = 1, month = ELEINT, year = 1004).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate(day = 1, month = MARPENOTH, year = 1004).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate(day = 1, month = UKTAR, year = 1004).nextHoliday())
        assertEquals(SHIELDMEET, HarposDate(day = 1, month = NIGHTAL, year = 1004).nextHoliday())
        assertEquals(SHIELDMEET, MIDWINTER.toDate(1004).nextHoliday())
        assertEquals(GREENGRASS, SHIELDMEET.toDate(1004).nextHoliday())
        assertEquals(MIDSUMMER, GREENGRASS.toDate(1004).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, MIDSUMMER.toDate(1004).nextHoliday())
        assertEquals(MOON_FEAST, HIGHHARVESTTIDE.toDate(1004).nextHoliday())
        assertEquals(SHIELDMEET, MOON_FEAST.toDate(1004).nextHoliday())
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
    fun `numHolidaysPassed returns correct number on holiday when not leap year`() {
        assertEquals(1, SHIELDMEET.toDate(1001).numHolidaysPassed())
        assertEquals(2, GREENGRASS.toDate(1001).numHolidaysPassed())
        assertEquals(3, MIDSUMMER.toDate(1001).numHolidaysPassed())
        assertEquals(4, HIGHHARVESTTIDE.toDate(1001).numHolidaysPassed())
        assertEquals(5, MOON_FEAST.toDate(1001).numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when leap year`() {
        assertEquals(1, MIDWINTER.toDate(1004).numHolidaysPassed())
        assertEquals(2, SHIELDMEET.toDate(1004).numHolidaysPassed())
        assertEquals(3, GREENGRASS.toDate(1004).numHolidaysPassed())
        assertEquals(4, MIDSUMMER.toDate(1004).numHolidaysPassed())
        assertEquals(5, HIGHHARVESTTIDE.toDate(1004).numHolidaysPassed())
        assertEquals(6, MOON_FEAST.toDate(1004).numHolidaysPassed())
    }

    @Test(expected = InvalidDateException::class)
    fun `absoluteDayNumber throws error if date is not valid`() {
        HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).absoluteDayNumber()
    }

    @Test
    fun `absoluteDayNumber for first day of each month, non-leap year`() {
        assertEquals(1, HarposDate(day = 1, month = HAMMER, year = 1001).absoluteDayNumber())
        assertEquals(32, HarposDate(day = 1, month = ALTURIAK, year = 1001).absoluteDayNumber())
        assertEquals(62, HarposDate(day = 1, month = CHES, year = 1001).absoluteDayNumber())
        assertEquals(92, HarposDate(day = 1, month = TARSAKH, year = 1001).absoluteDayNumber())
        assertEquals(123, HarposDate(day = 1, month = MIRTUL, year = 1001).absoluteDayNumber())
        assertEquals(153, HarposDate(day = 1, month = KYTHORN, year = 1001).absoluteDayNumber())
        assertEquals(183, HarposDate(day = 1, month = FLAMERULE, year = 1001).absoluteDayNumber())
        assertEquals(214, HarposDate(day = 1, month = ELEASIAS, year = 1001).absoluteDayNumber())
        assertEquals(244, HarposDate(day = 1, month = ELEINT, year = 1001).absoluteDayNumber())
        assertEquals(275, HarposDate(day = 1, month = MARPENOTH, year = 1001).absoluteDayNumber())
        assertEquals(305, HarposDate(day = 1, month = UKTAR, year = 1001).absoluteDayNumber())
        assertEquals(336, HarposDate(day = 1, month = NIGHTAL, year = 1001).absoluteDayNumber())
    }

    @Test
    fun `absoluteDayNumber for first day of each month, leap year`() {
        assertEquals(1, HarposDate(day = 1, month = HAMMER, year = 1004).absoluteDayNumber())
        assertEquals(33, HarposDate(day = 1, month = ALTURIAK, year = 1004).absoluteDayNumber())
        assertEquals(63, HarposDate(day = 1, month = CHES, year = 1004).absoluteDayNumber())
        assertEquals(93, HarposDate(day = 1, month = TARSAKH, year = 1004).absoluteDayNumber())
        assertEquals(124, HarposDate(day = 1, month = MIRTUL, year = 1004).absoluteDayNumber())
        assertEquals(154, HarposDate(day = 1, month = KYTHORN, year = 1004).absoluteDayNumber())
        assertEquals(184, HarposDate(day = 1, month = FLAMERULE, year = 1004).absoluteDayNumber())
        assertEquals(215, HarposDate(day = 1, month = ELEASIAS, year = 1004).absoluteDayNumber())
        assertEquals(245, HarposDate(day = 1, month = ELEINT, year = 1004).absoluteDayNumber())
        assertEquals(276, HarposDate(day = 1, month = MARPENOTH, year = 1004).absoluteDayNumber())
        assertEquals(306, HarposDate(day = 1, month = UKTAR, year = 1004).absoluteDayNumber())
        assertEquals(337, HarposDate(day = 1, month = NIGHTAL, year = 1004).absoluteDayNumber())
    }

    @Test
    fun `absoluteDayNumber for last day of each month, non-leap year`() {
        assertEquals(30, HarposDate(day = 30, month = HAMMER, year = 1001).absoluteDayNumber())
        assertEquals(61, HarposDate(day = 30, month = ALTURIAK, year = 1001).absoluteDayNumber())
        assertEquals(91, HarposDate(day = 30, month = CHES, year = 1001).absoluteDayNumber())
        assertEquals(121, HarposDate(day = 30, month = TARSAKH, year = 1001).absoluteDayNumber())
        assertEquals(152, HarposDate(day = 30, month = MIRTUL, year = 1001).absoluteDayNumber())
        assertEquals(182, HarposDate(day = 30, month = KYTHORN, year = 1001).absoluteDayNumber())
        assertEquals(212, HarposDate(day = 30, month = FLAMERULE, year = 1001).absoluteDayNumber())
        assertEquals(243, HarposDate(day = 30, month = ELEASIAS, year = 1001).absoluteDayNumber())
        assertEquals(273, HarposDate(day = 30, month = ELEINT, year = 1001).absoluteDayNumber())
        assertEquals(304, HarposDate(day = 30, month = MARPENOTH, year = 1001).absoluteDayNumber())
        assertEquals(334, HarposDate(day = 30, month = UKTAR, year = 1001).absoluteDayNumber())
        assertEquals(365, HarposDate(day = 30, month = NIGHTAL, year = 1001).absoluteDayNumber())
    }

    @Test
    fun `absoluteDayNumber for last day of each month, leap year`() {
        assertEquals(30, HarposDate(day = 30, month = HAMMER, year = 1004).absoluteDayNumber())
        assertEquals(62, HarposDate(day = 30, month = ALTURIAK, year = 1004).absoluteDayNumber())
        assertEquals(92, HarposDate(day = 30, month = CHES, year = 1004).absoluteDayNumber())
        assertEquals(122, HarposDate(day = 30, month = TARSAKH, year = 1004).absoluteDayNumber())
        assertEquals(153, HarposDate(day = 30, month = MIRTUL, year = 1004).absoluteDayNumber())
        assertEquals(183, HarposDate(day = 30, month = KYTHORN, year = 1004).absoluteDayNumber())
        assertEquals(213, HarposDate(day = 30, month = FLAMERULE, year = 1004).absoluteDayNumber())
        assertEquals(244, HarposDate(day = 30, month = ELEASIAS, year = 1004).absoluteDayNumber())
        assertEquals(274, HarposDate(day = 30, month = ELEINT, year = 1004).absoluteDayNumber())
        assertEquals(305, HarposDate(day = 30, month = MARPENOTH, year = 1004).absoluteDayNumber())
        assertEquals(335, HarposDate(day = 30, month = UKTAR, year = 1004).absoluteDayNumber())
        assertEquals(366, HarposDate(day = 30, month = NIGHTAL, year = 1004).absoluteDayNumber())
    }

    @Test
    fun `absoluteDayNumber for each holiday, non-leap year`() {
        assertEquals(31, SHIELDMEET.toDate(year = 1001).absoluteDayNumber())
        assertEquals(122, GREENGRASS.toDate(year = 1001).absoluteDayNumber())
        assertEquals(213, MIDSUMMER.toDate(year = 1001).absoluteDayNumber())
        assertEquals(274, HIGHHARVESTTIDE.toDate(year = 1001).absoluteDayNumber())
        assertEquals(335, MOON_FEAST.toDate(year = 1001).absoluteDayNumber())
    }

    @Test
    fun `absoluteDayNumber for each holiday, leap year`() {
        assertEquals(31, MIDWINTER.toDate(year = 1004).absoluteDayNumber())
        assertEquals(32, SHIELDMEET.toDate(year = 1004).absoluteDayNumber())
        assertEquals(123, GREENGRASS.toDate(year = 1004).absoluteDayNumber())
        assertEquals(214, MIDSUMMER.toDate(year = 1004).absoluteDayNumber())
        assertEquals(275, HIGHHARVESTTIDE.toDate(year = 1004).absoluteDayNumber())
        assertEquals(336, MOON_FEAST.toDate(year = 1004).absoluteDayNumber())
    }

    @Test
    fun `daysInLeapYear returns correct number of days`() {
        assertEquals(366, HarposDate.daysInLeapYear)
    }

    @Test
    fun `daysInNonLeapYear returns correct number of days`() {
        assertEquals(365, HarposDate.daysInNonLeapYear)
    }

    @Test(expected = InvalidDateException::class)
    fun `fromAbsoluteDayNumber throws error if day number is less than 1`() {
        HarposDate.fromAbsoluteDayNumber(0, 1004)
    }

    @Test(expected = InvalidDateException::class)
    fun `fromAbsoluteDayNumber throws error if day number is greater than max for leap year`() {
        HarposDate.fromAbsoluteDayNumber(367, 1004)
    }

    @Test(expected = InvalidDateException::class)
    fun `fromAbsoluteDayNumber throws error if day number is greater than max for non-leap year`() {
        HarposDate.fromAbsoluteDayNumber(366, 1001)
    }

    @Test
    fun `fromAbsoluteDayNumber converts all holidays correctly for non-leap year`() {
        val year = 1001
        assertEquals(SHIELDMEET.toDate(year), HarposDate.fromAbsoluteDayNumber(31, year))
        assertEquals(GREENGRASS.toDate(year), HarposDate.fromAbsoluteDayNumber(122, year))
        assertEquals(MIDSUMMER.toDate(year), HarposDate.fromAbsoluteDayNumber(213, year))
        assertEquals(HIGHHARVESTTIDE.toDate(year), HarposDate.fromAbsoluteDayNumber(274, year))
        assertEquals(MOON_FEAST.toDate(year), HarposDate.fromAbsoluteDayNumber(335, year))
    }

    @Test
    fun `fromAbsoluteDayNumber converts all holidays correctly for leap year`() {
        val year = 1004
        assertEquals(MIDWINTER.toDate(year), HarposDate.fromAbsoluteDayNumber(31, year))
        assertEquals(SHIELDMEET.toDate(year), HarposDate.fromAbsoluteDayNumber(32, year))
        assertEquals(GREENGRASS.toDate(year), HarposDate.fromAbsoluteDayNumber(123, year))
        assertEquals(MIDSUMMER.toDate(year), HarposDate.fromAbsoluteDayNumber(214, year))
        assertEquals(HIGHHARVESTTIDE.toDate(year), HarposDate.fromAbsoluteDayNumber(275, year))
        assertEquals(MOON_FEAST.toDate(year), HarposDate.fromAbsoluteDayNumber(336, year))
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Hammer`() {
        val year = 1001
        assertEquals(
            HarposDate(day = 1, month = HAMMER, year = year),
            HarposDate.fromAbsoluteDayNumber(1, year)
        )
        assertEquals(
            HarposDate(day = 11, month = HAMMER, year = year),
            HarposDate.fromAbsoluteDayNumber(11, year)
        )
        assertEquals(
            HarposDate(day = 30, month = HAMMER, year = year),
            HarposDate.fromAbsoluteDayNumber(30, year)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Alturiak`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = ALTURIAK, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(32, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = ALTURIAK, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(42, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = ALTURIAK, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(61, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = ALTURIAK, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(33, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = ALTURIAK, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(43, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = ALTURIAK, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(62, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Ches`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = CHES, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(62, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = CHES, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(72, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = CHES, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(91, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = CHES, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(63, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = CHES, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(73, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = CHES, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(92, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Tarsakh`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = TARSAKH, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(92, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = TARSAKH, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(102, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = TARSAKH, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(121, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = TARSAKH, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(93, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = TARSAKH, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(103, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = TARSAKH, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(122, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Mirtul`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = MIRTUL, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(123, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = MIRTUL, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(133, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = MIRTUL, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(152, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = MIRTUL, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(124, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = MIRTUL, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(134, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = MIRTUL, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(153, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Kythorn`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = KYTHORN, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(153, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = KYTHORN, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(163, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = KYTHORN, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(182, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = KYTHORN, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(154, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = KYTHORN, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(164, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = KYTHORN, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(183, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Flamerule`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = FLAMERULE, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(183, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = FLAMERULE, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(193, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = FLAMERULE, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(212, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = FLAMERULE, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(184, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = FLAMERULE, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(194, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = FLAMERULE, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(213, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Eleint`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = ELEINT, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(244, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = ELEINT, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(254, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = ELEINT, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(273, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = ELEINT, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(245, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = ELEINT, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(255, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = ELEINT, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(274, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Marpenoth`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = MARPENOTH, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(275, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = MARPENOTH, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(285, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = MARPENOTH, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(304, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = MARPENOTH, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(276, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = MARPENOTH, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(286, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = MARPENOTH, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(305, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Uktar`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = UKTAR, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(305, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = UKTAR, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(315, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = UKTAR, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(334, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = UKTAR, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(306, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = UKTAR, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(316, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = UKTAR, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(335, leapYear)
        )
    }

    @Test
    fun `fromAbsoluteDayNumber converts first, middle, and last days of Nightal`() {
        val nonLeapYear = 1001
        assertEquals(
            HarposDate(day = 1, month = NIGHTAL, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(336, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = NIGHTAL, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(346, nonLeapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = NIGHTAL, year = nonLeapYear),
            HarposDate.fromAbsoluteDayNumber(365, nonLeapYear)
        )

        val leapYear = 1004
        assertEquals(
            HarposDate(day = 1, month = NIGHTAL, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(337, leapYear)
        )
        assertEquals(
            HarposDate(day = 11, month = NIGHTAL, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(347, leapYear)
        )
        assertEquals(
            HarposDate(day = 30, month = NIGHTAL, year = leapYear),
            HarposDate.fromAbsoluteDayNumber(366, leapYear)
        )
    }
}