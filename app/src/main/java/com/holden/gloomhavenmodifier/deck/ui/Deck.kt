package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel

import com.holden.gloomhavenmodifier.R

@Composable
fun Deck(
    initialDeck: DeckModel,
    viewModel: DeckViewModel = viewModel(factory = DeckViewModel.Factory(initialDeck))
) {

    val deck by viewModel.state.collectAsState()
    val cardModifier = Modifier
        .aspectRatio(CARD_ASPECT_RATIO)
        .fillMaxWidth(.8f)
        .padding(10.dp)
    Row {

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text( text = "remaining: ${deck.remaining()}")
        if (deck.remaining() > 0) {
            BackOfCard(modifier = cardModifier)
        } else {
            CardSlot(text = "no cards remaining")
        }

        Text(text = "drawn: ${deck.drawn()}")
        deck.mostRecentlyPlayed()?.let {
            Card(modifier = cardModifier, card = it)
        }
        if (deck.mostRecentlyPlayed() == null) {
            CardSlot(text = "empty discard")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { viewModel.shuffle() }) {
                Text(text = "Shuffle")
            }
            Button(onClick = { viewModel.draw() }) {
                Text(text = "Draw")
            }
        }
        if(deck.needsShuffle){
            Text(
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                text = "Needs Reshuffle!"
            )
        }


    }
}

@Composable
fun CardSlot(
    text: String
){
    Box(
        modifier = Modifier
            .aspectRatio(CARD_ASPECT_RATIO)
            .fillMaxWidth(.8f)
            .padding(10.dp)
    ){
        Text(modifier = Modifier.align(Alignment.Center), text = text, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
@Preview
fun DeckPreview() {
    Deck(
        DeckModel(
                listOf(
                CardModel("+ 1", "zero", null, false)
            ),
            1
        )
    )
}