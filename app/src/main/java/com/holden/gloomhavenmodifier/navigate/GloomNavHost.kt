package com.holden.gloomhavenmodifier.navigate

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.holden.gloomhavenmodifier.character.model.CharacterModel
import com.holden.gloomhavenmodifier.character.model.getLocalCharacter
import com.holden.gloomhavenmodifier.character.model.saveLocalCharacter
import com.holden.gloomhavenmodifier.character.ui.Character
import com.holden.gloomhavenmodifier.chooseCharacter.ui.ChooseCharacter
import com.holden.gloomhavenmodifier.deck.getLocalDeck
import com.holden.gloomhavenmodifier.deck.ui.Deck

enum class GloomDestination {
    Deck,
    Character,
    ChooseCharacter
}

@Composable
fun GloomNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    var currentCharacter by remember {
        mutableStateOf(CharacterModel.NoClass)
    }
    LaunchedEffect(Unit){
        currentCharacter = getLocalCharacter()
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = GloomDestination.Deck.name
    ) {
        composable(GloomDestination.Deck.name) {
            Deck(getLocalDeck())
        }
        composable(GloomDestination.Character.name) {
            Character(character = currentCharacter)
        }
        composable(GloomDestination.ChooseCharacter.name) {
            ChooseCharacter(onChosen = { chosen->
                currentCharacter = chosen
                saveLocalCharacter(chosen)
                navController.popBackStack()
            })
        }
    }
}