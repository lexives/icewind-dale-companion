package com.delarax.icewindDale.compantion.models.harpos

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.*
import com.delarax.icewindDale.companion.models.harpos.nextHoliday
import com.delarax.icewindDale.companion.models.harpos.priorHoliday
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
}