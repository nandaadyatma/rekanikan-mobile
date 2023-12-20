package com.example.rekanikan.view.edit_feeding_schedule

import androidx.lifecycle.ViewModel
import com.example.rekanikan.data.UserRepository

class EditFeedingScheduleViewModel(private val repository: UserRepository) : ViewModel() {
    fun editSchedule(
        hour: Int,
        minute: Int,
        portion: Int,
        isActive: Boolean,
        scheduleId: Int,
        feederId: String
    ) = repository.editSchedule(hour, minute, portion, isActive, scheduleId, feederId)

    fun deleteSchedule(
        scheduleId: Int,
        feederId: String
    ) = repository.deleteSchedule(scheduleId, feederId)

    fun getFeeder() = repository.getFeeder()
}