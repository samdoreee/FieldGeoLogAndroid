package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class SpotRequest(
    val latitude: Double,
    val longitude: Double,
    val strike: Int,
    val rockType: String,
    val geoStructure: String,
    val dip: Int,
    val direction: String,
)