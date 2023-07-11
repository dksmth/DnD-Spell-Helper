package com.example.dndspellhelper.data.remote.dto.character_level

import com.example.dndspellhelper.models.SpellSlot

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
    val spells_known: Int?,
) {

    constructor() : this(0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0)

    fun getSpellcastingPairs(): List<SpellSlot> {
        val list = mutableListOf<SpellSlot>()

        val string =
            "$spell_slots_level_1" + "$spell_slots_level_2" + "$spell_slots_level_3" + "$spell_slots_level_4" + "$spell_slots_level_5" + "$spell_slots_level_6" +
                    "$spell_slots_level_7" + "$spell_slots_level_8" + "$spell_slots_level_9"

        for (i in string.indices) {
            if (string[i].digitToInt() != 0) {
                list.add(SpellSlot(i + 1, string[i].digitToInt()))
            }
        }

        return list
    }
}
