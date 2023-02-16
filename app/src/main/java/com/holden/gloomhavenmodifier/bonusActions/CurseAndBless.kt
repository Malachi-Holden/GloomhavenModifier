package com.holden.gloomhavenmodifier.bonusActions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.R
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(R.string.blesses)
        )
        NumberPicker(
            modifier = Modifier.padding(10.dp),
            value = blesses,
            onUp = { addBless() },
            onDown = { removeBless() })
        NumberPicker(
            modifier = Modifier.padding(10.dp),
            value = curses,
            onUp = { addCurse() },
            onDown = { removeCurse() })
        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(R.string.curses)
        )
    }

}