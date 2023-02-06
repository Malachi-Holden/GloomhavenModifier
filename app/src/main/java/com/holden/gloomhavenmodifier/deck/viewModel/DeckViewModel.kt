package com.holden.gloomhavenmodifier.deck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeckViewModel(deck: DeckModel): ViewModel() {
    private val _state = MutableStateFlow(deck)
    val state = _state.asStateFlow()

    fun updateDeck(deck: DeckModel){
        _state.value = deck
    }

    fun draw(){
        _state.value = state.value.draw()
    }

    fun shuffle(){
        _state.value = state.value.shuffled()
    }

    fun insertCurse(){
        _state.value = state.value.insertCurse()
    }

    fun insertBless(){
        _state.value = state.value.insertBless()
    }

    class Factory(val deckModel: DeckModel): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DeckViewModel(deckModel) as T
        }
    }
}
