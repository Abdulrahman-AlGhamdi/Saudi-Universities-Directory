package com.ss.universitiesdirectory.ui.universities

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.data.remote.ResponseState
import com.ss.universitiesdirectory.ui.main.DefaultTopAppBar
import com.ss.universitiesdirectory.ui.main.Screen
import com.ss.universitiesdirectory.ui.main.SearchTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UniversitiesScreen(
    navController: NavHostController,
    viewModel: UniversitiesViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {

    Scaffold(
        topBar = {
            Column {
                UniversitiesTopBar(
                    isSearching = viewModel.isSearching,
                    isSearchingCallback = {
                        viewModel.searchText = ""
                        viewModel.isSearching = it
                    },
                    searchText = viewModel.searchText,
                    onSearchChangeCallback = {
                        viewModel.searchText = it
                        viewModel.searchList(it)
                    },
                    onSettingsClick = {
                        navController.navigate(route = Screen.SettingsScreen.route)
                    }
                )
                Divider()
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                UniversitiesContent(
                    universitiesList = viewModel.universitiesSearchedList,
                    onUniversityClick = {
                        viewModel.searchText = ""
                        viewModel.isSearching = false
                        navController.currentBackStackEntry?.savedStateHandle?.set("university", it)
                        navController.navigate(route = Screen.DetailsScreen.route)
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = viewModel.snackBarHost) }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        viewModel.universitiesState.collectAsState().let {
            when (val state = it.value) {
                is ResponseState.Progress -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                is ResponseState.Success -> state.data?.let {
                    viewModel.universities = state.data
                    viewModel.universitiesSearchedList = viewModel.universities
                }
                is ResponseState.Error -> state.message?.let {
                    coroutineScope.launch {
                        viewModel.snackBarHost.currentSnackbarData?.dismiss()
                        viewModel.snackBarHost.showSnackbar(it)
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun UniversitiesTopBar(
    isSearching: Boolean,
    isSearchingCallback: (Boolean) -> Unit,
    searchText: String,
    onSearchChangeCallback: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    if (isSearching) SearchTopAppBar(
        searchText = searchText,
        isSearching = isSearchingCallback,
        onSearchTextChange = onSearchChangeCallback
    ) else DefaultTopAppBar(
        title = R.string.universities_fragment,
        actions = {
            IconButton(onClick = { isSearchingCallback(true) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        }
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun UniversitiesContent(
    universitiesList: List<UniversityModel>,
    onUniversityClick: (UniversityModel) -> Unit
) = LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
    itemsIndexed(items = universitiesList, key = { _, item -> item.name }) { index, item ->
        Column(modifier = Modifier.animateItemPlacement()) {
            if (item.province) UniversityHeader(item, Modifier.align(Alignment.CenterHorizontally))
            else UniversityItem(item, onUniversityClick)
            if (index < universitiesList.lastIndex) Divider()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun UniversityHeader(province: UniversityModel, modifier: Modifier) = ListItem(
    headlineText = {
        Text(text = province.name, modifier = modifier, fontWeight = FontWeight.Bold)
    },
    colors = ListItemDefaults.colors(
        containerColor = Color.Transparent,
        headlineColor = MaterialTheme.colorScheme.primary
    )
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun UniversityItem(
    university: UniversityModel,
    onUniversityClick: (UniversityModel) -> Unit
) = ListItem(
    headlineText = { Text(text = university.name) },
    modifier = Modifier.clickable { onUniversityClick(university) },
    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
)