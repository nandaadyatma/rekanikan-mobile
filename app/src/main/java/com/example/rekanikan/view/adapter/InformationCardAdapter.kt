package com.example.rekanikan.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rekanikan.data.model.InformationCardItem
import com.example.rekanikan.databinding.ImageInfoItemBinding

class InformationCardAdapter(private val activity: Activity) : ListAdapter<InformationCardItem, InformationCardAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationCardAdapter.MyViewHolder {
        val binding = ImageInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformationCardAdapter.MyViewHolder, position: Int) {
        val infoItem = getItem(position)
        holder.bind(infoItem)
    }

    class MyViewHolder(val binding: ImageInfoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InformationCardItem){
            binding.infoTitle.text = item.title
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .into(binding.infoImage)
        }

    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InformationCardItem>() {
            override fun areItemsTheSame(oldItem: InformationCardItem, newItem: InformationCardItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: InformationCardItem, newItem: InformationCardItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}