package com.pru.ricknmorty.models


import com.google.gson.annotations.SerializedName

data class ListResponse(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("results")
    val data: List<ProfileItem>? = null
)