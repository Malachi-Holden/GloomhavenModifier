package com.holden.gloomhavenmodifier.deck.model

import com.holden.gloomhavenmodifier.deck.BaseCard
import com.holden.gloomhavenmodifier.deck.isBless
import com.holden.gloomhavenmodifier.deck.isCurse
import com.holden.gloomhavenmodifier.deck.oneTimeUse
import com.holden.gloomhavenmodifier.util.added
import com.holden.gloomhavenmodifier.util.removedFirst
import com.holden.gloomhavenmodifier.util.toInt
import kotlinx.serialization.Serializable
import kotlin.math.max

/**
 * cards: all cards in the deck. They will be drawn in order, from earlier indices onward
 * position: the position of the break between the drawn cards and remaining. Indices smaller than
 *  \position have been drawn. Indices larger than position are remaining.
 * needsShuffle: a crit or miss has been drawn and the deck needs reshuffling
 * curses: the number of curses in the deck
 * blesses: the number of blesses in the deck
 */
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

    fun drawnCards() = cards.subList(0, position)

    fun remainingCards() = cards.subList(position, cards.size)

    fun mostRecentlyPlayed() = cards.getOrNull(position - 1)

    fun draw() = if(position < cards.size) {
        copy(
            position = position + 1,
            needsShuffle = needsShuffle || cards[position].reshuffle,
            curses = curses - cards[position].isCurse().toInt(),
            blesses = blesses - cards[position].isBless().toInt()
        )
    } else {
        val shuffled = shuffled()
        shuffled().copy(position = 1, needsShuffle = shuffled.cards[0].reshuffle)
    }

    fun shuffled() = DeckModel(
        (remainingCards() + drawnCards().filter { !it.oneTimeUse() })
            .shuffled(),
        0,
        false,
        remainingCards().count { it.isCurse() },
        remainingCards().count { it.isBless() }
    )

    fun insertUnplayed(card: CardModel) = copy(cards = drawnCards() + remainingCards().added(card).shuffled())

    fun removeUnplayed(card: CardModel) = copy(
        cards = drawnCards() + remainingCards().removedFirst { it.description == card.description }
    )

    fun insertCurse() = insertUnplayed(BaseCard.Curse.card).copy(curses = curses + 1)

    fun insertBless() = insertUnplayed(BaseCard.Bless.card).copy(blesses = blesses + 1)

    fun removeCurse() = removeUnplayed(BaseCard.Curse.card).copy(curses = max(0, curses - 1))

    fun removeBless() = removeUnplayed(BaseCard.Bless.card).copy(blesses = max(0, blesses - 1))
}