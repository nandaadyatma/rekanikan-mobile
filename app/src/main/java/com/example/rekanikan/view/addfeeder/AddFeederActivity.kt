package com.example.rekanikan.view.addfeeder

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
import com.example.rekanikan.databinding.ActivityAddFeederBinding
import com.example.rekanikan.databinding.ActivityFeederBinding
import com.example.rekanikan.view.add_feeding_schedule.AddFeedingScheduleViewModel
import com.example.rekanikan.view.shop.ShopActivity

class AddFeederActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFeederBinding

    private val viewModel by viewModels<AddFeederViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFeederBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAddFeederAction()
    }

    private fun setupView(){
        binding.shopShortcut.setOnClickListener {
            startActivity(Intent(this@AddFeederActivity, ShopActivity::class.java))
        }

    }

    private fun setupAddFeederAction(){
        val feederId = binding.edFeederId
        val feederName = binding.edtName

        binding.addFeederButton.setOnClickListener {
            val feederIdInput = feederId.text.toString()
            val feederNameInput = feederName.text.toString()

            if (feederIdInput.isEmpty() || feederNameInput.isEmpty()){
                val message = getString(R.string.feeder_data_cannot_empty)
                Toast.makeText(this@AddFeederActivity, message, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addFeeder(feederId = feederIdInput, feederName = feederNameInput).observe(this) { result ->
                    if (result != null) {
                        when(result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }

                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(this@AddFeederActivity, result.error, Toast.LENGTH_SHORT).show()

                            }

                            is Result.Success -> {
                                showLoading(false)
                                alertDialog()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingLoop.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun alertDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            val message = context.getString(R.string.success_add_feeder)
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