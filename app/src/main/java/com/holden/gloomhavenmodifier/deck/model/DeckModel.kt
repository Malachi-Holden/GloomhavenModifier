package com.holden.gloomhavenmodifier.deck.model

import com.holden.gloomhavenmodifier.util.added
import com.holden.gloomhavenmodifier.util.shuffled

@kotlinx.serialization.Serializable
class DeckModel(val cards: List<CardModel>, val position: Int = 0, val needsShuffle: Boolean = false) {

    fun drawn() = position

    fun remaining() = cards.size - drawn()

    fun drawnCards() = cards.subList(position, cards.size)

    fun remainingCards() = cards.subList(0, position)

    fun mostRecentlyPlayed() = cards.getOrNull(position - 1)

    fun draw() = if(position < cards.size) {
        DeckModel(cards, position + 1, needsShuffle || cards[position].reshuffle == true)
    } else {
        cards.shuffled().let { shuffledCards->
            DeckModel(shuffledCards, 1, shuffledCards[0].reshuffle)
        }
    }

    fun shuffled() = DeckModel(cards.shuffled(), 0, false)

    fun insertUnplayed(card: CardModel) = DeckModel(cards.added(card).shuffled(0, position), position)
}