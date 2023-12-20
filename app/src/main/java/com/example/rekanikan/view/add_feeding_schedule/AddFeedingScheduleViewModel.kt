package com.example.rekanikan.view.add_feeding_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.rekanikan.data.UserRepository
import com.example.rekanikan.data.pref.UserModel

class AddFeedingScheduleViewModel(private val repository: UserRepository): ViewModel() {
    fun addFeedingSchedule(
        hour: Int,
        minute: Int,
        portion: Int,
        isActive: Boolean,
        feederId: String,
    ) = repository.addFeedingSchedule(hour, minute, portion, isActive, feederId)

    fun getFeeder() = repository.getFeeder()

}

