package com.delarax.icewindDale.compantion.models.harpos

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.harpos.*
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.*
import com.delarax.icewindDale.companion.models.harpos.HarposMonth.*
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

    @Test(expected = InvalidDateException::class)
    fun `numHolidaysPassed throws error if date is not valid`() {
        HarposDate(day = 1, month = null, year = 1001, holiday = MIDWINTER).numHolidaysPassed()
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
        assertEquals(HAMMER, HarposDate.midwinter(1004).priorMonth())
        assertEquals(HAMMER, HarposDate.shieldmeet(1234).priorMonth())
        assertEquals(TARSAKH, HarposDate.greengrass(1234).priorMonth())
        assertEquals(FLAMERULE, HarposDate.midsummer(1234).priorMonth())
        assertEquals(ELEINT, HarposDate.highharvesttide(1234).priorMonth())
        assertEquals(UKTAR, HarposDate.moonFeast(1234).priorMonth())
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
        assertEquals(ALTURIAK, HarposDate.midwinter(1004).nextMonth())
        assertEquals(ALTURIAK, HarposDate.shieldmeet(1234).nextMonth())
        assertEquals(MIRTUL, HarposDate.greengrass(1234).nextMonth())
        assertEquals(ELEASIAS, HarposDate.midsummer(1234).nextMonth())
        assertEquals(MARPENOTH, HarposDate.highharvesttide(1234).nextMonth())
        assertEquals(NIGHTAL, HarposDate.moonFeast(1234).nextMonth())
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
        assertEquals(MIDWINTER, HarposDate.midwinter(1004).lastHoliday())
        assertEquals(SHIELDMEET, HarposDate.shieldmeet(1234).lastHoliday())
        assertEquals(GREENGRASS, HarposDate.greengrass(1234).lastHoliday())
        assertEquals(MIDSUMMER, HarposDate.midsummer(1234).lastHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate.highharvesttide(1234).lastHoliday())
        assertEquals(MOON_FEAST, HarposDate.moonFeast(1234).lastHoliday())
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
        assertEquals(GREENGRASS, HarposDate.shieldmeet(1001).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate.greengrass(1001).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate.midsummer(1001).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate.highharvesttide(1001).nextHoliday())
        assertEquals(SHIELDMEET, HarposDate.moonFeast(1001).nextHoliday())
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
        assertEquals(GREENGRASS, HarposDate.shieldmeet(1003).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate.greengrass(1003).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate.midsummer(1003).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate.highharvesttide(1003).nextHoliday())
        assertEquals(MIDWINTER, HarposDate.moonFeast(1003).nextHoliday())
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
        assertEquals(SHIELDMEET, HarposDate.midwinter(1004).nextHoliday())
        assertEquals(GREENGRASS, HarposDate.shieldmeet(1004).nextHoliday())
        assertEquals(MIDSUMMER, HarposDate.greengrass(1004).nextHoliday())
        assertEquals(HIGHHARVESTTIDE, HarposDate.midsummer(1004).nextHoliday())
        assertEquals(MOON_FEAST, HarposDate.highharvesttide(1004).nextHoliday())
        assertEquals(SHIELDMEET, HarposDate.moonFeast(1004).nextHoliday())
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
        assertEquals(1, HarposDate.shieldmeet(1001).numHolidaysPassed())
        assertEquals(2, HarposDate.greengrass(1001).numHolidaysPassed())
        assertEquals(3, HarposDate.midsummer(1001).numHolidaysPassed())
        assertEquals(4, HarposDate.highharvesttide(1001).numHolidaysPassed())
        assertEquals(5, HarposDate.moonFeast(1001).numHolidaysPassed())
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when leap year`() {
        assertEquals(1, HarposDate.midwinter(1004).numHolidaysPassed())
        assertEquals(2, HarposDate.shieldmeet(1004).numHolidaysPassed())
        assertEquals(3, HarposDate.greengrass(1004).numHolidaysPassed())
        assertEquals(4, HarposDate.midsummer(1004).numHolidaysPassed())
        assertEquals(5, HarposDate.highharvesttide(1004).numHolidaysPassed())
        assertEquals(6, HarposDate.moonFeast(1004).numHolidaysPassed())
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
        assertEquals(31, HarposDate.shieldmeet(year = 1001).absoluteDayNumber())
        assertEquals(122, HarposDate.greengrass(year = 1001).absoluteDayNumber())
        assertEquals(213, HarposDate.midsummer(year = 1001).absoluteDayNumber())
        assertEquals(274, HarposDate.highharvesttide(year = 1001).absoluteDayNumber())
        assertEquals(335, HarposDate.moonFeast(year = 1001).absoluteDayNumber())
    }

    @Test
    fun `absoluteDayNumber for each holiday, leap year`() {
        assertEquals(31, HarposDate.midwinter(year = 1004).absoluteDayNumber())
        assertEquals(32, HarposDate.shieldmeet(year = 1004).absoluteDayNumber())
        assertEquals(123, HarposDate.greengrass(year = 1004).absoluteDayNumber())
        assertEquals(214, HarposDate.midsummer(year = 1004).absoluteDayNumber())
        assertEquals(275, HarposDate.highharvesttide(year = 1004).absoluteDayNumber())
        assertEquals(336, HarposDate.moonFeast(year = 1004).absoluteDayNumber())
    }
}