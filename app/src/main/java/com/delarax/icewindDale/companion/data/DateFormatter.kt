package com.delarax.icewindDale.companion.data

import android.util.Log
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.*
import com.delarax.icewindDale.companion.extensions.*
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.models.DateFormat
import com.delarax.icewindDale.companion.models.exceptions.InvalidDateFormatException
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.harpos.num
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import com.delarax.icewindDale.companion.models.nunavut.num

class DateFormatter {

    companion object {
        fun formatDate(format: DateFormat, date: Date, isHoliday: Boolean): String = when {
            !date.isValid -> date.toString()
            isHoliday -> DateFormatter().formatDateFromPattern(format.holidayPattern, date)
            else -> DateFormatter().formatDateFromPattern(format.pattern, date)
        }
    }

    // TODO: allow escaping characters
    fun formatDateFromPattern(pattern: String, date: Date) : String {
        val listOfChars = pattern.toList()

        // find the first instance of a valid symbol and how many characters long it is
        var indexAndLength: Pair<Int, Int> = -1 to 0

        for (i in 0..listOfChars.lastIndex) {
            val charAsString = listOfChars[i].toString()
            val nextChar = listOfChars.getOrNull(i + 1) ?: ""
            val bothChars = charAsString + nextChar.toString()

            if (isDoubleCharSymbol(bothChars)) {
                indexAndLength = i to 2
                break
            } else if (isSingleCharSymbol(charAsString)) {
                indexAndLength = i to 1
                break
            }
        }

        // if it didn't find a symbol, return formatString
        if (indexAndLength.first == -1) {
            return pattern
        }

        // split the string into [pre-symbol, symbol, post-symbol]
        val splitString = pattern.toTriple(indexAndLength.first, indexAndLength.second)
        
        // combine the three sections by parsing the middle section and calling
        // formatDate on the remaining string
//        return try {
//            splitString.first +
//                    parseSymbol(splitString.second, date) +
//                    formatDateFromPattern(splitString.third, date)
//        } catch (e: Throwable) {
//            Log.e(
//                "DATE_FORMATTER",
//                e.message ?: "Unknown error while formatting date from pattern '$pattern'"
//            )
//            pattern
//        }
        /** TODO( log didn't work. also, the error message was
        * "'s' is not a valid symbol for formatting a NunavutDate"
        * When it should have been
        * "Tried to format symbol 's' but date did not have a season")
        **/
        return splitString.first +
                parseSymbol(splitString.second, date) +
                formatDateFromPattern(splitString.third, date)
    }

    private fun isSingleCharSymbol(string: String) : Boolean = when (string) {
        YEAR.value,
        RECKONING.value,
        MONTH_NUM.value,
        SEASON_NUM.value,
        DAY_NUM.value,
        DAY_WRITTEN.value,
        HOLIDAY_NAME.value -> true
        else -> false
    }

    private fun isDoubleCharSymbol(string: String) : Boolean = when (string) {
        MONTH_SHORT.value,
        MONTH_LONG.value,
        MONTH_NUM_FULL.value,
        SEASON_ABBREVIATION.value,
        SEASON_FULL.value,
        SEASON_NUM_FULL.value,
        DAY_NUM_FULL.value,
        HOLIDAY_ABBREVIATION.value,
        HOLIDAY_FULL.value -> true
        else -> false
    }

    @Throws(InvalidDateFormatException::class)
    private fun parseSymbol(symbol: String, date: Date) : String = when (symbol) {
        YEAR.value -> { date.year.toString() }
        RECKONING.value -> { harpos(date, symbol).reckoning }
        MONTH_SHORT.value -> {
            harpos(date, symbol).month?.name?.enumCaseToTitleCase()
                ?: throw harposMonthError(symbol)
        }
        MONTH_LONG.value -> {
            harpos(date, symbol).month?.commonName
                ?: throw harposMonthError(symbol)
        }
        MONTH_NUM.value -> {
            harpos(date, symbol).month?.num().toStringOrNull()
                ?: throw harposMonthError(symbol)
        }
        MONTH_NUM_FULL.value -> {
            harpos(date, symbol).month?.num()?.leadingZeros(2)
                ?: throw harposMonthError(symbol)
        }
        SEASON_ABBREVIATION.value -> {
            nunavut(date, symbol).season?.abbreviation?.name
                ?: throw nunavutSeasonError(symbol)
        }
        SEASON_FULL.value -> {
            nunavut(date, symbol).season?.fullName
                ?: throw nunavutSeasonError(symbol)
        }
        SEASON_NUM.value -> {
            nunavut(date, symbol).season?.num().toStringOrNull()
                ?: throw nunavutSeasonError(symbol)
        }
        SEASON_NUM_FULL.value -> {
            nunavut(date, symbol).season?.num()?.leadingZeros(2)
                ?: throw nunavutSeasonError(symbol)
        }
        DAY_NUM.value -> { date.day.toString() }
        DAY_NUM_FULL.value -> { date.day.leadingZeros(2) }
        DAY_WRITTEN.value -> { date.day.toStringWithSuffix() }
        HOLIDAY_NAME.value -> {
            harpos(date, symbol).holiday?.fullName
                ?: throw holidayError(symbol)
        }
        HOLIDAY_ABBREVIATION.value -> {
            nunavut(date, symbol).holiday?.abbreviation?.name
                ?: throw holidayError(symbol)
        }
        HOLIDAY_FULL.value -> {
            nunavut(date, symbol).holiday?.fullName
                ?: throw holidayError(symbol)
        }
        else -> throw InvalidDateFormatException("Invalid symbol: '$symbol'")
    }

    @Throws(InvalidDateFormatException::class)
    private fun harpos(date: Date, symbol: String) : HarposDate = when (date) {
        is HarposDate -> date
        else -> throw InvalidDateFormatException(
            "'$symbol' is not a valid symbol for formatting a NunavutDate"
        )
    }

    @Throws(InvalidDateFormatException::class)
    private fun nunavut(date: Date, symbol: String) : NunavutDate = when (date) {
        is NunavutDate -> date
        else -> throw InvalidDateFormatException(
            "'$symbol' is not a valid symbol for formatting a NunavutDate"
        )
    }

    private fun harposMonthError(symbol: String) = InvalidDateFormatException(
            "Tried to format symbol '$symbol' but date did not have a month"
    )

    private fun nunavutSeasonError(symbol: String) = InvalidDateFormatException(
        "Tried to format symbol '$symbol' but date did not have a season"
    )

    private fun holidayError(symbol: String) = InvalidDateFormatException(
        "Tried to format symbol '$symbol' but date did not have a holiday"
    )

    enum class Symbol(val value: String){
        YEAR("y"),
        RECKONING("R"),
        MONTH_SHORT("MS"),
        MONTH_LONG("ML"),
        MONTH_NUM("m"),
        MONTH_NUM_FULL("mm"),
        SEASON_ABBREVIATION("SA"),
        SEASON_FULL("SF"),
        SEASON_NUM("s"),
        SEASON_NUM_FULL("ss"),
        DAY_NUM("d"),
        DAY_NUM_FULL("dd"),
        DAY_WRITTEN("D"),
        HOLIDAY_NAME("H"),
        HOLIDAY_ABBREVIATION("HA"),
        HOLIDAY_FULL("HF")
    }
}