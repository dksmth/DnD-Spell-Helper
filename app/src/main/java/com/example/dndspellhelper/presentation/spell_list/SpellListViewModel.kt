package com.example.dndspellhelper.presentation.spell_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndspellhelper.data.SpellsRepository
import com.example.dndspellhelper.models.Spell
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpellListViewModel @Inject constructor(private val spellsRepository: SpellsRepository) :
    ViewModel() {

    private val _sortByLevel = MutableStateFlow(false)
    val sortByLevel = _sortByLevel.asStateFlow()

    private val _showFavourites = MutableStateFlow(false)
    val showFavourites = _showFavourites.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private var _spellNames = MutableStateFlow(listOf<Spell>())

    private val _classOfSpell = MutableStateFlow("")
    val classOfSpell = _classOfSpell.asStateFlow()

    private val _levelOfSpell = MutableStateFlow(IntRange(0,10))
    val levelOfSpell = _levelOfSpell.asStateFlow()

    private val _castingTime = MutableStateFlow("")
    val castingTime = _castingTime.asStateFlow()

    val spellNames = _spellNames

        .combine(_showFavourites) { spells, showFav ->
            if (showFav) spells.filter { it.favourite } else spells
        }
        .combine(_sortByLevel) { spells, sortByLevel ->
            if (sortByLevel) spells.sortedBy { it.level } else spells.sortedBy { it.name }
        }
        .combine(_searchText) { spells, text ->
            spells.filter { it.name.lowercase().contains(text) }
        }
        .combine(_classOfSpell) { spells , className ->
            if (className != "") spells.filter { spell ->
                val classNames = spell.classNames.map { it.name }

                classNames.contains(className)
            } else spells
        }
        .combine(_levelOfSpell) { spells, levelRange ->
            spells.filter { it.level in levelRange }
        }
        .combine(_castingTime) { spells, castTime ->
            if (castTime != "") {
                spells.filter { it.casting_time == castTime }
            } else {
                spells
            }
        }

        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _spellNames.value
        )

    lateinit var chosenSpell: Spell

    init {
        getEverything()
    }

    private fun getEverything() {
        viewModelScope.launch(Dispatchers.IO) {
            _spellNames.emit(spellsRepository.getSpellInfo())
        }
    }

    fun updateFavouritesStatus(spell: Spell, favourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            spellsRepository.updateFavouritesStatus(spell.name, favourite)

            val index = _spellNames.value.indexOf(spell)

            val smth = _spellNames.value.toMutableList()

            smth[index] = spell.copy(favourite = favourite)
            _spellNames.emit(smth)
        }
    }

    fun changeFavouriteState() {
        _showFavourites.value = !_showFavourites.value
    }

    fun sortByAlphabet() {
        _sortByLevel.value = false
    }

    fun sortByLevel() {
        _sortByLevel.value = true
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text.lowercase()
    }

    fun onClassChange(playerClass: String) {
        _classOfSpell.value = if (playerClass != "Any") playerClass else ""
    }

    fun onLevelChange(levelRange: IntRange) {
        _levelOfSpell.value = levelRange
    }

    fun onCastTimeChange(castTime: String) {
        _castingTime.value = if (castTime != "Any") castTime else ""
    }
}