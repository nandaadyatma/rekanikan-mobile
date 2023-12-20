package com.example.rekanikan.view.edit_feeding_schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.rekanikan.R
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.data.result.Result
import com.example.rekanikan.databinding.ActivityEditFeedingScheduleBinding
import com.example.rekanikan.utils.TimePickerFragment
import com.example.rekanikan.view.add_feeding_schedule.AddFeedingScheduleViewModel
import com.example.rekanikan.view.feeder.FeederActivity
import com.example.rekanikan.view.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditFeedingScheduleActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityEditFeedingScheduleBinding

    private val viewModel by viewModels<EditFeedingScheduleViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditFeedingScheduleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val scheduleId = intent.getIntExtra(SCHEDULE_ID, 0)
        val scheduleHour = intent.getIntExtra(SCHEDULE_HOUR, 0)
        val scheduleMinute = intent.getIntExtra(SCHEDULE_MINUTE, 0)
        val schedulePortion = intent.getIntExtra(SCHEDULE_PORTION, 0)
        val scheduleIsactive = intent.getBooleanExtra(SCHEDULE_PORTION, true)


        setupDataSchedule(hour = scheduleHour, minute = scheduleMinute, portion = schedulePortion, isActive = scheduleIsactive)

        setUpAction(scheduleId)

    }

    companion object {
        const val SCHEDULE_ID = "schedule_id"
        const val SCHEDULE_HOUR = "schedule_hour"
        const val SCHEDULE_MINUTE = "schedule_minute"
        const val SCHEDULE_PORTION = "schedule_portion"
        const val SCHEDULE_ISACTIVE = "schedule_isactive"
    }

    fun setupDataSchedule(hour: Int, minute: Int, portion: Int, isActive: Boolean){
        binding.timeEdt.setText("$hour:$minute")
        binding.feedingAmountEdt.setText(portion.toString())
    }

    fun setUpAction(scheduleId: Int){
        val time = binding.timeEdt
        val portion = binding.feedingAmountEdt

        if (time.text.toString().isEmpty() || portion.text.toString().isEmpty()) {
            Toast.makeText(this, "data cannot empty", Toast.LENGTH_SHORT).show()
        } else {
            binding.editScheduleButton.setOnClickListener {
                viewModel.getFeeder().observe(this){ feeder ->
                    if (feeder != null){
                        when(feeder) {
                            is Result.Loading -> {
                                showLoading(true)
                            }

                            is Result.Success -> {
                                showLoading(false)

                            val timeInput = time.text.toString()
                            val portionInput = portion.text.toString().toInt()

                            val splitTime = timeInput.split(":")
                            val hour = splitTime[0]
                            val minute = splitTime[1]


                                viewModel.editSchedule(
                                    hour = hour.toInt(),
                                    minute = minute.toInt(),
                                    portion = portionInput,
                                    isActive = true,
                                    scheduleId = scheduleId,
                                    feederId = feeder.data.data.id
                                ).observe(this){ result ->
                                    if (result != null) {
                                        when (result) {
                                            is Result.Loading -> {
                                                showLoading(true)
                                            }

                                            is Result.Error -> {
                                                showLoading(false)
                                                Toast.makeText(this, "${result.error}", Toast.LENGTH_SHORT).show()
                                            }

                                            is Result.Success -> {
                                                showLoading(false)
                                                alertDialog()

                                            }
                                        }
                                    }
                                }
                            }

                            is Result.Error ->  {
                                showLoading(false)
                                Toast.makeText(this, "${feeder.error}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }

            }
        }

        binding.deleteScheduleButton.setOnClickListener {
            viewModel.getFeeder().observe(this) { feeder ->
                if (feeder != null){
                    when(feeder) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            viewModel.deleteSchedule(
                                scheduleId = scheduleId,
                                feederId = feeder.data.data.id
                            ).observe(this){ result ->
                                if (result != null) {
                                    when(result) {
                                        is Result.Loading -> {
                                            showLoading(true)
                                        }

                                        is Result.Success -> {
                                            showLoading(false)
                                            successDeleteAlertDialog()
                                        }

                                        is Result.Error -> {
                                            showLoading(false)
                                            Toast.makeText(this@EditFeedingScheduleActivity, result.error, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }

                        is Result.Error ->  {
                            showLoading(false)
                            Toast.makeText(this, "${feeder.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


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

    private fun showLoading(isLoading: Boolean){
        binding.loadingLoop.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun alertDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            val message = context.getString(R.string.success_edit_schedule)
            val okay = context.getString(R.string.okay)
            setMessage(message)
            setPositiveButton(okay) { _, _ ->
                finish()
                finish()
                val intent = Intent(this@EditFeedingScheduleActivity, FeederActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            create()
            show()
        }
    }

    private fun successDeleteAlertDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            val message = context.getString(R.string.success_delete_schedule)
            val okay = context.getString(R.string.okay)
            setMessage(message)
            setPositiveButton(okay) { _, _ ->
                finish()
                finish()
                val intent = Intent(this@EditFeedingScheduleActivity, FeederActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            create()
            show()
        }
    }
}