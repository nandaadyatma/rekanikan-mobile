package com.example.rekanikan.view.product_detail

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.rekanikan.R
import com.example.rekanikan.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var strikeTroughPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        strikeTroughPrice = binding.productPrice2
        strikeTroughPrice.paintFlags = strikeTroughPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}