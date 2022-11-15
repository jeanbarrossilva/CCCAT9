package io.branas.cccat9.exception.format

class NonNumericException(char: Char): IllegalArgumentException("Invalid non-numeric character found: $char")