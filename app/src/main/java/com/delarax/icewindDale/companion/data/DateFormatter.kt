package com.delarax.icewindDale.companion.data

import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.DAY_NUM
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.DAY_NUM_FULL
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.DAY_WRITTEN
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.HOLIDAY_ABBREVIATION
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.HOLIDAY_FULL
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.HOLIDAY_NAME
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.MONTH_FULL
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.MONTH_NUM
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.MONTH_NUM_FULL
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.MONTH_SHORT
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.RECKONING
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.SEASON_ABBREVIATION
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.SEASON_FULL
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.SEASON_NUM
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.SEASON_NUM_FULL
import com.delarax.icewindDale.companion.data.DateFormatter.Symbol.YEAR
import com.delarax.icewindDale.companion.extensions.enumCaseToTitleCase
import com.delarax.icewindDale.companion.extensions.leadingZeros
import com.delarax.icewindDale.companion.extensions.toStringOrNull
import com.delarax.icewindDale.companion.extensions.toStringWithSuffix
import com.delarax.icewindDale.companion.extensions.toTriple
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.models.DateFormat
import com.delarax.icewindDale.companion.models.exceptions.InvalidDateFormatException
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.harpos.num
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import com.delarax.icewindDale.companion.models.nunavut.num

class DateFormatter {

    companion object {
        fun formatDate(format: DateFormat, date: Date): String = when {
            !date.isValid -> date.toString()
            date.isHoliday -> DateFormatter().formatDateFromPattern(format.holidayPattern, date)
            else -> DateFormatter().formatDateFromPattern(format.pattern, date)
        }
    }

    fun formatDateFromPattern(pattern: String, date: Date) : String {
        val listOfChars = pattern.toList()

        // find the first instance of a valid symbol and how many characters long it is.
        // skips over a symbol if it's preceded by a '\'
        var indexAndLength: Pair<Int, Int>? = null
        var i = 0
        while (i < listOfChars.size && indexAndLength == null){
            val charAsString = listOfChars[i].toString()
            val nextChar = listOfChars.getOrNull(i + 1) ?: ""
            val bothChars = charAsString + nextChar.toString()
            val isEscaped = listOfChars.getOrNull(i - 1) == '\\'

            if (isDoubleCharSymbol(bothChars)) {
                if (isEscaped) {
                    i += 2
                } else {
                    indexAndLength = i to 2
                }

            } else if (isSingleCharSymbol(charAsString)) {
                if (isEscaped) {
                    i += 1
                } else {
                    indexAndLength = i to 1
                }
            } else {
                i += 1
            }
        }

        // if it didn't find a symbol, return formatString
        return indexAndLength?.let { (i, len) ->
            // split the string into [pre-symbol, symbol, post-symbol]
            val splitString = pattern.toTriple(i, len)

            // combine the three sections by parsing the middle section and calling
            // formatDate on the remaining string
            return splitString.first.replace("\\", "") +
                    parseSymbol(splitString.second, date) +
                    formatDateFromPattern(splitString.third, date)
        } ?: pattern.replace("\\", "")
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
        MONTH_FULL.value,
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
        RECKONING.value -> { (date as? HarposDate)?.reckoning ?: symbol }
        MONTH_SHORT.value -> {
            (date as? HarposDate)?.month?.name?.enumCaseToTitleCase() ?: symbol
        }
        MONTH_FULL.value -> {
            (date as? HarposDate)?.month?.commonName ?: symbol
        }
        MONTH_NUM.value -> {
            (date as? HarposDate)?.month?.num().toStringOrNull() ?: symbol
        }
        MONTH_NUM_FULL.value -> {
            (date as? HarposDate)?.month?.num()?.leadingZeros(2) ?: symbol
        }
        SEASON_ABBREVIATION.value -> {
            (date as? NunavutDate)?.season?.abbreviation?.name ?: symbol
        }
        SEASON_FULL.value -> {
            (date as? NunavutDate)?.season?.fullName ?: symbol
        }
        SEASON_NUM.value -> {
            (date as? NunavutDate)?.season?.num().toStringOrNull() ?: symbol
        }
        SEASON_NUM_FULL.value -> {
            (date as? NunavutDate)?.season?.num()?.leadingZeros(2) ?: symbol
        }
        DAY_NUM.value -> { date.day.toString() }
        DAY_NUM_FULL.value -> { date.day.leadingZeros(2) }
        DAY_WRITTEN.value -> { date.day.toStringWithSuffix() }
        HOLIDAY_NAME.value -> {
            (date as? HarposDate)?.holiday?.fullName ?: symbol
        }
        HOLIDAY_ABBREVIATION.value -> {
            (date as? NunavutDate)?.holiday?.abbreviation?.name ?: symbol
        }
        HOLIDAY_FULL.value -> {
            (date as? NunavutDate)?.holiday?.fullName ?: symbol
        }
        else -> symbol
    }

    private enum class Symbol(val value: String){
        YEAR("y"),
        RECKONING("R"),
        MONTH_SHORT("MS"),
        MONTH_FULL("MF"),
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