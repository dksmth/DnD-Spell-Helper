package com.example.dndspellhelper.data

import com.example.dndspellhelper.data.remote.dto.character_level.ClassLevelDto
import com.example.dndspellhelper.data.remote.dto.spell.SpellDto
import com.example.dndspellhelper.models.ClassLevel
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
        material = material,
        classNames = classes
    )
}

fun ClassLevelDto.toClassLevel(): ClassLevel {
    return ClassLevel(
        className = `class`.name,
        level = level,
        spellcasting = spellcasting
    )
}