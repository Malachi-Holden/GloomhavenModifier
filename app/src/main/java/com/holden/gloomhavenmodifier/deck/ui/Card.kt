package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.deck.BaseCard
import com.holden.gloomhavenmodifier.deck.model.CardModel

val CARD_ASPECT_RATIO = 408f / 266f

@Composable
fun Card(modifier: Modifier = Modifier, card: CardModel) {
    Image(
        modifier = modifier.clip(MaterialTheme.shapes.medium),
        painter = cardPainter(card = card),
        contentDescription = stringResource(id = R.string.card_content_description, card.description),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun cardPainter(card: CardModel): Painter {
    val activity = LocalContext.current
    val id = activity.resources.getIdentifier(
        card.imageRes,
        "drawable",
        "com.holden.gloomhavenmodifier"
    )
    if (id == 0) return painterResource(id = R.drawable.card_not_found)
    return painterResource(
        id = id
    )
}