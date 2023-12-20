package com.example.rekanikan.view.addfeeder

import androidx.lifecycle.ViewModel
import com.example.rekanikan.data.UserRepository

class AddFeederViewModel(private val repository: UserRepository): ViewModel() {
    fun addFeeder(feederId: String, feederName: String) = repository.addFeeder(feederId, feederName)
}