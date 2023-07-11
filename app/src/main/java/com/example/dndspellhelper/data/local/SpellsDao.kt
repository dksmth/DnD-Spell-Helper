package com.example.dndspellhelper.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.models.SpellSlot

@Dao
interface SpellsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSpells(exerciseList: List<Spell>)

    @Query("SELECT * FROM spells")
    suspend fun getAllSpellsFromDB(): List<Spell>

//    @Query("SELECT * FROM spells WHERE favourite = :favourite")
//    suspend fun getFavouriteSpells(favourite: Boolean): List<Spell>

    @Query("UPDATE spells SET favourite = :favourite WHERE name =:name")
    suspend fun updateFavouritesStatus(favourite: Boolean, name: String)

    @Query("SELECT * FROM spells WHERE level = :level")
    suspend fun getSpellsWithLevel(level: Int): List<Spell>

    @Query("SELECT EXISTS(SELECT * FROM spells)")
    suspend fun isExists(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(playerCharacter: PlayerCharacter)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<PlayerCharacter>

    @Query("UPDATE characters SET knownSpells =:newList WHERE name = :name")
    suspend fun updateCharacterSpells(newList: List<Spell>, name: String)

    @Query("UPDATE characters SET spellCasting =:newSpellSlots WHERE name =:name")
    suspend fun updateCharacterSpellSlots(newSpellSlots: List<SpellSlot>, name: String)
}