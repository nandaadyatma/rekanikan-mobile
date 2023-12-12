package com.example.rekanikan.view.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rekanikan.R
import com.example.rekanikan.data.ViewModelFactory
import com.example.rekanikan.data.dummy.FeedingHistoryData
import com.example.rekanikan.data.dummy.PromoAndInformationData
import com.example.rekanikan.data.model.FeedingHistoryItem
import com.example.rekanikan.data.model.InformationCardItem
import com.example.rekanikan.databinding.ActivityMainBinding
import com.example.rekanikan.view.about.AboutActivity
import com.example.rekanikan.view.adapter.FeedingHistoryAdapter
import com.example.rekanikan.view.adapter.InformationCardAdapter
import com.example.rekanikan.view.feeder.FeederActivity
import com.example.rekanikan.view.fishscan.FishScanActivity
import com.example.rekanikan.view.shop.ShopActivity
import com.example.rekanikan.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setGreeting()
        setupAction()

        binding.feederCard.visibility = View.GONE
        binding.rvHistoryFeeding.visibility = View.GONE
        setHistoryFeedingData(FeedingHistoryData.data.subList(0, 3))
        setPromoAndInformationData(PromoAndInformationData.data)
    }

    private fun setupAction() {
        binding.fishscanMenu.setOnClickListener{
            val intent = Intent(this@MainActivity, FishScanActivity::class.java)
            startActivity(intent)
        }

        binding.feederMenu.setOnClickListener{
            val intent = Intent(this@MainActivity, FeederActivity::class.java)
            startActivity(intent)
        }

        binding.shopMenu.setOnClickListener{
            val intent = Intent(this@MainActivity, ShopActivity::class.java)
            startActivity(intent)
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.about_menu -> {
                    startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                    true
                }
                else -> false
            }

        }
    }

    private fun setGreeting(){
        viewModel.getSession().observe(this){ user ->
            val greeting = getString(R.string.greeting, user.email)
            binding.greeting1.text = greeting
        }
    }

    private fun setHistoryFeedingData(data: List<FeedingHistoryItem>){
        binding.blankHistoryData.visibility = View.GONE
        binding.feederCard.visibility = View.VISIBLE
        binding.rvHistoryFeeding.visibility = View.VISIBLE

        val adapter = FeedingHistoryAdapter(this@MainActivity)
        adapter.submitList(data)
        binding.rvHistoryFeeding.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvHistoryFeeding.layoutManager = layoutManager
    }

    private fun setPromoAndInformationData(data: List<InformationCardItem>){
        val adapter = InformationCardAdapter(this@MainActivity)
        adapter.submitList(data)
        binding.rvPromoinfo.adapter = adapter

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPromoinfo.layoutManager = layoutManager
    }
}