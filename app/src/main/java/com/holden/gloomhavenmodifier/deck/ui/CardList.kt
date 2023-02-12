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
    onClose: ()->Unit,
    cards: List<CardModel>,
    extraCardContent: @Composable (Int)->Unit
){
    var focusedCard by remember {
        mutableStateOf(-1)
    }
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    ClosableOverlay(onClose = onClose) {
        LazyColumn(state = lazyListState) {
            items(cards.size){
                Column(modifier = Modifier.combinedClickable(onClick = {}, onLongClick = {
                    focusedCard = if (focusedCard == cards.size - it - 1) {
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
                        card = cards[cards.size - it - 1]
                    )
                    if (focusedCard == it){
                        extraCardContent(cards.size - it - 1)
                    }
                }

            }
        }
    }
}