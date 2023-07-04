package com.example.dndspellhelper.data.remote.dto.character_level

data class ClassLevel(
    val ability_score_bonuses: Int,
    val `class`: Class,
    val class_specific: ClassSpecific,
    val features: List<Feature>,
    val index: String,
    val level: Int,
    val prof_bonus: Int,
    val spellcasting: Spellcasting,
    val url: String
)