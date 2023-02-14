package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        modifier = modifier,
        painter = cardPainter(card = card),
        contentDescription = stringResource(id = R.string.card_content_description, card.description),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun cardPainter(card: CardModel): Painter {
    val activity = LocalContext.current
    if (card.resourceType == "url") {
        return rememberAsyncImagePainter(model = card.imageUrl)
    }
    if (card.resourceType == "local") {
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
    return painterResource(id = R.drawable.card_not_found)
}

@Composable
fun BackOfCard(modifier: Modifier = Modifier) =
    Card(modifier = modifier, card = BaseCard.BackOfCard.card)