package com.example.dndspellhelper.data.remote

import com.example.dndspellhelper.data.remote.dto.SpellDto
import com.example.dndspellhelper.models.AllSpells
import retrofit2.http.GET
import retrofit2.http.Path

interface SpellsApi {

    @GET("/api/spells")
    suspend fun getAllSpells(): AllSpells

    @GET("/api/spells/{index}")
    suspend fun getSpell(@Path("index") index: String): SpellDto

    companion object {
        const val BASE_URL = "https://www.dnd5eapi.co"
    }
}