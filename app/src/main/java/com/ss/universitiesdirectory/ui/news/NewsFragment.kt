package com.ss.universitiesdirectory.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentNewsBinding
import com.ss.universitiesdirectory.repository.news.NewsRepository.NewsStatus
import com.ss.universitiesdirectory.utils.showSnackBar
import com.ss.universitiesdirectory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private val binding by viewBinding(FragmentNewsBinding::bind)
    private val argument by navArgs<NewsFragmentArgs>()
    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showData()
    }

    private fun showData() = lifecycleScope.launch(Dispatchers.Main) {
        viewModel.getNews(argument.rssUrl).collect {
            when (it) {
                NewsStatus.NewsLoading -> {
                    binding.newsList.visibility = View.GONE
                    binding.progress.visibility = View.VISIBLE
                }
                is NewsStatus.NewsList -> {
                    binding.newsList.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                    binding.newsList.adapter = NewsAdapter(it.newsList)
                }
                is NewsStatus.NewsFailed -> requireView().showSnackBar(it.message)
            }
        }
    }
}