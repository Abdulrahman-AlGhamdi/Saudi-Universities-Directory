package com.ss.universitiesdirectory.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.data.model.news.NewsModel
import com.ss.universitiesdirectory.repository.news.NewsRepositoryImpl.NewsStatus
import com.ss.universitiesdirectory.ui.theme.Gray
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White
import kotlinx.coroutines.launch

private lateinit var vm: NewsViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewsScreen(navController: NavHostController, viewModel: NewsViewModel, address: String) {
    vm = viewModel
    vm.getUniversityNews(address)

    Scaffold(
        topBar = { NewsTopBar(navController) },
        content = { NewsContent(it, navController) },
        snackbarHost = { SnackbarHost(hostState = vm.snackBarHost) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NewsTopBar(navController: NavHostController) = CenterAlignedTopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.news_fragment),
            fontFamily = FontFamily(Font(R.font.quest_regular))
        )
    },
    navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = PrimaryColor,
        titleContentColor = White,
        navigationIconContentColor = White,
        actionIconContentColor = White
    )
)

@Composable
private fun NewsContent(paddingValues: PaddingValues, navController: NavHostController) = Box(
    modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
) {
    vm.newsStatus.collectAsState().apply {
        when (val state = this.value) {
            is NewsStatus.NewsFailed -> vm.coroutineScope.launch {
                vm.snackBarHost.currentSnackbarData?.dismiss()
                vm.snackBarHost.showSnackbar(state.message)
            }
            NewsStatus.NewsLoading -> CircularProgressIndicator(
                color = PrimaryColor,
                modifier = Modifier.align(Alignment.Center)
            )
            is NewsStatus.NewsList -> NewsList(navController, state.newsList)
        }
    }
}

@Composable
fun NewsList(navController: NavHostController, newsList: List<NewsModel>) = LazyColumn {
    items(newsList) { news ->
        Column(
            modifier = Modifier
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set("url", news.link)
                    navController.navigate(route = "website")
                }
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = news.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
        }
        Divider(color = Gray)
    }
}