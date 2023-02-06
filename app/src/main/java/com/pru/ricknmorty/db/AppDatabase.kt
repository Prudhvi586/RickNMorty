package com.pru.ricknmorty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pru.ricknmorty.db.converters.ListConverter
import com.pru.ricknmorty.db.daos.CharacterDao
import com.pru.ricknmorty.db.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CharacterDao
}

