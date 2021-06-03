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
import com.ss.universitiesdirectory.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val argument by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        showData()

        return binding.root
    }

    private fun showData() {
        val university = argument.university
        val directions = DetailsFragmentDirections
        binding.detailsLogo.load(university.Logo)
        binding.about.text = university.About

        if (university.Colleges.isNotEmpty()) {
            binding.colleges.visibility = View.VISIBLE
            binding.colleges.setOnClickListener {
                val action = directions.actionDetailsFragmentToWebsiteFragment(university.Colleges)
                findNavController().navigate(action)
            }
        }
        if (university.News.isNotEmpty()) {
            binding.rss.visibility = View.VISIBLE
            binding.rss.setOnClickListener {
                val action = directions.actionDetailsFragmentToNewsFragment(university.News)
                findNavController().navigate(action)
            }
        }
        if (university.Website.isNotEmpty()) {
            binding.website.visibility = View.VISIBLE
            binding.website.setOnClickListener {
                val action = directions.actionDetailsFragmentToWebsiteFragment(university.Website)
                findNavController().navigate(action)
            }
        }
        if (university.Application.isNotEmpty()) {
            binding.detailsApplication.visibility = View.VISIBLE
            binding.detailsApplication.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Application)))
            }
        }
        if (university.Location.isNotEmpty()) {
            binding.detailsLocation.visibility = View.VISIBLE
            binding.detailsLocation.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Location)))
            }
        }
        if (university.Twitter.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.twitter.visibility = View.VISIBLE
            binding.twitter.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Twitter)))
            }
        }
        if (university.Facebook.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.facebook.visibility = View.VISIBLE
            binding.facebook.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Facebook)))
            }
        }
        if (university.Youtube.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.youtube.visibility = View.VISIBLE
            binding.youtube.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Youtube)))
            }
        }
        if (university.Instagram.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.instagram.visibility = View.VISIBLE
            binding.instagram.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Instagram)))
            }
        }
        if (university.Snapchat.isNotEmpty()) {
            binding.communicationHeader.visibility = View.VISIBLE
            binding.snapchat.visibility = View.VISIBLE
            binding.snapchat.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Snapchat)))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}