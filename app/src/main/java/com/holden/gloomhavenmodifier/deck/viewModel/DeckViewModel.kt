package com.holden.gloomhavenmodifier.deck.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(deck: DeckModel): ViewModel() {
    private val _state = MutableStateFlow(deck)
    val state = _state.asStateFlow()

    fun updateDeck(deck: DeckModel){
        _state.value = deck
    }

    fun draw(){
        _state.value = state.value.draw()
    }

    fun draw(at: Int){
        _state.value = state.value.draw(at)
    }

    fun shuffle(){
        _state.value = state.value.shuffled()
    }

    fun shuffleRemainingCards(){
        _state.value = state.value.shuffledRemaining()
    }

    fun insertCurse(){
        _state.value = state.value.insertCurse()
    }

    fun insertBless(){
        _state.value = state.value.insertBless()
    }

    fun cleanDeck(){
        _state.value = state.value.cleanDeck()
    }

    fun insertBonusMinus(){
        _state.value = state.value.insertScenarioMinus()
    }

    fun removeCurse(){
        _state.value = state.value.removeCurse()
    }

    fun removeBless(){
        _state.value = state.value.removeBless()
    }

    fun removeBonusMinus(){
        _state.value = state.value.removeScenarioMinus()
    }

    fun undrawCard(at: Int){
        _state.value = state.value.unDrawCard(at)
    }

    class Factory(val deckModel: DeckModel): ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DeckViewModel(deckModel) as T
        }
    }
}
