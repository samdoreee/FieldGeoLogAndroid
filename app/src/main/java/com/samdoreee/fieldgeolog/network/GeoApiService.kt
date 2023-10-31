package com.samdoreee.fieldgeolog.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http://172.20.10.2:8080"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .build()

interface GeoApiService {
    @GET("api/spots")
    suspend fun getAllSpots(): List<Spot>

    @POST("api/spots")
    suspend fun addSpot(@Body spotRequest: SpotRequest)

    @POST("api/users")
    suspend fun addUser(@Body userRequest: UserRequest): Response<UserResponse>
    @GET("api/personalRecords")
    suspend fun getAllRecords(): Response<List<PersonalRecordResponse>>
}

object GeoApi {
    val retrofitService : GeoApiService by lazy {
        retrofit.create(GeoApiService::class.java)
    }
}