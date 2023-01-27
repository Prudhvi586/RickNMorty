package com.pru.ricknmorty.controller

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AppController {
    private val navChannel_ = Channel<NavigationIntent>(
        capacity = Channel.UNLIMITED,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val navChannel
        get() = navChannel_.receiveAsFlow()

    fun navigate(route: String) {
        val navigationIntent = NavigationIntent.Navigate(route = route)
        navChannel_.trySend(navigationIntent)
    }

    fun navigate(
        route: String,
        navOptions: NavOptions,
    ) {
        val navigationIntent = NavigationIntent.Navigate(route = route, navOptions = navOptions)
        navChannel_.trySend(navigationIntent)
    }

    fun popBack() {
        navChannel_.trySend(NavigationIntent.PopBackStack)
    }

    fun showLoader() {
        navChannel_.trySend(NavigationIntent.ShowLoader)
    }

    fun dismissLoader() {
        navChannel_.trySend(NavigationIntent.DismissLoader)
    }

    fun showAlert(message: String) {
        navChannel_.trySend(NavigationIntent.ShowAlert(message = message))
    }

    sealed class NavigationIntent {
        data class Navigate(
            var route: String,
            var navOptions: NavOptions? = null
        ) : NavigationIntent()

        object PopBackStack : NavigationIntent()
        object ShowLoader : NavigationIntent()
        object DismissLoader : NavigationIntent()
        data class ShowAlert(var message: String) : NavigationIntent()
    }
}