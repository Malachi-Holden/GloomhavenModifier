package com.holden.gloomhavenmodifier.character.model

suspend fun getLocalCharacter()
    = CharacterModel.NoClass // only if there is no local character

fun saveLocalCharacter(character: CharacterModel){
    // write serialized character to disk
}