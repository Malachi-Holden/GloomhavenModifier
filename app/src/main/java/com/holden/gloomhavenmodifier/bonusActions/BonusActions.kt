package com.holden.gloomhavenmodifier.bonusActions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.holden.gloomhavenmodifier.LocalComponentActivity
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel
import com.holden.gloomhavenmodifier.util.ui.NumberPicker

@Composable
fun BonusActions(
    showCleanDeckDialog: ()->Unit
){
    val viewModel: DeckViewModel = hiltViewModel(LocalComponentActivity.current)
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
                modifier = Modifier.padding(10.dp),
                value = deck.bonusMinuses,
                onUp = { viewModel.insertBonusMinus() },
                onDown = { viewModel.removeBonusMinus() })
            Column {
                Text(
                    text = stringResource(R.string.bonus_minuses)
                )
                Text(
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.bonus_minuses_explanation)
                )
            }
        }

        Button(onClick = { viewModel.shuffleRemainingCards() }) {
            Text(text = stringResource(R.string.shuffle_remaining))
        }

        Button(onClick = showCleanDeckDialog) {
            Text(text = stringResource(R.string.clean_deck))
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
                Text(text = stringResource(R.string.do_it))
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        title = {
            Text(text = stringResource(R.string.clean_the_deck_question))
        },
        text = {
            Text(text = stringResource(R.string.clean_deck_warning_body))
        }
    )
}

