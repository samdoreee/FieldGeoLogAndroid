package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class SpotResponse(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val createDT: String,
    val modifyDT: String,
    val weatherInfo: String,
    val strike: Int,
    val rockType: String,
    val geoStructure: String,
    val dip: Int,
    val direction: String,
    val memoResponseDTOList: List<Memo>,
    val fileName:String,
)