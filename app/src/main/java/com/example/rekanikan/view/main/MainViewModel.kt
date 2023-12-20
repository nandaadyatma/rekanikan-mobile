package com.example.rekanikan.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rekanikan.data.UserRepository
import com.example.rekanikan.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getFeeder() = repository.getFeeder()

    fun getArticles() = repository.getArticles()

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

}

