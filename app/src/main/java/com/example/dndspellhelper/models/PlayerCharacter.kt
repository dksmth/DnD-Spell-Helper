package com.example.dndspellhelper.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    tableName = "characters"
)
data class PlayerCharacter(
    @PrimaryKey val name: String,
    val characterClass: String,
    val level: String,
    val knownSpells: List<Spell> = listOf(),
    val attackModifier: Int,
    val spellDC: Int,
): Parcelable {
//    constructor() :
//        this(
//            name = "Mir",
//            characterClass = "Bard",
//            level = "7",
//            knownSpells = listOf<Spell>(),
//            attackModifier = 16,
//            spellDC = 16
//        )

}