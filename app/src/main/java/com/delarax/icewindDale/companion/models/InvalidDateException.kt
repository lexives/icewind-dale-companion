package com.delarax.icewindDale.companion.models

import java.lang.Exception

class InvalidDateException(message: String) : Exception(message) {
    constructor(harposDate: HarposDate) : this(
        "$harposDate is not a valid date in the Calendar of Harpos"
    )

    constructor(nunavutDate: NunavutDate) : this(
        "$nunavutDate is not a valid date in the Nunavut Calendar"
    )
}