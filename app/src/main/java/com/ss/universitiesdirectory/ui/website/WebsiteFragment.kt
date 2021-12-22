package com.ss.universitiesdirectory.ui.website

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentWebsiteBinding
import com.ss.universitiesdirectory.utils.viewBinding

@SuppressLint("SetJavaScriptEnabled")
class WebsiteFragment : Fragment(R.layout.fragment_website) {

    private val binding by viewBinding(FragmentWebsiteBinding::bind)
    private val argument by navArgs<WebsiteFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        showWebsite()
    }

    private fun showWebsite() {
        binding.website.settings.javaScriptEnabled = true
        binding.website.loadUrl(argument.url)

        binding.website.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_website) startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(argument.url)))
        return super.onOptionsItemSelected(item)
    }
}