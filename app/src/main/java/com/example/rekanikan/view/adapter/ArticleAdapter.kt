package com.example.rekanikan.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rekanikan.data.model.ArticleItem
import com.example.rekanikan.databinding.ArticleItemBinding
import com.example.rekanikan.view.article.ArticleActivity

class ArticleAdapter(private val activity: Activity) : ListAdapter<ArticleItem, ArticleAdapter.MyViewHolder>( ArticleAdapter.DIFF_CALLBACK
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.MyViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.MyViewHolder, position: Int) {
        val articleItem = getItem(position)
        holder.bind(articleItem)
        holder.itemView.setOnClickListener{
            val moveArticleDataIntent = Intent(holder.itemView.context, ArticleActivity::class.java)
            moveArticleDataIntent.putExtra(ArticleActivity.ARTICLE_URL,articleItem.content)
            activity.startActivity(moveArticleDataIntent)
        }
    }

    class MyViewHolder(val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleItem){

            val title = item.title
            val author = item.authorName
            val imageUrl = item.photoUrl
            var contentUrl = item.content

            binding.articleTitleTv.text = title
            binding.articleDateTv.text = author
            Glide.with(binding.root.context)
                .load(imageUrl)
                .into(binding.articleImage)
        }

    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleItem>() {
            override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}