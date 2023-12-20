package com.example.rekanikan.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.rekanikan.R
import com.example.rekanikan.view.main.MainActivity
import com.example.rekanikan.view.onboarding.OnBoardingActivity
import com.example.rekanikan.view.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val isFirstInstall = sharedPreferences.getBoolean("isFirstInstall", true)



        val countDownTimer = object : CountDownTimer(2000L, 1000){
            override fun onTick(millisUnitilFinished: Long) {
            }

            override fun onFinish() {
                if (isFirstInstall){
//                    Toast.makeText(this@SplashActivity, "this is first install", Toast.LENGTH_SHORT).show()
                    navigateToOnBoardingActivity()
                    sharedPreferences.edit().putBoolean("isFirstInstall", false).apply()
                } else {
                    navigateToWelcomeActivity()
                }

            }
        }

        countDownTimer.start()
    }

    private fun navigateToWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToOnBoardingActivity() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}