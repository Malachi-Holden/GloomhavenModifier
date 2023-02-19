package com.holden.gloomhavenmodifier

import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.editCharacter.model.Perk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterModelTest {
    lateinit var character: CharacterModel

    @Before
    fun setup(){
        character = CharacterModel(
            title = "Mock",
            specialtyCards = listOf(
                CardModel(
                    description = "card 1",
                    reshuffle = false
                ),
                CardModel(
                    description = "card 2",
                    reshuffle = false
                )
            ),
            perks = listOf(
                Perk(
                    description = "remove +1, add card 1",
                    add = listOf("card 1"),
                    remove = listOf("+ 1")
                ),
                Perk(
                    description = "remove -1, add card 2",
                    add = listOf("card 2"),
                    remove = listOf("- 1")
                ),
                Perk(
                    description = "remove -1, add + 2",
                    add = listOf("+ 2"),
                    remove = listOf("- 1")
                )
            ),
            appliedPerks = listOf(
                Perk(
                    description = "remove +1, add card 1",
                    add = listOf("card 1"),
                    remove = listOf("+ 1")
                ),
                Perk(
                    description = "remove -1, add + 2",
                    add = listOf("+ 2"),
                    remove = listOf("- 1")
                )
            )
        )
    }

    @Test
    fun testBuildDeck(){
        val deck = character.buildDeck()
        assertEquals("Should be 4 + 1s", 4, deck.cards.count { it.description == "+ 1" })
        assertEquals("Should be 4 - 1s", 4, deck.cards.count { it.description == "- 1" })
        assertEquals("Should be 1 card 1", 1, deck.cards.count { it.description == "card 1" })
        assertEquals("Should be 1 + 2s", 2, deck.cards.count { it.description == "+ 2" })
    }
}