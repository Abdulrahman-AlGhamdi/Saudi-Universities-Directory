package com.ss.universitiesdirectory.ui.website

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentWebsiteBinding
import com.ss.universitiesdirectory.utils.viewBinding

class WebsiteFragment : Fragment(R.layout.fragment_website) {

    private val binding by viewBinding(FragmentWebsiteBinding::bind)
    private val argument by navArgs<WebsiteFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        showWebsite()
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
}