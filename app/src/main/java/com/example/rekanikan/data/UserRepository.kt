package com.example.rekanikan.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.rekanikan.data.pref.UserModel
import com.example.rekanikan.data.pref.UserPreference
import com.example.rekanikan.data.remote.api.ApiConfig
import com.example.rekanikan.data.remote.api.ApiScanConfig
import com.example.rekanikan.data.remote.api.ApiService
import com.example.rekanikan.data.remote.request.AddFeederRequest
import com.example.rekanikan.data.remote.request.AddScheduleRequest
import com.example.rekanikan.data.remote.request.EditScheduleRequest
import com.example.rekanikan.data.remote.request.LoginRequest
import com.example.rekanikan.data.remote.request.RegisterRequest
import com.example.rekanikan.data.remote.response.AddFeederResponse
import com.example.rekanikan.data.remote.response.AddScheduleResponse
import com.example.rekanikan.data.remote.response.DeleteScheduleResponse
import com.example.rekanikan.data.remote.response.EditSchedulesResponse
import com.example.rekanikan.data.remote.response.ErrorResponse
import com.example.rekanikan.data.remote.response.GetArticlesResponse
import com.example.rekanikan.data.remote.response.GetFeederResponse
import com.example.rekanikan.data.remote.response.GetSchedulesResponse
import com.example.rekanikan.data.remote.response.LoginResponse
import com.example.rekanikan.data.remote.response.RegisterResponse
import com.example.rekanikan.data.remote.response.ScanFishResponse
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

    fun addFeedingSchedule(
        hour: Int,
        minute: Int,
        portion: Int,
        isActive: Boolean,
        feederId: String,
    ): LiveData<Result<AddScheduleResponse>>{
        val addScheduleRequest = AddScheduleRequest(hour, minute, portion, isActive, feederId)
        return liveData {
            emit(Result.Loading)
            try {
                val user = runBlocking { userPreference.getSession().first() }
                val apiService = ApiConfig.getApiService(user.token)
                val addScheduleResponse = apiService.addSchedule(addScheduleRequest)
                if (addScheduleResponse.message == "Schedule successfully created!"){
                    emit(Result.Success(addScheduleResponse))
                } else {
                    emit(Result.Error(addScheduleResponse.message))
                }
            } catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error("Add schedule failed: $errorMessage"))
            } catch (e: Exception){
                emit(Result.Error("Signal Problem"))
            }
        }

    }

    fun addFeeder(
        feederId: String,
        feederName: String
    ): LiveData<Result<AddFeederResponse>>{
        val addFeederRequest = AddFeederRequest(id = feederId, name = feederName)
        return liveData {
            emit(Result.Loading)
            try {
                val user = runBlocking { userPreference.getSession().first() }
                val apiService = ApiConfig.getApiService(user.token)
                val addFeederResponse = apiService.addFeeder(addFeederRequest)
                if (addFeederResponse.message == "Feeder berhasil ditambahkan!"){
                    emit(Result.Success(addFeederResponse))
                } else {
                    emit(Result.Error(addFeederResponse.message))
                }
            } catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error("Add feeder failed: $errorMessage"))
            } catch (e: Exception){
                emit(Result.Error("Signal Problem"))
            }
        }
    }

    fun getSchedule(feederId: String): LiveData<Result<GetSchedulesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val user = runBlocking { userPreference.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            val getSchedulesResponse = apiService.getSchedules(feederId)
            if (getSchedulesResponse.data != null){
                emit(Result.Success(getSchedulesResponse))
            }
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error("Get schedules failed: $errorMessage"))
        } catch (e: Exception){
            emit(Result.Error("Signal Problem"))
        }

    }

    fun getArticles(): LiveData<Result<GetArticlesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val user = runBlocking { userPreference.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            val getArticlesResponse = apiService.getArticles()
            if (getArticlesResponse.message.isNotEmpty()){
                emit(Result.Success(getArticlesResponse))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error("Get schedules failed: $errorMessage"))
        } catch (e: Exception) {
            emit(Result.Error("Signal Problem"))
        }
    }

    fun editSchedule(
        hour: Int,
        minute: Int,
        portion: Int,
        isActive: Boolean,
        scheduleId: Int,
        feederId: String
    ): LiveData<Result<EditSchedulesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val editScheduleRequest = EditScheduleRequest(hour, minute, portion, isActive, feederId)
            val user = runBlocking { userPreference.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            val editSchedulesResponse = apiService.editSchedule(scheduleId = scheduleId, feederId = feederId, editScheduleRequest)
            if (editSchedulesResponse.data != null){
                emit(Result.Success(editSchedulesResponse))
            }
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error("Edit schedule failed: $errorMessage"))
        } catch (e: Exception){
            emit(Result.Error("Signal Problem"))
        }
    }

    fun deleteSchedule(
        scheduleId: Int,
        feederId: String,
        ): LiveData<Result<DeleteScheduleResponse>> = liveData {
            emit(Result.Loading)
        try {
            val user = runBlocking { userPreference.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            val deleteScheduleResponse = apiService.deleteSchedule(scheduleId = scheduleId, feederId = feederId)
            if (deleteScheduleResponse.message == "Schedule successfully deleted") {
                emit(Result.Success(deleteScheduleResponse))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error("Deleted schedule failed: $errorMessage"))
        } catch (e: Exception) {
            emit(Result.Error("Signal Problem"))
        }
    }

    fun scanImage(imageFile: File) = liveData<Result<ScanFishResponse>> {
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

    fun getFeeder(): LiveData<Result<GetFeederResponse>> = liveData {
        emit(Result.Loading)
        try {
            val user = runBlocking { userPreference.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            val successResponse = apiService.getFeeder()
            if (successResponse != null){
                emit(Result.Success(successResponse))
            } else {
                emit(Result.Error("error"))
            }
        } catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(Result.Error("Get feeder error: ${errorResponse.errors}"))
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