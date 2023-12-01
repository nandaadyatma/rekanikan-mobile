package com.example.rekanikan.di

import android.content.Context
import com.example.rekanikan.data.UserRepository
import com.example.rekanikan.data.pref.UserPreference
import com.example.rekanikan.data.pref.dataStore
import com.example.rekanikan.data.remote.api.ApiConfig
import com.example.rekanikan.data.remote.api.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
}