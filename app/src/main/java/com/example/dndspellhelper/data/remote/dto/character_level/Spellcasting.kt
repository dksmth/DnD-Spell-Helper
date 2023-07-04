package com.example.dndspellhelper.data.remote.dto.character_level

data class Spellcasting(
    val cantrips_known: Int?,
    val spell_slots_level_1: Int,
    val spell_slots_level_2: Int,
    val spell_slots_level_3: Int,
    val spell_slots_level_4: Int,
    val spell_slots_level_5: Int,
    val spell_slots_level_6: Int,
    val spell_slots_level_7: Int,
    val spell_slots_level_8: Int,
    val spell_slots_level_9: Int,
    val spells_known: Int?
)