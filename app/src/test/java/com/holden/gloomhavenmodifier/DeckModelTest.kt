package com.holden.gloomhavenmodifier

import com.holden.gloomhavenmodifier.deck.isCurse
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class DeckModelTest {

    lateinit var deck: DeckModel

    @Before
    fun setup(){
        deck = CharacterModel.NoClass.buildDeck()
    }

    @Test
    fun testInsertCurse(){
        deck = deck.draw()
        deck = deck.draw()
        deck = deck.insertCurse()
        assertCursesIsCorrect(deck, 1, 0, 18, 2)
    }

    @Test
    fun testRemoveCurse(){
        deck = deck.draw()
        deck = deck.draw()
        deck = deck.insertCurse()
        deck = deck.removeCurse()
        assertCursesIsCorrect(deck, 0, 0, 18, 2) }

    @Test
    fun testInsertCurseAndShuffle(){
        deck = deck.draw()
        deck = deck.draw()
        deck = deck.insertCurse()
        deck = deck.shuffled()
        assertCursesIsCorrect(deck, 1, 0, 20, 0)
}

    @Test
    fun testDrawCardAt(){
        repeat(20) {
            val deck2 = deck.draw(it)
            assertEquals(
                "most recently played card should be the drawn card (${it})",
                deck.cards[it],
                deck2.mostRecentlyPlayed()
            )
        }
    }

    fun assertCursesIsCorrect(
        deck: DeckModel,
        cursesInDeck: Int,
        cursesInDiscard: Int,
        remainingCards: Int,
        discardedCards: Int
    ){
        assertEquals("there should be $cursesInDeck curses in the deck", cursesInDeck,  deck.cards.count { it.isCurse() })
        assertEquals("there should be $cursesInDeck curses in the remaining cards",cursesInDeck,  deck.remainingCards().count { it.isCurse() })

        assertEquals("the curses counter should be $cursesInDeck", cursesInDeck, deck.curses)
        assertEquals("the deck should have no extra cards", remainingCards + cursesInDeck + discardedCards + cursesInDiscard, deck.cards.size)
        assertEquals("the remaining should have no extra cards", remainingCards + cursesInDeck, deck.remainingCards().size)
        assertEquals("the drawn should have no extra cards", discardedCards + cursesInDiscard, deck.drawnCards().size)

    }
}