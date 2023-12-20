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
import com.example.rekanikan.data.model.ArticleItem
import com.example.rekanikan.data.model.FeedingHistoryItem
import com.example.rekanikan.data.model.InformationCardItem
import com.example.rekanikan.data.result.Result
import com.example.rekanikan.databinding.ActivityMainBinding
import com.example.rekanikan.view.about.AboutActivity
import com.example.rekanikan.view.adapter.ArticleAdapter
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

        setPromoAndInformationData(PromoAndInformationData.data)

        viewModel.getArticles().observe(this){ result ->
            if (result != null){
                when(result) {
                    is Result.Loading -> { showLoading(true)}

                    is Result.Success -> {
                        showLoading(false)
                        val articles = result.data.message
                        if (articles.isNotEmpty()){
                            val data = articles.map {
                                ArticleItem(
                                    articleId = it.articleId,
                                    authorName = it.authorName,
                                    tag = it.tag,
                                    photoUrl = it.photoUrl,
                                    title = it.title,
                                    content = it.content,
                                )
                            }
                            setArticleData(data)
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
//                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFeeder().observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> { showLoading(true)}

                    is Result.Success -> {
                        showLoading(false)
                        val feeder = result.data.data
                        if(feeder != null) {
                            binding.noFeederAddedCard.visibility = View.GONE
                            binding.feederCard.visibility = View.VISIBLE

                            binding.feederName.text = feeder.name

                            setHistoryFeedingData(FeedingHistoryData.data.subList(0, 3))
                        }
                    }

                    is Result.Error -> {
                        showLoading(false)
//                        Toast.makeText(this@MainActivity, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
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

        binding.feederCard.setOnClickListener{
            val intent = Intent(this@MainActivity, FeederActivity::class.java)
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
            val greeting = getString(R.string.greeting, user.name)
            binding.greeting1.text = greeting
        }
    }

    private fun setHistoryFeedingData(data: List<FeedingHistoryItem>){
        binding.blankHistoryData.visibility = View.GONE
        binding.rvHistoryFeeding.visibility = View.VISIBLE

        val adapter = FeedingHistoryAdapter(this@MainActivity)
        adapter.submitList(data)
        binding.rvHistoryFeeding.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvHistoryFeeding.layoutManager = layoutManager
    }

    private fun setArticleData(data: List<ArticleItem>){
        val adapter = ArticleAdapter(this@MainActivity)
        adapter.submitList(data)
        binding.rvArticles.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvArticles.layoutManager = layoutManager

    }

    private fun setPromoAndInformationData(data: List<InformationCardItem>){
        val adapter = InformationCardAdapter(this@MainActivity)
        adapter.submitList(data)
        binding.rvPromoinfo.adapter = adapter

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPromoinfo.layoutManager = layoutManager
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingLoop.visibility = if (isLoading) View.VISIBLE else View.GONE

    }
}