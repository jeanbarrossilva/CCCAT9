package io.branas.cccat9.exception.format

class InvalidLengthException(expected: Int, given: Int):
    IllegalArgumentException("CPF should have a length of $expected, but got $given instead")