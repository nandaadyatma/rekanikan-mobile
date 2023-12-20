package com.example.rekanikan.view.product_detail

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.rekanikan.data.model.ProductItem
import com.example.rekanikan.utils.Utils.withNumberingFormat
import com.example.rekanikan.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var strikeTroughPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        val discount = intent.getIntExtra(EXTRA_DISCOUNT,0)
        val finalPrice = intent.getIntExtra(EXTRA_PRICE,0)
        val productTitle = intent.getStringExtra(EXTRA_TITLE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val detail = intent.getStringExtra(EXTRA_DETAIL)
        val image = intent.getIntExtra(EXTRA_IMAGE,0)

        val previousPrice = finalPrice * (100 + discount)/100
        val finalPriceFormatted= (finalPrice).toString().withNumberingFormat()
        val previousPriceFormatted = (previousPrice).toString().withNumberingFormat()

        binding.productPrice1.text = "Rp${finalPriceFormatted}"
        binding.productPrice2.text = "Rp${previousPriceFormatted}"

        binding.productDetailImg.setImageResource(image)
        binding.productName.text = productTitle
        binding.descriptionContent.text = description

        binding.detailContent.text = detail

        strikeTroughPrice = binding.productPrice2
        strikeTroughPrice.paintFlags = strikeTroughPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_SUBTITLE = "extra_subtitle"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_DISCOUNT = "extra_discount"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_DETAIL = "extra_detail"
    }
}