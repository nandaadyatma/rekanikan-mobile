package com.example.rekanikan.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rekanikan.di.Injection
import com.example.rekanikan.view.about.AboutViewModel
import com.example.rekanikan.view.add_feeding_schedule.AddFeedingScheduleViewModel
import com.example.rekanikan.view.addfeeder.AddFeederViewModel
import com.example.rekanikan.view.edit_feeding_schedule.EditFeedingScheduleViewModel
import com.example.rekanikan.view.feeder.FeederViewModel
import com.example.rekanikan.view.fishscan.FishScanViewModel
import com.example.rekanikan.view.login.LoginViewModel
import com.example.rekanikan.view.main.MainViewModel
import com.example.rekanikan.view.register.RegisterViewModel
import com.example.rekanikan.view.welcome.WelcomeViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> {
                AboutViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FishScanViewModel::class.java) -> {
                FishScanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FeederViewModel::class.java) -> {
                FeederViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddFeedingScheduleViewModel::class.java) -> {
                AddFeedingScheduleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditFeedingScheduleViewModel::class.java) -> {
                EditFeedingScheduleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddFeederViewModel::class.java) -> {
                AddFeederViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}