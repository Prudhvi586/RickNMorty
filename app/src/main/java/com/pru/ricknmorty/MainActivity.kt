package com.pru.ricknmorty

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pru.ricknmorty.controller.AppController
import com.pru.ricknmorty.ui.screens.profile_details.ProfileDetailsScreen
import com.pru.ricknmorty.ui.screens.profile_master.ProfileMasterScreen
import com.pru.ricknmorty.ui.theme.RickNMortyTheme
import com.pru.ricknmorty.utils.Routes
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickNMortyTheme {
                Surface(modifier = Modifier.background(Color.White)) {
                    NavigationUI()
                }
            }
        }
    }
}

@Composable
private fun MainActivity.NavigationUI() {
    val navController = rememberNavController()
    var loadingState by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                appController.navChannel.collect {
                    when (it) {
                        is AppController.NavigationIntent.Navigate -> {
                            navController.navigate(it.route, it.navOptions)
                        }
                        AppController.NavigationIntent.PopBackStack -> {
                            if (!navController.popBackStack()) {
                                finish()
                            }
                        }
                        AppController.NavigationIntent.DismissLoader -> {
                            loadingState = false
                        }
                        is AppController.NavigationIntent.ShowAlert -> {
                            Toast.makeText(this@NavigationUI, it.message, Toast.LENGTH_SHORT).show()
                        }
                        AppController.NavigationIntent.ShowLoader -> {
                            loadingState = true
                        }
                    }
                }
            }
        }
    }
    MyNavHost(navController = navController)
    if (loadingState) {
        Dialog(
            onDismissRequest = {
                loadingState = false
            }, properties = DialogProperties(

            )
        ) {
            Card(
                modifier = Modifier.padding(10.dp),
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium,
                backgroundColor = Color.White,
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ProfileMasterScreen.routeName
    ) {
        composable(Routes.ProfileMasterScreen.routeName) { ProfileMasterScreen() }
        composable(Routes.ProfileDetailScreen.routeName.plus("?id={id}"), arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val id = it.arguments?.getInt("id") ?: 0
            ProfileDetailsScreen(id = id)
        }
    }
}