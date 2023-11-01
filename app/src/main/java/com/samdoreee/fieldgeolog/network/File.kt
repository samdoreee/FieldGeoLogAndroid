package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class File(
    val id: Long,
    val fileName: String,
    val filePath: String
)
