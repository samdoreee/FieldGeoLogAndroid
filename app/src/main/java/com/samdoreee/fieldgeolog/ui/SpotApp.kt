package com.samdoreee.fieldgeolog.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.SpotRequest
import com.samdoreee.fieldgeolog.ui.theme.FieldGeoLogTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

@Composable
fun SpotApp(modifier: Modifier = Modifier) {
    val spotViewModel: SpotViewModel = viewModel()
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SpotList(spotViewModel, Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    runBlocking {
                        launch {
//                            addSpot(SpotRequest(1.2, 12.0))
                        }
                    }
                    spotViewModel.getAllSpots()
                }
            ) {
                Text(text = "add new Spot")
            }
        }
    }
}

suspend fun addSpot(spotRequest: SpotRequest) {
    try {
        GeoApi.retrofitService.addSpot(spotRequest)
    } catch (e: IOException) {
        SpotUiState.Error
    }
}

@Composable
private fun SpotList(
    spotViewModel: SpotViewModel,
    modifier: Modifier
) {
    when (val spotUiState = spotViewModel.spotUiState) {
        is SpotUiState.Success -> LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(spotUiState.spotList) { spot ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(5.dp, Color.Black)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = spot.latitude.toString())
                    Text(text = spot.latitude.toString())
                    Text(text = spot.createDt.toString())
                }
            }
        }
        is SpotUiState.Loading -> Text(text = "loading...")
        is SpotUiState.Error -> Text(text = "error")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FieldGeoLogTheme {
        SpotApp()
    }
}