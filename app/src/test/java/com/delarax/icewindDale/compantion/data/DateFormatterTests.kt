package com.delarax.icewindDale.compantion.data

import com.delarax.icewindDale.companion.data.DateFormatter
import com.delarax.icewindDale.companion.models.harpos.*
import com.delarax.icewindDale.companion.models.nunavut.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DateFormatterTests {

    private val dateFormatter = DateFormatter()
    private val harposDayTwo = HarposDate(day = 2, month = HarposMonth.KYTHORN, year = 1234)
    private val harposDayTwelve = HarposDate(day = 12, month = HarposMonth.KYTHORN, year = 1234)
    private val harposHoliday = HarposHoliday.MOON_FEAST.toDate(1234)
    private val nunavutDayTwo =
        NunavutDate(day = 2, season = NunavutSeason.SKIN_TENTS, year = 1234)
    private val nunavutDayTwelve =
        NunavutDate(day = 12, season = NunavutSeason.SKIN_TENTS, year = 1234)
    private val nunavutHoliday = NunavutHoliday.MOON_FEAST.toDate(1234)

    @Test
    fun `formatDate formats a valid harpos non-holiday`() {
        assertEquals(
            "6.2.1234 DR",
            DateFormatter.formatDate(HarposDateFormat.STANDARD, harposDayTwo)
        )
    }

    @Test
    fun `formatDate formats a valid harpos holiday`() {
        assertEquals(
            "The Feast of the Moon 1234 DR",
            DateFormatter.formatDate(HarposDateFormat.STANDARD, harposHoliday)
        )
    }

    @Test
    fun `formatDate formats a valid nunavut non-holiday`() {
        assertEquals(
            "2.6.1234",
            DateFormatter.formatDate(NunavutDateFormat.STANDARD, nunavutDayTwo)
        )
    }

    @Test
    fun `formatDate formats a valid nunavut holiday`() {
        assertEquals(
            "The Feast of the Moon, 1234",
            DateFormatter.formatDate(NunavutDateFormat.STANDARD, nunavutHoliday)
        )
    }

    @Test
    fun `formatDate just converts to a string if the date is not valid`() {
        assertEquals(
            "HarposDate(day=1, month=KYTHORN, year=1234, holiday=MOON_FEAST)",
            DateFormatter.formatDate(
                HarposDateFormat.STANDARD,
                HarposDate(
                    day = 1,
                    month = HarposMonth.KYTHORN,
                    year = 1234,
                    holiday = HarposHoliday.MOON_FEAST
                )
            )
        )
        assertEquals(
            "NunavutDate(day=1, season=SKIN_TENTS, year=1234, holiday=MOON_FEAST)",
            DateFormatter.formatDate(
                NunavutDateFormat.STANDARD,
                NunavutDate(
                    day = 1,
                    season = NunavutSeason.SKIN_TENTS,
                    year = 1234,
                    holiday = NunavutHoliday.MOON_FEAST
                )
            )
        )
    }

    @Test
    fun `formatDateFromPattern returns the pattern if it has no formatting symbols`() {
        val pattern = "b.o.p"
        assertEquals(pattern, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))
        assertEquals(pattern, dateFormatter.formatDateFromPattern(pattern, harposHoliday))
        assertEquals(pattern, dateFormatter.formatDateFromPattern(pattern, nunavutDayTwo))
        assertEquals(pattern, dateFormatter.formatDateFromPattern(pattern, nunavutHoliday))
    }

    @Test
    fun `formatDateFromPattern can match all harpos symbols`() {
        var pattern = "D d dd m mm MF MS R y"
        var expectedString = "2nd 2 02 6 06 The Time of Flowers Kythorn DR 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))
        expectedString = "12th 12 12 6 06 The Time of Flowers Kythorn DR 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwelve))

        pattern = "D d dd H R y"
        expectedString = "1st 1 01 The Feast of the Moon DR 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposHoliday))
    }

    @Test
    fun `formatDateFromPattern can match all nunavut symbols`() {
        var pattern = "D d dd s ss SF SA y"
        var expectedString = "2nd 2 02 6 06 Season of the Skin Tents ST 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, nunavutDayTwo))
        expectedString = "12th 12 12 6 06 Season of the Skin Tents ST 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, nunavutDayTwelve))

        pattern = "D d dd HF HA y"
        expectedString = "1st 1 01 The Feast of the Moon FM 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, nunavutHoliday))
    }

    @Test
    fun `formatDateFromPattern skips nunavut symbols when formatting a harpos date`() {
        var pattern = "s ss SF SA"
        assertEquals(pattern, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))

        pattern = "HF HA"
        assertEquals(pattern, dateFormatter.formatDateFromPattern(pattern, harposHoliday))
    }

    @Test
    fun `formatDateFromPattern skips harpos symbols when formatting a nunavut date`() {
        var pattern = "m mm MF MS R"
        assertEquals(pattern, dateFormatter.formatDateFromPattern(pattern, nunavutDayTwo))

        pattern = "H"
        assertEquals(pattern, dateFormatter.formatDateFromPattern(pattern, nunavutHoliday))
    }

    @Test
    fun `formatDateFromPattern skips harpos symbols if escaped with a backslash`() {
        var pattern = "\\D \\d \\dd \\m \\mm \\MF \\MS \\R \\y"
        var expectedString = "D d dd m mm MF MS R y"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))

        pattern = "\\d \\m y"
        expectedString = "d m 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))

        pattern = "\\D \\d \\dd \\H \\R \\y"
        expectedString = "D d dd H R y"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposHoliday))
    }

    @Test
    fun `formatDateFromPattern skips nunavut symbols if escaped with a backslash`() {
        var pattern = "\\D \\d \\dd \\s \\ss \\SF \\SA \\y"
        var expectedString = "D d dd s ss SF SA y"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, nunavutDayTwo))

        pattern = "\\d \\m y"
        expectedString = "d m 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, nunavutDayTwo))

        pattern = "\\D \\d \\dd \\HF \\HA \\y"
        expectedString = "D d dd HF HA y"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, nunavutHoliday))
    }

    @Test
    fun `formatDateFromPattern skips harpos holiday symbols if date is not a holiday`() {
        var pattern = "D d dd m mm MF MS R y"
        var expectedString = "1st 1 01 m mm MF MS DR 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposHoliday))

        pattern = "D d dd H R y"
        expectedString = "2nd 2 02 H DR 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))
    }

    @Test
    fun `formatDateFromPattern skips harpos month symbols if date is not in a month`() {
        var pattern = "D d dd s ss SF SA y"
        var expectedString = "1st 1 01 s ss SF SA 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, nunavutHoliday))

        pattern = "D d dd HF HA y"
        expectedString = "2nd 2 02 HF HA 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, nunavutDayTwo))
    }

    @Test
    fun `formatDateFromPattern retains spaces before and after symbol`() {
        var pattern = " y"
        var expectedString = " 1234"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))

        pattern = "y "
        expectedString = "1234 "
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))

        pattern = " y "
        expectedString = " 1234 "
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))
    }

    @Test
    fun `formatDateFromPattern retains spaces before and after escaped symbol`() {
        var pattern = " \\y"
        var expectedString = " y"
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))

        pattern = "\\y "
        expectedString = "y "
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))

        pattern = " \\y "
        expectedString = " y "
        assertEquals(expectedString, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))
    }

    @Test
    fun `formatDateFromPattern formats harpos date with other text in the pattern`() {
        var pattern = "d \\da\\y\\s into MF, y R"
        var expectedResult = "2 days into The Time of Flowers, 1234 DR"
        assertEquals(expectedResult, dateFormatter.formatDateFromPattern(pattern, harposDayTwo))

        pattern = "To\\da\\y i\\s a holi\\da\\y! H, y R"
        expectedResult = "Today is a holiday! The Feast of the Moon, 1234 DR"
        assertEquals(expectedResult, dateFormatter.formatDateFromPattern(pattern, harposHoliday))
    }

    @Test
    fun `formatDateFromPattern formats nunavut date with other text in the pattern`() {
        var pattern = "The D \\Da\\y of the SF, Year y"
        var expectedResult = "The 2nd Day of the Season of the Skin Tents, Year 1234"
        assertEquals(expectedResult, dateFormatter.formatDateFromPattern(pattern, nunavutDayTwo))

        pattern = "To\\da\\y i\\s a holi\\da\\y! HF, Year y"
        expectedResult = "Today is a holiday! The Feast of the Moon, Year 1234"
        assertEquals(expectedResult, dateFormatter.formatDateFromPattern(pattern, nunavutHoliday))
    }
}