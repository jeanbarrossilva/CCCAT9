package io.branas.cccat9.extensions

fun <T> Collection<IndexedValue<T>>.mapIndex(transform: (index: Int) -> Int): List<IndexedValue<T>> {
    return map {
        it.copy(index = transform(it.index))
    }
}

fun <T> Collection<IndexedValue<T>>.withReversedIndex(): List<IndexedValue<T>> {
    return reversed().mapIndexed { index, value ->
        IndexedValue(value.index, elementAt(index).value)
    }
}