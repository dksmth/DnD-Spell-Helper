package com.example.dndspellhelper.presentation.character_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndspellhelper.data.SpellsRepository
import com.example.dndspellhelper.models.PlayerCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val spellsRepository: SpellsRepository) :
    ViewModel() {

    private val _allCharacters = MutableStateFlow(listOf<PlayerCharacter>())
    val allCharacters = _allCharacters.asStateFlow()

    fun getAllCharacters() {
        viewModelScope.launch {
            _allCharacters.emit(spellsRepository.getAllCharacters())
        }
    }

    fun insertCharacter(playerCharacter: PlayerCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            spellsRepository.insertCharacter(playerCharacter = playerCharacter)
            _allCharacters.emit(_allCharacters.value + playerCharacter)
        }
    }
}