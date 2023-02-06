package com.holden.gloomhavenmodifier.chooseCharacter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.holden.gloomhavenmodifier.editCharacter.BuiltInCharacterRepo
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.holden.gloomhavenmodifier.editCharacter.RemoteCharacterRepo

@Composable
fun ChooseCharacter(onChosen: (CharacterModel)->Unit){
    var localCharacters by remember {
        mutableStateOf<List<CharacterModel>?>(null)
    }
    var remoteCharacters by remember {
        mutableStateOf<List<CharacterModel>?>(null)
    }
    var chosenCharacter by remember {
        mutableStateOf<CharacterModel?>(null)
    }
    var showChosenCharacterConfirmation by remember {
        mutableStateOf(false)
    }
    val assets = LocalContext.current.assets
    val localRepo = remember {
        BuiltInCharacterRepo(assets)
    }

    val remoteRepo = remember {
        RemoteCharacterRepo()
    }
    LaunchedEffect(Unit){
        localCharacters = localRepo.getCharacters()
    }
    LaunchedEffect(Unit){
        remoteCharacters = remoteRepo.getCharacters()
    }
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Choose a new character class", fontWeight = FontWeight.Bold)
            val locals = localCharacters
            if(locals == null){
                CircularProgressIndicator()
            } else{
                CharacterOptions(characters = locals, onChooseCharacter = {
                    chosenCharacter = it
                    showChosenCharacterConfirmation = true
                })
            }

            val remotes = remoteCharacters
            if (remotes == null){
                CircularProgressIndicator()
            } else {
                CharacterOptions(characters = remotes, onChooseCharacter = {
                    chosenCharacter = it
                    showChosenCharacterConfirmation = true
                })
            }
        }
        if(showChosenCharacterConfirmation) {
            CharacterChoiceConfirmationDialogue(
                modifier = Modifier.align(Alignment.Center),
                onCancel = { showChosenCharacterConfirmation = false },
                onChosen = { chosenCharacter?.let(onChosen) }
            )
        }
    }
}

@Composable
fun CharacterOptions(characters: List<CharacterModel>, onChooseCharacter: (CharacterModel)->Unit){
    LazyColumn{
        items(characters){
            Button(onClick = {
                onChooseCharacter(it)
            }) {
                Text(text = it.title)
            }
        }
    }
}

@Composable
fun CharacterChoiceConfirmationDialogue(modifier: Modifier = Modifier, onCancel: ()->Unit, onChosen: () -> Unit){
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onCancel,
        confirmButton = {
            Button(onClick = onChosen) {
                Text(text = "Do it")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = "Cancel")
            }
        },
        title = {
            Text(text = "Replace current character?")
        },
        text = {
            Text(text = "This will override any saved progress on your current character and will reset the deck to unshuffled")
        }
    )
}