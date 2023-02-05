package com.holden.gloomhavenmodifier.character.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.holden.gloomhavenmodifier.character.LocalCharacterRepo
import com.holden.gloomhavenmodifier.character.model.CharacterModel

/**
 * Example view for showing how character loading works
 */
@Composable
fun CharacterList(){
    var characterList by remember {
        mutableStateOf<List<CharacterModel>?>(null)
    }
    var chosenCharacter by remember {
        mutableStateOf<CharacterModel?>(null)
    }
    val assets = LocalContext.current.assets
    LaunchedEffect(Unit){
        characterList = LocalCharacterRepo(assets).getCharacters()
    }
    val characters = characterList
    if(characters == null){
        CircularProgressIndicator()
    } else {
        Column {
            CharacterButtonList(characterList = characters, onCharacterSelected = {
                chosenCharacter = it
            })
            val chosen = chosenCharacter
            if (chosen != null) {
                Character(character = chosen)
            }
        }
    }
}

@Composable
fun CharacterButtonList(characterList: List<CharacterModel>, onCharacterSelected: (CharacterModel)->Unit){
    Row {
        for (character in characterList){
            Button(onClick = { onCharacterSelected(character) }) {
                Text(text = character.title)
            }
        }
    }
}