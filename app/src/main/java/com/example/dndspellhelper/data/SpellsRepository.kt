package com.example.dndspellhelper.data

import com.example.dndspellhelper.data.local.SpellsDatabase
import com.example.dndspellhelper.data.remote.SpellsApi
import com.example.dndspellhelper.data.remote.dto.character_level.ClassLevel
import com.example.dndspellhelper.data.remote.dto.character_level.Spellcasting
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

    suspend fun insertCharacter(playerCharacter: PlayerCharacter) =
        dao.insertCharacter(playerCharacter)

    suspend fun getAllCharacters(): List<PlayerCharacter> = dao.getAllCharacters()

    private suspend fun getFullSpellcasting(className: String): ArrayList<ClassLevel> =
        api.getSpellcastingForClass(className)

    suspend fun getClassLevelInfo(className: String, level: Int): ClassLevel {
        val validatedLevel = if (level > 20) 19 else level - 1
        return getFullSpellcasting(className)[validatedLevel]
    }

    suspend fun getClassLevelInfo(playerCharacter: PlayerCharacter): ClassLevel {
        val validatedLevel = if (playerCharacter.level > 20) 19 else playerCharacter.level - 1
        return getFullSpellcasting(playerCharacter.characterClass.lowercase())[validatedLevel]
    }

    suspend fun getSpellcastingForCharacter(character: PlayerCharacter): Spellcasting {
        val classLevel = getClassLevelInfo(character)
        return classLevel.spellcasting
    }

    suspend fun getSpellSlotsForCharacter(character: PlayerCharacter): List<SpellSlot> {
        val spellcasting = getSpellcastingForCharacter(character)
        return spellcasting.getSpellcastingPairs()
    }

    suspend fun updateCharacterSpells(newList: List<Spell>, id: String) =
        dao.updateCharacterSpells(newList, id)

    suspend fun updateCharacterSpellcasting(newSpellSlots: List<SpellSlot>, name: String) =
        dao.updateCharacterSpellSlots(newSpellSlots, name)

    suspend fun getSpellWithLevel(level: Int) = dao.getSpellsWithLevel(level)
}