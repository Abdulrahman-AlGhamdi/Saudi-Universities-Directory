package com.ss.universitiesdirectory.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ss.universitiesdirectory.RSS.Downloader
import com.ss.universitiesdirectory.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val argument by navArgs<NewsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        init()
        showData()

        return binding.root
    }

    private fun init() {
        binding.newsList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showData() {
        Downloader(argument.rssUrl, binding.newsList).execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}