package com.holden.gloomhavenmodifier.navigate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.holden.gloomhavenmodifier.bonusActions.BonusActions
import com.holden.gloomhavenmodifier.bonusActions.CleanDeckConfirmation
import com.holden.gloomhavenmodifier.deck.viewModel.DeckViewModel
import kotlinx.coroutines.launch

@Composable
fun GloomScaffold() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val deckViewModel: DeckViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    var showCleanDeckConfirmation by remember {
        mutableStateOf(false)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = currentDestination?.route ?: "Deck")
                },
                navigationIcon = when (currentDestination?.route) {
                    GloomDestination.Deck.name -> {
                        {
                            IconButton(onClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }) {
                                Icon(Icons.Default.Menu, "bonus actions")
                            }
                        }
                    }
                    GloomDestination.Character.name,
                    GloomDestination.ChooseCharacter.name -> {
                        {
                            IconButton(onClick = { scope.launch { navController.popBackStack() } }) {
                                Icon(Icons.Default.ArrowBack, "back")
                            }
                        }
                    }
                    else -> null
                },
                actions = {
                    when (currentDestination?.route) {
                        GloomDestination.Deck.name -> Button(onClick = {
                            navController.navigate(
                                GloomDestination.Character.name
                            )
                        }) {
                            Text(text = "Edit Character")
                        }
                        GloomDestination.Character.name -> Button(onClick = {
                            navController.navigate(
                                GloomDestination.ChooseCharacter.name
                            )
                        }) {
                            Text(text = "New Character Class")
                        }
                    }
                }
            )
        },
        drawerContent = {
            BonusActions(showCleanDeckDialog = { showCleanDeckConfirmation = true })
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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