package com.holden.gloomhavenmodifier.editCharacter.model

import com.holden.gloomhavenmodifier.deck.model.CardModel
import kotlinx.serialization.Serializable

@Serializable
class Perk(val description: String, val count: Int = 1, val add: List<String>, val remove: List<String>)

fun List<CardModel>.applyPerks(availableCards: Map<String, CardModel>, perks: List<Perk>) = buildList {
    val removeSet = perks.flatMap { it.remove }.groupingBy { it }.eachCount().toMutableMap()
    for (card in this@applyPerks){
        if ((removeSet[card.description] ?: 0) > 0){
            removeSet[card.description] = (removeSet[card.description] ?: 0) - 1
        } else{
            add(card)
        }
    }
    for (perk in perks){
        for (add in perk.add){
            availableCards[add]?.let { add(it) }
        }
    }
}