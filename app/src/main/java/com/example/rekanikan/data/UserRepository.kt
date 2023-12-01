package com.example.rekanikan.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.rekanikan.data.pref.UserModel
import com.example.rekanikan.data.pref.UserPreference
import com.example.rekanikan.data.remote.api.ApiService
import com.example.rekanikan.data.remote.response.ErrorResponse
import com.example.rekanikan.data.remote.response.RegisterResponse
import com.example.rekanikan.data.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
){

    fun register(
        name: String,
        email: String,
        password: String,
    ): LiveData<Result<RegisterResponse>>{
        return liveData {
            emit(Result.Loading)
            try {
                val registerResponse = apiService.register(name, email, password)
                if (registerResponse.error == false) {
                    emit(Result.Success(registerResponse))
                } else {
                    emit(Result.Error(registerResponse.message ))
                }
            } catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error("Registration Failed: $errorMessage"))
            } catch (e: Exception){
                emit(Result.Error("Signal Problem"))
            }
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