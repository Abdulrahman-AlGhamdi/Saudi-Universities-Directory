package com.ss.universitiesdirectory.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.RawNewsItemBinding
import com.ss.universitiesdirectory.data.model.news.NewsModel
import com.ss.universitiesdirectory.utils.navigateTo

class NewsAdapter(
    private val newsList: List<NewsModel>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(
        private val binding: RawNewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: NewsModel) {
            binding.title.text = news.title
            binding.date.text = news.date

            binding.root.setOnClickListener {
                val directions = NewsFragmentDirections
                val action = directions.actionNewsFragmentToWebsiteFragment(news.link)
                itemView.findNavController().navigateTo(action, R.id.newsFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder(
        RawNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount() = newsList.size
}