package com.example.rekanikan.view.register

import androidx.lifecycle.ViewModel
import com.example.rekanikan.data.UserRepository

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    fun register(
        name: String,
        email: String,
        password: String
    ) = repository.register(name, email, password)
}