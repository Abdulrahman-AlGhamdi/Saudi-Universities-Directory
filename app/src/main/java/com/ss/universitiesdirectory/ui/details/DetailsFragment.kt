package com.ss.universitiesdirectory.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.rememberAsyncImagePainter
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentDetailsBinding
import com.ss.universitiesdirectory.ui.theme.*
import com.ss.universitiesdirectory.utils.navigateTo
import com.ss.universitiesdirectory.utils.viewBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val argument by navArgs<DetailsFragmentArgs>()
    private val directions = DetailsFragmentDirections

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val university = argument.university

        binding.composeView.apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            this.setContent {
                Column(modifier = Modifier.padding(all = 16.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = university.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Column(Modifier.verticalScroll(state = rememberScrollState())) {
                        Text(
                            text = getString(R.string.about),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = university.about,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        if (university.website.isNotEmpty()) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(backgroundColor = WebsiteColor),
                                onClick = {
                                    val action =
                                        directions.actionDetailsFragmentToWebsiteFragment(university.website)
                                    findNavController().navigateTo(action, R.id.detailsFragment)
                                }
                            ) {
                                Text(text = getString(R.string.website), color = Color.White)
                            }
                        }
                        if (university.colleges.isNotEmpty()) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(backgroundColor = CollegesColor),
                                onClick = {
                                    val action =
                                        directions.actionDetailsFragmentToWebsiteFragment(university.colleges)
                                    findNavController().navigateTo(action, R.id.detailsFragment)
                                }
                            ) {
                                Text(text = getString(R.string.colleges), color = Color.White)
                            }
                        }
                        if (university.news.isNotEmpty()) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(backgroundColor = NewsColor),
                                onClick = {
                                    val action =
                                        directions.actionDetailsFragmentToNewsFragment(university.news)
                                    findNavController().navigateTo(action, R.id.detailsFragment)
                                }
                            ) {
                                Text(text = getString(R.string.news), color = Color.White)
                            }
                        }
                        if (university.application.isNotEmpty()) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(backgroundColor = ApplicationColor),
                                onClick = {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(university.application)
                                        )
                                    )
                                }
                            ) {
                                Text(text = getString(R.string.application), color = Color.White)
                            }
                        }
                        if (university.location.isNotEmpty()) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(backgroundColor = LocationColor),
                                onClick = {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(university.location)
                                        )
                                    )
                                }
                            ) {
                                Text(text = getString(R.string.location), color = Color.White)
                            }
                        }
                        Text(
                            text = getString(R.string.communication),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                            if (university.instagram.isNotEmpty()) {
                                Button(
                                    onClick = { openSocialMedia(university.instagram) },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = InstagramColor),
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(horizontal = 4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_instagram),
                                        contentDescription = null,
                                        tint = White
                                    )
                                }
                            }
                            if (university.twitter.isNotEmpty()) {
                                Button(
                                    onClick = { openSocialMedia(university.twitter) },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = TwitterColor),
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(horizontal = 4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_twitter),
                                        contentDescription = null,
                                        tint = White
                                    )
                                }
                            }
                            if (university.youtube.isNotEmpty()) {
                                Button(
                                    onClick = { openSocialMedia(university.youtube) },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = YoutubeColor),
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(horizontal = 4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_youtube),
                                        contentDescription = null,
                                        tint = White
                                    )
                                }
                            }
                            if (university.facebook.isNotEmpty()) {
                                Button(
                                    onClick = { openSocialMedia(university.facebook) },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = FacebookColor),
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(horizontal = 4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_facebook),
                                        contentDescription = null,
                                        tint = White
                                    )
                                }
                            }
                            if (university.snapchat.isNotEmpty()) {
                                Button(
                                    onClick = { openSocialMedia(university.snapchat) },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = SnapchatColor),
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(horizontal = 4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_snapchat),
                                        contentDescription = null,
                                        tint = White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun openSocialMedia(stringUri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringUri))
        val chooser = Intent.createChooser(intent, "Open app")
        startActivity(chooser)
    }
}