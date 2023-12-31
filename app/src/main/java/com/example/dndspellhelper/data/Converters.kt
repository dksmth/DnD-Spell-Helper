package com.example.dndspellhelper.data

import androidx.room.TypeConverter
import com.example.dndspellhelper.data.remote.dto.character_level.Spellcasting
import com.example.dndspellhelper.data.remote.dto.spell.ClassName
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.models.SpellSlot
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

    @TypeConverter
    fun fromListOfClassesToString(list: List<ClassName>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToListClasses(string: String): List<ClassName>? {
        val type = object : TypeToken<List<ClassName>?>() {}.type

        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromSpellcastingToString(spellcasting: Spellcasting): String {
        return gson.toJson(spellcasting)
    }

    @TypeConverter
    fun fromStringToSpellcasting(string: String): Spellcasting {
        val type = object : TypeToken<Spellcasting>() {}.type

        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromSpellSlotListToString(spellcasting: List<SpellSlot>): String {
        return gson.toJson(spellcasting)
    }

    @TypeConverter
    fun fromStringToListSpellSlot(string: String): List<SpellSlot> {
        val type = object : TypeToken<List<SpellSlot>>() {}.type

        return gson.fromJson(string, type)
    }

}