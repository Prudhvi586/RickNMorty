package com.pru.ricknmorty.ui.screens.profile_master

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pru.ricknmorty.appController
import com.pru.ricknmorty.utils.Routes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileMasterScreen(viewModel: ProfileMasterViewModel = viewModel()) {
    val data = viewModel.data.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(data.value.size) { index ->
                val item = data.value[index]
                Box(
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 5.dp,
                        start = 5.dp,
                        end = 5.dp
                    )
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        elevation = 8.dp, onClick = {
                            appController.navigate(Routes.ProfileDetailScreen.routeName.plus("?id=${item.id}"))
                        }
                    ) {
                        Row(modifier = Modifier.padding(10.dp)) {
                            AsyncImage(
                                model = item.image, contentDescription = null, modifier = Modifier
                                    .size(70.dp)
                                    .clip(
                                        CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.padding(start = 10.dp))
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(text = item.name ?: "-", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.padding(top = 5.dp))
                                Text(text = item.status.plus(" - ").plus(item.species))
                            }
                        }
                    }
                }
                if (index == data.value.size - 2) {
                    viewModel.getProfiles()
                }
            }
        }
    }
}