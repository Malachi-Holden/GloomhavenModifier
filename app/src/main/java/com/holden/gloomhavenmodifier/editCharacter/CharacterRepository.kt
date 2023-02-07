package com.holden.gloomhavenmodifier.editCharacter

import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterState
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel

/**
 * Resource for fetching data about a character from a data source (usually remote)
 */
interface CharacterRepository {
    suspend fun getCharacters(): CharacterState
}