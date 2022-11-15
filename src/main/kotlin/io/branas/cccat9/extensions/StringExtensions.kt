package io.branas.cccat9.extensions

fun String.remove(char: Char): String {
    return replace("$char", "")
}