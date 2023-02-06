package com.pru.ricknmorty.utils

import com.pru.ricknmorty.db.entities.CharacterEntity
import com.pru.ricknmorty.db.entities.LocationEntity
import com.pru.ricknmorty.db.entities.OriginEntity
import com.pru.ricknmorty.models.Location
import com.pru.ricknmorty.models.Origin
import com.pru.ricknmorty.models.ProfileItem

object Mapper {
    fun ProfileItem.toCharacterEntity() = CharacterEntity(
        created = this.created,
        episode = this.episode,
        gender = this.gender,
        id = this.id,
        image = this.image,
        location = LocationEntity(
            location_name = this.location?.name,
            location_url = this.location?.url
        ),
        name = this.name,
        origin = OriginEntity(
            origin_name = this.origin?.name,
            origin_url = this.origin?.url
        ),
        species = this.species,
        status = this.status,
        type = this.type,
        url = this.url
    )

    fun CharacterEntity.toProfileItem() = ProfileItem(
        created = this.created,
        episode = this.episode,
        gender = this.gender,
        id = this.id,
        image = this.image,
        location = Location(
            name = this.location?.location_name,
            url = this.location?.location_url
        ),
        name = this.name,
        origin = Origin(
            name = this.origin?.origin_name,
            url = this.origin?.origin_url
        ),
        species = this.species,
        status = this.status,
        type = this.type,
        url = this.url
    )
}