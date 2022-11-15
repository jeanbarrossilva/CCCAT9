package io.branas.cccat9

import io.branas.cccat9.exception.format.InvalidLengthException
import io.branas.cccat9.exception.format.InvalidVerifierSeparatorException
import io.branas.cccat9.exception.format.NonNumericException
import io.branas.cccat9.exception.format.separator.InvalidFirstBaseSeparatorException
import io.branas.cccat9.exception.format.separator.InvalidSecondBaseSeparatorException
import io.branas.cccat9.exception.verifier.InvalidFirstVerifierDigitException
import io.branas.cccat9.exception.verifier.InvalidSecondVerifierDigitException
import io.branas.cccat9.extensions.mapIndex
import io.branas.cccat9.extensions.remove
import io.branas.cccat9.extensions.withReversedIndex

data class Cpf(val value: String) {
    private val firstVerifierDigit
        get() = verifier.first().digitToInt()
    private val secondVerifierDigit
        get() = verifier.last().digitToInt()
    private val verifier
        get() = value.split(VERIFIER_SEPARATOR).last()
    private val unformattedValue
        get() = value.remove(BASE_SEPARATOR).remove(BASE_SEPARATOR).remove(VERIFIER_SEPARATOR)

    init {
        validate()
    }

    private fun validate() {
        validateFormat()
        validateVerifier()
    }

    private fun validateFormat() {
        validateLength()
        validateFirstBaseSeparator()
        validateSecondBaseSeparator()
        validateVerifierSeparator()
        validateNonSeparatorCharacters()
    }

    private fun validateLength() {
        val length = value.length
        if (length != LENGTH) {
            throw InvalidLengthException(expected = LENGTH, given = length)
        }
    }

    private fun validateFirstBaseSeparator() {
        if (value[FIRST_BASE_SEPARATOR_INDEX] != BASE_SEPARATOR) {
            throw InvalidFirstBaseSeparatorException()
        }
    }

    private fun validateSecondBaseSeparator() {
        if (value[SECOND_BASE_SEPARATOR_INDEX] != BASE_SEPARATOR) {
            throw InvalidSecondBaseSeparatorException()
        }
    }

    private fun validateVerifierSeparator() {
        val verifierSeparator = value[VERIFIER_SEPARATOR_INDEX]
        if (verifierSeparator != VERIFIER_SEPARATOR) {
            throw InvalidVerifierSeparatorException(expected = VERIFIER_SEPARATOR, given = verifierSeparator)
        }
    }

    private fun validateNonSeparatorCharacters() {
        unformattedValue.filterNot(Char::isDigit).firstOrNull()?.let {
            throw NonNumericException(it)
        }
    }

    private fun validateVerifier() {
        getExpectedVerifier()
            .also { if (firstVerifierDigit != it) throw InvalidFirstVerifierDigitException(it, firstVerifierDigit) }
            .let(::getExpectedVerifier)
            .also { if (secondVerifierDigit != it) throw InvalidSecondVerifierDigitException(it, secondVerifierDigit) }
    }

    private fun getExpectedVerifier(anchor: Int? = null): Int {
        return unformattedValue
            .removeSuffix(verifier)
            .map(Char::digitToInt)
            .let { anchor?.let(it::plus) ?: it }
            .withIndex()
            .toList()
            .withReversedIndex()
            .mapIndex(2::plus)
            .sumOf { (multiplier, digit) -> digit * multiplier }
            .rem(11)
            .let { if (it < 2) 0 else 11 - it }
    }

    companion object {
        private const val LENGTH = 14
        private const val BASE_SEPARATOR = '.'
        private const val FIRST_BASE_SEPARATOR_INDEX = 3
        private const val SECOND_BASE_SEPARATOR_INDEX = 7
        private const val VERIFIER_SEPARATOR = '-'
        private const val VERIFIER_SEPARATOR_INDEX = 11
    }
}