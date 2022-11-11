package com.samdoreee.fieldgeolog.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.Spot
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface SpotUiState {
    data class Success(val spotList: List<Spot>) : SpotUiState
    object Error : SpotUiState
    object Loading : SpotUiState
}

class SpotViewModel : ViewModel() {

    var spotUiState: SpotUiState by mutableStateOf(SpotUiState.Loading)
        private set

    init {
        getAllSpots()
    }

    fun getAllSpots() {
        viewModelScope.launch {
            spotUiState = try {
                val spotList = GeoApi.retrofitService.getAllSpots()
                SpotUiState.Success(spotList)
            } catch (e: IOException) {
                SpotUiState.Error
            }
        }
    }
}