package com.holden.gloomhavenmodifier.deck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeckViewModel(deckModel: DeckModel): ViewModel() {
    private val _state = MutableStateFlow(deckModel)
    val state = _state.asStateFlow()

    fun draw(){
        _state.value = state.value.draw()
    }

    fun shuffle(){
        _state.value = state.value.shuffled()
    }

    class Factory(val deckModel: DeckModel): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DeckViewModel(deckModel) as T
        }
    }
}
