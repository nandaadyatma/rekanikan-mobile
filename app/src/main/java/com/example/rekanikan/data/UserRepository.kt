package com.example.rekanikan.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.rekanikan.data.pref.UserModel
import com.example.rekanikan.data.pref.UserPreference
import com.example.rekanikan.data.remote.api.ApiConfig
import com.example.rekanikan.data.remote.api.ApiScanConfig
import com.example.rekanikan.data.remote.api.ApiService
import com.example.rekanikan.data.remote.request.LoginRequest
import com.example.rekanikan.data.remote.request.RegisterRequest
import com.example.rekanikan.data.remote.response.ErrorResponse
import com.example.rekanikan.data.remote.response.LoginResponse
import com.example.rekanikan.data.remote.response.RegisterResponse
import com.example.rekanikan.data.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
){

    fun register(
        name: String,
        email: String,
        password: String,
    ): LiveData<Result<RegisterResponse>>{
        val registerRequest = RegisterRequest(name, email, password)
        return liveData {
            emit(Result.Loading)
            try {
                val registerResponse = apiService.register(registerRequest)
                if (registerResponse.error == false) {
                    emit(Result.Success(registerResponse))
                } else {
                    emit(Result.Error(registerResponse.message ))
                }
            } catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error("Registration Failed: $errorMessage"))
            } catch (e: Exception){
                emit(Result.Error("Signal Problem"))
            }
        }

    }

    fun login(
        email: String,
        password: String,
    ): LiveData<Result<LoginResponse>>{
        val loginRequest = LoginRequest(email, password)
        return liveData {
            emit(Result.Loading)
            try {
                val loginResponse = apiService.login(loginRequest)
                if (loginResponse.error == false) {
                    emit(Result.Success(loginResponse))
                } else {
                    emit(Result.Error(loginResponse.message))
                }
            } catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error("Login Failed: $errorMessage"))
            } catch (e: Exception){
                emit(Result.Error("Signal Problem"))
            }
        }
    }

    fun scanImage(imageFile: File) = liveData {
        emit(Result.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )

        try {
            val user = runBlocking { userPreference.getSession().first() }
            val apiService = ApiScanConfig.getApiService(user.token)
            val successResponse = apiService.scanFish(multipartBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(Result.Error("Scan failed: ${errorResponse.errors}"))
        } catch (e: Exception){
            emit(Result.Error("Signal Problem"))
        }
    }


    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}