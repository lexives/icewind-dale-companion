package com.delarax.icewindDale.compantion.models

import com.delarax.icewindDale.companion.models.*
import com.delarax.icewindDale.companion.models.HarposHoliday.*
import com.delarax.icewindDale.companion.models.HarposMonth.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HarposDateExtTests {

    @Test
    fun `date with no month or holiday is not valid`() {
        Assert.assertFalse(
            HarposDate(day = 20, month = null, year = 1234, holiday = null).isValid()
        )
    }

    @Test
    fun `date with both a month and a holiday is not valid`() {
        Assert.assertFalse(
            HarposDate(day = 20, month = MIRTUL, year = 1234, holiday = MIDSUMMER).isValid()
        )
    }

    @Test
    fun `date with a year less than 1 is not valid`() {
        Assert.assertFalse(
            HarposDate(day = 20, month = MIRTUL, year = 0).isValid()
        )

        Assert.assertFalse(
            HarposDate(day = 20, month = MIRTUL, year = -1).isValid()
        )
    }

    @Test
    fun `date with day less than 1 is not valid`() {
        Assert.assertFalse(
            HarposDate(day = 0, month = MIRTUL, year = 1234).isValid()
        )

        Assert.assertFalse(
            HarposDate(day = -1, month = MIRTUL, year = 1234).isValid()
        )
    }

    @Test
    fun `date with day greater than 30 is not valid`() {
        Assert.assertFalse(
            HarposDate(day = 31, month = MIRTUL, year = 1234).isValid()
        )
    }

    @Test
    fun `date with day other than 1 on a holiday is not valid`() {
        Assert.assertFalse(
            HarposDate(day = 20, month = null, year = 1234, holiday = MIDSUMMER).isValid()
        )
    }

    @Test
    fun `valid date within a month`() {
        Assert.assertTrue(
            HarposDate(day = 20, month = MIRTUL, year = 1234).isValid()
        )
        Assert.assertTrue(
            HarposDate(day = 30, month = MIRTUL, year = 1234).isValid()
        )
    }

    @Test
    fun `valid date on a holiday`() {
        Assert.assertTrue(
            HarposDate(day = 1, month = null, year = 1234, holiday = MIDSUMMER).isValid()
        )
    }

    @Test
    fun `priorMonth returns correct month in all cases`() {
        Assert.assertEquals(UKTAR, NIGHTAL.priorMonth())
        Assert.assertEquals(MARPENOTH, UKTAR.priorMonth())
        Assert.assertEquals(ELEINT, MARPENOTH.priorMonth())
        Assert.assertEquals(ELEASIAS, ELEINT.priorMonth())
        Assert.assertEquals(FLAMERULE, ELEASIAS.priorMonth())
        Assert.assertEquals(KYTHORN, FLAMERULE.priorMonth())
        Assert.assertEquals(MIRTUL, KYTHORN.priorMonth())
        Assert.assertEquals(TARSAKH, MIRTUL.priorMonth())
        Assert.assertEquals(CHES, TARSAKH.priorMonth())
        Assert.assertEquals(ALTURIAK, CHES.priorMonth())
        Assert.assertEquals(HAMMER, ALTURIAK.priorMonth())
        Assert.assertEquals(NIGHTAL, HAMMER.priorMonth())
    }

    @Test
    fun `nextMonth returns correct month in all cases`() {
        Assert.assertEquals(ALTURIAK, HAMMER.nextMonth())
        Assert.assertEquals(CHES, ALTURIAK.nextMonth())
        Assert.assertEquals(TARSAKH, CHES.nextMonth())
        Assert.assertEquals(MIRTUL, TARSAKH.nextMonth())
        Assert.assertEquals(KYTHORN, MIRTUL.nextMonth())
        Assert.assertEquals(FLAMERULE, KYTHORN.nextMonth())
        Assert.assertEquals(ELEASIAS, FLAMERULE.nextMonth())
        Assert.assertEquals(ELEINT, ELEASIAS.nextMonth())
        Assert.assertEquals(MARPENOTH, ELEINT.nextMonth())
        Assert.assertEquals(UKTAR, MARPENOTH.nextMonth())
        Assert.assertEquals(NIGHTAL, UKTAR.nextMonth())
        Assert.assertEquals(HAMMER, NIGHTAL.nextMonth())
    }

    @Test
    fun `lastHoliday returns correct holiday in all cases`() {
        Assert.assertEquals(MOON_FEAST, HAMMER.lastHoliday())
        Assert.assertEquals(SHIELDMEET, ALTURIAK.lastHoliday())
        Assert.assertEquals(SHIELDMEET, CHES.lastHoliday())
        Assert.assertEquals(SHIELDMEET, TARSAKH.lastHoliday())
        Assert.assertEquals(GREENGRASS, MIRTUL.lastHoliday())
        Assert.assertEquals(GREENGRASS, KYTHORN.lastHoliday())
        Assert.assertEquals(GREENGRASS, FLAMERULE.lastHoliday())
        Assert.assertEquals(MIDSUMMER, ELEASIAS.lastHoliday())
        Assert.assertEquals(MIDSUMMER, ELEINT.lastHoliday())
        Assert.assertEquals(HIGHHARVESTTIDE, MARPENOTH.lastHoliday())
        Assert.assertEquals(HIGHHARVESTTIDE, UKTAR.lastHoliday())
        Assert.assertEquals(MOON_FEAST, NIGHTAL.lastHoliday())
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        val year = 1001
        Assert.assertEquals(SHIELDMEET, HAMMER.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, ALTURIAK.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, CHES.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, TARSAKH.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, MIRTUL.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, KYTHORN.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, FLAMERULE.nextHoliday(year))
        Assert.assertEquals(HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        Assert.assertEquals(HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, MARPENOTH.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, UKTAR.nextHoliday(year))
        Assert.assertEquals(SHIELDMEET, NIGHTAL.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        val year = 1003
        Assert.assertEquals(SHIELDMEET, HAMMER.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, ALTURIAK.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, CHES.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, TARSAKH.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, MIRTUL.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, KYTHORN.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, FLAMERULE.nextHoliday(year))
        Assert.assertEquals(HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        Assert.assertEquals(HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, MARPENOTH.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, UKTAR.nextHoliday(year))
        Assert.assertEquals(MIDWINTER, NIGHTAL.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when leap year`() {
        val year = 1004
        Assert.assertEquals(MIDWINTER, HAMMER.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, ALTURIAK.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, CHES.nextHoliday(year))
        Assert.assertEquals(GREENGRASS, TARSAKH.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, MIRTUL.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, KYTHORN.nextHoliday(year))
        Assert.assertEquals(MIDSUMMER, FLAMERULE.nextHoliday(year))
        Assert.assertEquals(HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        Assert.assertEquals(HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, MARPENOTH.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, UKTAR.nextHoliday(year))
        Assert.assertEquals(SHIELDMEET, NIGHTAL.nextHoliday(year))
    }
}