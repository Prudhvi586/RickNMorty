package com.pru.ricknmorty.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_tbl")
data class CharacterEntity(

    val created: String? = null,

    val episode: List<String>? = null,

    val gender: String? = null,

    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,

    val image: String? = null,

    @Embedded
    val location: LocationEntity? = null,

    val name: String? = null,

    @Embedded
    val origin: OriginEntity? = null,

    val species: String? = null,

    val status: String? = null,

    val type: String? = null,

    val url: String? = null
)

data class LocationEntity(
    val location_name: String? = null,
    val location_url: String? = null
)

data class OriginEntity(
    val origin_name: String? = null,
    val origin_url: String? = null
)
