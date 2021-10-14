package com.delarax.icewindDale.compantion.models.harpos

import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.*
import com.delarax.icewindDale.companion.models.harpos.HarposMonth.*
import com.delarax.icewindDale.companion.models.harpos.lastHoliday
import com.delarax.icewindDale.companion.models.harpos.nextHoliday
import com.delarax.icewindDale.companion.models.harpos.nextMonth
import com.delarax.icewindDale.companion.models.harpos.priorMonth
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
}