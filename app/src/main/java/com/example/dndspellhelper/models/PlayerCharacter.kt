package com.example.dndspellhelper.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dndspellhelper.data.remote.dto.character_level.Spellcasting


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
    val spellCasting: Spellcasting = Spellcasting(0,0,0,0,0,0,0,0,0,0,0)
) {
    constructor() :
        this(
            name = "Mir",
            characterClass = "Bard",
            level = 7,
            knownSpells = listOf<Spell>(),
            attackModifier = 16,
            spellDC = 16
        )

}