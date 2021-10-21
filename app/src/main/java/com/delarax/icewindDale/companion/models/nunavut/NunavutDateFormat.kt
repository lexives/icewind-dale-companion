package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.models.DateFormat

/**
 * Roughly based on DateTimeFormatter for Java's LocalDataTime:
 * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
 *
 * Symbol   | Description               | Example
 * --------------------------------------------------------
 * y        | Year                      | 1234
 * SA       | Season abbreviation       | ST
 * SF       | Season full name          | Season of the Skin Tents
 * s, ss    | Season number             | 6, 06
 * d, dd    | Day number                | 2, 02
 * D        | Day number with suffix    | 2nd
 * HA       | Holiday abbreviation      | FS
 * HF       | Holiday full name         | The Festival of the Sun
 */
enum class NunavutDateFormat(
    override val pattern: String,
    override val holidayPattern: String
) : DateFormat {
    // 2.6.1234 / FS.1234
    SHORT("d.s.y", "HA.y"),

    // 2.ST.1234 / FS.1234
    SHORT_ALTERNATE("d.SA.y", "HA.y"),

    // 02.06.1234 / FS.1234
    SHORT_FULL_NUMBERS("dd.ss.y", "HA.y"),

    // 02.ST.1234 / FS.1234
    SHORT_ALTERNATE_FULL_NUMBERS("dd.ss.y", "HA.y"),

    // 2.6.1234 / The Festival of the Sun, 1234
    STANDARD("d.s.y", "HF, y"),

    // 02.06.1234 / The Festival of the Sun, 1234
    STANDARD_FULL_NUMBERS("dd.ss.y", "HF, y"),

    // The 2nd Day of the Season of the Skin Tents, Year 1234 /The Festival of the Sun, Year 1234
    WRITTEN("The D Day of the SF Year y", "HF year y")
}