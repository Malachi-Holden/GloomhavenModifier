package com.holden.gloomhavenmodifier.editCharacter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.editCharacter.model.Perk

@Composable
fun EditCharacter(character: CharacterModel, onSave: (CharacterModel)->Unit) {
    val appliedPerkDescs = character.appliedPerks.map { it.description }.toSet()
    val selectedPerks = remember {
        mutableStateMapOf<Perk, Boolean>().apply {
            putAll(character.perks.associate { it to appliedPerkDescs.contains(it.description) })
        }
    }

    var showSaveConfirmationDialogue by remember {
        mutableStateOf(false)
    }

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(fontWeight = FontWeight.Bold, text = character.title)

            Text(text = "Perksheet")
            LazyColumn {
                items(character.perks) { perk ->
                    Row {
                        Checkbox(
                            checked = selectedPerks[perk] ?: false,
                            onCheckedChange = { selectedPerks[perk] = it })
                        Text(text = perk.description)
                    }
                }
            }
            Button(onClick = {
                showSaveConfirmationDialogue = true
            }) {
                Text(text = "Save")
            }

        }
        if (showSaveConfirmationDialogue){
            AlertDialog(
                modifier = Modifier.align(Alignment.Center),
                onDismissRequest = { showSaveConfirmationDialogue = false },
                dismissButton = {
                    Button(onClick = { showSaveConfirmationDialogue = false }) {
                        Text(text = "Cancel")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        onSave(
                            character.copy(
                                appliedPerks = selectedPerks.filter{ it.value }.keys.toList()
                            )
                        )
                    }) {
                        Text(text = "Do it")
                    }
                },
                title = {
                    Text(text = "Unshuffle deck?")
                },
                text = {
                    Text(text = "Changing your perks will reset your deck to unshuffled")
                }
            )
        }
    }

}