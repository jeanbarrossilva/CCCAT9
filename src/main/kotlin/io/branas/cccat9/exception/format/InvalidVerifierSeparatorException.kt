package io.branas.cccat9.exception.format

class InvalidVerifierSeparatorException(expected: Char, given: Char):
    IllegalArgumentException("Verifier separator should be a \"$expected\", but got a \"$given\" instead")