package com.holden.gloomhavenmodifier.chooseCharacter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.holden.gloomhavenmodifier.character.LocalCharacterRepo
import com.holden.gloomhavenmodifier.character.model.CharacterModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ChooseCharacter(onChosen: (CharacterModel)->Unit){
    var characterList by remember {
        mutableStateOf<List<CharacterModel>?>(null)
    }
    var chosenCharacter by remember {
        mutableStateOf<CharacterModel?>(null)
    }
    var showChosenCharacterConfirmation by remember {
        mutableStateOf(false)
    }
    val assets = LocalContext.current.assets
    LaunchedEffect(Unit){
        characterList = LocalCharacterRepo(assets).getCharacters()
    }
    val characters = characterList
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Choose a new character class")
            if(characters == null){
                CircularProgressIndicator()
            } else{
                LazyColumn{
                    items(characters){
                        Button(onClick = {
                            chosenCharacter = it
                            showChosenCharacterConfirmation = true
                        }) {
                            Text(text = it.title)
                        }
                    }
                }
            }
        }
        if(showChosenCharacterConfirmation) {
            AlertDialog(
                modifier = Modifier.align(Alignment.Center),
                onDismissRequest = { showChosenCharacterConfirmation = false },
                confirmButton = {
                    Button(onClick = { chosenCharacter?.let(onChosen) }) {
                        Text(text = "Do it")
                    }
                },
                dismissButton = {
                    Button(onClick = { showChosenCharacterConfirmation = false }) {
                        Text(text = "Cancel")
                    }
                },
                title = {
                    Text(text = "Replace current character?")
                },
                text = {
                    Text(text = "This will override any saved progress on your current character")
                }
            )
        }
    }
}