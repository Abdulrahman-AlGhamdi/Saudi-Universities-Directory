package com.ss.universitiesdirectory.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ss.universitiesdirectory.databinding.FragmentWebsiteBinding

class WebsiteFragment : Fragment() {

    private var _binding: FragmentWebsiteBinding? = null
    private val binding get() = _binding!!
    private val argument by navArgs<WebsiteFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWebsiteBinding.inflate(inflater, container, false)

        init()
        showUrl()

        return binding.root
    }

    private fun init() {
        binding.website.settings.javaScriptEnabled
    }

    private fun showUrl() {
        binding.website.loadUrl(argument.url)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}