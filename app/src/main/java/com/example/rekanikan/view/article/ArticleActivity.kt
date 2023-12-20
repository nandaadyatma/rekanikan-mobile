package com.example.rekanikan.view.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.rekanikan.R
import com.example.rekanikan.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityArticleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val articleUrl = intent.getStringExtra(ARTICLE_URL) as String

        val webView = binding.webView

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
            }
        }
        webView.loadUrl(articleUrl)
    }

    companion object {
        const val ARTICLE_URL = "article_url"
    }
}