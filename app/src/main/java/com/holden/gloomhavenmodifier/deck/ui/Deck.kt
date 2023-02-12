package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.holden.gloomhavenmodifier.LocalComponentActivity
import com.holden.gloomhavenmodifier.bonusActions.CurseAndBless
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterViewModel
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel

@Composable
fun Deck() {
    val deckViewModel: DeckViewModel = hiltViewModel(LocalComponentActivity.current)
    val deck by deckViewModel.state.collectAsState()

    val characterViewModel: CharacterViewModel = hiltViewModel(LocalComponentActivity.current)
    val character by characterViewModel.currentCharacterState.collectAsState()

    var showCardHistory by remember {
        mutableStateOf(false)
    }
    val cardState = rememberCardState()
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

            Text(text = "remaining: ${deck.remaining()}")
            if (deck.remaining() > 0) {
                BackOfCard(modifier = cardModifier)
            } else {
                CardSlot(modifier = cardModifier, text = "no cards remaining")
            }

            Text(text = "drawn: ${deck.drawn()}")
            deck.mostRecentlyPlayed()?.let {
                Card(
                    modifier = cardModifier.clickable { showCardHistory = true  },
                    card = it)
            }
            if (deck.mostRecentlyPlayed() == null) {
                CardSlot(modifier = cardModifier, text = "empty discard")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { deckViewModel.shuffle() }) {
                    Text(text = "Shuffle")
                }
                Button(onClick = { deckViewModel.draw() }) {
                    Text(text = "Draw")
                }
            }
            Text(
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                text = if (deck.needsShuffle) "Needs Reshuffling!" else ""
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
        if (showCardHistory){
            CardList(
                onClose = { showCardHistory = false },
                cards = deck.drawnCards(),
                cardState = cardState,
                extraCardContent = {
                    Button(onClick = {
                        deckViewModel.undrawCard(it)
                        cardState.hideExtraContent()
                    }) {
                        Text(text = "Return card to deck")
                    }
                }
            )
        }
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