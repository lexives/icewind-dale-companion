package com.delarax.icewindDale.companion.models.harpos

enum class HarposHoliday(
    val priorMonth: HarposMonth,
    val nextMonth: HarposMonth,
    val isQuadrennial: Boolean,
    val fullName: String
) {
    MIDWINTER(HarposMonth.HAMMER, HarposMonth.ALTURIAK, true, "Midwinter"),
    SHIELDMEET(HarposMonth.HAMMER, HarposMonth.ALTURIAK, false, "Shieldmeet"),
    GREENGRASS(HarposMonth.TARSAKH, HarposMonth.MIRTUL, false, "Greengrass"),
    MIDSUMMER(HarposMonth.FLAMERULE, HarposMonth.ELEASIAS, false, "Midsummer"),
    HIGHHARVESTTIDE(HarposMonth.ELEINT, HarposMonth.MARPENOTH, false, "Highharvesttide"),
    MOON_FEAST(HarposMonth.UKTAR, HarposMonth.NIGHTAL, false, "The Feast of the Moon")
}