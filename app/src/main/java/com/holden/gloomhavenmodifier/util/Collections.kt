package com.holden.gloomhavenmodifier.util

/**
 * Shuffle a list of items within a particular range
 */
fun <E>List<E>.shuffled(from: Int, to: Int = size)
    = subList(0, from) + subList(from, to).shuffled() + subList(to, size)

fun <E>List<E>.added(item: E, at: Int = size) = subList(0, at) + item + subList(at, size)

fun <E>List<E>.removed(at: Int) = subList(0, at) + subList(at + 1, size)