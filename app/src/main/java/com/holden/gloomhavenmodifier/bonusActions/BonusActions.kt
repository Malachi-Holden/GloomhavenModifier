package com.holden.gloomhavenmodifier.bonusActions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel
import com.holden.gloomhavenmodifier.util.ui.NumberPicker

@Composable
fun BonusActions(
    viewModel: DeckViewModel,
    onClose: ()->Unit
){
    val deck by viewModel.state.collectAsState()

    var showCleanDeckConfirmation by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
            .clickable { onClose() }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(0.8f)
                .background(color = Color.White.copy(alpha = 0.95f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, "close")
                }
            }

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

            Button(onClick = { showCleanDeckConfirmation = true }) {
                Text(text = "Clean Deck")
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    onClick = onClose
                ) {
                    Text(text = "Ok")
                }
            }
        }


        if (showCleanDeckConfirmation) {
            CleanDeckConfirmation(
                modifier = Modifier.align(Alignment.Center),
                onCancel = { showCleanDeckConfirmation = false },
                onConfirm = {
                    viewModel.cleanDeck()
                    showCleanDeckConfirmation = false
                }
            )
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