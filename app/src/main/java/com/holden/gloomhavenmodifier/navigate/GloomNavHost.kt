package com.holden.gloomhavenmodifier.navigate

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.editCharacter.model.getLocalCharacter
import com.holden.gloomhavenmodifier.editCharacter.model.saveLocalCharacter
import com.holden.gloomhavenmodifier.editCharacter.ui.EditCharacter
import com.holden.gloomhavenmodifier.chooseCharacter.ui.ChooseCharacter
import com.holden.gloomhavenmodifier.deck.getDefaultDeck
import com.holden.gloomhavenmodifier.deck.getLocalDeck
import com.holden.gloomhavenmodifier.deck.ui.Deck
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel

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
    val deckViewModel: DeckViewModel = viewModel(factory = DeckViewModel.Factory(getDefaultDeck()))
    LaunchedEffect(Unit){
        currentCharacter = getLocalCharacter()
        deckViewModel.updateDeck(currentCharacter.buildDeck())
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = GloomDestination.Deck.name
    ) {
        composable(GloomDestination.Deck.name) {
            Deck(deckViewModel)
        }
        composable(GloomDestination.Character.name) {
            EditCharacter(
                character = currentCharacter,
                onSave = {
                    currentCharacter = it
                    deckViewModel.updateDeck(currentCharacter.buildDeck())
                    saveLocalCharacter(currentCharacter)
                    navController.popBackStack()
                }
            )
        }
        composable(GloomDestination.ChooseCharacter.name) {
            ChooseCharacter(onChosen = { chosen->
                currentCharacter = chosen
                deckViewModel.updateDeck(currentCharacter.buildDeck())
                saveLocalCharacter(chosen)
                navController.popBackStack()
            })
        }
    }
}