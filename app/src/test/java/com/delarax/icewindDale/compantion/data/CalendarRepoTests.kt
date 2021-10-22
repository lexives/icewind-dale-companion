package com.delarax.icewindDale.compantion.data

import com.delarax.icewindDale.companion.data.CalendarRepo
import com.delarax.icewindDale.companion.models.Calendar
import com.delarax.icewindDale.companion.models.DateConversionMode
import com.delarax.icewindDale.companion.models.exceptions.IllegalDateConversionException
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday
import com.delarax.icewindDale.companion.models.harpos.HarposMonth.*
import com.delarax.icewindDale.companion.models.harpos.toDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday
import com.delarax.icewindDale.companion.models.nunavut.NunavutSeason.*
import com.delarax.icewindDale.companion.models.nunavut.toDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CalendarRepoTests {

    private val calendarRepo = CalendarRepo()

    @Test
    fun `getNunavutFromHarpos for all harpos holidays, non-leap year`() {
        val year = 1001
        assertEquals(
            NunavutDate(day = 1, season = IGLOO, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.SHIELDMEET.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 11, season = BEATING_ICE, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.GREENGRASS.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 11, season = RUNNING_CHAR, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.MIDSUMMER.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 21, season = BARE_MOUNTAIN, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.HIGHHARVESTTIDE.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 21, season = ELK_HUNT, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.MOON_FEAST.toDate(year))
        )
    }

    @Test
    fun `getNunavutFromHarpos for all harpos holidays, leap year`() {
        val year = 1004
        assertEquals(
            NunavutHoliday.MIDWINTER.toDate(year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.MIDWINTER.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 1, season = IGLOO, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.SHIELDMEET.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 11, season = BEATING_ICE, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.GREENGRASS.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 11, season = RUNNING_CHAR, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.MIDSUMMER.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 21, season = BARE_MOUNTAIN, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.HIGHHARVESTTIDE.toDate(year))
        )
        assertEquals(
            NunavutDate(day = 21, season = ELK_HUNT, year = year),
            calendarRepo.getNunavutFromHarpos(HarposHoliday.MOON_FEAST.toDate(year))
        )
    }

    @Test
    fun `getNunavutFromHarpos for first day of each month, non-leap year`() {
        val year = 1001
        assertEquals(
            NunavutDate(day = 1, season = FALLING_STARS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = HAMMER, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 2, season = IGLOO, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = ALTURIAK, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 32, season = IGLOO, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = CHES, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 21, season = SEAL_PUPS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = TARSAKH, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 12, season = BEATING_ICE, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = MIRTUL, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 22, season = NESTING_GEESE, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = KYTHORN, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 21, season = SKIN_TENTS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = FLAMERULE, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 12, season = RUNNING_CHAR, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = ELEASIAS, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 12, season = BERRIES, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = ELEINT, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 22, season = BARE_MOUNTAIN, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = MARPENOTH, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 12, season = JARLMOOT, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = UKTAR, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 22, season = ELK_HUNT, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = NIGHTAL, year = year)
            )
        )
    }

    @Test
    fun `getNunavutFromHarpos for first day of each month, leap year`() {
        val year = 1004
        assertEquals(
            NunavutDate(day = 1, season = FALLING_STARS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = HAMMER, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 2, season = IGLOO, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = ALTURIAK, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 32, season = IGLOO, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = CHES, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 21, season = SEAL_PUPS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = TARSAKH, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 12, season = BEATING_ICE, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = MIRTUL, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 22, season = NESTING_GEESE, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = KYTHORN, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 21, season = SKIN_TENTS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = FLAMERULE, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 12, season = RUNNING_CHAR, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = ELEASIAS, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 12, season = BERRIES, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = ELEINT, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 22, season = BARE_MOUNTAIN, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = MARPENOTH, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 12, season = JARLMOOT, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = UKTAR, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 22, season = ELK_HUNT, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 1, month = NIGHTAL, year = year)
            )
        )
    }

    @Test
    fun `getNunavutFromHarpos for last day of each month, non-leap year`() {
        val year = 1001
        assertEquals(
            NunavutDate(day = 30, season = FALLING_STARS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = HAMMER, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 31, season = IGLOO, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = ALTURIAK, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = SEAL_PUPS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = CHES, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 10, season = BEATING_ICE, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = TARSAKH, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 21, season = NESTING_GEESE, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = MIRTUL, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = SKIN_TENTS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = KYTHORN, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 10, season = RUNNING_CHAR, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = FLAMERULE, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 11, season = BERRIES, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = ELEASIAS, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = BARE_MOUNTAIN, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = ELEINT, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 11, season = JARLMOOT, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = MARPENOTH, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = ELK_HUNT, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = UKTAR, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = NIGHTAL, year = year)
            )
        )
    }

    @Test
    fun `getNunavutFromHarpos for last day of each month, leap year`() {
        val year = 1004
        assertEquals(
            NunavutDate(day = 30, season = FALLING_STARS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = HAMMER, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 31, season = IGLOO, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = ALTURIAK, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = SEAL_PUPS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = CHES, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 10, season = BEATING_ICE, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = TARSAKH, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 21, season = NESTING_GEESE, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = MIRTUL, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = SKIN_TENTS, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = KYTHORN, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 10, season = RUNNING_CHAR, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = FLAMERULE, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 11, season = BERRIES, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = ELEASIAS, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = BARE_MOUNTAIN, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = ELEINT, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 11, season = JARLMOOT, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = MARPENOTH, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = ELK_HUNT, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = UKTAR, year = year)
            )
        )
        assertEquals(
            NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = year),
            calendarRepo.getNunavutFromHarpos(
                HarposDate(day = 30, month = NIGHTAL, year = year)
            )
        )
    }

    @Test
    fun `getHarposFromNunavut for all nunavut holidays, non-leap year`() {
        val year = 1001
        assertEquals(
            HarposDate(day = 10, month = CHES, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.OMINGMAK.toDate(year))
        )
        assertEquals(
            HarposDate(day = 10, month = KYTHORN, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.SUN_FESTIVAL.toDate(year))
        )
        assertEquals(
            HarposDate(day = 10, month = ELEINT, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.ALIANAT.toDate(year))
        )
        assertEquals(
            HarposDate(day = 10, month = UKTAR, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.TUNNIQAIJUK.toDate(year))
        )
        assertEquals(
            HarposDate(day = 10, month = NIGHTAL, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.MOON_FEAST.toDate(year))
        )
    }

    @Test
    fun `getHarposFromNunavut for all nunavut holidays, leap year`() {
        val year = 1004
        assertEquals(
            HarposDate(day = 10, month = CHES, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.OMINGMAK.toDate(year))
        )
        assertEquals(
            HarposDate(day = 10, month = KYTHORN, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.SUN_FESTIVAL.toDate(year))
        )
        assertEquals(
            HarposDate(day = 10, month = ELEINT, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.ALIANAT.toDate(year))
        )
        assertEquals(
            HarposDate(day = 10, month = UKTAR, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.TUNNIQAIJUK.toDate(year))
        )
        assertEquals(
            HarposDate(day = 10, month = NIGHTAL, year = year),
            calendarRepo.getHarposFromNunavut(NunavutHoliday.MOON_FEAST.toDate(year))
        )
    }

    @Test
    fun `getHarposFromNunavut for first day of each season, non-leap year`() {
        val year = 1001
        assertEquals(
            HarposDate(day = 1, month = HAMMER, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = FALLING_STARS, year = year)
            )
        )
        assertEquals(
            HarposHoliday.SHIELDMEET.toDate(year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = IGLOO, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = CHES, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = SEAL_PUPS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 21, month = TARSAKH, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = BEATING_ICE, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 10, month = MIRTUL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = NESTING_GEESE, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = KYTHORN, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = SKIN_TENTS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 21, month = FLAMERULE, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = RUNNING_CHAR, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 20, month = ELEASIAS, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = BERRIES, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = ELEINT, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = BARE_MOUNTAIN, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 20, month = MARPENOTH, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = JARLMOOT, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = UKTAR, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = ELK_HUNT, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = NIGHTAL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = year)
            )
        )
    }

    @Test
    fun `getHarposFromNunavut for first day of each season, leap year`() {
        val year = 1004
        assertEquals(
            HarposDate(day = 1, month = HAMMER, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = FALLING_STARS, year = year)
            )
        )
        assertEquals(
            HarposHoliday.SHIELDMEET.toDate(year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = IGLOO, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = CHES, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = SEAL_PUPS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 21, month = TARSAKH, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = BEATING_ICE, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 10, month = MIRTUL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = NESTING_GEESE, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = KYTHORN, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = SKIN_TENTS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 21, month = FLAMERULE, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = RUNNING_CHAR, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 20, month = ELEASIAS, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = BERRIES, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = ELEINT, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = BARE_MOUNTAIN, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 20, month = MARPENOTH, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = JARLMOOT, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = UKTAR, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = ELK_HUNT, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 11, month = NIGHTAL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 1, season = DENNING_POLAR_BEAR, year = year)
            )
        )
    }

    @Test
    fun `getHarposFromNunavut for last day of each season, non-leap year`() {
        val year = 1001
        assertEquals(
            HarposDate(day = 30, month = HAMMER, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 30, season = FALLING_STARS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = CHES, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 40, season = IGLOO, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 20, month = TARSAKH, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 40, season = SEAL_PUPS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = MIRTUL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 20, season = BEATING_ICE, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = KYTHORN, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 30, season = NESTING_GEESE, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 20, month = FLAMERULE, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 40, season = SKIN_TENTS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 19, month = ELEASIAS, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 30, season = RUNNING_CHAR, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = ELEINT, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 20, season = BERRIES, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 19, month = MARPENOTH, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 40, season = BARE_MOUNTAIN, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = UKTAR, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 20, season = JARLMOOT, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = NIGHTAL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 30, season = ELK_HUNT, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 30, month = NIGHTAL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = year)
            )
        )
    }

    @Test
    fun `getHarposFromNunavut for last day of each season, leap year`() {
        val year = 1004
        assertEquals(
            HarposDate(day = 30, month = HAMMER, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 30, season = FALLING_STARS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = CHES, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 40, season = IGLOO, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 20, month = TARSAKH, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 40, season = SEAL_PUPS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = MIRTUL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 20, season = BEATING_ICE, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = KYTHORN, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 30, season = NESTING_GEESE, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 20, month = FLAMERULE, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 40, season = SKIN_TENTS, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 19, month = ELEASIAS, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 30, season = RUNNING_CHAR, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = ELEINT, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 20, season = BERRIES, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 19, month = MARPENOTH, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 40, season = BARE_MOUNTAIN, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = UKTAR, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 20, season = JARLMOOT, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 9, month = NIGHTAL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 30, season = ELK_HUNT, year = year)
            )
        )
        assertEquals(
            HarposDate(day = 30, month = NIGHTAL, year = year),
            calendarRepo.getHarposFromNunavut(
                NunavutDate(day = 20, season = DENNING_POLAR_BEAR, year = year)
            )
        )
    }

    @Test(expected = IllegalDateConversionException::class)
    fun `convertDate throws error if converting from harpos but cast fails`() {
        calendarRepo.convertDate(
            NunavutDate.fromAbsoluteDayNumber(1, 1001),
            DateConversionMode(from = Calendar.HARPOS, to = Calendar.NUNAVUT)
        )
    }

    @Test
    fun `convertDate gives the same date if converting from harpos to harpos`() {
        val date = HarposDate.fromAbsoluteDayNumber(1, 1001)
        assertEquals(
            date,
            calendarRepo.convertDate(
                date,
                DateConversionMode(from = Calendar.HARPOS, to = Calendar.HARPOS)
            )
        )
    }

    @Test
    fun `convertDate successfully converts from harpos to nunavut`() {
        assertEquals(
            NunavutDate.fromAbsoluteDayNumber(1, 1001),
            calendarRepo.convertDate(
                HarposDate.fromAbsoluteDayNumber(1, 1001),
                DateConversionMode(from = Calendar.HARPOS, to = Calendar.NUNAVUT)
            )
        )
    }

    @Test(expected = IllegalDateConversionException::class)
    fun `convertDate throws error if converting from nunavut but cast fails`() {
        calendarRepo.convertDate(
            HarposDate.fromAbsoluteDayNumber(1, 1001),
            DateConversionMode(from = Calendar.NUNAVUT, to = Calendar.HARPOS)
        )
    }

    @Test
    fun `convertDate gives the same date if converting from nunavut to nunavut`() {
        val date = NunavutDate.fromAbsoluteDayNumber(1, 1001)
        assertEquals(
            date,
            calendarRepo.convertDate(
                date,
                DateConversionMode(from = Calendar.NUNAVUT, to = Calendar.NUNAVUT)
            )
        )
    }

    @Test
    fun `convertDate successfully converts from nunavut to harpos`() {
        assertEquals(
            HarposDate.fromAbsoluteDayNumber(1, 1001),
            calendarRepo.convertDate(
                NunavutDate.fromAbsoluteDayNumber(1, 1001),
                DateConversionMode(from = Calendar.NUNAVUT, to = Calendar.HARPOS)
            )
        )
    }
}