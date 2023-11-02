package com.samdoreee.fieldgeolog.network

import com.samdoreee.fieldgeolog.data.model.CommunityModel
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class ArticleRequest(
    val recordId: Long
)