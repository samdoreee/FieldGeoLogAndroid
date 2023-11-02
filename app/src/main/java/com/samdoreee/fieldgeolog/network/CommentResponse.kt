package com.samdoreee.fieldgeolog.network

import com.samdoreee.fieldgeolog.data.model.CommentModel
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
){
    fun convertToCommentModel(): CommentModel {
        return CommentModel(
            id = this.id,
            comment_content = this.content,
            comment_date = this.createDT,
            comment_author = this.nickName,
            comment_author_profile = 0
        )
    }
}

