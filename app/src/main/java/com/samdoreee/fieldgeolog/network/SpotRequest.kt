package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class SpotRequest(
    val latitude: Double,
    val longitude: Double,
)