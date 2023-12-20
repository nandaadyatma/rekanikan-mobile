package com.example.rekanikan.view.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.rekanikan.R
import com.example.rekanikan.data.model.OnBoardingItem
import com.example.rekanikan.view.adapter.OnBoardingAdapter
import com.example.rekanikan.view.welcome.WelcomeActivity
import me.relex.circleindicator.CircleIndicator3

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var indicator: CircleIndicator3
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val onboardingItems = listOf(
            OnBoardingItem("Scan & get fish care recommendation", "Deskripsi 1", R.drawable.onboarding1),
            OnBoardingItem("Feed your fish on time\n" +
                    "and remotely from app", "Deskripsi 3", R.drawable.onboarding2),
            OnBoardingItem("Get feeder & fish care products from our shop", "Deskripsi 1", R.drawable.onboarding3),
        )

        val onboardingAdapter = OnBoardingAdapter(onboardingItems)
        viewPager = findViewById(R.id.viewPager)
        indicator = findViewById(R.id.onboarding_indicator)
        startButton = findViewById(R.id.start_btn)

        viewPager.adapter = onboardingAdapter
        indicator.setViewPager(viewPager)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 2) {
                    startButton.alpha = 1f
                    startButton.startAnimation(AnimationUtils.loadAnimation(this@OnBoardingActivity, R.anim.fade_in))
                    startButton.visibility = View.VISIBLE
                    startButton.setOnClickListener {
                        startActivity(Intent(this@OnBoardingActivity, WelcomeActivity::class.java))
                    }
                } else {
                    startButton.startAnimation(AnimationUtils.loadAnimation(this@OnBoardingActivity, R.anim.fade_out))
                    startButton.alpha = 0f
                    startButton.visibility = View.GONE
                }
            }
        })


    }
}