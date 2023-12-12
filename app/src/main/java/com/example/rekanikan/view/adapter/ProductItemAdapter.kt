package com.example.rekanikan.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rekanikan.data.model.FeedingHistoryItem
import com.example.rekanikan.data.model.ProductItem
import com.example.rekanikan.databinding.FeedingHistoryItemBinding
import com.example.rekanikan.databinding.ProductItemBinding
import com.example.rekanikan.view.product_detail.ProductDetailActivity


class ProductItemAdapter(private val activity: Activity) : ListAdapter<ProductItem, ProductItemAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemAdapter.MyViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductItemAdapter.MyViewHolder, position: Int) {
        val productItem = getItem(position)
        holder.bind(productItem)
        holder.itemView.setOnClickListener{
            val moveDetailIntent = Intent(holder.itemView.context, ProductDetailActivity::class.java)
//            moveUserDataIntent.putExtra(Deta.USERNAME, user.login)
            activity.startActivity(moveDetailIntent)
        }
    }

    class MyViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductItem){
            binding.productTitle.text = item.title
            binding.productSubTitle.text = item.subTitle
            binding.productPrice.text = "Rp.${item.price}"
        }

    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}