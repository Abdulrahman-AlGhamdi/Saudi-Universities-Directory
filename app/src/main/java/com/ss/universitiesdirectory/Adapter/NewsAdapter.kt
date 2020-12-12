package com.ss.universitiesdirectory.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ss.universitiesdirectory.Model.NewsModel
import com.ss.universitiesdirectory.R

class NewsAdapter(private val newsList: List<NewsModel>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.item_title)
        var date: TextView = itemView.findViewById(R.id.item_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_raw_holder, parent, false)
    )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]

        holder.title.text = news.Title
        holder.date.text = news.Date

    }

    override fun getItemCount() = newsList.size
}