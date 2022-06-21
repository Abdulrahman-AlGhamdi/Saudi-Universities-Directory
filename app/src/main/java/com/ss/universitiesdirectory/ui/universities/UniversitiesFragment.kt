package com.ss.universitiesdirectory.ui.universities

import android.os.Bundle
import android.view.View
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
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.data.remote.ResponseStatus
import com.ss.universitiesdirectory.databinding.FragmentUniversitiesBinding
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.Gray
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White
import com.ss.universitiesdirectory.utils.navigateTo
import com.ss.universitiesdirectory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UniversitiesFragment : Fragment(R.layout.fragment_universities) {

    private val binding by viewBinding(FragmentUniversitiesBinding::bind)
    private val viewModel by viewModels<UniversitiesViewModel>()
    private val directions = UniversitiesFragmentDirections
    private lateinit var universitiesJob: Job

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.composeView.apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            this.setContent {
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
                    content = {
                        UniversitiesContent(
                            paddingValues = it,
                            listOfUniversities = viewModel.listOfUniversities,
                            onUniversityClick = {
                                val action =
                                    directions.actionUniversitiesFragmentToDetailsFragment(it)
                                findNavController().navigateTo(action, R.id.universitiesFragment)
                            }
                        )
                    },
                    snackbarHost = { SnackbarHost(hostState = viewModel.snackBarHost) }
                )

                GetAllUniversities(
                    snackBarCallback = {
                        viewModel.coroutineScope.launch {
                            viewModel.snackBarHost.currentSnackbarData?.dismiss()
                            viewModel.snackBarHost.showSnackbar(it)
                        }
                    }
                )
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
        title = { Text(text = getString(R.string.fragment_universities)) },
        actions = {
            IconButton(onClick = isSearchingCallback) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
            IconButton(onClick = {
                val action = directions.actionUniversitiesFragmentToSettingsFragment()
                findNavController().navigateTo(action, R.id.universitiesFragment)
            }) {
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
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                items = listOfUniversities,
                key = { it.name },
                itemContent = {
                    Box(modifier = Modifier.animateItemPlacement()) {
                        if (it.province) UniversityHeader(it)
                        else UniversityItem(it, onUniversityClick)
                        Divider(color = Gray)
                    }
                }
            )
        }
    }

    @Composable
    private fun GetAllUniversities(
        snackBarCallback: (String) -> Unit
    ) = Box(modifier = Modifier.fillMaxSize()) {
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
                is ResponseStatus.Failed -> snackBarCallback(state.message)
                else -> Unit
            }
        }
    }

    @Composable
    private fun UniversityHeader(province: UniversityModel) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        content = { Text(text = province.name, fontWeight = FontWeight.Bold, color = Black) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

    @Composable
    private fun UniversityItem(
        university: UniversityModel,
        onUniversityClick: (UniversityModel) -> Unit
    ) = Column(
        content = { Text(text = university.name, color = Black) },
        modifier = Modifier
            .clickable { onUniversityClick(university) }
            .fillMaxWidth()
            .padding(16.dp)
    )

    override fun onDestroyView() {
        if (::universitiesJob.isInitialized) universitiesJob.cancel()
        super.onDestroyView()
    }
}