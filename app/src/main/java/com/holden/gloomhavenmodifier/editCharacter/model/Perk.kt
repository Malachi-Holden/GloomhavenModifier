package com.holden.gloomhavenmodifier.editCharacter.model

import com.holden.gloomhavenmodifier.deck.model.CardModel
import kotlinx.serialization.Serializable

@Serializable
class Perk(val description: String, val add: List<String>, val remove: List<String>)

fun List<CardModel>.applyPerks(availableCards: Map<String, CardModel>, perks: List<Perk>) = buildList {
    val removeSet = perks.flatMap { it.remove }.toMutableSet()
    for (card in this@applyPerks){
        if (removeSet.contains(card.description)){
            removeSet.remove(card.description)
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