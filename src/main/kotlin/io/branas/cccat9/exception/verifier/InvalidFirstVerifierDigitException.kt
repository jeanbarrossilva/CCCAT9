package io.branas.cccat9.exception.verifier

class InvalidFirstVerifierDigitException(expected: Int, given: Int):
    IllegalArgumentException("First verifier digit is expected to be $expected instead of $given")