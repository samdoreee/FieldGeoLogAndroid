package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class FileRequest(
    val fileFolder: String,
    val fileName: String,
)
