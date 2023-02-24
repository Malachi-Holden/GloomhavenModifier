package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.deck.BaseCard
import com.holden.gloomhavenmodifier.deck.model.CardModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedDeck(
    modifier: Modifier = Modifier,
    backOfCard: CardModel? = BaseCard.BackOfCard.card,
    revealedCard: CardModel?,
    remainingCards: Int,
    drawnCards: Int,
    onRemainingLongTapped: ()->Unit,
    onDrawnTapped: ()->Unit
){
    var revealedIsHighlighted by remember {
        mutableStateOf(false )
    }
    var isFirstTime by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(remainingCards){
        if (isFirstTime) {
            isFirstTime = false
            return@LaunchedEffect
        }
        revealedIsHighlighted = true
        delay(100)
        revealedIsHighlighted = false
    }
    val alpha by animateFloatAsState(targetValue = if (revealedIsHighlighted) 0.5f else 0.0f)
    val tint = Color.White.copy(alpha = alpha)
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val cardModifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .aspectRatio(CARD_ASPECT_RATIO)
            .padding(10.dp)
        CardSlot(
            modifier = cardModifier
                .combinedClickable(
                    onClick = {},
                    onLongClick = onRemainingLongTapped
                ),
            card = backOfCard,
            numCards = remainingCards,
            emptyText = stringResource(R.string.no_cards_remaining)
        )
        CardSlot(
            modifier = cardModifier
                .clickable(onClick = onDrawnTapped),
            card = revealedCard,
            numCards = drawnCards,
            emptyText = stringResource(R.string.empty_discard),
            tintColor = tint
        )
    }
}


@Composable
fun CardSlot(
    modifier: Modifier = Modifier,
    emptyText: String,
    card: CardModel?,
    numCards: Int,
    tintColor: Color? = null
){
    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(.8f)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.large
                )
        ) {
            Text(modifier = Modifier
                .align(Alignment.Center),
                text = emptyText,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
            )
        }

        if (card != null){
            Card(card = card, modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(CARD_ASPECT_RATIO)
                .padding(10.dp)
                .align(Alignment.Center),
                tintColor = tintColor
            )
        }

        Text(
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomEnd)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.extraSmall
                )
                .defaultMinSize(minWidth = 25.dp)
                .padding(5.dp),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            text = "$numCards"
        )

    }
}

@Preview
@Composable
fun AnimatedDeckPreview(){
    AnimatedDeck(
        modifier = Modifier
            .fillMaxHeight()
            .border(2.dp, Color.Red),
        revealedCard = BaseCard.Crit.card,
        remainingCards = 24,
        drawnCards = 1,
        onRemainingLongTapped = {  },
        onDrawnTapped = {  }
    )
}