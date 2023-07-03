package com.example.dndspellhelper.models

data class Character(
    val name: String,
    val characterClass: String,
    val level: String,
    val knownSpells: List<Spell>,
    val attackModifier: Int,
    val spellDC: Int,
) {
    constructor() :
        this(
            name = "Mir",
            characterClass = "Bard",
            level = "7",
            knownSpells = listOf<Spell>(),
            attackModifier = 16,
            spellDC = 16
        )

}