package com.ss.universitiesdirectory.ui.universities

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.data.remote.ResponseState
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.Gray
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UniversitiesScreen(navController: NavHostController, viewModel: UniversitiesViewModel) {
    val onUniversityClick: (UniversityModel) -> Unit = {
        navController.currentBackStackEntry?.savedStateHandle?.set("university", it)
        navController.navigate(route = "details")
    }

    val isSearchingCallback: (Boolean) -> Unit = {
        viewModel.isSearching = it
    }

    val onSearchChangeCallback: (String) -> Unit = {
        viewModel.searchText = it
        viewModel.searchList(it)
    }

    val onSettingsClick: () -> Unit = {
        navController.navigate(route = "settings")
    }

    Scaffold(
        topBar = {
            UniversitiesTopBar(
                isSearching = viewModel.isSearching,
                isSearchingCallback = isSearchingCallback,
                searchText = viewModel.searchText,
                onSearchChangeCallback = onSearchChangeCallback,
                onSettingsClick = onSettingsClick
            )
        },
        content = { paddingValues ->
            UniversitiesContent(
                paddingValues = paddingValues,
                universitiesList = viewModel.universitiesSearchedList,
                onUniversityClick = onUniversityClick
            )
        },
        snackbarHost = { SnackbarHost(hostState = viewModel.snackBarHost) }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        viewModel.universitiesState.collectAsState().let {
            when (val state = it.value) {
                is ResponseState.Progress -> CircularProgressIndicator(
                    color = PrimaryColor,
                    modifier = Modifier.align(Alignment.Center)
                )
                is ResponseState.Success -> state.data?.let {
                    viewModel.universities = state.data
                    viewModel.universitiesSearchedList = viewModel.universities
                }
                is ResponseState.Error -> state.message?.let { it1 -> viewModel.errorMessage = it1 }
                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = viewModel.errorMessage, block = {
        if (viewModel.errorMessage == "") return@LaunchedEffect
        viewModel.snackBarHost.currentSnackbarData?.dismiss()
        viewModel.snackBarHost.showSnackbar(viewModel.errorMessage)
    })
}

@Composable
private fun UniversitiesTopBar(
    isSearching: Boolean,
    isSearchingCallback: (Boolean) -> Unit,
    searchText: String,
    onSearchChangeCallback: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    if (isSearching) UniversitiesSearchTopBar(
        searchText = searchText,
        isSearchingCallback = isSearchingCallback,
        onSearchChangeCallback = onSearchChangeCallback
    )
    else UniversitiesDefaultTopBar(
        onSettingsClick = onSettingsClick,
        isSearchingCallback = isSearchingCallback
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun UniversitiesDefaultTopBar(
    isSearchingCallback: (Boolean) -> Unit,
    onSettingsClick: () -> Unit
) = CenterAlignedTopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.universities_fragment),
            fontFamily = FontFamily(Font(R.font.quest_regular))
        )
    },
    actions = {
        IconButton(onClick = { isSearchingCallback(true) }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
        IconButton(onClick = onSettingsClick) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
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
@OptIn(ExperimentalMaterial3Api::class)
private fun UniversitiesSearchTopBar(
    searchText: String,
    isSearchingCallback: (Boolean) -> Unit,
    onSearchChangeCallback: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        value = searchText,
        onValueChange = { onSearchChangeCallback(it) },
        placeholder = { Text(text = stringResource(id = R.string.universities_search)) },
        shape = RoundedCornerShape(0.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = {
                onSearchChangeCallback("")
                isSearchingCallback(false)
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = White,
            containerColor = PrimaryColor,
            textColor = White,
            placeholderColor = White,
            focusedLeadingIconColor = White,
            focusedTrailingIconColor = White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .focusRequester(focusRequester)
    )

    LaunchedEffect(key1 = focusRequester) {
        focusRequester.requestFocus()
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun UniversitiesContent(
    paddingValues: PaddingValues,
    universitiesList: List<UniversityModel>,
    onUniversityClick: (UniversityModel) -> Unit
) = LazyColumn(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(paddingValues)
) {
    items(items = universitiesList, key = { it.name }) {
        Box(modifier = Modifier.animateItemPlacement()) {
            if (it.province) UniversityHeader(it, Modifier.align(Alignment.Center))
            else UniversityItem(it, onUniversityClick)
            Divider(color = Gray)
        }
    }
}

@Composable
private fun UniversityHeader(province: UniversityModel, modifier: Modifier) = Text(
    text = province.name,
    fontWeight = FontWeight.Bold,
    color = Black,
    modifier = modifier.padding(16.dp)
)

@Composable
private fun UniversityItem(
    university: UniversityModel,
    onUniversityClick: (UniversityModel) -> Unit
) = Text(
    text = university.name,
    color = Color.Gray,
    modifier = Modifier
        .clickable { onUniversityClick(university) }
        .fillMaxWidth()
        .padding(16.dp)
)