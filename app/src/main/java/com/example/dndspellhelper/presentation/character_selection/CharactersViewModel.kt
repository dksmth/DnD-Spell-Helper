package com.example.dndspellhelper.presentation.character_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndspellhelper.data.SpellsRepository
import com.example.dndspellhelper.data.remote.dto.character_level.ClassLevel
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.models.Spell
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

    private val _characterClassAndLevelInfo = MutableStateFlow<ClassLevel?>(null)
    val characterInfo = _characterClassAndLevelInfo.asStateFlow()

    private val _character = MutableStateFlow<PlayerCharacter?>(null)
    val character = _character.asStateFlow()

    private val _filteredSpells = MutableStateFlow<List<Spell>?>(null)
    val filteredSpells = _filteredSpells.asStateFlow()

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            _allCharacters.emit(spellsRepository.getAllCharacters())
        }
    }

    fun insertCharacter(playerCharacter: PlayerCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            spellsRepository.insertCharacter(playerCharacter)

            _allCharacters.emit(_allCharacters.value + playerCharacter)
        }
    }

    fun getSpellsForLevelAndClass(level: Int) {
        val characterClass = _character.value!!.characterClass

        viewModelScope.launch(Dispatchers.IO) {
            val filterForLevel = spellsRepository.getSpellWithLevel(level)

            val filterForClass = filterForLevel.filter { spell ->
                spell.classNames.any { playerClass -> playerClass.name == characterClass }
            }

            val filterForDuplicates = filterForClass.filter { it !in character.value!!.knownSpells }

            _filteredSpells.emit(filterForDuplicates)
        }
    }

    fun addNewSpellToCharacterSpellList(spell: Spell) {
        val newList = _character.value!!.knownSpells + spell

        viewModelScope.launch {
            spellsRepository.updateCharacterSpells(newList, _character.value!!.name)

            _character.emit(_character.value!!.copy(knownSpells = newList))
            getAllCharacters()
        }
    }

    fun setCharacter(character: PlayerCharacter) {
        viewModelScope.launch {
            _character.emit(character)

            val characterLevel = if (character.level > 20) 19 else character.level - 1

            _characterClassAndLevelInfo.emit(
                spellsRepository.getSpellcastingForClassAndLevel(
                    character.characterClass.lowercase(),
                    characterLevel
                )
            )
        }
    }
}