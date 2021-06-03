package com.ss.universitiesdirectory.ui.website

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentWebsiteBinding

class WebsiteFragment : Fragment() {

    private var _binding: FragmentWebsiteBinding? = null
    private val binding get() = _binding!!
    private val argument by navArgs<WebsiteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebsiteBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        showWebsite()

        return binding.root
    }

    private fun showWebsite() {
        binding.website.settings.javaScriptEnabled
        binding.website.loadUrl(argument.url)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_website)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(argument.url)))
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}