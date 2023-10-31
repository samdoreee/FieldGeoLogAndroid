package com.samdoreee.fieldgeolog.network

import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecordResponse(
    val id: Long,
    val recordTitle: String,
    val userId: Long,
    val nickname: String,
    val createDT: String,
    val modifyDT: String,
    val spotResponseDTOList: List<Spot>,
    val thumbnailPath: String,

){
    fun convertToMyRecordModel(): MyRecordModel {
        return MyRecordModel(
            id = this.id,
            title = this.recordTitle,
            date = this.createDT,
            location = "", // 원래 코드에 location 값을 설정하는 로직이 없었으므로 그대로 두었습니다.
            thumbnail = "https://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg"  // URL 값을 Int로 변환하는 로직이 필요한데, 여기서는 임의로 0을 설정했습니다.
        )
    }
}
