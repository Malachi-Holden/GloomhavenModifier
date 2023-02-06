package com.holden.gloomhavenmodifier.editCharacter

import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel

/**
 * Resource for fetching data about a character from a data source (usually remote)
 */
interface CharacterRepository {
    suspend fun getCharacters(): List<CharacterModel>

    operator fun plus(other: CharacterRepository) = object : CharacterRepository{
        override suspend fun getCharacters(): List<CharacterModel>
            = this@CharacterRepository.getCharacters() + other.getCharacters()
    }
}