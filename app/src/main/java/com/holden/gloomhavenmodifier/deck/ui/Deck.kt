package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.holden.gloomhavenmodifier.LocalComponentActivity
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.bonusActions.CurseAndBless
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterViewModel
import com.holden.gloomhavenmodifier.deck.DeckRepository
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel
import com.holden.gloomhavenmodifier.util.ui.ClosableOverlay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Deck() {
    val deckViewModel: DeckViewModel = hiltViewModel(LocalComponentActivity.current)
    val deck by deckViewModel.state.collectAsState()

    val characterViewModel: CharacterViewModel = hiltViewModel(LocalComponentActivity.current)
    val character by characterViewModel.currentCharacterState.collectAsState()

    var showCardHistory by remember {
        mutableStateOf(false)
    }
    var showDeckInternal by remember {
        mutableStateOf(false)
    }
    var showRevealDeckWarning by remember {
        mutableStateOf(false)
    }
    Box {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(5.dp),
            text = character.title,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val cardModifier = Modifier
                .weight(1f, fill = false)
                .fillMaxHeight()
                .aspectRatio(CARD_ASPECT_RATIO)
                .padding(10.dp)

            Text(text = stringResource(id = R.string.remaining_cards, deck.remaining()))
            if (deck.remaining() > 0) {
                BackOfCard(modifier = cardModifier.combinedClickable(
                    onClick = {},
                    onLongClick = { showRevealDeckWarning = true }
                ))
            } else {
                CardSlot(modifier = cardModifier, text = stringResource(R.string.no_cards_remaining))
            }

            Text(text = stringResource(id = R.string.drawn_cards, deck.drawn()))
            deck.mostRecentlyPlayed()?.let {
                Card(
                    modifier = cardModifier.clickable { showCardHistory = true  },
                    card = it)
            }
            if (deck.mostRecentlyPlayed() == null) {
                CardSlot(modifier = cardModifier, text = stringResource(R.string.empty_discard))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { deckViewModel.shuffle() }) {
                    Text(text = stringResource(R.string.shuffle))
                }
                Button(onClick = { deckViewModel.draw() }) {
                    Text(text = stringResource(R.string.draw))
                }
            }
            Text(
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                text = if (deck.needsShuffle) stringResource(R.string.needs_reshuffling) else ""
            )

            CurseAndBless(
                blesses = deck.blesses,
                curses = deck.curses,
                addBless = { deckViewModel.insertBless() },
                addCurse = { deckViewModel.insertCurse() },
                removeBless = { deckViewModel.removeBless() },
                removeCurse = { deckViewModel.removeCurse() }
            )
        }

        DiscardPile(showCardHistory, deck, deckViewModel, onClose = { showCardHistory = false })

        DeckRevealConfirmationDialogue(
            visible = showRevealDeckWarning,
            modifier = Modifier.align(Alignment.Center),
            onCancel = { showRevealDeckWarning = false },
            onConfirmed = {
                showRevealDeckWarning = false
                showDeckInternal = true
            }
        )

        RemainingCards(showDeckInternal, deck, deckViewModel, onClose = { showDeckInternal = false })
    }
}

@Composable
private fun DiscardPile(
    showCardHistory: Boolean,
    deck: DeckModel,
    deckViewModel: DeckViewModel,
    onClose: () -> Unit
) {
    if (showCardHistory) {
        val cardState = rememberCardState()
        ClosableOverlay(onClose = {
            cardState.hideExtraContent()
            onClose()
        }) {
            CardList(
                cards = deck.drawnCards(),
                cardState = cardState,
                reverseLayout = true,
                extraCardContent = {
                    Button(onClick = {
                        deckViewModel.undrawCard(it)
                        cardState.hideExtraContent()
                    }) {
                        Text(text = stringResource(R.string.return_card))
                    }
                }
            )
        }
    }
}

@Composable
private fun RemainingCards(
    showDeckInternal: Boolean,
    deck: DeckModel,
    deckViewModel: DeckViewModel,
    onClose: ()->Unit
) {
    if (showDeckInternal) {
        val cardState = rememberCardState()
        ClosableOverlay(onClose = {
            cardState.hideExtraContent()
            onClose()
        }) {
            Column {
                CardList(
                    modifier = Modifier.weight(1.0f),
                    cards = deck.remainingCards(),
                    cardState = cardState,
                    reverseLayout = false,
                    extraCardContent = {
                        Button(onClick = {
                            deckViewModel.draw(it + deck.drawn())
                            cardState.hideExtraContent()
                        }) {
                            Text(text = stringResource(R.string.draw_this))
                        }
                    }
                )
                Button(onClick = {
                    deckViewModel.shuffleRemainingCards()
                    cardState.hideExtraContent()
                    onClose()
                }) {
                    Text(text = stringResource(id = R.string.shuffle_remaining))
                }
            }
        }

    }
}


@Composable
fun DeckRevealConfirmationDialogue(visible: Boolean, modifier: Modifier = Modifier, onCancel: ()->Unit, onConfirmed: () -> Unit){
    if (visible){
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onCancel,
            confirmButton = {
                Button(onClick = onConfirmed) {
                    Text(text = stringResource(R.string.do_it))
                }
            },
            dismissButton = {
                Button(onClick = onCancel) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            title = {
                Text(text = stringResource(R.string.reveal_deck_question))
            },
            text = {
                Text(text = stringResource(R.string.reveal_deck_warning_body))
            }
        )
    }

}

@Composable
fun CardSlot(
    modifier: Modifier = Modifier,
    text: String
){
    Box(
        modifier = modifier
    ){
        Text(modifier = Modifier.align(Alignment.Center), text = text, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun RemainingCardsPreview(){
    val deck = DeckRepository.getDefaultDeck()
    RemainingCards(showDeckInternal = true, deck = deck, deckViewModel = DeckViewModel(deck), onClose = {})
}