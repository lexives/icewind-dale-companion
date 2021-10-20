package com.delarax.icewindDale.companion.models

/**
 * Roughly based on DateTimeFormatter for Java's LocalDataTime:
 * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
 *
 * Symbol   | Description               | Example
 * --------------------------------------------------------
 * y        | Year                      | 1234
 * R        | Reckoning                 | DR
 * MS       | Month/Season short name   | Kythorn/ST
 * MF       | Month/Season full name    | The Time of Flowers/Season of the Skin Tents
 * m, mm    | Month/Season number       | 6, 06
 * d, dd    | Day number                | 2, 02
 * D        | Day number with suffix    | 2nd
 * HS       | Holiday short name        | Greengrass, FS
 * HF       | Holiday full name         | Greengrass, The Festival of the Sun
 */

interface DateFormat {
    val pattern: String
    val holidayPattern: String
}

enum class HarposDateFormat(
    override val pattern: String,
    override val holidayPattern: String
) : DateFormat{
    STANDARD("m.d.y R", "HS y R"),                          // 6.2.1234 DR / Greengrass 1234 DR
    FULL_NUMBERS("mm.dd.y R", "HS y R"),                    // 06.02.1234 DR / Greengrass 1234 DR
    WRITTEN("MS the D, y R", "HS, y R"),                    // Kythorn the 2nd, 1234 DR /
                                                            // Greengrass, 1234 DR
    WRITTEN_ALTERNATE("d days into MF, y R", "HS, y R"),    // 2 days into The Time of Flowers,
                                                            // 1234 DR / Greengrass, 1234 DR
}

enum class NunavutDateFormat(
    override val pattern: String,
    override val holidayPattern: String
) : DateFormat{
    SHORT("d.m.y", "HS.y"),                             // 2.6.1234 / FS.1234
    SHORT_ALTERNATE("d.MS.y", "HS.y"),                  // 2.ST.1234 / FS.1234
    SHORT_FULL_NUMBERS("dd.mm.y", "HS.y"),              // 02.06.1234 / FS.1234
    SHORT_ALTERNATE_FULL_NUMBERS("dd.mm.y", "HS.y"),    // 02.ST.1234 / FS.1234
    STANDARD("d.m.y", "HF, y"),                         // 2.6.1234 / The Festival of the Sun, 1234
    STANDARD_FULL_NUMBERS("dd.mm.y", "HF, y"),          // 02.06.1234 /
                                                        // The Festival of the Sun, 1234
    WRITTEN("The D Day of the MF Year Y", "HF year Y")  // The 2nd Day of the Season of the
                                                        // Skin Tents, Year 1234 /
                                                        // The Festival of the Sun, Year 1234
}