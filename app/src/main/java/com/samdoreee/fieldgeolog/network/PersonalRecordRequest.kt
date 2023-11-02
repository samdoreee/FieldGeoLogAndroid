package com.samdoreee.fieldgeolog.network

import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecordRequest(
    val recordTitle: String,
    val userId: Long,
    )


