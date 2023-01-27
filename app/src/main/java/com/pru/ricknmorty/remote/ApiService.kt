package com.pru.ricknmorty.remote

import com.pru.ricknmorty.models.ListResponse
import com.pru.ricknmorty.models.ProfileItem
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/character")
    suspend fun getList(@Query("page") page: Int): ListResponse

    @GET("/api/character/{id}")
    suspend fun getProfileItem(@Path("id") id: Int): ProfileItem

    @GET("/api/character/avatar/{name}")
    suspend fun downloadAvatar(@Path("name") name: String): ResponseBody
}