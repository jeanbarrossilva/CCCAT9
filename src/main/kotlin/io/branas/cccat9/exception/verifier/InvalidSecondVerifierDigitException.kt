package io.branas.cccat9.exception.verifier

class InvalidSecondVerifierDigitException(expected: Int, given: Int):
    IllegalArgumentException("Second verifier digit is expected to be $expected instead of $given")