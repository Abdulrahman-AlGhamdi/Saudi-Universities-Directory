package com.ss.universitiesdirectory.ui.news

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.data.model.news.NewsModel
import com.ss.universitiesdirectory.databinding.FragmentNewsBinding
import com.ss.universitiesdirectory.repository.news.NewsRepository.NewsStatus
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White
import com.ss.universitiesdirectory.utils.navigateTo
import com.ss.universitiesdirectory.utils.showSnackBar
import com.ss.universitiesdirectory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private val binding by viewBinding(FragmentNewsBinding::bind)
    private val argument by navArgs<NewsFragmentArgs>()
    private val viewModel: NewsViewModel by viewModels()
    private val directions = NewsFragmentDirections

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUniversityNews(argument.rssUrl)

        binding.composeView.apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            this.setContent {
                viewModel.newsStatus.collectAsState().apply {
                    when (val status = this.value) {
                        is NewsStatus.NewsList -> NewsList(status.newsList)
                        is NewsStatus.NewsFailed -> requireView().showSnackBar(status.message)
                        NewsStatus.NewsLoading -> Box(
                            contentAlignment = Alignment.Center,
                            content = { CircularProgressIndicator(color = PrimaryColor) }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun NewsList(newsList: List<NewsModel>) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(newsList) { news ->
                Column(
                    modifier = Modifier
                        .clickable {
                            val action = directions.actionNewsFragmentToWebsiteFragment(news.link)
                            findNavController().navigateTo(action, R.id.newsFragment)
                        }
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = news.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = if (isSystemInDarkTheme()) White else Black
                    )
                }
                Divider()
            }
        }
    }
}