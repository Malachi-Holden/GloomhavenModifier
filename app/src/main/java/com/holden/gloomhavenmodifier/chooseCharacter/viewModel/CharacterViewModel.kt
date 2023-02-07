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
    val localRepo: BuiltInCharacterRepo,
    val remoteRepo: RemoteCharacterRepo
): ViewModel() {
    private var _localCharacterState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val localCharacterState = _localCharacterState.asStateFlow()

    private var _remoteCharacterState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val remoteCharacterState = _remoteCharacterState.asStateFlow()

    init {
        viewModelScope.launch {
            _localCharacterState.value = localRepo.getCharacters()
        }
        viewModelScope.launch {
            _remoteCharacterState.value = remoteRepo.getCharacters()
        }
    }
}

sealed class CharacterState{
    object Loading: CharacterState()
    data class Loaded(val characters: List<CharacterModel>): CharacterState()
    class Error(val error: Throwable): CharacterState()
}