package com.pru.ricknmorty.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pru.ricknmorty.db.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_tbl")
    fun getAll(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM character_tbl where id = :id limit 1")
    fun getCharacterById(id : Int?) : CharacterEntity?
}