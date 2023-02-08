package com.holden.gloomhavenmodifier.editCharacter.model

import com.holden.gloomhavenmodifier.deck.BaseCard
import com.holden.gloomhavenmodifier.deck.DeckRepository
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import kotlinx.serialization.Serializable

@Serializable
data class CharacterModel(
    val title: String,
    val specialtyCards: List<CardModel>,
    val perks: List<Perk>,
    val appliedPerks: List<Perk> = listOf()
) {
    fun buildDeck() = DeckModel(
        cards = DeckRepository.getDefaultDeck().cards.applyPerks(
            BaseCard.values().associate { it.card.description to it.card }
                    + specialtyCards.associate { it.description to it },
            appliedPerks
        ),
        needsShuffle = true
    )

    companion object{
        val NoClass = CharacterModel(
            "No Class",
            listOf(),
            listOf()
        )
    }
}

