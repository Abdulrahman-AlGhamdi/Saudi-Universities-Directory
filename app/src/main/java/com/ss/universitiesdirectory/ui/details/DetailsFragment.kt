package com.ss.universitiesdirectory.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentDetailsBinding
import com.ss.universitiesdirectory.utils.viewBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val argument by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showData()
    }

    private fun showData() {
        val university = argument.university
        val directions = DetailsFragmentDirections
        binding.detailsLogo.load(university.logo)
        binding.about.text = university.about

        if (university.colleges.isNotEmpty()) {
            binding.colleges.visibility = View.VISIBLE
            binding.colleges.setOnClickListener {
                val action = directions.actionDetailsFragmentToWebsiteFragment(university.colleges)
                findNavController().navigate(action)
            }
        }
        if (university.news.isNotEmpty()) {
            binding.rss.visibility = View.VISIBLE
            binding.rss.setOnClickListener {
                val action = directions.actionDetailsFragmentToNewsFragment(university.news)
                findNavController().navigate(action)
            }
        }
        if (university.website.isNotEmpty()) {
            binding.website.visibility = View.VISIBLE
            binding.website.setOnClickListener {
                val action = directions.actionDetailsFragmentToWebsiteFragment(university.website)
                findNavController().navigate(action)
            }
        }
        if (university.application.isNotEmpty()) {
            binding.detailsApplication.visibility = View.VISIBLE
            binding.detailsApplication.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.application)))
            }
        }
        if (university.location.isNotEmpty()) {
            binding.detailsLocation.visibility = View.VISIBLE
            binding.detailsLocation.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.location)))
            }
        }
        if (university.twitter.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.twitter.visibility = View.VISIBLE
            binding.twitter.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.twitter)))
            }
        }
        if (university.facebook.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.facebook.visibility = View.VISIBLE
            binding.facebook.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.facebook)))
            }
        }
        if (university.youtube.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.youtube.visibility = View.VISIBLE
            binding.youtube.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.youtube)))
            }
        }
        if (university.instagram.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.instagram.visibility = View.VISIBLE
            binding.instagram.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.instagram)))
            }
        }
        if (university.snapchat.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.snapchat.visibility = View.VISIBLE
            binding.snapchat.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.snapchat)))
            }
        }
    }
}