package com.holden.gloomhavenmodifier.util

import kotlin.math.min

/**
 * Shuffle a list of items within a particular range
 */
fun <E>List<E>.added(item: E, at: Int = size) = subList(0, at) + item + subList(at, size)

fun <E>List<E>.removedFirst(predicate: (E)->Boolean) = indexOfFirst(predicate).let {
    if(it < 0) return@let this
    subList(0, it) + subList(it + 1, size)
}

fun <E>List<E>.moved(from: Int, to: Int) = if (from < to){
    subList(0, from) + subList(from + 1, to) + get(from) + subList(to, size)
} else {
    subList(0, to) + get(from) + subList(to, from) + subList(from + 1, size)
}

fun <E>List<E>.count(from: Int, to: Int, predicate: (E) -> Boolean) = subList(from, to).count(predicate)

fun <E>List<E>.any(from: Int, to: Int, predicate: (E) -> Boolean) = subList(from, to).any(predicate)