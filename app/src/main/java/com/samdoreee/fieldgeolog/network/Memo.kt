package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class Memo(
    val id: Long,
    val description: String,
    val fileList: List<File>
)
