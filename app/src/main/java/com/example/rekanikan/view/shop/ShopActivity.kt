package com.example.rekanikan.view.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rekanikan.R
import com.example.rekanikan.data.dummy.PromoAndInformationData
import com.example.rekanikan.data.dummy.ShopProductData
import com.example.rekanikan.data.model.InformationCardItem
import com.example.rekanikan.data.model.ProductItem
import com.example.rekanikan.databinding.ActivityShopBinding
import com.example.rekanikan.view.adapter.InformationCardAdapter
import com.example.rekanikan.view.adapter.ProductItemAdapter

class ShopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityShopBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       setProductData(ShopProductData.data)

    }

    private fun setProductData(data: List<ProductItem>){
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        val adapter = ProductItemAdapter(this@ShopActivity)
        binding.rvProducts.adapter = adapter

        adapter.submitList(ShopProductData.data)
    }


}

