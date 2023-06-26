package com.ss.universitiesdirectory.screen.universities

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.ui.common.DefaultCenterTopAppBar
import com.ss.universitiesdirectory.ui.common.DefaultIconButton
import com.ss.universitiesdirectory.ui.common.DefaultLinearProgressIndicator
import com.ss.universitiesdirectory.ui.common.DefaultScaffold
import com.ss.universitiesdirectory.ui.common.DefaultSnackBar
import com.ss.universitiesdirectory.ui.common.SearchTopAppBar
import com.ss.universitiesdirectory.ui.theme.SaudiUniversitiesComposeTheme
import com.ss.universitiesdirectory.utils.ResponseState

@Composable
fun UniversitiesScreen(
    universitiesState: ResponseState<List<UniversityModel>>,
    onUniversitySearch: (String) -> Unit,
    onDetailsClick: (UniversityModel) -> Unit,
    onSettingsClick: () -> Unit
) {
    val snackBarHost = remember { SnackbarHostState() }
    var searchText by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }

    DefaultScaffold(
        topBar = {
            if (isSearching) SearchTopAppBar(
                searchText = searchText,
                onSearchTextChange = { searchText = it; onUniversitySearch(it) },
                onClose = { isSearching = false; searchText = ""; onUniversitySearch("") }
            ) else UniversitiesTopAppBar(
                isSearchingCallback = { isSearching = it },
                onSettingsClick = onSettingsClick
            )
        },
        snackBarHost = { SnackbarHost(hostState = snackBarHost) }
    ) {
        when (universitiesState) {
            is ResponseState.Success -> universitiesState.data?.let {
                UniversitiesList(universitiesList = it, onUniversityClick = onDetailsClick)
            }

            is ResponseState.Error -> universitiesState.message?.let {
                DefaultSnackBar(snackBarState = snackBarHost, message = it)
            }

            is ResponseState.Progress -> DefaultLinearProgressIndicator()

            else -> Unit
        }
    }
}

@Composable
private fun UniversitiesTopAppBar(
    isSearchingCallback: (Boolean) -> Unit,
    onSettingsClick: () -> Unit
) = DefaultCenterTopAppBar(
    actions = {
        DefaultIconButton(onClick = { isSearchingCallback(true) }, icon = Icons.Default.Search)
        DefaultIconButton(onClick = onSettingsClick, icon = Icons.Default.Settings)
    },
    title = R.string.universities_screen
)

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun UniversitiesList(
    universitiesList: List<UniversityModel>,
    onUniversityClick: (UniversityModel) -> Unit,
) = LazyColumn {
    itemsIndexed(items = universitiesList, key = { _, item -> item.name }) { index, item ->
        Column(modifier = Modifier.animateItemPlacement()) {
            if (item.province) UniversityHeader(item)
            else UniversityItem(item, onUniversityClick)
            if (index < universitiesList.lastIndex) Divider()
        }
    }
}

@Composable
private fun UniversityHeader(province: UniversityModel) = ListItem(
    headlineContent = {
        Text(
            text = province.name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
    },
    colors = ListItemDefaults.colors(
        containerColor = Color.Transparent,
        headlineColor = MaterialTheme.colorScheme.primary
    )
)

@Composable
private fun UniversityItem(
    university: UniversityModel,
    onUniversityClick: (UniversityModel) -> Unit,
) = ListItem(
    headlineContent = { Text(text = university.name) },
    modifier = Modifier.clickable { onUniversityClick(university) },
    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
)

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
private fun UniversitiesScreenPreview() = SaudiUniversitiesComposeTheme {
    UniversitiesScreen(
        universitiesState = ResponseState.Success(listOf()),
        onUniversitySearch = {},
        onDetailsClick = {},
        onSettingsClick = {}
    )
}