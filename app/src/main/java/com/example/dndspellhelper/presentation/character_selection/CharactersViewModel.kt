package com.example.dndspellhelper.presentation.character_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndspellhelper.data.SpellsRepository
import com.example.dndspellhelper.data.remote.dto.character_level.Spellcasting
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.models.SpellSlot
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

    private val _character = MutableStateFlow<PlayerCharacter?>(null)
    val character = _character.asStateFlow()

    private val _filteredSpells = MutableStateFlow<List<Spell>?>(null)
    val filteredSpells = _filteredSpells.asStateFlow()

    private val _chosenSpell = MutableStateFlow<Spell?>(null)
    val chosenSpell = _chosenSpell.asStateFlow()

    var defaultSpellSlots: List<SpellSlot> = Spellcasting().getSpellcastingPairs()

    var spellcasting = Spellcasting()

    var showAddButton = false

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() =
        viewModelScope.launch(Dispatchers.IO) { _allCharacters.emit(spellsRepository.getAllCharacters()) }

    fun emitSpell(spell: Spell) = viewModelScope.launch { _chosenSpell.emit(spell) }

    fun createNewCharacter(
        name: String,
        characterClass: String,
        level: Int,
        attackModifier: Int,
        spellDC: Int,
        abilityMod: Int,
    ) {
        val newCharacter = PlayerCharacter(
            name = name,
            characterClass = characterClass,
            level = level,
            attackModifier = attackModifier,
            spellDC = spellDC,
            abilityModifier = abilityMod
        )

        viewModelScope.launch(Dispatchers.IO) {
            val spellSlots = spellsRepository.getSpellSlots(newCharacter)
            val newCharacterWithSpellcasting = newCharacter.copy(spellCasting = spellSlots)

            spellsRepository.insertCharacter(newCharacterWithSpellcasting)
            _allCharacters.emit(_allCharacters.value + newCharacterWithSpellcasting)
        }
    }

    fun nameNotInCharacterNames(name: String): Boolean {
        return _allCharacters.value.none { it.name == name }
    }

    fun deleteCharacter(playerCharacter: PlayerCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            _allCharacters.emit(_allCharacters.value - playerCharacter)
            spellsRepository.deleteCharacters(playerCharacter.name)
        }
    }

    fun setCharacter(character: PlayerCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            _character.emit(character)

            defaultSpellSlots = spellsRepository.getSpellSlots(character)
            spellcasting = spellsRepository.getSpellcasting(character)
        }
    }

    fun filterClassSpellsForLevel(level: Int) {
        val playerClass = _character.value!!.characterClass

        viewModelScope.launch(Dispatchers.IO) {
            val filtered =
                spellsRepository.getSpellWithLevel(level)
                    .filter { spell ->
                        spell.classNames.any { charClass -> charClass.name == playerClass }
                    }
                    .filter { spell ->
                        spell !in character.value!!.knownSpells
                    }

            _filteredSpells.emit(filtered)
        }
    }

    private fun updateCharacterSpellList(newList: List<Spell>) {
        viewModelScope.launch {
            spellsRepository.updateCharacterSpells(newList, _character.value!!.name)

            _character.emit(_character.value!!.copy(knownSpells = newList))
            getAllCharacters()
        }
    }

    fun addSpellToSpellList(spell: Spell) =
        updateCharacterSpellList(_character.value!!.knownSpells + spell)

    fun deleteSpellFromSpellList(spell: Spell) =
        updateCharacterSpellList(_character.value!!.knownSpells - spell)

    private fun updateCharacterSpellSlots(level: Int, newValue: Int) {
        val newSpellSlotsList = _character.value?.spellCasting?.toMutableList()

        newSpellSlotsList?.set(level, newSpellSlotsList[level].copy(amountAtLevel = newValue))

        viewModelScope.launch {
            spellsRepository.updateCharacterSpellcasting(
                newSpellSlotsList!!.toList(),
                character.value!!.name
            )

            _character.emit(_character.value!!.copy(spellCasting = newSpellSlotsList))
            getAllCharacters()
        }
    }

    fun minusSpellSlotAtLevel(level: Int) =
        updateCharacterSpellSlots(
            level,
            (_character.value?.spellCasting?.get(level)?.amountAtLevel ?: 1) - 1
        )

    fun plusSpellSlotAtLevel(level: Int) =
        updateCharacterSpellSlots(
            level,
            (_character.value?.spellCasting?.get(level)?.amountAtLevel ?: 1) + 1
        )

    fun refreshCharacterSpellSlots() {
        viewModelScope.launch {
            _character.emit(_character.value!!.copy(spellCasting = defaultSpellSlots))
        }
    }

}