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
        assertEquals("there should be one curse in the deck",1,  deck.cards.count { it.isCurse() })
        assertEquals("there should be one curse in the remaining cards",1,  deck.remainingCards().count { it.isCurse() })

        assertEquals("the curses counter should be 1", 1, deck.curses)
        assertEquals("the deck should have 1 extra card", 21, deck.cards.size)
        assertEquals("the remaining should have 1 extra card", 19, deck.remainingCards().size)
        assertEquals("the drawn should have no extra cards", 2, deck.drawnCards().size)
    }

    @Test
    fun testRemoveCurse(){
        deck = deck.draw()
        deck = deck.draw()
        deck = deck.insertCurse()
        deck = deck.removeCurse()
        assertEquals("there should be no curses in the deck",0,  deck.cards.count { it.isCurse() })
        assertEquals("there should be no curses in the remaining cards",0,  deck.remainingCards().count { it.isCurse() })

        assertEquals("the curses counter should be 0", 0, deck.curses)
        assertEquals("the deck should have no extra cards", 20, deck.cards.size)
        assertEquals("the remaining should have no extra cards", 18, deck.remainingCards().size)
        assertEquals("the drawn should have no extra cards", 2, deck.drawnCards().size)
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
}