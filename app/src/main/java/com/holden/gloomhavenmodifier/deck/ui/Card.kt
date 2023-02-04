package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.deck.model.CardModel

val CARD_ASPECT_RATIO = 408f/266f

@Composable
fun Card(modifier: Modifier = Modifier, card: CardModel) {
    Image(
        modifier = modifier,
        painter = painterResource(id = card.imageRes),
        contentDescription = "card: ${card.description}",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun BackOfCard(modifier: Modifier = Modifier) =
    Card(modifier = modifier, card = CardModel("back of card", R.drawable.back, false))