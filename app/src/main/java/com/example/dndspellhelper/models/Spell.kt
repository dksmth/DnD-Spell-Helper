package com.example.dndspellhelper.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val favourite: Boolean = false
): Parcelable