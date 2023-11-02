package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class FileResponse(
    val id: Long,
    val fileFolder: String,
    val fileName: String,
)
