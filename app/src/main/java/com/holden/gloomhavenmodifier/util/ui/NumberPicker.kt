package com.holden.gloomhavenmodifier.util.ui

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NumberPicker(modifier: Modifier = Modifier, value: Int, onUp: ()->Unit, onDown: ()->Unit){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onUp) {
            Icon(painterResource(id = R.drawable.arrow_up_float), contentDescription = "plus" )
        }

        Text(text = "$value")

        IconButton(onClick = onDown) {
            Icon(painterResource(id = R.drawable.arrow_down_float), contentDescription = "minus" )
        }
    }
}

@Preview
@Composable
fun NumberPickerPreview(){
    NumberPicker(value = 4, onUp = {}, onDown = {})
}