package com.pru.ricknmorty.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.pru.ricknmorty.db.AppDatabase
import com.pru.ricknmorty.models.ListResponse
import com.pru.ricknmorty.models.ProfileItem
import com.pru.ricknmorty.remote.ApiService
import com.pru.ricknmorty.utils.ApiState
import com.pru.ricknmorty.utils.Mapper.toCharacterEntity
import com.pru.ricknmorty.utils.Mapper.toProfileItem
import kotlinx.coroutines.flow.flow

class ApiRepository(private val apiService: ApiService, private val appDatabase: AppDatabase) {

    fun getList(page: Int) = flow<ApiState<ListResponse>> {
        val data = appDatabase.userDao().getAll()
        if (data.isEmpty()) {
            emit(ApiState.Loading())
        } else if (page == 1) {
            emit(
                ApiState.Success(
                    ListResponse(
                        info = null,
                        data = data.map { it.toProfileItem() })
                )
            )
        } else {
            emit(ApiState.Loading())
        }
        kotlin.runCatching {
            apiService.getList(page)
        }.onSuccess {
            appDatabase.userDao().insertAll(it.data!!.map { profile ->
                profile.toCharacterEntity()
            })
            emit(ApiState.Success(it))
        }.onFailure {
            emit(ApiState.Failure(it.message))
        }
    }

    fun getProfileItem(id: Int) = flow<ApiState<ProfileItem>> {
        val data = appDatabase.userDao().getCharacterById(id)
        if (data == null) {
            emit(ApiState.Loading())
        } else {
            emit(ApiState.Success(data.toProfileItem()))
        }
        kotlin.runCatching {
            apiService.getProfileItem(id)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure {
            emit(ApiState.Failure(it.message))
        }
    }

    fun downloadAvatar(name: String) = flow<ApiState<Bitmap>> {
        emit(ApiState.Loading())
        kotlin.runCatching {
            apiService.downloadAvatar(name)
        }.onSuccess {
            val bitmap = BitmapFactory.decodeStream(it.byteStream())
            emit(ApiState.Success(bitmap))
        }.onFailure {
            emit(ApiState.Failure(it.message))
        }
    }
}