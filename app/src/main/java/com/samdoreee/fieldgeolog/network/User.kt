package com.samdoreee.fieldgeolog.network

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val email: String,
    val nickName: String,
    val profileImage: String,
    val isValid: Boolean,
)