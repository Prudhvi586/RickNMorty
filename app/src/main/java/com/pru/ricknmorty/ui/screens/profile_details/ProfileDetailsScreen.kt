package com.pru.ricknmorty.ui.screens.profile_details

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun ProfileDetailsScreen(id: Int, viewModel: ProfileDetailsViewModel = viewModel()) {
    val data = viewModel.data.collectAsState()
    val colorItem = viewModel.colorItem
    val bgColor = remember { Animatable(Color.White) }
    val txtColor = remember { Animatable(Color.Black) }
    LaunchedEffect(Unit) {
        viewModel.getProfile(id)
        colorItem.collect {
            if (it != null) {
                bgColor.animateTo(it.bgColor, tween(500))
                txtColor.animateTo(it.textColor, tween(500))
            }
        }
    }
    data.value?.let { profileItem ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = bgColor.value)
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = profileItem.image, contentDescription = null,
                modifier = Modifier
                    .clip(
                        CircleShape
                    ),
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = profileItem.name ?: "-",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = txtColor.value
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "Origin: ".plus(profileItem.origin?.name),
                fontSize = 14.sp,
                color = txtColor.value
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "Location: ".plus(profileItem.location?.name),
                fontSize = 14.sp,
                color = txtColor.value
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "Episodes: ".plus(profileItem.episode?.size),
                fontSize = 14.sp,
                color = txtColor.value
            )
        }
    }
}