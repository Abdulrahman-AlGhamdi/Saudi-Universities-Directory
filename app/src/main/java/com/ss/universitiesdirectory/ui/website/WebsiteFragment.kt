package com.ss.universitiesdirectory.ui.website

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidView
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

        binding.composeView.apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            this.setContent {
                WebViewPage(url = argument.url)
            }
        }
    }

    @Composable
    fun WebViewPage(url: String) {
        AndroidView(factory = {
            WebView(it).apply {
                this.webViewClient = WebViewClient()
                this.settings.javaScriptEnabled = true
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                loadUrl(url)
            }
        }, update = {
            it.loadUrl(url)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(argument.url))
        if (item.itemId == R.id.menu_website) startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}