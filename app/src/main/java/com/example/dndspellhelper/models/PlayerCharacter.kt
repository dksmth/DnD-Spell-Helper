package com.example.dndspellhelper.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "characters"
)
data class PlayerCharacter(
    @PrimaryKey val name: String,
    val characterClass: String,
    val level: Int,
    val knownSpells: List<Spell> = listOf(),
    val attackModifier: Int,
    val spellDC: Int,
    val abilityModifier: Int,
    val spellCasting: List<SpellSlot> = listOf()
)