package com.delarax.icewindDale.companion.models

import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate

class InvalidDateException(message: String) : Exception(message) {
    constructor(harposDate: HarposDate) : this(
        "$harposDate is not a valid date in the Calendar of Harpos"
    )

    constructor(nunavutDate: NunavutDate) : this(
        "$nunavutDate is not a valid date in the Nunavut Calendar"
    )
}