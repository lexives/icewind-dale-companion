package com.delarax.icewindDale.compantion.extensions

import com.delarax.icewindDale.companion.extensions.leadingZeros
import com.delarax.icewindDale.companion.extensions.toStringWithSuffix
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IntExtTests {

    @Test
    fun `leadingZeros pads the given number of zeros`() {
        assertEquals("1", 1.leadingZeros(0))
        assertEquals("1", 1.leadingZeros(1))
        assertEquals("01", 1.leadingZeros(2))
        assertEquals("001", 1.leadingZeros(3))

        assertEquals("123", 123.leadingZeros(0))
        assertEquals("123", 123.leadingZeros(1))
        assertEquals("123", 123.leadingZeros(2))
        assertEquals("123", 123.leadingZeros(3))
    }

    @Test
    fun `toStringWithSuffix, 0 to 9`() {
        assertEquals("0th", 0.toStringWithSuffix())
        assertEquals("1st", 1.toStringWithSuffix())
        assertEquals("2nd", 2.toStringWithSuffix())
        assertEquals("3rd", 3.toStringWithSuffix())
        assertEquals("4th", 4.toStringWithSuffix())
        assertEquals("5th", 5.toStringWithSuffix())
        assertEquals("6th", 6.toStringWithSuffix())
        assertEquals("7th", 7.toStringWithSuffix())
        assertEquals("8th", 8.toStringWithSuffix())
        assertEquals("9th", 9.toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, 10 to 19`() {
        assertEquals("10th", 10.toStringWithSuffix())
        assertEquals("11th", 11.toStringWithSuffix())
        assertEquals("12th", 12.toStringWithSuffix())
        assertEquals("13th", 13.toStringWithSuffix())
        assertEquals("14th", 14.toStringWithSuffix())
        assertEquals("15th", 15.toStringWithSuffix())
        assertEquals("16th", 16.toStringWithSuffix())
        assertEquals("17th", 17.toStringWithSuffix())
        assertEquals("18th", 18.toStringWithSuffix())
        assertEquals("19th", 19.toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, 20 to 29`() {
        assertEquals("20th", 20.toStringWithSuffix())
        assertEquals("21st", 21.toStringWithSuffix())
        assertEquals("22nd", 22.toStringWithSuffix())
        assertEquals("23rd", 23.toStringWithSuffix())
        assertEquals("24th", 24.toStringWithSuffix())
        assertEquals("25th", 25.toStringWithSuffix())
        assertEquals("26th", 26.toStringWithSuffix())
        assertEquals("27th", 27.toStringWithSuffix())
        assertEquals("28th", 28.toStringWithSuffix())
        assertEquals("29th", 29.toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, 100 to 109`() {
        assertEquals("100th", 100.toStringWithSuffix())
        assertEquals("101st", 101.toStringWithSuffix())
        assertEquals("102nd", 102.toStringWithSuffix())
        assertEquals("103rd", 103.toStringWithSuffix())
        assertEquals("104th", 104.toStringWithSuffix())
        assertEquals("105th", 105.toStringWithSuffix())
        assertEquals("106th", 106.toStringWithSuffix())
        assertEquals("107th", 107.toStringWithSuffix())
        assertEquals("108th", 108.toStringWithSuffix())
        assertEquals("109th", 109.toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, 110 to 119`() {
        assertEquals("110th", 110.toStringWithSuffix())
        assertEquals("111th", 111.toStringWithSuffix())
        assertEquals("112th", 112.toStringWithSuffix())
        assertEquals("113th", 113.toStringWithSuffix())
        assertEquals("114th", 114.toStringWithSuffix())
        assertEquals("115th", 115.toStringWithSuffix())
        assertEquals("116th", 116.toStringWithSuffix())
        assertEquals("117th", 117.toStringWithSuffix())
        assertEquals("118th", 118.toStringWithSuffix())
        assertEquals("119th", 119.toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, 120 to 129`() {
        assertEquals("120th", 120.toStringWithSuffix())
        assertEquals("121st", 121.toStringWithSuffix())
        assertEquals("122nd", 122.toStringWithSuffix())
        assertEquals("123rd", 123.toStringWithSuffix())
        assertEquals("124th", 124.toStringWithSuffix())
        assertEquals("125th", 125.toStringWithSuffix())
        assertEquals("126th", 126.toStringWithSuffix())
        assertEquals("127th", 127.toStringWithSuffix())
        assertEquals("128th", 128.toStringWithSuffix())
        assertEquals("129th", 129.toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, -1 to -9`() {
        assertEquals("-1st", (-1).toStringWithSuffix())
        assertEquals("-2nd", (-2).toStringWithSuffix())
        assertEquals("-3rd", (-3).toStringWithSuffix())
        assertEquals("-4th", (-4).toStringWithSuffix())
        assertEquals("-5th", (-5).toStringWithSuffix())
        assertEquals("-6th", (-6).toStringWithSuffix())
        assertEquals("-7th", (-7).toStringWithSuffix())
        assertEquals("-8th", (-8).toStringWithSuffix())
        assertEquals("-9th", (-9).toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, -10 to -19`() {
        assertEquals("-10th", (-10).toStringWithSuffix())
        assertEquals("-11th", (-11).toStringWithSuffix())
        assertEquals("-12th", (-12).toStringWithSuffix())
        assertEquals("-13th", (-13).toStringWithSuffix())
        assertEquals("-14th", (-14).toStringWithSuffix())
        assertEquals("-15th", (-15).toStringWithSuffix())
        assertEquals("-16th", (-16).toStringWithSuffix())
        assertEquals("-17th", (-17).toStringWithSuffix())
        assertEquals("-18th", (-18).toStringWithSuffix())
        assertEquals("-19th", (-19).toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, -20 to -29`() {
        assertEquals("-20th", (-20).toStringWithSuffix())
        assertEquals("-21st", (-21).toStringWithSuffix())
        assertEquals("-22nd", (-22).toStringWithSuffix())
        assertEquals("-23rd", (-23).toStringWithSuffix())
        assertEquals("-24th", (-24).toStringWithSuffix())
        assertEquals("-25th", (-25).toStringWithSuffix())
        assertEquals("-26th", (-26).toStringWithSuffix())
        assertEquals("-27th", (-27).toStringWithSuffix())
        assertEquals("-28th", (-28).toStringWithSuffix())
        assertEquals("-29th", (-29).toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, -101 to -109`() {
        assertEquals("-101st", (-101).toStringWithSuffix())
        assertEquals("-102nd", (-102).toStringWithSuffix())
        assertEquals("-103rd", (-103).toStringWithSuffix())
        assertEquals("-104th", (-104).toStringWithSuffix())
        assertEquals("-105th", (-105).toStringWithSuffix())
        assertEquals("-106th", (-106).toStringWithSuffix())
        assertEquals("-107th", (-107).toStringWithSuffix())
        assertEquals("-108th", (-108).toStringWithSuffix())
        assertEquals("-109th", (-109).toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, -110 to -119`() {
        assertEquals("-110th", (-110).toStringWithSuffix())
        assertEquals("-111th", (-111).toStringWithSuffix())
        assertEquals("-112th", (-112).toStringWithSuffix())
        assertEquals("-113th", (-113).toStringWithSuffix())
        assertEquals("-114th", (-114).toStringWithSuffix())
        assertEquals("-115th", (-115).toStringWithSuffix())
        assertEquals("-116th", (-116).toStringWithSuffix())
        assertEquals("-117th", (-117).toStringWithSuffix())
        assertEquals("-118th", (-118).toStringWithSuffix())
        assertEquals("-119th", (-119).toStringWithSuffix())
    }

    @Test
    fun `toStringWithSuffix, -120 to -129`() {
        assertEquals("-120th", (-120).toStringWithSuffix())
        assertEquals("-121st", (-121).toStringWithSuffix())
        assertEquals("-122nd", (-122).toStringWithSuffix())
        assertEquals("-123rd", (-123).toStringWithSuffix())
        assertEquals("-124th", (-124).toStringWithSuffix())
        assertEquals("-125th", (-125).toStringWithSuffix())
        assertEquals("-126th", (-126).toStringWithSuffix())
        assertEquals("-127th", (-127).toStringWithSuffix())
        assertEquals("-128th", (-128).toStringWithSuffix())
        assertEquals("-129th", (-129).toStringWithSuffix())
    }
}