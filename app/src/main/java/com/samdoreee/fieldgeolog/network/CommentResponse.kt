package com.samdoreee.fieldgeolog.network

import com.samdoreee.fieldgeolog.data.model.CommunityModel
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class CommentResponse(
    val id: Long,
    val userId: Long,
    val nickName: String,
    val content: String,
    val createDT: String,
    val modifyDT: String,
)