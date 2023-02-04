package com.holden.gloomhavenmodifier.navigate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.holden.gloomhavenmodifier.character.ui.Character
import com.holden.gloomhavenmodifier.deck.getLocalDeck
import com.holden.gloomhavenmodifier.deck.model.CardModel
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import com.holden.gloomhavenmodifier.deck.ui.Deck
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel

enum class GloomDestination {
    Deck,
    Character,
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
            Character()
        }
    }