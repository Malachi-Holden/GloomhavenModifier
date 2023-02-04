package com.holden.gloomhavenmodifier.navigate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun GloomScaffold(){
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = currentDestination?.route ?: "Deck")
                },
                navigationIcon = if (currentDestination?.route == GloomDestination.Deck.name) {
                    null
                } else {
                    {
                        IconButton(onClick = { scope.launch { navController.popBackStack() } }) {
                            Icon(Icons.Default.ArrowBack, "back")
                        }
                    }
                },
                actions = {
                    if (currentDestination?.route == GloomDestination.Deck.name) {
                        Button(onClick = { navController.navigate(GloomDestination.Character.name) }) {
                            Text(text = "Edit Character")
                        }
                    }
                }
            )
        },
    ) {
        GloomNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
    }
}