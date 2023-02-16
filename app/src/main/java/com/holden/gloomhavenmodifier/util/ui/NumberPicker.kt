package com.holden.gloomhavenmodifier.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberPicker(modifier: Modifier = Modifier, value: Int, onUp: ()->Unit, onDown: ()->Unit){
    Column(
        modifier = modifier.background(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.secondaryContainer
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = onUp
        ) {
            Icon(
                modifier = modifier.size(70.dp),
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "add",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Text(
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            text = "$value",
            style = MaterialTheme.typography.titleMedium
        )

        IconButton(
            onClick = onDown
        ) {
            Icon(
                modifier = modifier.size(70.dp),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "minus",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview
@Composable
fun NumberPickerPreview(){
    NumberPicker(value = 4, onUp = {}, onDown = {})
}