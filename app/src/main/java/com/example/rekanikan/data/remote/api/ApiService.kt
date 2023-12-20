package com.example.rekanikan.data.remote.api

import com.example.rekanikan.data.remote.request.AddFeederRequest
import com.example.rekanikan.data.remote.request.AddScheduleRequest
import com.example.rekanikan.data.remote.request.EditScheduleRequest
import com.example.rekanikan.data.remote.request.LoginRequest
import com.example.rekanikan.data.remote.request.RegisterRequest
import com.example.rekanikan.data.remote.response.AddFeederResponse
import com.example.rekanikan.data.remote.response.AddScheduleResponse
import com.example.rekanikan.data.remote.response.DeleteScheduleResponse
import com.example.rekanikan.data.remote.response.EditScheduleData
import com.example.rekanikan.data.remote.response.EditSchedulesResponse
import com.example.rekanikan.data.remote.response.GetArticlesResponse
import com.example.rekanikan.data.remote.response.GetFeederResponse
import com.example.rekanikan.data.remote.response.GetSchedulesResponse
import com.example.rekanikan.data.remote.response.LoginResponse
import com.example.rekanikan.data.remote.response.RegisterResponse
import com.example.rekanikan.data.remote.response.ScanFishResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Headers("Content-type: application/json")
    @POST("users")
    suspend fun register(
        @Body requestBody: RegisterRequest
    ): RegisterResponse

    @Headers("Content-type: application/json")
    @POST("users/login")
    suspend fun login(
        @Body requestBody: LoginRequest
    ): LoginResponse

    @Multipart
    @POST("predict")
    suspend fun scanFish(
        @Part file: MultipartBody.Part,
    ): ScanFishResponse

    @GET("feeder/get/")
    suspend fun getFeeder(): GetFeederResponse

    @Headers("Content-type: application/json")
    @POST("feeder/input")
    suspend fun addFeeder(
        @Body requestBody: AddFeederRequest
    ): AddFeederResponse

    @Headers("Content-type: application/json")
    @POST("schedule/input")
    suspend fun addSchedule(
        @Body requestBody: AddScheduleRequest
    ): AddScheduleResponse

    @GET("schedule/get/{feederId}")
    suspend fun getSchedules(
        @Path("feederId") feederId: String
    ): GetSchedulesResponse

    @Headers("Content-type: application/json")
    @PUT("schedule/edit/{scheduleId}/{feederId}")
    suspend fun editSchedule(
        @Path("scheduleId") scheduleId: Int,
        @Path("feederId") feederId: String,
        @Body requestBody: EditScheduleRequest
    ): EditSchedulesResponse

    @DELETE("schedule/delete/{scheduleId}/{feederId}")
    suspend fun deleteSchedule(
        @Path("scheduleId") scheduleId: Int,
        @Path("feederId") feederId: String,
    ): DeleteScheduleResponse

    @GET("article/getall")
    suspend fun getArticles(): GetArticlesResponse
}