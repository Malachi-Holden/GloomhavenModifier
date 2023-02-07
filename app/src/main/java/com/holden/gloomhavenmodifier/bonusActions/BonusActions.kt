package com.holden.gloomhavenmodifier.bonusActions

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel
import com.holden.gloomhavenmodifier.util.ui.NumberPicker

@Composable
fun BonusActions(
//    viewModel: DeckViewModel,
    showCleanDeckDialog: ()->Unit
){
    val viewModel: DeckViewModel = hiltViewModel()
    val deck by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .padding(end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CurseAndBless(
            blesses = deck.blesses,
            curses = deck.curses,
            addBless = { viewModel.insertBless() },
            addCurse = { viewModel.insertCurse() },
            removeBless = { viewModel.removeBless() },
            removeCurse = { viewModel.removeCurse() }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            NumberPicker(
                value = deck.bonusMinuses,
                onUp = { viewModel.insertBonusMinus() },
                onDown = { viewModel.removeBonusMinus() })
            Text(text = "Bonus Minuses (scenario and item effects)")
        }

        Button(onClick = showCleanDeckDialog) {
            Text(text = "Clean Deck")
        }
    }
}


@Composable
fun CleanDeckConfirmation(modifier: Modifier = Modifier, onCancel: ()->Unit, onConfirm: ()->Unit){
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onCancel,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Do it")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = "Cancel")
            }
        },
        title = {
            Text(text = "Clean the deck?")
        },
        text = {
            Text(text = "This will remove any blesses, curses, or bonus -1s (e.g. from scenario effects)")
        }
    )
}