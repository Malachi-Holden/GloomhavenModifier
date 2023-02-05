package com.holden.gloomhavenmodifier.character.model

import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.util.mutated
import com.holden.gloomhavenmodifier.util.removeIf
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