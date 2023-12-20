package com.example.rekanikan.view.feeder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.rekanikan.data.UserRepository
import com.example.rekanikan.data.pref.UserModel

class FeederViewModel(private val repository: UserRepository): ViewModel() {
    fun getFeeder() = repository.getFeeder()

    fun getSchedules(feederId: String) = repository.getSchedule(feederId)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}