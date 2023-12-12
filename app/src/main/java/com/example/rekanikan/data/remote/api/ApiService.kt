package com.example.rekanikan.data.remote.api

import com.example.rekanikan.data.remote.request.LoginRequest
import com.example.rekanikan.data.remote.request.RegisterRequest
import com.example.rekanikan.data.remote.response.LoginResponse
import com.example.rekanikan.data.remote.response.RegisterResponse
import com.example.rekanikan.data.remote.response.ScanFishResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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



}