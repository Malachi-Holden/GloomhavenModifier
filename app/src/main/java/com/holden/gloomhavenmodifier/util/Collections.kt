package com.holden.gloomhavenmodifier.util

/**
 * Shuffle a list of items within a particular range
 */
fun <E>List<E>.added(item: E, at: Int = size) = subList(0, at) + item + subList(at, size)

fun <E>List<E>.removedFirst(predicate: (E)->Boolean) = indexOfFirst(predicate).let {
    if(it < 0) return@let this
    subList(0, it) + subList(it + 1, size)
}