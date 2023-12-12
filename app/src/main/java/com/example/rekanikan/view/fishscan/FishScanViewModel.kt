package com.example.rekanikan.view.fishscan

import androidx.lifecycle.ViewModel
import com.example.rekanikan.data.UserRepository
import java.io.File

class FishScanViewModel(private val repository: UserRepository): ViewModel() {
    fun scanFish(image: File) = repository.scanImage(image)
}