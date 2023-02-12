package com.holden.gloomhavenmodifier.deck.model

import com.holden.gloomhavenmodifier.deck.*
import com.holden.gloomhavenmodifier.util.*
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
    val blesses: Int = 0,
    val bonusMinuses: Int = 0
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
        shuffled.copy(position = 1, needsShuffle = shuffled.cards[0].reshuffle)
    }

    /**
     * Draws a card from specified position `at` and puts it in the discard pile
     */
    fun draw(at: Int)  = cards.moved(at, position).let { cards->
        copy(
            cards = cards,
            curses = cards.count(position + 1, cards.size){it.isCurse()},
            blesses = cards.count(position + 1, cards.size){it.isBless()},
            position = position + 1
        )
    }.shuffledRemaining()

    fun shuffled() = copy(
        cards = (remainingCards() + drawnCards().filter { !it.oneTimeUse() })
            .shuffled(),
        position =0,
        needsShuffle = false,
        blesses = remainingCards().count { it.isCurse() },
        curses = remainingCards().count { it.isBless() }
    )

    fun shuffledRemaining() = copy(
        cards = drawnCards() + remainingCards().shuffled()
    )

    /**
     * Removes a card from the discarded cards and returns it to the remaining undrawn cards
     */
    fun unDrawCard(at: Int) = cards.moved(at, cards.size).let { cards->
        copy(
            cards = cards,
            curses = cards.count(position - 1, cards.size){it.isCurse()},
            blesses = cards.count(position - 1, cards.size){it.isBless()},
            position = position - 1
        )
    }.shuffledRemaining()

    /**
     * Inserts a card into the remaining unplayed cards
     */
    fun insertUnplayed(card: CardModel) = copy(cards = drawnCards() + remainingCards().added(card).shuffled())

    /**
     * Removes a card from the remaining unplayed cards
     */
    fun removeUnplayed(card: CardModel) = copy(
        cards = drawnCards() + remainingCards().removedFirst { it.description == card.description }
    )

    fun insertCurse() = insertUnplayed(BaseCard.Curse.card).copy(curses = curses + 1)

    fun insertBless() = insertUnplayed(BaseCard.Bless.card).copy(blesses = blesses + 1)

    fun insertScenarioMinus()
        = insertUnplayed(BaseCard.BonusMinus.card)
        .copy(bonusMinuses = bonusMinuses + 1)

    fun removeCurse() = removeUnplayed(BaseCard.Curse.card).copy(curses = max(0, curses - 1))

    fun removeBless() = removeUnplayed(BaseCard.Bless.card).copy(blesses = max(0, blesses - 1))

    fun removeScenarioMinus()
        = removeUnplayed(BaseCard.BonusMinus.card)
        .copy(bonusMinuses = max(0, bonusMinuses - 1))

    /**
     * Removes all scenario-specific cards. Leaves only cards from perks and base cards
     */
    fun cleanDeck() = DeckModel(
        cards = cards.filter { !it.isExtraCard() }.shuffled()
    )

}