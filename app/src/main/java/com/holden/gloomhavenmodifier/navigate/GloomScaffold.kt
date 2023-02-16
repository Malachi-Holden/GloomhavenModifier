package com.holden.gloomhavenmodifier.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.holden.gloomhavenmodifier.LocalComponentActivity
import com.holden.gloomhavenmodifier.R
import com.holden.gloomhavenmodifier.bonusActions.BonusActions
import com.holden.gloomhavenmodifier.bonusActions.CleanDeckConfirmation
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterState
import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterViewModel
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomScaffold() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val deckViewModel: DeckViewModel = hiltViewModel(LocalComponentActivity.current)
    val characterViewModel: CharacterViewModel = hiltViewModel(LocalComponentActivity.current)
    val character by characterViewModel.currentCharacterState.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var showCleanDeckConfirmation by remember {
        mutableStateOf(false)
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                BonusActions(showCleanDeckDialog = { showCleanDeckConfirmation = true })
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = currentDestination?.scaffoldTitle(character) ?: "",
                            style = MaterialTheme.typography.displaySmall
                        )
                    },
                    navigationIcon = {
                        currentDestination?.currentIcon(
                            onOpenBonusActions = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            onBack = {
                                scope.launch {
                                    navController.popBackStack()
                                }
                            }
                        )
                    },
                    actions = {
                        when (currentDestination?.route) {
                            GloomDestination.Deck.name -> Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                ),
                                onClick = {
                                    navController.navigate(
                                        GloomDestination.Character.name
                                    )
                                }
                            ) {
                                Text(text = stringResource(R.string.edit_character))
                            }
                            GloomDestination.Character.name -> Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                ),
                                onClick = {
                                    navController.navigate(
                                        GloomDestination.ChooseCharacter.name
                                    )
                                }
                            ) {
                                Text(text = stringResource(R.string.new_character))
                            }
                        }
                    }
                )
            },
        ) {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize()) {
                GloomNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
                if (showCleanDeckConfirmation){
                    CleanDeckConfirmation(
                        onCancel = { showCleanDeckConfirmation = false },
                        onConfirm = {
                            deckViewModel.cleanDeck()
                            showCleanDeckConfirmation = false
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun NavDestination.scaffoldTitle(currentCharacter: CharacterModel?) =
    when(route){
        GloomDestination.Deck.name,
        GloomDestination.Character.name
            -> currentCharacter?.title
        GloomDestination.ChooseCharacter.name
            -> stringResource(R.string.choose_character)
        else -> ""
    }


@Composable
fun NavDestination.currentIcon(onOpenBonusActions: ()->Unit, onBack: ()->Unit){
    when (route) {
        GloomDestination.Deck.name -> {
            IconButton(onClick = onOpenBonusActions) {
                Icon(
                    Icons.Default.Menu,
                    stringResource(R.string.bonus_actions_image_desc)
                )
            }
        }
        GloomDestination.Character.name,
        GloomDestination.ChooseCharacter.name -> {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    stringResource(R.string.back_button_img_desc)
                )
            }
        }
        else -> {}
    }
}