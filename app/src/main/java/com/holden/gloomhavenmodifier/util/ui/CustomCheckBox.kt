package com.holden.gloomhavenmodifier.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.util.toInt

@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    checkColor: Color = MaterialTheme.colorScheme.onPrimary,
    checked: Boolean,
    onCheckedChange: (Boolean)->Unit,
    enabled: Boolean = true
){
    val borderColor = if(checked) Color.Transparent else color
    val borderWidth = 1.5*(!checked).toInt()
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(percent = 10))
            .toggleable(value = checked, role = Role.Checkbox, enabled = enabled) {
                onCheckedChange(it)
            }
            .background(if (checked) color else Color.Transparent)
            .border(width = borderWidth.dp, color = borderColor, RoundedCornerShape(percent = 10))
            .then(modifier)
            .size(25.dp),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.Check,
                contentDescription = "checked icon",
                tint = checkColor
            )
        }
    }
}

@Preview
@Composable
fun CustomCheckBoxPreviewChecked(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomCheckBox(modifier = Modifier.size(70.dp), checked = true, onCheckedChange = {}, enabled = true)
    }

}

@Preview
@Composable
fun CustomCheckBoxPreviewUnchecked(){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomCheckBox(modifier = Modifier.size(70.dp), checked = false, onCheckedChange = {}, enabled = true)
    }}