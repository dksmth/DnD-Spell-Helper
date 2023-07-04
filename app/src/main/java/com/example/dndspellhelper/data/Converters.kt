package com.example.dndspellhelper.data

import androidx.room.TypeConverter
import com.example.dndspellhelper.models.Spell
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromListString(spell: List<String>): String {
        return gson.toJson(spell)
    }

    @TypeConverter
    fun toListString(string: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type

        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromListOfSpellsToString(list: List<Spell>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToListOfSpells(string: String): List<Spell> {
        val type = object : TypeToken<List<Spell>>() {}.type

        return gson.fromJson(string, type)
    }
}