package com.example.rekanikan.data.dummy

import com.example.rekanikan.data.model.FeedingHistoryItem

class FeedingHistoryData {
    companion object {
        val data = listOf(
            FeedingHistoryItem(
                id = 1,
                time = "16:00",
                portion = 5,
                date = "22-11-2023"
            ),
            FeedingHistoryItem(
                id = 2,
                time = "08:00",
                portion = 5,
                date = "22-11-2023"
            ),
            FeedingHistoryItem(
                id = 3,
                time = "16:00",
                portion = 5,
                date = "21-11-2023"
            ),
            FeedingHistoryItem(
                id = 4,
                time = "08:00",
                portion = 5,
                date = "21-11-2023"
            ),
        )
    }

}