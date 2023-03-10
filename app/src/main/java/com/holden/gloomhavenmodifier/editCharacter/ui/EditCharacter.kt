package com.holden.gloomhavenmodifier.editCharacter.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.holden.gloomhavenmodifier.LocalComponentActivity
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterViewModel
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.editCharacter.model.Perk
import com.holden.gloomhavenmodifier.util.toInt
import com.holden.gloomhavenmodifier.util.ui.CustomCheckBox

@Composable
fun EditCharacter(onSave: (CharacterModel)->Unit) {
    val characterViewModel: CharacterViewModel = hiltViewModel(LocalComponentActivity.current)
    val character by characterViewModel.currentCharacterState.collectAsState()
    val appliedPerkDescs = character.appliedPerks.groupingBy { it.description }.eachCount()
    val selectedPerks = remember {
        mutableStateMapOf<Perk, Int>().apply {
            putAll(character.perks.associateWith { appliedPerkDescs[it.description] ?: 0 })
        }
    }

    var showSaveConfirmationDialogue by remember {
        mutableStateOf(false)
    }

    Box {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(modifier = Modifier.weight(1.0f).fillMaxWidth()) {
                items(character.perks) { perk ->
                    PerkCheckRow(
                        perk = perk,
                        checked = selectedPerks[perk] ?: 0,
                        onCheckChanged = { selectedPerks[perk] = it }
                    )
                }
            }
            Button(
                modifier = Modifier.padding(10.dp).fillMaxWidth(.65f),
                onClick = {
                    showSaveConfirmationDialogue = true
                }) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = "Save"
                )
            }

        }
        if (showSaveConfirmationDialogue){
            AlertDialog(
                modifier = Modifier.align(Alignment.Center),
                onDismissRequest = { showSaveConfirmationDialogue = false },
                dismissButton = {
                    Button(onClick = { showSaveConfirmationDialogue = false }) {
                        Text(text = stringResource(R.string.cancel))
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        onSave(
                            character.copy(
                                appliedPerks = selectedPerks.flatMap {entry-> List(entry.value){entry.key} }
                            )
                        )
                    }) {
                        Text(text = stringResource(R.string.do_it))
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

@Composable
fun PerkCheckRow(perk: Perk, checked: Int, onCheckChanged: (Int)->Unit){
    Row(
        modifier = Modifier.padding(5.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(perk.count){ i->
            CustomCheckBox(
                modifier = Modifier.size(30.dp),
                checked = i < checked,
                onCheckedChange = { onCheckChanged(checked + 2 * it.toInt() - 1) }
            )
        }
        Text(
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            text = perk.description
        )
    }
}

@Preview
@Composable
fun PerkCheckRowPreview(){
    var checked by remember {
        mutableStateOf(2)
    }
    PerkCheckRow(perk = Perk("hello perks", 3, listOf(), listOf()), checked = checked, onCheckChanged = { checked = it })
}