package com.holden.gloomhavenmodifier.chooseCharacter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.holden.gloomhavenmodifier.LocalComponentActivity
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterState
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterViewModel
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel

@Composable
fun ChooseCharacter(
    onChosen: (CharacterModel)->Unit
){
    val viewModel: CharacterViewModel = hiltViewModel(LocalComponentActivity.current)
    var chosenCharacter by remember {
        mutableStateOf<CharacterModel?>(null)
    }
    var showChosenCharacterConfirmation by remember {
        mutableStateOf(false)
    }
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = stringResource(R.string.choose_class), fontWeight = FontWeight.Bold)
            when (val local = viewModel.localCharacterListState.collectAsState().value){
                is CharacterState.Loading -> CircularProgressIndicator()
                is CharacterState.Error -> Text(text = stringResource(R.string.error_local_characters))
                is CharacterState.Loaded -> CharacterOptions(characters = local.characters, onChooseCharacter = {
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
                Text(text = stringResource(R.string.do_it))
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        title = {
            Text(text = stringResource(R.string.replace_character_question))
        },
        text = {
            Text(text = stringResource(R.string.replace_character_warning_body))
        }
    )
}