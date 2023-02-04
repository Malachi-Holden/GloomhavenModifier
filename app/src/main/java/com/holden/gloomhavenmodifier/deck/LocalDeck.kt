package com.holden.gloomhavenmodifier.deck

import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.deck.model.DeckModel

//use room instead
fun getLocalDeck() = getDefaultDeck()

fun getDefaultDeck() = DeckModel(buildList {
    repeat(5){
        add(CardModel("+ 0", R.drawable.zero, false))
    }
    repeat(5){
        add(CardModel("+ 1", R.drawable.one, false))
    }
    repeat(5){
        add(CardModel("- 1", R.drawable.minus_one, false))
    }
    add(CardModel("- 2", R.drawable.minus_two, false))
    add(CardModel("+ 2", R.drawable.two, false))
    add(CardModel("Ø", R.drawable.miss, true))
    add(CardModel("x 2", R.drawable.crit, true))
})