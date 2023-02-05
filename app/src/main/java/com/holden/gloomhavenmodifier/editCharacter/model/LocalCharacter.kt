package com.holden.gloomhavenmodifier.editCharacter.model

import android.content.Context
import com.holden.gloomhavenmodifier.deck.GLOOM_CHARACTER_FILE
import com.holden.gloomhavenmodifier.getLocalGloomObject
import com.holden.gloomhavenmodifier.saveLocalGloomObject


fun saveLocalCharacter(context: Context, character: CharacterModel){
    saveLocalGloomObject(context, character, GLOOM_CHARACTER_FILE)
}

fun getLocalCharacter(context: Context): CharacterModel{
    return getLocalGloomObject(context, GLOOM_CHARACTER_FILE) ?: CharacterModel.NoClass
}