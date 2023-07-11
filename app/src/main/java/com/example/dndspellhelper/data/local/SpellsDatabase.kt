package com.example.dndspellhelper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dndspellhelper.data.Converters
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.models.PlayerCharacter

@Database(
    entities = [Spell::class, PlayerCharacter::class],
    version = 14,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SpellsDatabase: RoomDatabase() {

    abstract val dao: SpellsDao
}