package com.holden.gloomhavenmodifier.deck

import android.content.Context
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.getLocalGloomObject
import com.holden.gloomhavenmodifier.saveLocalGloomObject
import javax.inject.Inject
import javax.inject.Singleton

enum class BaseCard(val card: CardModel){
    BackOfCard(CardModel("back of card", "back", null, false)),
    Zero(CardModel("+ 0", "zero", null, false)),
    One(CardModel("+ 1", "one", null, false)),
    MinusOne(CardModel("- 1", "minus_one", null, false)),
    MinusTwo(CardModel("- 2", "minus_two", null, false)),
    Two(CardModel("+ 2", "two", null, false)),
    Miss(CardModel("Ø", "miss", null, true)),
    Crit(CardModel("x 2", "crit", null, true)),
    Curse(CardModel("curse", "curse", null, false)),
    Bless(CardModel("bless", "bless", null, false)),
    BonusMinus(CardModel("*- 1", "scenario_effect_minus_1", null, false))
}

fun CardModel.isBless() = (description == BaseCard.Bless.card.description)

fun CardModel.isCurse() = (description == BaseCard.Curse.card.description)

fun CardModel.isBonusMinus() = description == BaseCard.BonusMinus.card.description

fun CardModel.oneTimeUse() = isCurse() || isBless()

fun CardModel.isExtraCard() = isCurse() || isBless() || isBonusMinus()

@Singleton
class DeckRepository @Inject constructor(val deck: DeckModel) {
    companion object{

        val GLOOM_DECK_FILE = "GLOOM_DECK_FILE"


        fun saveLocalDeck(context: Context, deck: DeckModel){
            saveLocalGloomObject(context, deck, GLOOM_DECK_FILE)
        }

        fun getLocalDeck(context: Context): DeckModel {
            return getLocalGloomObject<DeckModel>(context, GLOOM_DECK_FILE) ?: CharacterModel.NoClass.buildDeck()
        }


        fun getDefaultDeck() = DeckModel(buildList {
            repeat(6){
                add(BaseCard.Zero.card)
            }
            repeat(5){
                add(BaseCard.One.card)
            }
            repeat(5){
                add(BaseCard.MinusOne.card)
            }
            add(BaseCard.MinusTwo.card)
            add(BaseCard.Two.card)
            add(BaseCard.Miss.card)
            add(BaseCard.Crit.card)
        })
    }
}