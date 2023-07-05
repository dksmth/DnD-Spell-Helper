package com.example.dndspellhelper.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dndspellhelper.data.remote.dto.spell.ClassName


@Entity(
    tableName = "spells"
)
data class Spell(
    val casting_time: String,
    val components: List<String>,
    val concentration: Boolean,
    val material: String?,
    val desc: List<String>,
    val duration: String,
    val index: String,
    val level: Int,
    @PrimaryKey
    val name: String,
    val range: String,
    val ritual: Boolean,
    val favourite: Boolean = false,
    val classNames: List<ClassName>
)