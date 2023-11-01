package com.samdoreee.fieldgeolog.network

import com.samdoreee.fieldgeolog.data.model.CommunityModel
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class ArticleResponse(
    val id: Long,
    val personalRecordResponseDTO: PersonalRecordResponse,
    val title: String,
    val userId: Long,
    val nickname: String,
    val createDT: String
) {
    fun convertToCommunityModel(): CommunityModel {
        return CommunityModel(
            id = this.id,
            title = this.title,
            location = "",
            date = this.createDT,
            author = nickname,
            thumbnail = this.personalRecordResponseDTO.fileName  // URL 값을 Int로 변환하는 로직이 필요한데, 여기서는 임의로 0을 설정했습니다.
        )
    }
}


