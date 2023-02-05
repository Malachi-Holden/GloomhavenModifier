package com.holden.gloomhavenmodifier.deck

import android.content.Context
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File
import java.io.FileNotFoundException

val GLOOM_CHARACTER_FILE = "GLOOM_CHARACTER_FILE"
val GLOOM_DECK_FILE = "GLOOM_DECK_FILE"

enum class BaseCard(val card: CardModel){
    BackOfCard(CardModel("back of card", "back", null, false)),
    Zero(CardModel("+ 0", "zero", null, false)),
    One(CardModel("+ 1", "one", null, false)),
    MinusOne(CardModel("- 1", "minus_one", null, false)),
    MinusTwo(CardModel("- 2", "minus_two", null, false)),
    Two(CardModel("+ 2", "two", null, false)),
    Miss(CardModel("Ø", "miss", null, true)),
    Crit(CardModel("x 2", "crit", null, true))
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified A>saveLocalGloomObject(context: Context, obj: A, file: String){
    val json = Json.encodeToString(obj)
    context.openFileOutput(file, Context.MODE_PRIVATE).use {
        it.write(json.toByteArray())
    }
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified A>getLocalGloomObject(context: Context, file: String): A?{
    try {
        val json = context.openFileInput(file).bufferedReader().use {
            it.readText()
        }

        return Json.decodeFromString(json)
    } catch (e: FileNotFoundException){
        return null
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun saveLocalDeck(context: Context, deck: DeckModel){
    saveLocalGloomObject(context, deck, GLOOM_DECK_FILE)
}

@OptIn(ExperimentalSerializationApi::class)
fun getLocalDeck(context: Context): DeckModel{
    return getLocalGloomObject<DeckModel>(context, GLOOM_DECK_FILE) ?: CharacterModel.NoClass.buildDeck()
}

@OptIn(ExperimentalSerializationApi::class)
fun saveLocalCharacter(context: Context, character: CharacterModel){
    saveLocalGloomObject(context, character, GLOOM_CHARACTER_FILE)
}

@OptIn(ExperimentalSerializationApi::class)
fun getLocalCharacter(context: Context): CharacterModel{
    return getLocalGloomObject(context, GLOOM_CHARACTER_FILE) ?: CharacterModel.NoClass
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