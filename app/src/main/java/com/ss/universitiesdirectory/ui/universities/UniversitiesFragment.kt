package com.ss.universitiesdirectory.ui.universities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.data.remote.ResponseStatus
import com.ss.universitiesdirectory.databinding.FragmentUniversitiesBinding
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.White
import com.ss.universitiesdirectory.utils.navigateTo
import com.ss.universitiesdirectory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class UniversitiesFragment : Fragment(R.layout.fragment_universities) {

    private val binding by viewBinding(FragmentUniversitiesBinding::bind)
    private val viewModel by viewModels<UniversitiesViewModel>()
    private val directions = UniversitiesFragmentDirections
    private lateinit var universitiesJob: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.composeView.apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            this.setContent {
                GetAllUniversities()
            }
        }
    }

    @Composable
    fun GetAllUniversities() {
        viewModel.universitiesState.collectAsState().apply {
            when (val state = this.value) {
                ResponseStatus.Progress -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = { CircularProgressIndicator() }
                )
                is ResponseStatus.Success<*> -> UniversitiesList(state.data as List<UniversityModel>)
                is ResponseStatus.Failed -> Snackbar(content = { Text(text = state.message) })
                else -> Unit
            }
        }
    }

    @Composable
    fun UniversitiesList(list: List<UniversityModel>) = LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize()
    ) {
        items(list, key = { it.id }) { university ->
            if (university.province) UniversityHeader(university)
            else UniversityItem(university)
            Divider()
        }
    }

    @Composable
    fun UniversityHeader(province: UniversityModel) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = province.name,
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) White else Black
        )
    }

    @Composable
    fun UniversityItem(university: UniversityModel) {
        Column(
            modifier = Modifier
                .clickable {
                    val action = directions.actionUniversitiesFragmentToDetailsFragment(university)
                    findNavController().navigateTo(action, R.id.universitiesFragment)
                }
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = university.name,
                color = if (isSystemInDarkTheme()) White else Black
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_universities, menu)
//        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextChange(newText: String): Boolean {
//                val searchedList =
//                    viewModel.universities.filter { it.name.lowercase().contains(newText) }
//
//                if (searchedList.isEmpty()) binding.noSearch.visibility = View.VISIBLE
//                else binding.noSearch.visibility = View.GONE
//                return false
//            }
//
//            override fun onQueryTextSubmit(query: String): Boolean = false
//        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            val directions = UniversitiesFragmentDirections
            val action = directions.actionUniversitiesFragmentToSettingsFragment()
            findNavController().navigateTo(action, R.id.universitiesFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        if (::universitiesJob.isInitialized) universitiesJob.cancel()
        super.onDestroyView()
    }
}