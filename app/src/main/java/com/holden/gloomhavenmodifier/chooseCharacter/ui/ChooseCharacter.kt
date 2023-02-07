package com.holden.gloomhavenmodifier.chooseCharacter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterState
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterViewModel
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel

@Composable
fun ChooseCharacter(
    viewModel: CharacterViewModel = hiltViewModel(),
    onChosen: (CharacterModel)->Unit
){
    var chosenCharacter by remember {
        mutableStateOf<CharacterModel?>(null)
    }
    var showChosenCharacterConfirmation by remember {
        mutableStateOf(false)
    }
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Choose a new character class", fontWeight = FontWeight.Bold)
            when (val local = viewModel.localCharacterState.collectAsState().value){
                is CharacterState.Loading -> CircularProgressIndicator()
                is CharacterState.Error -> Text(text = "Error loading local characters")
                is CharacterState.Loaded -> CharacterOptions(characters = local.characters, onChooseCharacter = {
                    chosenCharacter = it
                    showChosenCharacterConfirmation = true
                })
            }
            when (val remote = viewModel.remoteCharacterState.collectAsState().value){
                is CharacterState.Loading -> CircularProgressIndicator()
                is CharacterState.Error -> Text(text = "Error loading remote characters")
                is CharacterState.Loaded -> CharacterOptions(characters = remote.characters, onChooseCharacter = {
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