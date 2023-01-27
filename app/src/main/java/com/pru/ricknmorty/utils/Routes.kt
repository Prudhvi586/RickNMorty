package com.pru.ricknmorty.utils

sealed class Routes(var routeName: String) {
    object ProfileMasterScreen : Routes("profile_master_screen")
    object ProfileDetailScreen : Routes("profile_details_screen")
}