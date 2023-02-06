package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel
import com.holden.gloomhavenmodifier.util.ui.NumberPicker

@Composable
fun Deck(
    viewModel: DeckViewModel
) {
    val deck by viewModel.state.collectAsState()
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
        Text( text = "remaining: ${deck.remaining()}")
        if (deck.remaining() > 0) {
            BackOfCard(modifier = cardModifier)
        } else {
            CardSlot(modifier = cardModifier, text = "no cards remaining")
        }

        Text(text = "drawn: ${deck.drawn()}")
        deck.mostRecentlyPlayed()?.let {
            Card(modifier = cardModifier, card = it)
        }
        if (deck.mostRecentlyPlayed() == null) {
            CardSlot(modifier = cardModifier, text = "empty discard")
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
        Text(
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            text = if (deck.needsShuffle) "Needs Reshuffling!" else ""
        )
        Row(
            modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                NumberPicker(value = deck.blesses, onUp = { viewModel.insertBless() }, onDown = {viewModel.removeBless()})
                Text(text = "Blesses")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                NumberPicker(value = deck.curses, onUp = { viewModel.insertCurse() }, onDown = {viewModel.removeCurse()})
                Text(text = "Curses")
            }
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