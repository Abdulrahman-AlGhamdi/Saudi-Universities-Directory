package com.ss.universitiesdirectory.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.ss.universitiesdirectory.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val argument by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        showData()

        return binding.root
    }

    private fun showData() {
        val university = argument.university

        if (university.Logo.isNotEmpty())
            Picasso.get().load(university.Logo).into(binding.detailsLogo)
        if (university.About.isNotEmpty())
            binding.detailsAbout.text = university.About
        if (university.College.isNotEmpty()) {
            binding.detailsCollege.visibility = View.VISIBLE
            binding.detailsCollege.setOnClickListener {
                findNavController().navigate(
                        DetailsFragmentDirections.actionDetailsFragmentToWebsiteFragment(
                                university.College
                        )
                )
            }
        }
        if (university.News.isNotEmpty()) {
            binding.detailsRss.visibility = View.VISIBLE
            binding.detailsRss.setOnClickListener {
                findNavController().navigate(
                        DetailsFragmentDirections.actionDetailsFragmentToWebsiteFragment(
                                university.News
                        )
                )
            }
        }
        if (university.Website.isNotEmpty()) {
            binding.detailsWebsite.visibility = View.VISIBLE
            binding.detailsWebsite.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Website)))
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
            binding.detailsCommunication.visibility = View.VISIBLE
            binding.detailsTwitter.visibility = View.VISIBLE
            binding.detailsTwitter.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Twitter)))
            }
        }
        if (university.Facebook.isNotEmpty()) {
            binding.detailsCommunication.visibility = View.VISIBLE
            binding.detailsFacebook.visibility = View.VISIBLE
            binding.detailsFacebook.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Facebook)))
            }
        }
        if (university.Youtube.isNotEmpty()) {
            binding.detailsCommunication.visibility = View.VISIBLE
            binding.detailsYoutube.visibility = View.VISIBLE
            binding.detailsYoutube.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Youtube)))
            }
        }
        if (university.Instagram.isNotEmpty()) {
            binding.detailsCommunication.visibility = View.VISIBLE
            binding.detailsInstagram.visibility = View.VISIBLE
            binding.detailsInstagram.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Instagram)))
            }
        }
        if (university.Snapchat.isNotEmpty()) {
            binding.detailsCommunication.visibility = View.VISIBLE
            binding.detailsSnapchat.visibility = View.VISIBLE
            binding.detailsSnapchat.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(university.Snapchat)))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}