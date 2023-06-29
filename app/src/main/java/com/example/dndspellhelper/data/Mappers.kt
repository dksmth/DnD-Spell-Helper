package com.example.dndspellhelper.data

import com.example.dndspellhelper.data.remote.dto.SpellDto
import com.example.dndspellhelper.models.Spell

fun SpellDto.toSpell(): Spell {
    return Spell(
        casting_time = casting_time,
        components = components,
        concentration = concentration,
        desc = desc,
        duration = duration,
        index = index,
        level = level,
        name = name,
        range = range,
        ritual = ritual,
        material = material
    )
}
