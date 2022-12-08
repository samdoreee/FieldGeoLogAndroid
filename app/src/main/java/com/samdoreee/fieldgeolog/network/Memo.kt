package com.samdoreee.fieldgeolog.network

data class Memo(
    val id: Long,
    val description: String,
    val fileList: List<File>
)
