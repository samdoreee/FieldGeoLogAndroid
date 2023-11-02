package com.samdoreee.fieldgeolog.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("api/users/{userId}/is-existing")
    suspend fun existsByUserId(@Path("userId") userId: Long) : Boolean

    @GET("api/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Long) : Response<UserResponse>

    @GET("api/personalRecords/user")
    suspend fun getRecordsByUserId(@Query("userId") userId: Long): Response<List<PersonalRecordResponse>>

    @GET("api/personalRecords")
    suspend fun getAllRecords(): Response<List<PersonalRecordResponse>>

    @GET("api/personalRecords/{recordId}")
    suspend fun getRecord(@Path("recordId") recordId: Long): Response<PersonalRecordResponse>

    @GET("api/articles")
    suspend fun getAllArticles(): Response<List<ArticleResponse>>

    @GET("api/articles/{articleId}")
    suspend fun getArticle(@Path("articleId") articleId: Long) : Response<ArticleResponse>

    @GET("api/articles/{articleId}/comments")
    suspend fun getAllComments(@Path("articleId") articleId: Long) : Response<List<CommentResponse>>


}

object GeoApi {
    val retrofitService : GeoApiService by lazy {
        retrofit.create(GeoApiService::class.java)
    }
}