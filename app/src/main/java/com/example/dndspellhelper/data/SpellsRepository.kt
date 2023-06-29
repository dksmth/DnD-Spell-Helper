package com.example.dndspellhelper.data

import com.example.dndspellhelper.data.local.SpellsDatabase
import com.example.dndspellhelper.data.remote.SpellsApi
import com.example.dndspellhelper.data.remote.dto.SpellDto
import com.example.dndspellhelper.models.Spell
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpellsRepository @Inject constructor(
    db: SpellsDatabase,
    private val api: SpellsApi,
) {
    private val dao = db.dao

    private suspend fun getSpellInfoFromApi(): List<SpellDto> {
        val spellsIndexes = api.getAllSpells().results.map { it.index }

        return spellsIndexes.map { api.getSpell(it) }
    }

    suspend fun getSpellInfo(): List<Spell> {
        return if (dao.isExists()) {
            dao.getAllSpellsFromDB()
        } else {
            val allSpells = getSpellInfoFromApi().map { it.toSpell() }

            dao.insertAllSpells(exerciseList = allSpells)

            allSpells
        }
    }

    suspend fun updateFavouritesStatus(name: String, favourite: Boolean) =
        dao.updateFavouritesStatus(favourite, name)

    suspend fun getAllFavourites() = dao.getFavouriteSpells(true)
}