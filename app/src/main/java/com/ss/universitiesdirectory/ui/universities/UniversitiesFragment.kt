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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.data.remote.ResponseStatus
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.Gray
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White
import kotlinx.coroutines.launch

private lateinit var nv: NavHostController

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UniversitiesScreen(navController: NavHostController, viewModel: UniversitiesViewModel) {
    nv = navController

    Scaffold(
        topBar = {
            UniversitiesTopBar(
                searchText = viewModel.searchText,
                isSearching = viewModel.isSearching,
                isSearchingCallback = {
                    viewModel.searchText = ""
                    viewModel.searchList("")
                    viewModel.isSearching = !viewModel.isSearching
                },
                searchChangeCallback = {
                    viewModel.searchText = it
                    viewModel.searchList(it)
                }
            )
        },
        content = { paddingValues ->
            UniversitiesContent(
                paddingValues = paddingValues,
                listOfUniversities = viewModel.listOfUniversities,
                onUniversityClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("university", it)
                    nv.navigate(route = "details")
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = viewModel.snackBarHost) }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        viewModel.universitiesState.collectAsState().let {
            when (val state = it.value) {
                is ResponseStatus.Success<*> -> {
                    viewModel.universities = state.data as List<UniversityModel>
                    viewModel.listOfUniversities = viewModel.universities
                }
                ResponseStatus.Progress -> CircularProgressIndicator(
                    color = PrimaryColor,
                    modifier = Modifier.align(Alignment.Center)
                )
                is ResponseStatus.Failed -> viewModel.coroutineScope.launch {
                    viewModel.snackBarHost.currentSnackbarData?.dismiss()
                    viewModel.snackBarHost.showSnackbar(state.message)
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun UniversitiesTopBar(
    searchText: String,
    isSearching: Boolean,
    isSearchingCallback: () -> Unit,
    searchChangeCallback: (String) -> Unit
) = if (isSearching) UniversitiesSearchTopBar(
    searchText = searchText,
    searchChangeCallback = searchChangeCallback,
    isSearchingCallback = isSearchingCallback
) else UniversitiesDefaultTopBar(isSearchingCallback = isSearchingCallback)

@Composable
private fun UniversitiesDefaultTopBar(isSearchingCallback: () -> Unit) = CenterAlignedTopAppBar(
    title = { Text(text = "Universities") },
    actions = {
        IconButton(onClick = isSearchingCallback) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
        IconButton(onClick = { nv.navigate(route = "settings") }) {
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
private fun UniversitiesSearchTopBar(
    searchText: String,
    searchChangeCallback: (String) -> Unit,
    isSearchingCallback: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        value = searchText,
        onValueChange = { searchChangeCallback(it) },
        shape = RoundedCornerShape(0.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = isSearchingCallback) {
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
    listOfUniversities: List<UniversityModel>,
    onUniversityClick: (UniversityModel) -> Unit
) = Box(modifier = Modifier.padding(paddingValues)) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(
            items = listOfUniversities,
            key = { it.name },
        ) {
            Box(modifier = Modifier.animateItemPlacement()) {
                if (it.province) UniversityHeader(it, Modifier.align(Alignment.Center))
                else UniversityItem(it, onUniversityClick)
                Divider(color = Gray)
            }
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