package com.example.rekanikan.data.model

data class FeedingScheduleItem(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val portion: Int,
    val isActive: Boolean,
)
