package com.example.dndspellhelper.data

import com.example.dndspellhelper.data.local.SpellsDatabase
import com.example.dndspellhelper.data.remote.SpellsApi
import com.example.dndspellhelper.data.remote.dto.character_level.ClassLevel
import com.example.dndspellhelper.data.remote.dto.spell.SpellDto
import com.example.dndspellhelper.models.PlayerCharacter
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

    suspend fun insertCharacter(playerCharacter: PlayerCharacter) =
        dao.insertCharacter(playerCharacter)

    suspend fun getAllCharacters(): List<PlayerCharacter> = dao.getAllCharacters()

    private suspend fun getFullSpellcasting(className: String): ArrayList<ClassLevel> =
        api.getSpellcastingForClass(className)

    suspend fun getSpellcastingForClassAndLevel(className: String, level: Int): ClassLevel =
        getFullSpellcasting(className)[level]
}