package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class Spot(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val createDt: String,
    val weatherInfo: String
)