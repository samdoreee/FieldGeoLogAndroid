package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class MemoResponse(
    val id: Long,
    val description: String,
    val pictureResponseDTOList: List<FileResponse>
)
