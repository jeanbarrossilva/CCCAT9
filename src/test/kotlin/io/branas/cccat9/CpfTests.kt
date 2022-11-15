package io.branas.cccat9

import io.branas.cccat9.exception.format.InvalidLengthException
import io.branas.cccat9.exception.format.InvalidVerifierSeparatorException
import io.branas.cccat9.exception.format.NonNumericException
import io.branas.cccat9.exception.format.separator.InvalidFirstBaseSeparatorException
import io.branas.cccat9.exception.format.separator.InvalidSecondBaseSeparatorException
import io.branas.cccat9.exception.verifier.InvalidFirstVerifierDigitException
import io.branas.cccat9.exception.verifier.InvalidSecondVerifierDigitException
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class CpfTests {
    @Test
    fun `GIVEN a valid value WHEN creating a CPF with it THEN it does not throw`() {
        assertDoesNotThrow {
            Cpf("012.345.678-90")
        }
    }

    @Test
    fun `GIVEN a value with a greater length WHEN creating a CPF with it THEN it is invalid`() {
        assertThrows<InvalidLengthException> {
            Cpf("012.345.678-901")
        }
    }

    @Test
    fun `GIVEN an incorrect first base separator WHEN creating a CPF THEN it is invalid`() {
        assertThrows<InvalidFirstBaseSeparatorException> {
            Cpf("012>345.678-90")
        }
    }

    @Test
    fun `GIVEN an incorrect second base separator WHEN creating a CPF THEN it is invalid`() {
        assertThrows<InvalidSecondBaseSeparatorException> {
            Cpf("012.345<678.90")
        }
    }

    @Test
    fun `GIVEN an incorrect verifier separator WHEN creating a CPF THEN it is invalid`() {
        assertThrows<InvalidVerifierSeparatorException> {
            Cpf("012.345.678!90")
        }
    }

    @Test
    fun `GIVEN an incorrect first verifier digit WHEN creating a CPF with it THEN it is invalid`() {
        assertThrows<InvalidFirstVerifierDigitException> {
            Cpf("238.348.999-34")
        }
    }

    @Test
    fun `GIVEN an incorrect second verifier digit WHEN creating a CPF THEN it is invalid`() {
        assertThrows<InvalidSecondVerifierDigitException> {
            Cpf("123.086.344-38")
        }
    }

    @Test
    fun `GIVEN a non-numeric base character WHEN creating a CPF THEN it is invalid`() {
        assertThrows<NonNumericException> {
            Cpf("012.34a.678-90")
        }
    }

    @Test
    fun `GIVEN a non-numeric verifier character WHEN creating a CPF THEN it is invalid`() {
        assertThrows<NonNumericException> {
            Cpf("012.345.678-9b")
        }
    }
}