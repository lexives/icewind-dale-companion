package com.delarax.icewindDale.compantion.extensions

import com.delarax.icewindDale.companion.extensions.capitalize
import com.delarax.icewindDale.companion.extensions.enumCaseToTitleCase
import com.delarax.icewindDale.companion.extensions.toTriple
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StrExtTests {

    @Test
    fun `capitalize capitalizes only the first character of a string`() {
        assertEquals("Test string", "test string".capitalize())
        assertEquals("Test string", "Test string".capitalize())
        assertEquals("123", "123".capitalize())
    }

    @Test
    fun `enumCaseToTitleCase converts a string separated by underscores to separated by spaces with each word capitalized`() {
        assertEquals("Test String", "TEST_STRING".enumCaseToTitleCase())
        assertEquals("Test String", "test_string".enumCaseToTitleCase())
        assertEquals("123", "123".enumCaseToTitleCase())
    }

    @Test
    fun `enumCaseToTileCase only capitalizes words that are separated by underscores`() {
        assertEquals("This is a Test String", "THIS IS A_TEST_STRING".enumCaseToTitleCase())
        assertEquals("This is a Test String", "this is a_test_string".enumCaseToTitleCase())
    }

    @Test(expected = StringIndexOutOfBoundsException::class)
    fun `toTriple throws error if index is less than 0`() {
        "0123456789".toTriple(-1, 1)
    }

    @Test(expected = StringIndexOutOfBoundsException::class)
    fun `toTriple throws error if index is greater than last index of string`() {
        "0123456789".toTriple(10, 1)
    }

    @Test(expected = StringIndexOutOfBoundsException::class)
    fun `toTriple throws error if index plus length is greater than length of string`() {
        "0123456789".toTriple(9, 2)
    }

    @Test
    fun `toTriple returns a triple with three sub-strings of the original string, length 1`() {
        val string = "0123456789"
        val len = 1
        assertEquals(Triple("", "0", "123456789"), string.toTriple(0, len))
        assertEquals(Triple("012", "3", "456789"), string.toTriple(3, len))
        assertEquals(Triple("012345678", "9", ""), string.toTriple(9, len))
    }

    @Test
    fun `toTriple returns a triple with three sub-strings of the original string, length 2`() {
        val string = "0123456789"
        val len = 2
        assertEquals(Triple("", "01", "23456789"), string.toTriple(0, len))
        assertEquals(Triple("012", "34", "56789"), string.toTriple(3, len))
        assertEquals(Triple("01234567", "89", ""), string.toTriple(8, len))
    }

    @Test
    fun `toTriple returns a triple with three sub-strings of the original string, length 0`() {
        val string = "0123456789"
        val len = 0
        assertEquals(Triple("", "", "0123456789"), string.toTriple(0, len))
        assertEquals(Triple("012", "", "3456789"), string.toTriple(3, len))
        assertEquals(Triple("012345678", "", "9"), string.toTriple(9, len))
    }

    @Test
    fun `toTriple returns a triple with three sub-strings of the original string, length max`() {
        val string = "0123456789"
        val len = 10
        assertEquals(Triple("", "0123456789", ""), string.toTriple(0, len))
    }
}