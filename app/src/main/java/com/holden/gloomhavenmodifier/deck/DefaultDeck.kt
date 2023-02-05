package com.holden.gloomhavenmodifier.deck

import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.deck.model.DeckModel

enum class BaseCard(val card: CardModel){
    BackOfCard(CardModel("back of card", "back", null, false)),
    Zero(CardModel("+ 0", "zero", null, false)),
    One(CardModel("+ 1", "one", null, false)),
    MinusOne(CardModel("- 1", "minus_one", null, false)),
    MinusTwo(CardModel("- 2", "minus_two", null, false)),
    Two(CardModel("+ 2", "two", null, false)),
    Miss(CardModel("Ø", "miss", null, true)),
    Crit(CardModel("x 2", "crit", null, true))
}

//use room instead
fun getLocalDeck() = getDefaultDeck()

fun getDefaultDeck() = DeckModel(buildList {
    repeat(5){
        add(BaseCard.Zero.card)
    }
    repeat(5){
        add(BaseCard.One.card)
    }
    repeat(5){
        add(BaseCard.MinusOne.card)
    }
    add(BaseCard.MinusTwo.card)
    add(BaseCard.Two.card)
    add(BaseCard.Miss.card)
    add(BaseCard.Crit.card)
})