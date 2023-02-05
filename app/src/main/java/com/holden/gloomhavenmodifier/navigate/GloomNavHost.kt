package com.holden.gloomhavenmodifier.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.holden.gloomhavenmodifier.character.ui.CharacterList
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
) =
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = GloomDestination.Deck.name
    ) {
        composable(GloomDestination.Deck.name) {
            Deck(getLocalDeck())
        }
        composable(GloomDestination.Character.name) {
            CharacterList()
        }
        composable(GloomDestination.ChooseCharacter.name){
            ChooseCharacter(onChosen = {
                navController.popBackStack()
            })
        }
    }