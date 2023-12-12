package com.example.rekanikan.view.feeder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rekanikan.R
import com.example.rekanikan.databinding.ActivityFeederBinding
import com.example.rekanikan.view.add_feeding_schedule.AddFeedingScheduleActivity
import com.example.rekanikan.view.addfeeder.AddFeederActivity

class FeederActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeederBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeederBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

    }

    private fun setupAction(){
        binding.addFeederCard.setOnClickListener{
            startActivity(Intent(this@FeederActivity, AddFeederActivity::class.java))
        }
        binding.addNewPlan.setOnClickListener{
            startActivity(Intent(this@FeederActivity, AddFeedingScheduleActivity::class.java))
        }

    }
}