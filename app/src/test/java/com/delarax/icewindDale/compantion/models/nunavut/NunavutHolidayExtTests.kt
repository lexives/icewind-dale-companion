package com.delarax.icewindDale.compantion.models.nunavut

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.nunavut.*
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.*
import org.junit.Assert
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
        Assert.assertEquals(MOON_FEAST, OMINGMAK.priorHoliday(year))
        Assert.assertEquals(OMINGMAK, SUN_FESTIVAL.priorHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, ALIANAT.priorHoliday(year))
        Assert.assertEquals(ALIANAT, TUNNIQAIJUK.priorHoliday(year))
        Assert.assertEquals(TUNNIQAIJUK, MOON_FEAST.priorHoliday(year))
    }

    @Test
    fun `priorHoliday returns correct holiday when not leap year and last year was leap year`() {
        val year = 1001
        Assert.assertEquals(MOON_FEAST, OMINGMAK.priorHoliday(year))
        Assert.assertEquals(OMINGMAK, SUN_FESTIVAL.priorHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, ALIANAT.priorHoliday(year))
        Assert.assertEquals(ALIANAT, TUNNIQAIJUK.priorHoliday(year))
        Assert.assertEquals(TUNNIQAIJUK, MOON_FEAST.priorHoliday(year))
    }

    @Test
    fun `priorHoliday returns correct holiday when leap year`() {
        val year = 1004
        Assert.assertEquals(MOON_FEAST, MIDWINTER.priorHoliday(year))
        Assert.assertEquals(MIDWINTER, OMINGMAK.priorHoliday(year))
        Assert.assertEquals(OMINGMAK, SUN_FESTIVAL.priorHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, ALIANAT.priorHoliday(year))
        Assert.assertEquals(ALIANAT, TUNNIQAIJUK.priorHoliday(year))
        Assert.assertEquals(TUNNIQAIJUK, MOON_FEAST.priorHoliday(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `nextHoliday throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.nextHoliday(1001)
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is not leap year`() {
        val year = 1001
        Assert.assertEquals(SUN_FESTIVAL, OMINGMAK.nextHoliday(year))
        Assert.assertEquals(ALIANAT, SUN_FESTIVAL.nextHoliday(year))
        Assert.assertEquals(TUNNIQAIJUK, ALIANAT.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, TUNNIQAIJUK.nextHoliday(year))
        Assert.assertEquals(OMINGMAK, MOON_FEAST.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when not leap year and next year is leap year`() {
        val year = 1003
        Assert.assertEquals(SUN_FESTIVAL, OMINGMAK.nextHoliday(year))
        Assert.assertEquals(ALIANAT, SUN_FESTIVAL.nextHoliday(year))
        Assert.assertEquals(TUNNIQAIJUK, ALIANAT.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, TUNNIQAIJUK.nextHoliday(year))
        Assert.assertEquals(MIDWINTER, MOON_FEAST.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday when leap year`() {
        val year = 1004
        Assert.assertEquals(OMINGMAK, MIDWINTER.nextHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, OMINGMAK.nextHoliday(year))
        Assert.assertEquals(ALIANAT, SUN_FESTIVAL.nextHoliday(year))
        Assert.assertEquals(TUNNIQAIJUK, ALIANAT.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, TUNNIQAIJUK.nextHoliday(year))
        Assert.assertEquals(OMINGMAK, MOON_FEAST.nextHoliday(year))
    }

    @Test(expected = InvalidDateException::class)
    fun `numHolidaysPassed throws an error if the holiday could not have occurred that year`() {
        MIDWINTER.numHolidaysPassed(1001)
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when not leap year`() {
        val year = 1001
        Assert.assertEquals(1, OMINGMAK.numHolidaysPassed(year))
        Assert.assertEquals(2, SUN_FESTIVAL.numHolidaysPassed(year))
        Assert.assertEquals(3, ALIANAT.numHolidaysPassed(year))
        Assert.assertEquals(4, TUNNIQAIJUK.numHolidaysPassed(year))
        Assert.assertEquals(5, MOON_FEAST.numHolidaysPassed(year))
    }

    @Test
    fun `numHolidaysPassed returns correct number on holiday when leap year`() {
        val year = 1004
        Assert.assertEquals(1, MIDWINTER.numHolidaysPassed(year))
        Assert.assertEquals(2, OMINGMAK.numHolidaysPassed(year))
        Assert.assertEquals(3, SUN_FESTIVAL.numHolidaysPassed(year))
        Assert.assertEquals(4, ALIANAT.numHolidaysPassed(year))
        Assert.assertEquals(5, TUNNIQAIJUK.numHolidaysPassed(year))
        Assert.assertEquals(6, MOON_FEAST.numHolidaysPassed(year))
    }
}