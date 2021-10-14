package com.delarax.icewindDale.compantion.models

import com.delarax.icewindDale.companion.models.*
import com.delarax.icewindDale.companion.models.NunavutHoliday.*
import com.delarax.icewindDale.companion.models.NunavutSeason.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NunavutDateExtTests {

    @Test
    fun `priorSeason returns correct season in all cases`() {
        Assert.assertEquals(JARLMOOT, ELK_HUNT.priorSeason())
        Assert.assertEquals(BARE_MOUNTAIN, JARLMOOT.priorSeason())
        Assert.assertEquals(BERRIES, BARE_MOUNTAIN.priorSeason())
        Assert.assertEquals(RUNNING_CHAR, BERRIES.priorSeason())
        Assert.assertEquals(SKIN_TENTS, RUNNING_CHAR.priorSeason())
        Assert.assertEquals(NESTING_GEESE, SKIN_TENTS.priorSeason())
        Assert.assertEquals(BEATING_ICE, NESTING_GEESE.priorSeason())
        Assert.assertEquals(SEAL_PUPS, BEATING_ICE.priorSeason())
        Assert.assertEquals(IGLOO, SEAL_PUPS.priorSeason())
        Assert.assertEquals(FALLING_STARS, IGLOO.priorSeason())
        Assert.assertEquals(DENNING_POLAR_BEAR, FALLING_STARS.priorSeason())
        Assert.assertEquals(ELK_HUNT, DENNING_POLAR_BEAR.priorSeason())
    }

    @Test
    fun `nextSeason returns correct season in all cases`() {
        Assert.assertEquals(FALLING_STARS, DENNING_POLAR_BEAR.nextSeason())
        Assert.assertEquals(IGLOO, FALLING_STARS.nextSeason())
        Assert.assertEquals(SEAL_PUPS, IGLOO.nextSeason())
        Assert.assertEquals(BEATING_ICE, SEAL_PUPS.nextSeason())
        Assert.assertEquals(NESTING_GEESE, BEATING_ICE.nextSeason())
        Assert.assertEquals(SKIN_TENTS, NESTING_GEESE.nextSeason())
        Assert.assertEquals(RUNNING_CHAR, SKIN_TENTS.nextSeason())
        Assert.assertEquals(BERRIES, RUNNING_CHAR.nextSeason())
        Assert.assertEquals(BARE_MOUNTAIN, BERRIES.nextSeason())
        Assert.assertEquals(JARLMOOT, BARE_MOUNTAIN.nextSeason())
        Assert.assertEquals(ELK_HUNT, JARLMOOT.nextSeason())
        Assert.assertEquals(DENNING_POLAR_BEAR, ELK_HUNT.nextSeason())
    }

    @Test
    fun `lastHoliday returns correct holiday for not leap year`() {
        val year = 1001
        Assert.assertEquals(MOON_FEAST, DENNING_POLAR_BEAR.lastHoliday(year))
        Assert.assertEquals(MOON_FEAST, FALLING_STARS.lastHoliday(year))
        Assert.assertEquals(MOON_FEAST, IGLOO.lastHoliday(year))
        Assert.assertEquals(OMINGMAK, SEAL_PUPS.lastHoliday(year))
        Assert.assertEquals(OMINGMAK, BEATING_ICE.lastHoliday(year))
        Assert.assertEquals(OMINGMAK, NESTING_GEESE.lastHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, SKIN_TENTS.lastHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, RUNNING_CHAR.lastHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, BERRIES.lastHoliday(year))
        Assert.assertEquals(ALIANAT, BARE_MOUNTAIN.lastHoliday(year))
        Assert.assertEquals(ALIANAT, JARLMOOT.lastHoliday(year))
        Assert.assertEquals(ALIANAT, ELK_HUNT.lastHoliday(year))
    }

    @Test
    fun `lastHoliday returns correct holiday for leap year`() {
        val year = 1004
        Assert.assertEquals(MOON_FEAST, DENNING_POLAR_BEAR.lastHoliday(year))
        Assert.assertEquals(MOON_FEAST, FALLING_STARS.lastHoliday(year))
        Assert.assertEquals(MIDWINTER, IGLOO.lastHoliday(year))
        Assert.assertEquals(OMINGMAK, SEAL_PUPS.lastHoliday(year))
        Assert.assertEquals(OMINGMAK, BEATING_ICE.lastHoliday(year))
        Assert.assertEquals(OMINGMAK, NESTING_GEESE.lastHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, SKIN_TENTS.lastHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, RUNNING_CHAR.lastHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, BERRIES.lastHoliday(year))
        Assert.assertEquals(ALIANAT, BARE_MOUNTAIN.lastHoliday(year))
        Assert.assertEquals(ALIANAT, JARLMOOT.lastHoliday(year))
        Assert.assertEquals(ALIANAT, ELK_HUNT.lastHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday for not leap year`() {
        val year = 1001
        Assert.assertEquals(OMINGMAK, DENNING_POLAR_BEAR.nextHoliday(year))
        Assert.assertEquals(OMINGMAK, FALLING_STARS.nextHoliday(year))
        Assert.assertEquals(OMINGMAK, IGLOO.nextHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, SEAL_PUPS.nextHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, BEATING_ICE.nextHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, NESTING_GEESE.nextHoliday(year))
        Assert.assertEquals(ALIANAT, SKIN_TENTS.nextHoliday(year))
        Assert.assertEquals(ALIANAT, RUNNING_CHAR.nextHoliday(year))
        Assert.assertEquals(ALIANAT, BERRIES.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, BARE_MOUNTAIN.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, JARLMOOT.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, ELK_HUNT.nextHoliday(year))
    }

    @Test
    fun `nextHoliday returns correct holiday for leap year`() {
        val year = 1004
        Assert.assertEquals(MIDWINTER, DENNING_POLAR_BEAR.nextHoliday(year))
        Assert.assertEquals(MIDWINTER, FALLING_STARS.nextHoliday(year))
        Assert.assertEquals(OMINGMAK, IGLOO.nextHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, SEAL_PUPS.nextHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, BEATING_ICE.nextHoliday(year))
        Assert.assertEquals(SUN_FESTIVAL, NESTING_GEESE.nextHoliday(year))
        Assert.assertEquals(ALIANAT, SKIN_TENTS.nextHoliday(year))
        Assert.assertEquals(ALIANAT, RUNNING_CHAR.nextHoliday(year))
        Assert.assertEquals(ALIANAT, BERRIES.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, BARE_MOUNTAIN.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, JARLMOOT.nextHoliday(year))
        Assert.assertEquals(MOON_FEAST, ELK_HUNT.nextHoliday(year))
    }
}