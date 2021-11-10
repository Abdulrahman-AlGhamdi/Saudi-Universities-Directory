package com.ss.universitiesdirectory.ui.universities

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentUniversitiesBinding
import com.ss.universitiesdirectory.model.UniversityModelItem
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository.UniversitiesState
import com.ss.universitiesdirectory.ui.universities.UniversitiesFragment.ViewState.NO_INTERNET
import com.ss.universitiesdirectory.ui.universities.UniversitiesFragment.ViewState.WITH_INTERNET
import com.ss.universitiesdirectory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UniversitiesFragment : Fragment(R.layout.fragment_universities) {

    private val binding by viewBinding(FragmentUniversitiesBinding::bind)
    private val viewModel: UniversitiesViewModel by viewModels()
    private var universities = mutableListOf<UniversityModelItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val manager = requireActivity().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        if (capabilities != null) customView(WITH_INTERNET) else customView(NO_INTERNET)
    }

    private fun getUniversities() {
        val layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.universitiesList.layoutManager = layoutManager
        binding.universitiesList.addItemDecoration(itemDecoration)

        lifecycleScope.launchWhenCreated {
            viewModel.getAllUniversities().collect {
                when (it) {
                    UniversitiesState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.universitiesList.visibility = View.GONE
                    }
                    is UniversitiesState.Successful -> {
                        setHasOptionsMenu(true)
                        binding.progress.visibility = View.GONE
                        binding.universitiesList.visibility = View.VISIBLE
                        universities.clear()
                        universities.addAll(it.universities)
                        binding.universitiesList.adapter = UniversitiesAdapter(universities)
                    }
                    is UniversitiesState.Failed -> {
                        Snackbar.make(requireView(), it.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
                binding.universitiesList.adapter = UniversitiesAdapter(universities)
            }
        }
    }

    private fun customView(state: ViewState) {
        when (state) {
            NO_INTERNET -> {
                binding.progress.visibility = View.GONE
                binding.noNetwork.visibility = View.VISIBLE
                binding.noNetwork.setOnClickListener { init() }
            }
            WITH_INTERNET -> {
                binding.noNetwork.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
                binding.message.text = getString(R.string.message, String(Character.toChars(0x2764)))
                getUniversities()
            }
        }
    }

    enum class ViewState {
        NO_INTERNET,
        WITH_INTERNET
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_universities, menu)

        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val searchedList = universities.filter { it.name.lowercase().contains(query) }

                if (searchedList.isEmpty()) {
                    binding.noSearch.visibility         = View.VISIBLE
                    binding.universitiesList.visibility = View.GONE
                } else {
                    binding.noSearch.visibility         = View.GONE
                    binding.universitiesList.visibility = View.VISIBLE
                    binding.universitiesList.adapter    = UniversitiesAdapter(searchedList)
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val searchedList = universities.filter { it.name.lowercase().contains(newText) }

                if (searchedList.isEmpty()) {
                    binding.noSearch.visibility         = View.VISIBLE
                    binding.universitiesList.visibility = View.GONE
                } else {
                    binding.noSearch.visibility         = View.GONE
                    binding.universitiesList.visibility = View.VISIBLE
                    binding.universitiesList.adapter    = UniversitiesAdapter(searchedList)
                }

                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            val directions = UniversitiesFragmentDirections
            val action = directions.actionUniversitiesFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }
}