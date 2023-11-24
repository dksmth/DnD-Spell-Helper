package com.example.dndspellhelper.models

import androidx.room.Entity
import com.example.dndspellhelper.data.remote.dto.character_level.Spellcasting

@Entity(tableName = "class_level", primaryKeys = ["className","level"])
data class ClassLevel (
    val className: String,
    val level: Int,
    val spellcasting: Spellcasting
)