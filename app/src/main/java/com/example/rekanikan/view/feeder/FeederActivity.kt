package com.example.rekanikan.view.feeder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.data.dummy.FeedingHistoryData
import com.example.rekanikan.data.model.FeedingScheduleItem
import com.example.rekanikan.databinding.ActivityFeederBinding
import com.example.rekanikan.view.add_feeding_schedule.AddFeedingScheduleActivity
import com.example.rekanikan.view.addfeeder.AddFeederActivity
import com.example.rekanikan.data.result.Result
import com.example.rekanikan.view.adapter.ScheduleItemAdapter

class FeederActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeederBinding

    private val viewModel by viewModels<FeederViewModel> {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeederBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()








    }

    override fun onResume() {
        super.onResume()
//        Toast.makeText(this, "resume", Toast.LENGTH_SHORT).show()

        viewModel.getFeeder().observe(this){ result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this@FeederActivity, "${result.error}", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Success -> {
                        showLoading(false)
                        val feederResponse = result.data
//                        Toast.makeText(this@FeederActivity, "${feederResponse.data}", Toast.LENGTH_SHORT).show()

                        setUpView(feederName = feederResponse.data.name, feederId = feederResponse.data.id)

                        viewModel.getSchedules(feederResponse.data.id).observe(this) {result ->
                            if (result != null) {
                                when (result) {
                                    is Result.Loading -> {
                                        showLoading(true)
                                    }

                                    is Result.Error -> {
                                        showLoading(false)
                                        Toast.makeText(this@FeederActivity, "${result.error}", Toast.LENGTH_SHORT).show()
                                    }

                                    is Result.Success -> {
                                        showLoading(false)
                                        val scheduleResponse = result.data

                                        if (scheduleResponse != null){
//                                            Toast.makeText(this@FeederActivity, "${scheduleResponse.data}", Toast.LENGTH_SHORT).show()

                                            val data = scheduleResponse.data.map {
                                                FeedingScheduleItem(
                                                    id = it.id,
                                                    hour = it.hour,
                                                    minute = it.minute,
                                                    portion = it.portion,
                                                    isActive = it.isActive
                                                )
                                            }

                                            if (data.size >= 2){
                                                binding.addNewPlan.visibility = View.GONE
                                                setUpScheduleRv(data.subList(0,2))
                                            } else {
                                                binding.addNewPlan.visibility = View.VISIBLE
                                                setUpScheduleRv(data)
                                            }
                                        }


                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private fun setupAction(){
        binding.addFeederCard.setOnClickListener{
            startActivity(Intent(this@FeederActivity, AddFeederActivity::class.java))
        }
        binding.addNewPlan.setOnClickListener{
            startActivity(Intent(this@FeederActivity, AddFeedingScheduleActivity::class.java))
        }

    }

    private fun setUpView(feederName: String, feederId: String){

        binding.feederCard.visibility = View.VISIBLE
        binding.addFeederCard.visibility = View.GONE

        binding.feederName.text = feederName
        binding.feederId.text = feederId
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingLoop.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setUpScheduleRv(data: List<FeedingScheduleItem>){
        val adapter = ScheduleItemAdapter(this@FeederActivity)
        adapter.submitList(data)
        binding.feedingScheduleRv.adapter = adapter

        binding.dailyScheduleCardBlank.visibility = View.GONE
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.feedingScheduleRv.layoutManager = layoutManager

    }

}