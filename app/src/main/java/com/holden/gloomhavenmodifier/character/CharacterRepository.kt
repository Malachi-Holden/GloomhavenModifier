package com.holden.gloomhavenmodifier.character

import com.holden.gloomhavenmodifier.character.model.CharacterModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject

/**
 * Resource for fetching data about a character from a data source (usually remote)
 */
interface CharacterRepository {
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getCharacters(): List<CharacterModel> = buildList {
        for (jsonCharacter in getCharacterJson()){
            add(
                Json.decodeFromString(jsonCharacter)
            )
        }
    }

    suspend fun getCharacterJson(): List<String>
}