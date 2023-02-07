package com.holden.gloomhavenmodifier.bonusActions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.holden.gloomhavenmodifier.util.ui.NumberPicker

@Composable
fun CurseAndBless(
    blesses: Int,
    curses: Int,
    addBless: ()->Unit,
    addCurse: ()->Unit,
    removeBless: ()->Unit,
    removeCurse: ()->Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            NumberPicker(
                value = blesses,
                onUp = { addBless() },
                onDown = { removeBless() })
            Text(text = "Blesses")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            NumberPicker(
                value = curses,
                onUp = { addCurse() },
                onDown = { removeCurse() })
            Text(text = "Curses")
        }
    }
}