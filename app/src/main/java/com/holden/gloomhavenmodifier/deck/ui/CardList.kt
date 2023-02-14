package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.util.ui.ClosableOverlay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardList(
    cards: List<CardModel>,
    reverseLayout: Boolean,
    cardState: CardState = rememberCardState(),
    extraCardContent: @Composable (Int)->Unit
){
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var focusedCard by (cardState as CardListCardState).focusedCard
    LazyColumn(state = lazyListState, reverseLayout = reverseLayout) {
        items(cards.size){
            Column(modifier = Modifier.combinedClickable(onClick = {}, onLongClick = {
                focusedCard = if (focusedCard == it) {
                    -1
                } else {
                    scope.launch { lazyListState.scrollToItem(it) }
                    it
                }
            })){
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(CARD_ASPECT_RATIO)
                        .padding(10.dp),
                    card = cards[it]
                )
                if (focusedCard == it){
                    extraCardContent(it)
                }
            }

        }
    }
}

@Composable
fun rememberCardState(): CardState = remember {
    CardListCardState(-1)
}

interface CardState{
    fun hideExtraContent()
}

class CardListCardState(initialVal: Int): CardState{
    val focusedCard = mutableStateOf(initialVal)
    override fun hideExtraContent() {
        focusedCard.value = -1
    }
}