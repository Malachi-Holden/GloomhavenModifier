package com.holden.gloomhavenmodifier.editCharacter.ui

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.editCharacter.model.Perk
import com.holden.gloomhavenmodifier.util.toInt
import com.holden.gloomhavenmodifier.util.ui.CustomCheckBox

@Composable
fun EditCharacter(character: CharacterModel, onSave: (CharacterModel)->Unit) {
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
        Column(modifier = Modifier.fillMaxSize()) {
            Text(fontWeight = FontWeight.Bold, text = character.title)

            Text(text = "Perksheet")
            LazyColumn {
                items(character.perks) { perk ->
                    PerkCheckRow(
                        perk = perk,
                        checked = selectedPerks[perk] ?: 0,
                        onCheckChanged = { selectedPerks[perk] = it }
                    )
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
                                appliedPerks = selectedPerks.flatMap {entry-> List(entry.value){entry.key} }
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

@Composable
fun PerkCheckRow(perk: Perk, checked: Int, onCheckChanged: (Int)->Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
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
    PerkCheckRow(perk = Perk("", 3, listOf(), listOf()), checked = checked, onCheckChanged = { checked = it })
}