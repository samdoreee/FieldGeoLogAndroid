package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class Spot(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val createDT: String,
    val weatherInfo: String,
    val strike: Int,
    val rockType: String,
    val geoStructure: String,
    val dip: Int,
    val direction: String,
)