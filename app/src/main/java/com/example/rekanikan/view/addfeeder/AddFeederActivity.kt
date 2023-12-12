package com.example.rekanikan.view.addfeeder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rekanikan.R
import com.example.rekanikan.databinding.ActivityAddFeederBinding
import com.example.rekanikan.databinding.ActivityFeederBinding
import com.example.rekanikan.view.shop.ShopActivity

class AddFeederActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFeederBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFeederBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView(){
        binding.shopShortcut.setOnClickListener {
            startActivity(Intent(this@AddFeederActivity, ShopActivity::class.java))
        }
    }
}