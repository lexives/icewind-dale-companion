package com.delarax.icewindDale.companion.data

import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.harpos.HarposDate
import com.delarax.icewindDale.companion.models.nunavut.NunavutDate
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class CalendarRepo {

    @Throws(InvalidDateException::class)
    fun getNunavutFromHarpos(harposDate: HarposDate) : NunavutDate =
        NunavutDate.fromAbsoluteDayNumber(harposDate.absoluteDayNumber(), harposDate.year)

    @Throws(InvalidDateException::class)
    fun getHarposFromNunavut(nunavutDate: NunavutDate) : HarposDate =
        HarposDate.fromAbsoluteDayNumber(nunavutDate.absoluteDayNumber(), nunavutDate.year)

}