package com.example.dndspellhelper.presentation.spell_list

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.data.SpellsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(private val spellsRepository: SpellsRepository) :
    ViewModel() {

    private var _spellNames = mutableStateListOf<Spell>()
    val spellNames: List<Spell>
        get() = _spellNames

    lateinit var chosenSpell: Spell

    var favourites = mutableListOf<Spell>()


    fun getEverything() {
        viewModelScope.launch(Dispatchers.IO) {
            changeState(spellsRepository.getSpellInfo())
        }
    }

    private fun changeState(newState: List<Spell>) {
        _spellNames = newState.toMutableStateList()
    }

    private fun getFavourites() {
        viewModelScope.launch {
            favourites.addAll(spellsRepository.getAllFavourites())
        }
    }

    fun updateFavouritesStatus(spell: Spell, favourite: Boolean) {
        viewModelScope.launch {
            spellsRepository.updateFavouritesStatus(spell.name, favourite)
        }

        val index = _spellNames.indexOf(spell)
        _spellNames[index] = _spellNames[index].copy(favourite = favourite)
    }

}