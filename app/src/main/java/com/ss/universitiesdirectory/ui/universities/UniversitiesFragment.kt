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
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentUniversitiesBinding
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository.UniversitiesState
import com.ss.universitiesdirectory.utils.navigateTo
import com.ss.universitiesdirectory.utils.showSnackBar
import com.ss.universitiesdirectory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UniversitiesFragment : Fragment(R.layout.fragment_universities) {

    private val binding by viewBinding(FragmentUniversitiesBinding::bind)
    private val viewModel: UniversitiesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        getUniversities()
    }

    private fun init() {
        binding.message.text = getString(R.string.message, String(Character.toChars(0x2764)))

        val layoutManager  = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.universitiesList.layoutManager = layoutManager
        binding.universitiesList.addItemDecoration(itemDecoration)

        val manager = requireActivity().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        if (capabilities != null) checkNetwork(isConnected = true) else checkNetwork(isConnected = false)
    }

    private fun checkNetwork(isConnected: Boolean) = if (isConnected) {
        binding.noNetwork.visibility = View.GONE
        binding.progress.visibility  = View.VISIBLE
        if (viewModel.universities.isNullOrEmpty()) viewModel.getAllUniversities()
        else binding.universitiesList.adapter = UniversitiesAdapter(viewModel.universities)
        Unit
    } else {
        binding.progress.visibility  = View.GONE
        binding.noNetwork.visibility = View.VISIBLE
        binding.noNetworkButton.setOnClickListener { init() }
    }

    private fun getUniversities() = lifecycleScope.launch(Dispatchers.Main) {
        viewModel.universitiesState.collect {
            when (it) {
                UniversitiesState.Idle -> Unit
                UniversitiesState.Loading -> {
                    binding.progress.visibility         = View.VISIBLE
                    binding.universitiesList.visibility = View.GONE
                }
                is UniversitiesState.Successful -> {
                    setHasOptionsMenu(true)
                    binding.progress.visibility         = View.GONE
                    binding.universitiesList.visibility = View.VISIBLE

                    if (viewModel.universities.isNullOrEmpty()) viewModel.universities.addAll(it.universities)
                    binding.universitiesList.adapter = UniversitiesAdapter(viewModel.universities)
                }
                is UniversitiesState.Failed -> requireView().showSnackBar(it.message)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_universities, menu)
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                val searchedList = viewModel.universities.filter { it.name.lowercase().contains(newText) }

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

            override fun onQueryTextSubmit(query: String): Boolean { return false }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            val directions = UniversitiesFragmentDirections
            val action = directions.actionUniversitiesFragmentToSettingsFragment()
            findNavController().navigateTo(action, R.id.universitiesFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}