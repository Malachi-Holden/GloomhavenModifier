package com.holden.gloomhavenmodifier.editCharacter

import android.content.Context
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterState
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.getLocalGloomObject
import com.holden.gloomhavenmodifier.saveLocalGloomObject

/**
 * Resource for fetching data about a character from a data source (usually remote)
 */
interface CharacterRepository {
    suspend fun getCharacters(): CharacterState

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