package com.example.rekanikan.view.add_feeding_schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.rekanikan.R
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.data.result.Result
import com.example.rekanikan.databinding.ActivityAddFeederBinding
import com.example.rekanikan.databinding.ActivityAddFeedingScheduleBinding
import com.example.rekanikan.utils.TimePickerFragment
import com.example.rekanikan.view.about.AboutViewModel
import com.example.rekanikan.view.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFeedingScheduleActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityAddFeedingScheduleBinding

    private val viewModel by viewModels<AddFeedingScheduleViewModel> {
        ViewModelFactory.getInstance(this)
    }

        override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddFeedingScheduleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAction()

    }

    fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "timePicker")
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeInput = dateFormat.format(calendar.time)
        binding.timeEdt.setText(timeInput)
    }

    fun setUpAction() {
        val time = binding.timeEdt
        val portion = binding.feedingAmountEdt

        binding.addScheduleButton.setOnClickListener {
            val timeInput = time.text.toString()

            if (time.text.toString().isEmpty()) {
                Toast.makeText(this, "time cannot empty", Toast.LENGTH_SHORT).show()
            } else {
                val splitTime = timeInput.split(":")
                val hour = splitTime[0]
                val minute = splitTime[1]
                val result = hour.toInt() + minute.toInt()

//                Toast.makeText(this, "hour: $hour, minute: $minute, hour + minute = $result", Toast.LENGTH_SHORT).show()

                viewModel.getFeeder().observe(this) { feeder ->
                    if (feeder != null) {
                        when(feeder) {
                            is Result.Loading -> {
                                showLoading(true)
                            }

                            is Result.Success -> {
                                showLoading(false)

                                viewModel.addFeedingSchedule(
                                    hour = hour.toInt(),
                                    minute = minute.toInt(),
                                    portion = portion.text.toString().toInt(),
                                    isActive = true,
                                    feederId = feeder.data.data.id
                                ).observe(this){ result ->
                                    when (result){
                                        is Result.Loading -> {
                                            showLoading(true)
                                        }

                                        is Result.Success -> {
                                            showLoading(false)
//                            Toast.makeText(this, "${result.data}", Toast.LENGTH_SHORT).show()
                                            alertDialog()
                                        }

                                        is Result.Error -> {
                                            showLoading(false)
                                            Toast.makeText(this, "${result.error}", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                }

                            }

                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(this@AddFeedingScheduleActivity, "${feeder.error}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }


            }


        }

    }

    private fun showLoading(isLoading: Boolean){
        binding.loadingLoop.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun alertDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            val message = context.getString(R.string.success_add_schedule)
            val okay = context.getString(R.string.okay)
            setMessage(message)
            setPositiveButton(okay) { _, _ ->
                finish()
            }
            create()
            show()
        }
    }
}