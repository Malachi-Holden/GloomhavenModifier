package com.holden.gloomhavenmodifier.character.model

import com.holden.gloomhavenmodifier.deck.BaseCard
import com.holden.gloomhavenmodifier.deck.getDefaultDeck
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class CharacterModel(
    val title: String,
    val specialtyCards: List<CardModel>,
    val perks: List<Perk>,
    @Transient val appliedPerks: List<Perk> = listOf()
) {
    fun buildDeck() = DeckModel(
        cards = getDefaultDeck().cards.applyPerks(
            BaseCard.values().associate { it.card.description to it.card }
                    + specialtyCards.associate { it.description to it },
            appliedPerks
        )
    )

}

