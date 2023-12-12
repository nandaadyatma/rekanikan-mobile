package com.example.rekanikan.data.model

data class FeedingScheduleItem(
    val id: Int,
    val hour: String,
    val minute: String,
    val startTime: String,
    val feederId: Int,
    val portion: Int,
    val isActive: Boolean,
)
