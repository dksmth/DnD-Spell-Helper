package com.example.dndspellhelper.data

import androidx.room.TypeConverter
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
}