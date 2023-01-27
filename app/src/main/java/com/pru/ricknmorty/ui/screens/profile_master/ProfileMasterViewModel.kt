package com.pru.ricknmorty.ui.screens.profile_master

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pru.ricknmorty.apiRepository
import com.pru.ricknmorty.appController
import com.pru.ricknmorty.models.ProfileItem
import com.pru.ricknmorty.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileMasterViewModel : ViewModel() {
    private val _data = MutableStateFlow<List<ProfileItem>>(emptyList())
    val data: StateFlow<List<ProfileItem>> get() = _data
    private var page = 1
    private var isLoading = false

    init {
        getProfiles()
    }

    fun getProfiles() {
        if (isLoading) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            apiRepository.getList(page).collect {
                when (it) {
                    is ApiState.Failure -> {
                        isLoading = false
                        appController.dismissLoader()
                        appController.showAlert(it.errorMessage)
                    }
                    is ApiState.Initial -> Unit
                    is ApiState.Loading -> {
                        isLoading = true
                        appController.showLoader()
                    }
                    is ApiState.Success -> {
                        isLoading = false
                        appController.dismissLoader()
                        page++
                        val prev = _data.value.toMutableList()
                        it.data?.data?.let { ls ->
                            prev.addAll(ls)
                            _data.value = prev
                        }
                    }
                }
            }
        }
    }
}