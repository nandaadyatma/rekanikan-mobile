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
import com.example.rekanikan.databinding.FeedingHistoryItemBinding


class FeedingHistoryAdapter(private val activity: Activity) : ListAdapter<FeedingHistoryItem, FeedingHistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingHistoryAdapter.MyViewHolder {
        val binding = FeedingHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedingHistoryAdapter.MyViewHolder, position: Int) {
        val historyItem = getItem(position)
        holder.bind(historyItem)
//        holder.itemView.setOnClickListener{
//            val moveUserDataIntent = Intent(holder.itemView.context, UserDetailActivity::class.java)
//            moveUserDataIntent.putExtra(UserDetailActivity.USERNAME, user.login)
//            activity.startActivity(moveUserDataIntent)
//        }
    }

    class MyViewHolder(val binding: FeedingHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedingHistoryItem){
            binding.timeSchedule.text = item.time
            binding.date.text = item.date
            binding.feedingPortion.text = "${item.portion} g"
        }

    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeedingHistoryItem>() {
            override fun areItemsTheSame(oldItem: FeedingHistoryItem, newItem: FeedingHistoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FeedingHistoryItem, newItem: FeedingHistoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}