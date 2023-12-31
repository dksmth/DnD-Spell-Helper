package com.example.dndspellhelper.data.remote

import com.example.dndspellhelper.data.remote.dto.character_level.ClassLevelDto
import com.example.dndspellhelper.data.remote.dto.spell.SpellDto
import com.example.dndspellhelper.models.AllSpells
import retrofit2.http.GET
import retrofit2.http.Path

interface SpellsApi {

    @GET("/api/spells")
    suspend fun getAllSpells(): AllSpells

    @GET("/api/spells/{index}")
    suspend fun getSpell(@Path("index") index: String): SpellDto

    @GET("/api/classes/{class}/levels")
    suspend fun getClassInfo(@Path("class") className: String): ArrayList<ClassLevelDto>

    companion object {
        const val BASE_URL = "https://www.dnd5eapi.co"
    }
}