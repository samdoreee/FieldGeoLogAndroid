package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class MemoRequest(
    val description: String
)
