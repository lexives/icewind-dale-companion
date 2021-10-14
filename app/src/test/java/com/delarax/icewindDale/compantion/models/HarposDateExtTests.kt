package com.delarax.icewindDale.compantion.models

import com.delarax.icewindDale.companion.models.*
import com.delarax.icewindDale.companion.models.HarposMonth.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HarposDateExtTests {

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
        Assert.assertEquals(HarposHoliday.MOON_FEAST, HAMMER.lastHoliday())
        Assert.assertEquals(HarposHoliday.SHIELDMEET, ALTURIAK.lastHoliday())
        Assert.assertEquals(HarposHoliday.SHIELDMEET, CHES.lastHoliday())
        Assert.assertEquals(HarposHoliday.SHIELDMEET, TARSAKH.lastHoliday())
        Assert.assertEquals(HarposHoliday.GREENGRASS, MIRTUL.lastHoliday())
        Assert.assertEquals(HarposHoliday.GREENGRASS, KYTHORN.lastHoliday())
        Assert.assertEquals(HarposHoliday.GREENGRASS, FLAMERULE.lastHoliday())
        Assert.assertEquals(HarposHoliday.MIDSUMMER, ELEASIAS.lastHoliday())
        Assert.assertEquals(HarposHoliday.MIDSUMMER, ELEINT.lastHoliday())
        Assert.assertEquals(HarposHoliday.HIGHHARVESTTIDE, MARPENOTH.lastHoliday())
        Assert.assertEquals(HarposHoliday.HIGHHARVESTTIDE, UKTAR.lastHoliday())
        Assert.assertEquals(HarposHoliday.MOON_FEAST, NIGHTAL.lastHoliday())
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        val year = 1001
        Assert.assertEquals(HarposHoliday.SHIELDMEET, HAMMER.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, ALTURIAK.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, CHES.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, TARSAKH.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, MIRTUL.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, KYTHORN.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, FLAMERULE.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MOON_FEAST, MARPENOTH.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MOON_FEAST, UKTAR.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.SHIELDMEET, NIGHTAL.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        val year = 1003
        Assert.assertEquals(HarposHoliday.SHIELDMEET, HAMMER.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, ALTURIAK.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, CHES.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, TARSAKH.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, MIRTUL.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, KYTHORN.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, FLAMERULE.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MOON_FEAST, MARPENOTH.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MOON_FEAST, UKTAR.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDWINTER, NIGHTAL.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when leap year`() {
        val year = 1004
        Assert.assertEquals(HarposHoliday.MIDWINTER, HAMMER.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, ALTURIAK.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, CHES.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.GREENGRASS, TARSAKH.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, MIRTUL.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, KYTHORN.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MIDSUMMER, FLAMERULE.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.HIGHHARVESTTIDE, ELEASIAS.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.HIGHHARVESTTIDE, ELEINT.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MOON_FEAST, MARPENOTH.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.MOON_FEAST, UKTAR.nextHoliday(year))
        Assert.assertEquals(HarposHoliday.SHIELDMEET, NIGHTAL.nextHoliday(year))
    }
}