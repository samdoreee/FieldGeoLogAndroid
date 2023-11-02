package com.samdoreee.fieldgeolog.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MyRecordModel (
    var id: Long,
    var title: String,
    var date: String,
    var location: String,
    var thumbnail: String
)
