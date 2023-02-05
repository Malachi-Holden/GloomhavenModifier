package com.holden.gloomhavenmodifier.editCharacter

import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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