package com.holden.gloomhavenmodifier.util

/**
 * Shuffle a list of items within a particular range
 */
fun <E>List<E>.shuffled(from: Int, to: Int = size)
    = subList(0, from) + subList(from, to).shuffled() + subList(to, size)

fun <E>List<E>.added(item: E, at: Int = size) = subList(0, at) + item + subList(at, size)

fun <E>List<E>.mutated(mutation: MutableList<E>.()->Unit) = buildList {
    addAll(this@mutated)
    mutation()
}

fun <E>List<E>.removeIf(conditions: List<(E)->Boolean>) = buildList {
    var i = 0
    for (item in this@removeIf){
        if (i < conditions.size && conditions[i](item)){
            i ++
        } else{
            add(item)
        }
    }
}