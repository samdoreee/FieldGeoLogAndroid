package com.samdoreee.fieldgeolog.record

import java.io.Serializable

class Record (
    val project_title: String,
    val project_thumbnail: String,
    val project_date: String,
    val project_location: String,
    val project_weather: String,
    val strike: String,
    val dip: String,
    val rocktype: String,
    val geo_struct: String
): Serializable