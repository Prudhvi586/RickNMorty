package com.pru.ricknmorty.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.pru.ricknmorty.models.ListResponse
import com.pru.ricknmorty.models.ProfileItem
import com.pru.ricknmorty.remote.ApiService
import com.pru.ricknmorty.utils.ApiState
import kotlinx.coroutines.flow.flow

class ApiRepository(private val apiService: ApiService) {

    fun getList(page: Int) = flow<ApiState<ListResponse>> {
        emit(ApiState.Loading())
        kotlin.runCatching {
            apiService.getList(page)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure {
            emit(ApiState.Failure(it.message))
        }
    }

    fun getProfileItem(id: Int) = flow<ApiState<ProfileItem>> {
        emit(ApiState.Loading())
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