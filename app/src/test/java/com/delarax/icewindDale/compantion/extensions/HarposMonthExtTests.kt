package com.delarax.icewindDale.compantion.extensions

import com.delarax.icewindDale.companion.models.harpos.*
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.*
import com.delarax.icewindDale.companion.models.harpos.HarposMonth.*
import org.junit.Assert.assertEquals
import org.junit.Test

class HarposMonthExtTests {

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
    fun `lastHoliday returns correct holiday for not leap year`() {
        val year = 1001
        assertEquals(MOON_FEAST, HAMMER.lastHoliday(year))
        assertEquals(SHIELDMEET, ALTURIAK.lastHoliday(year))
        assertEquals(SHIELDMEET, CHES.lastHoliday(year))
        assertEquals(SHIELDMEET, TARSAKH.lastHoliday(year))
        assertEquals(GREENGRASS, MIRTUL.lastHoliday(year))
        assertEquals(GREENGRASS, KYTHORN.lastHoliday(year))
        assertEquals(GREENGRASS, FLAMERULE.lastHoliday(year))
        assertEquals(MIDSUMMER, ELEASIAS.lastHoliday(year))
        assertEquals(MIDSUMMER, ELEINT.lastHoliday(year))
        assertEquals(HIGHHARVESTTIDE, MARPENOTH.lastHoliday(year))
        assertEquals(HIGHHARVESTTIDE, UKTAR.lastHoliday(year))
        assertEquals(MOON_FEAST, NIGHTAL.lastHoliday(year))
    }

    @Test
    fun `lastHoliday returns correct holiday for leap year`() {
        val year = 1004
        assertEquals(MOON_FEAST, HAMMER.lastHoliday(year))
        assertEquals(SHIELDMEET, ALTURIAK.lastHoliday(year))
        assertEquals(SHIELDMEET, CHES.lastHoliday(year))
        assertEquals(SHIELDMEET, TARSAKH.lastHoliday(year))
        assertEquals(GREENGRASS, MIRTUL.lastHoliday(year))
        assertEquals(GREENGRASS, KYTHORN.lastHoliday(year))
        assertEquals(GREENGRASS, FLAMERULE.lastHoliday(year))
        assertEquals(MIDSUMMER, ELEASIAS.lastHoliday(year))
        assertEquals(MIDSUMMER, ELEINT.lastHoliday(year))
        assertEquals(HIGHHARVESTTIDE, MARPENOTH.lastHoliday(year))
        assertEquals(HIGHHARVESTTIDE, UKTAR.lastHoliday(year))
        assertEquals(MOON_FEAST, NIGHTAL.lastHoliday(year))
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

    @Test
    fun `numHolidaysPassed returns correct number for non-leap year`() {
        val year = 1001
        assertEquals(0, HAMMER.numHolidaysPassed(year))
        assertEquals(1, ALTURIAK.numHolidaysPassed(year))
        assertEquals(1, CHES.numHolidaysPassed(year))
        assertEquals(1, TARSAKH.numHolidaysPassed(year))
        assertEquals(2, MIRTUL.numHolidaysPassed(year))
        assertEquals(2, KYTHORN.numHolidaysPassed(year))
        assertEquals(2, FLAMERULE.numHolidaysPassed(year))
        assertEquals(3, ELEASIAS.numHolidaysPassed(year))
        assertEquals(3, ELEINT.numHolidaysPassed(year))
        assertEquals(4, MARPENOTH.numHolidaysPassed(year))
        assertEquals(4, UKTAR.numHolidaysPassed(year))
        assertEquals(5, NIGHTAL.numHolidaysPassed(year))
    }

    @Test
    fun `numHolidaysPassed returns correct number for leap year`() {
        val year = 1004
        assertEquals(0, HAMMER.numHolidaysPassed(year))
        assertEquals(2, ALTURIAK.numHolidaysPassed(year))
        assertEquals(2, CHES.numHolidaysPassed(year))
        assertEquals(2, TARSAKH.numHolidaysPassed(year))
        assertEquals(3, MIRTUL.numHolidaysPassed(year))
        assertEquals(3, KYTHORN.numHolidaysPassed(year))
        assertEquals(3, FLAMERULE.numHolidaysPassed(year))
        assertEquals(4, ELEASIAS.numHolidaysPassed(year))
        assertEquals(4, ELEINT.numHolidaysPassed(year))
        assertEquals(5, MARPENOTH.numHolidaysPassed(year))
        assertEquals(5, UKTAR.numHolidaysPassed(year))
        assertEquals(6, NIGHTAL.numHolidaysPassed(year))
    }
}