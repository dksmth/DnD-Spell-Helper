package com.example.dndspellhelper.data.remote.dto.spell


data class SpellDto(
    val attack_type: String?,
    val casting_time: String,
    val classes: List<ClassName>,
    val components: List<String>,
    val concentration: Boolean,
    val damage: Damage?,
    val dc: Dc?,
    val desc: List<String>,
    val duration: String,
    val index: String,
    val level: Int,
    val name: String,
    val range: String,
    val ritual: Boolean,
    val school: School,
    val subclasses: List<Subclass>,
    val url: String,
    val material: String?
)