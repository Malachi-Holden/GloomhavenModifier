package com.holden.gloomhavenmodifier.chooseCharacter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holden.gloomhavenmodifier.editCharacter.BuiltInCharacterRepo
import com.holden.gloomhavenmodifier.editCharacter.RemoteCharacterRepo
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    initialCharacter: CharacterModel,
    val localRepo: BuiltInCharacterRepo,
    val remoteRepo: RemoteCharacterRepo
): ViewModel() {
    private var _localCharacterListState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val localCharacterListState = _localCharacterListState.asStateFlow()

    private var _remoteCharacterListState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val remoteCharacterListState = _remoteCharacterListState.asStateFlow()

    private var _currentCharacterState = MutableStateFlow(initialCharacter)
    val currentCharacterState = _currentCharacterState.asStateFlow()

    init {
        viewModelScope.launch {
            _localCharacterListState.value = localRepo.getCharacters()
        }
        viewModelScope.launch {
            _remoteCharacterListState.value = remoteRepo.getCharacters()
        }
    }

    fun chooseCharacter(character: CharacterModel){
        _currentCharacterState.value = character
    }
}

sealed class CharacterState{
    object Loading: CharacterState()
    data class Loaded(val characters: List<CharacterModel>): CharacterState()
    class Error(val error: Throwable): CharacterState()
}