package com.example.rekanikan.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.rekanikan.R
import com.example.rekanikan.view.main.MainActivity
import com.example.rekanikan.view.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val countDownTimer = object : CountDownTimer(3000L, 1000){
            override fun onTick(millisUnitilFinished: Long) {
            }

            override fun onFinish() {
                navigateToMainActivity()
            }
        }

        countDownTimer.start()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}