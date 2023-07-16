package com.example.dndspellhelper.data

import com.example.dndspellhelper.data.local.SpellsDatabase
import com.example.dndspellhelper.data.remote.SpellsApi
import com.example.dndspellhelper.data.remote.dto.character_level.ClassLevelDto
import com.example.dndspellhelper.data.remote.dto.spell.SpellDto
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.models.SpellSlot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpellsRepository @Inject constructor(
    db: SpellsDatabase,
    private val api: SpellsApi,
) {
    private val dao = db.dao

    // Spells

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

    // Favourite spells

    suspend fun updateFavouritesStatus(name: String, favourite: Boolean) =
        dao.updateFavouritesStatus(favourite, name)

    // Character

    suspend fun insertCharacter(playerCharacter: PlayerCharacter) =
        dao.insertCharacter(playerCharacter)

    suspend fun getAllCharacters(): List<PlayerCharacter> = dao.getAllCharacters()

    suspend fun deleteCharacters(name: String) = dao.deleteCharacter(name)

    // Character Spells

    suspend fun updateCharacterSpells(newList: List<Spell>, id: String) =
        dao.updateCharacterSpells(newList, id)

    suspend fun updateCharacterSpellcasting(newSpellSlots: List<SpellSlot>, name: String) =
        dao.updateCharacterSpellSlots(newSpellSlots, name)

    suspend fun getSpellWithLevel(level: Int) = dao.getSpellsWithLevel(level)


    // Class level

    private suspend fun getFullClassInfoFromApi(className: String): ArrayList<ClassLevelDto> =
        api.getClassInfo(className.lowercase())

    private suspend fun getInfoForClassAndLevelFromAPI(
        className: String,
        level: Int,
    ): ClassLevelDto {
        val validatedLevel = if (level > 20) 19 else level - 1
        return getFullClassInfoFromApi(className)[validatedLevel]
    }

    // Spellcasting and spell slots

    suspend fun getSpellSlots(playerCharacter: PlayerCharacter): List<SpellSlot> {
        return getSpellSlots(playerCharacter.characterClass, playerCharacter.level)
    }

    private suspend fun getSpellSlots(className: String, level: Int): List<SpellSlot> {
        return if (dao.classLevelInDB(className, level)) {
            getSpellSlotsFromDatabase(className,level)
        } else {
            val fromAPI = getInfoForClassAndLevelFromAPI(className, level)

            dao.insertClassLevel(fromAPI.toClassLevel())

            fromAPI.toClassLevel().spellcasting.getSpellcastingPairs()
        }
    }

    private fun getSpellSlotsFromDatabase(className: String, level: Int): List<SpellSlot> =
        dao.getClassLevel(className, level).spellcasting.getSpellcastingPairs()

}