package com.example.dndspellhelper.presentation.character_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndspellhelper.data.SpellsRepository
import com.example.dndspellhelper.data.remote.dto.character_level.ClassLevel
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.models.SpellFilter
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

    fun getSpellsWithFilter(spellFilter: SpellFilter) {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredWithLevel = spellsRepository.getSpellWithLevel(spellFilter.level!!)

            val secondFilter = filteredWithLevel.filter { spell ->
                spell.classNames?.any { playerClass ->
                    playerClass.name == (spellFilter.characterClass ?: "")
                } ?: true
            }

            val thirdFilter = secondFilter.filter { it !in character.value!!.knownSpells }

            _filteredSpells.emit(thirdFilter)
        }
    }

    fun addNewSpellToCharacterSpellList(spell: Spell) {
        val newList = _character.value!!.knownSpells + spell

        viewModelScope.launch {
            spellsRepository.updateCharacterSpells(newList, _character.value!!.name)

            _character.emit(_character.value!!.copy(knownSpells = newList))
        }
    }

    fun setCharacter(character: PlayerCharacter) {
        viewModelScope.launch {
            _character.emit(character)

            val smth = if (character.level > 20) 19 else character.level - 1

            _characterClassAndLevelInfo.emit(
                spellsRepository.getSpellcastingForClassAndLevel(
                    character.characterClass.lowercase(),
                    smth
                )
            )
        }
    }
}