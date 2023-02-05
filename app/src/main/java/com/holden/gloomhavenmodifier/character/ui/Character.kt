package com.holden.gloomhavenmodifier.character.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.holden.gloomhavenmodifier.character.model.CharacterModel

@Composable
fun Character(character: CharacterModel){
    Column {
        Text(fontWeight = FontWeight.Bold, text = character.title)
        Text(text = "cards:")
        LazyColumn(){
            items(character.specialtyCards){
                Text(text = it.description)
            }
        }
        Text(text = "perks:")
        LazyColumn{
            items(character.perks){ perk->
                Column(modifier = Modifier.border(2.dp, Color.Black)) {
                    Row {
                        Text(text = "add: ")
                        for (add in perk.add){
                            Text(text = add)
                        }
                    }
                    Row {
                        Text(text = "remove: ")
                        for (remove in perk.remove){
                            Text(text = remove)
                        }
                    }
                }
            }
        }
    }
}