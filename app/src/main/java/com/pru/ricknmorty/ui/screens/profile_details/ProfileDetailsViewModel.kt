package com.pru.ricknmorty.ui.screens.profile_details

import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.pru.ricknmorty.apiRepository
import com.pru.ricknmorty.appContext
import com.pru.ricknmorty.appController
import com.pru.ricknmorty.models.ColorItem
import com.pru.ricknmorty.models.ProfileItem
import com.pru.ricknmorty.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileDetailsViewModel : ViewModel() {
    private val _data = MutableStateFlow<ProfileItem?>(null)
    val data: StateFlow<ProfileItem?> get() = _data

    private val _colorItem = MutableStateFlow<ColorItem?>(null)
    val colorItem: StateFlow<ColorItem?> get() = _colorItem

    fun getProfile(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            apiRepository.getProfileItem(id).collect {
                when (it) {
                    is ApiState.Failure -> {
                        appController.dismissLoader()
                        appController.showAlert(it.errorMessage)
                    }
                    is ApiState.Initial -> Unit
                    is ApiState.Loading -> appController.showLoader()
                    is ApiState.Success -> {
                        appController.dismissLoader()
                        _data.value = it.data
                        downloadAvatar(it.data?.image?.split("avatar/")?.getOrNull(1))
                    }
                }
            }
        }
    }

    private suspend fun downloadAvatar(name: String?) {
        name?.let {
            apiRepository.downloadAvatar(it).collect {
                if (it is ApiState.Success) {
                    val bitmap = it.data
                    bitmap?.let { it1 ->
                        Palette.Builder(it1).generate { p ->
                            val colorItem =
                                ColorItem(bgColor = Color.White, textColor = Color.Black)
                            p?.getDominantColor(
                                ContextCompat.getColor(
                                    appContext,
                                    android.R.color.white
                                )
                            )?.let { c ->
                                colorItem.bgColor = Color(c)
                            }
                            p?.dominantSwatch?.bodyTextColor?.let {
                                colorItem.textColor = Color(it)
                            }
                            _colorItem.value = colorItem
                        }
                    }
                }
            }
        }
    }
}
