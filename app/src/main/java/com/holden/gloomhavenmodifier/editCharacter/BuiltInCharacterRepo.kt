package com.holden.gloomhavenmodifier.editCharacter

import android.content.Context
import android.content.res.AssetManager
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterState
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.getLocalGloomObject
import com.holden.gloomhavenmodifier.saveLocalGloomObject
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

val LOCAL_CHARACTER_FILE = "LocalCharacters.json"


class BuiltInCharacterRepo @Inject constructor(val assets: AssetManager): CharacterRepository {

    @OptIn(ExperimentalSerializationApi::class)
    fun getLocalCharacterFiles(): List<String>{
        val localCharacters = assets.open(LOCAL_CHARACTER_FILE).bufferedReader().use {
            it.readText()
        }
        return Json.decodeFromString(localCharacters)
    }

    fun getCharacterJson(): List<String> = buildList {
        for (fileName in getLocalCharacterFiles()){
            add(
                assets.open(fileName).bufferedReader().use {
                    it.readText()
                }
            )
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getCharacters(): CharacterState = CharacterState.Loaded(buildList {
        for (jsonCharacter in getCharacterJson()){
            add(
                Json.decodeFromString(jsonCharacter)
            )
        }
    })

    companion object{
        val GLOOM_CHARACTER_FILE = "GLOOM_CHARACTER_FILE"

        fun saveLocalCharacter(context: Context, character: CharacterModel){
            saveLocalGloomObject(context, character, GLOOM_CHARACTER_FILE)
        }

        fun getLocalCharacter(context: Context): CharacterModel{
            return getLocalGloomObject(context, GLOOM_CHARACTER_FILE) ?: CharacterModel.NoClass
        }
    }
}