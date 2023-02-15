package com.holden.gloomhavenmodifier.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClosableOverlay(
    onClose: ()->Unit,
    content: @Composable ()->Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { onClose() })
        Column(
            modifier = Modifier
                .fillMaxSize(0.8f)
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
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

            content()
        }

    }
}