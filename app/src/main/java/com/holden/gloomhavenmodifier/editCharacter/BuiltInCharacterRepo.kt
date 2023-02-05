package com.holden.gloomhavenmodifier.editCharacter

import android.content.res.AssetManager
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

val LOCAL_CHARACTER_FILE = "LocalCharacters.json"

class BuiltInCharacterRepo(val assets: AssetManager): CharacterRepository {

    @OptIn(ExperimentalSerializationApi::class)
    fun getLocalCharacterFiles(): List<String>{
        val localCharacters = assets.open(LOCAL_CHARACTER_FILE).bufferedReader().use {
            it.readText()
        }
        return Json.decodeFromString(localCharacters)
    }

    override suspend fun getCharacterJson(): List<String> = buildList {
        for (fileName in getLocalCharacterFiles()){
            add(
                assets.open(fileName).bufferedReader().use {
                    it.readText()
                }
            )
        }
    }
}