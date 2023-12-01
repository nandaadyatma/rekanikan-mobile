package com.example.rekanikan.view.about

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.databinding.ActivityAboutBinding
import com.example.rekanikan.view.welcome.WelcomeActivity

class AboutActivity : AppCompatActivity() {
    private val viewModel by viewModels<AboutViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this){ user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        setupAction()
    }

    fun setupAction(){
        binding.logout.setOnClickListener{
            viewModel.logout()
        }

    }
}