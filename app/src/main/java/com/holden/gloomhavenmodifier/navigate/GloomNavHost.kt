package com.holden.gloomhavenmodifier.navigate

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.holden.gloomhavenmodifier.LocalComponentActivity
import com.holden.gloomhavenmodifier.chooseCharacter.ui.ChooseCharacter
import com.holden.gloomhavenmodifier.deck.DeckRepository
import com.holden.gloomhavenmodifier.deck.ui.Deck
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel
import com.holden.gloomhavenmodifier.editCharacter.CharacterRepository
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import com.holden.gloomhavenmodifier.editCharacter.ui.EditCharacter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
    val deckViewModel: DeckViewModel = hiltViewModel(LocalComponentActivity.current)
    val context = LocalContext.current
    var currentCharacter by remember {
        mutableStateOf(CharacterRepository.getLocalCharacter(context))
    }
    LaunchedEffect(Unit){
        deckViewModel.state.onEach { DeckRepository.saveLocalDeck(context, it) }
            .launchIn(this)
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = GloomDestination.Deck.name
    ) {
        composable(GloomDestination.Deck.name) {
            Deck()
        }
        composable(GloomDestination.Character.name) {
            EditCharacter(
                character = currentCharacter,
                onSave = { chosen->
                    currentCharacter = chosen
                    updateCharacter(chosen, context, deckViewModel, navController)
                }
            )
        }
        composable(GloomDestination.ChooseCharacter.name) {
            ChooseCharacter(onChosen = { chosen->
                currentCharacter = chosen
                updateCharacter(chosen, context, deckViewModel, navController)
            })
        }
    }
}

fun updateCharacter(character: CharacterModel, context: Context, deckViewModel: DeckViewModel, navController: NavHostController){
    val deck = character.buildDeck()
    deckViewModel.updateDeck(deck)
    CharacterRepository.saveLocalCharacter(context, character)
    DeckRepository.saveLocalDeck(context, deck)
    navController.popBackStack()
}