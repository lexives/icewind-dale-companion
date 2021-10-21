package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.models.DateFormat

/**
 * Roughly based on DateTimeFormatter for Java's LocalDataTime:
 * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
 *
 * Symbol   | Description               | Example
 * --------------------------------------------------------
 * y        | Year                      | 1234
 * R        | Reckoning                 | DR
 * MS       | Month short name          | Kythorn
 * MF       | Month full name           | The Time of Flowers
 * m, mm    | Month number              | 6, 06
 * d, dd    | Day number                | 2, 02
 * D        | Day number with suffix    | 2nd
 * H        | Holiday name              | Greengrass
 */
enum class HarposDateFormat(
    override val pattern: String,
    override val holidayPattern: String
) : DateFormat {
    // 6.2.1234 DR / Greengrass 1234 DR
    STANDARD("m.d.y R", "H y R"),

    // 06.02.1234 DR / Greengrass 1234 DR
    FULL_NUMBERS("mm.dd.y R", "H y R"),

    // Kythorn the 2nd, 1234 DR / Greengrass, 1234 DR
    WRITTEN("MS the D, y R", "H, y R"),

    // 2 days into The Time of Flowers, 1234 DR / Greengrass, 1234 DR
    WRITTEN_ALTERNATE("d days into MF, y R", "H, y R"),
}