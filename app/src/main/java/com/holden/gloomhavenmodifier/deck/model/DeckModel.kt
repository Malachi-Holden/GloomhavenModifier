package com.holden.gloomhavenmodifier.deck.model

import com.holden.gloomhavenmodifier.deck.BaseCard
import com.holden.gloomhavenmodifier.deck.isBless
import com.holden.gloomhavenmodifier.deck.isCurse
import com.holden.gloomhavenmodifier.deck.oneTimeUse
import com.holden.gloomhavenmodifier.util.added
import com.holden.gloomhavenmodifier.util.shuffled
import kotlinx.serialization.Serializable

@Serializable
data class DeckModel(
    val cards: List<CardModel>,
    val position: Int = 0,
    val needsShuffle: Boolean = false,
    val curses: Int = 0,
    val blesses: Int = 0
) {

    fun drawn() = position

    fun remaining() = cards.size - drawn()

    fun drawnCards() = cards.subList(position, cards.size)

    fun remainingCards() = cards.subList(0, position)

    fun mostRecentlyPlayed() = cards.getOrNull(position - 1)

    fun draw() = if(position < cards.size) {
        copy(position = position + 1, needsShuffle = needsShuffle || cards[position].reshuffle)
    } else {
        cards.shuffled().let { shuffledCards->
            DeckModel(
                shuffledCards,
                1,
                shuffledCards[0].reshuffle,
                curses,
                blesses
            )
        }
    }.cleanDiscard()

    fun shuffled() = DeckModel(
        cards.shuffled(),
        0,
        false,
        curses,
        blesses
    )

    fun insertUnplayed(card: CardModel) = copy(cards = cards.added(card).shuffled(0, position))

    fun insertCurse() = insertUnplayed(BaseCard.Curse.card).copy(curses = curses + 1)

    fun insertBless() = insertUnplayed(BaseCard.Bless.card).copy(blesses = blesses + 1)

    fun cleanDiscard() = (remainingCards() + drawnCards().filter { !it.oneTimeUse() })
        .let { cards ->
            copy(
                cards = cards,
                blesses = remainingCards().count { it.isBless() },
                curses = remainingCards().count { it.isCurse() }
            )
        }
}