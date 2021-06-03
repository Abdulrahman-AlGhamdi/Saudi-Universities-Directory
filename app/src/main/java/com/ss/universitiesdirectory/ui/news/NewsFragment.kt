package com.ss.universitiesdirectory.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ss.universitiesdirectory.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val argument by navArgs<NewsFragmentArgs>()
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        showData()

        return binding.root
    }

    private fun showData() {
        lifecycleScope.launchWhenCreated {
            viewModel.getNews(argument.rssUrl).collect {
                when (it) {
                    is NewsRepository.NewsStatus.NewsFailed ->
                        Snackbar.make(requireView(), it.message, Snackbar.LENGTH_SHORT).show()
                    is NewsRepository.NewsStatus.NewsList -> {
                        binding.newsList.visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                        binding.newsList.adapter = NewsAdapter(it.newsList)
                    }
                    NewsRepository.NewsStatus.NewsLoading -> {
                        binding.newsList.visibility = View.GONE
                        binding.progress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}