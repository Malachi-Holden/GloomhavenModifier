package com.holden.gloomhavenmodifier.util.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberPicker(modifier: Modifier = Modifier, value: Int, onUp: ()->Unit, onDown: ()->Unit){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onUp
        ) {
            Icon(
                modifier = modifier.size(70.dp),
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "add"
            )
        }

        Text(text = "$value", fontSize = 25.sp)

        IconButton(
            onClick = onDown
        ) {
            Icon(
                modifier = modifier.size(70.dp),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "minus"
            )
        }
    }
}

@Preview
@Composable
fun NumberPickerPreview(){
    NumberPicker(value = 4, onUp = {}, onDown = {})
}