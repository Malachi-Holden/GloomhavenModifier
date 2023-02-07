package com.holden.gloomhavenmodifier.deck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.deck.model.DeckModel


@Composable
fun CardHistory(
    onClose: ()->Unit,
    deck: DeckModel
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background.copy(alpha = 0.5f))
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { onClose() })
        Column(
            modifier = Modifier
                .fillMaxSize(0.8f)
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
                .align(Alignment.Center)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                IconButton(
                    modifier = Modifier.padding(5.dp),
                    onClick = onClose
                ) {
                    Icon(Icons.Default.Close, "close")
                }
            }
            LazyColumn(){
                items(deck.drawn()){
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(CARD_ASPECT_RATIO)
                            .padding(10.dp),
                        card = deck.drawnCards()[deck.drawn() - it - 1]
                    )
                }
            }
        }

    }
}