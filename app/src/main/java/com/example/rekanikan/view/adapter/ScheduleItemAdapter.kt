package com.example.rekanikan.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rekanikan.data.model.FeedingScheduleItem
import com.example.rekanikan.databinding.FeedingScheduleItemBinding
import com.example.rekanikan.view.edit_feeding_schedule.EditFeedingScheduleActivity

class ScheduleItemAdapter(private val activity: Activity) : ListAdapter<FeedingScheduleItem, ScheduleItemAdapter.MyViewHolder>( ScheduleItemAdapter.DIFF_CALLBACK
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemAdapter.MyViewHolder {
        val binding = FeedingScheduleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleItemAdapter.MyViewHolder, position: Int) {
        val scheduleItem = getItem(position)
        holder.bind(scheduleItem)
        holder.itemView.setOnClickListener{
            val moveUserDataIntent = Intent(holder.itemView.context, EditFeedingScheduleActivity::class.java)
            moveUserDataIntent.putExtra(EditFeedingScheduleActivity.SCHEDULE_ID, scheduleItem.id)
            moveUserDataIntent.putExtra(EditFeedingScheduleActivity.SCHEDULE_HOUR, scheduleItem.hour)
            moveUserDataIntent.putExtra(EditFeedingScheduleActivity.SCHEDULE_MINUTE, scheduleItem.minute)
            moveUserDataIntent.putExtra(EditFeedingScheduleActivity.SCHEDULE_PORTION, scheduleItem.portion)
            moveUserDataIntent.putExtra(EditFeedingScheduleActivity.SCHEDULE_ISACTIVE, scheduleItem.isActive)
            activity.startActivity(moveUserDataIntent)
        }
    }

    class MyViewHolder(val binding: FeedingScheduleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedingScheduleItem){

            var hour = item.hour.toString()
            var minute = item.minute.toString()

            if (item.hour < 10){
                 hour = "0${item.hour}"
            } else {
                hour = "${item.hour}"
            }

            if (item.minute < 10) {
                minute = "0${item.minute}"
            } else {
                minute = "${item.minute}"
            }

            binding.timeSchedule.text = "$hour:$minute"
            binding.feedingPortion.text = "${item.portion} g"
        }

    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeedingScheduleItem>() {
            override fun areItemsTheSame(oldItem: FeedingScheduleItem, newItem: FeedingScheduleItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FeedingScheduleItem, newItem: FeedingScheduleItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}