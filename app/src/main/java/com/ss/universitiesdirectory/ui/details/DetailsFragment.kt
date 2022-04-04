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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
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
                            UniversityInformationNavigate(
                                action = directions.actionDetailsFragmentToWebsiteFragment(university.website),
                                message = R.string.website,
                                backgroundColor = WebsiteColor
                            )
                        }
                        if (university.colleges.isNotEmpty()) {
                            UniversityInformationNavigate(
                                action = directions.actionDetailsFragmentToWebsiteFragment(
                                    university.colleges
                                ),
                                message = R.string.colleges,
                                backgroundColor = CollegesColor
                            )
                        }
                        if (university.news.isNotEmpty()) {
                            UniversityInformationNavigate(
                                action = directions.actionDetailsFragmentToNewsFragment(
                                    university.news
                                ),
                                message = R.string.news,
                                backgroundColor = NewsColor
                            )
                        }
                        if (university.application.isNotEmpty()) {
                            UniversityInformationIntent(
                                action = university.application,
                                message = R.string.application,
                                backgroundColor = ApplicationColor
                            )
                        }
                        if (university.location.isNotEmpty()) {
                            UniversityInformationIntent(
                                action = university.location,
                                message = R.string.location,
                                backgroundColor = LocationColor
                            )
                        }
                        Text(
                            text = getString(R.string.communication),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Row {
                            if (university.instagram.isNotEmpty()) {
                                SocialMedia(
                                    modifier = Modifier.weight(weight = 1f),
                                    socialMedia = university.instagram,
                                    icon = painterResource(id = R.drawable.icon_instagram),
                                    color = InstagramColor
                                )
                            }
                            if (university.twitter.isNotEmpty()) {
                                SocialMedia(
                                    modifier = Modifier.weight(weight = 1f),
                                    socialMedia = university.twitter,
                                    icon = painterResource(id = R.drawable.icon_twitter),
                                    color = TwitterColor
                                )
                            }
                            if (university.youtube.isNotEmpty()) {
                                SocialMedia(
                                    modifier = Modifier.weight(weight = 1f),
                                    socialMedia = university.youtube,
                                    icon = painterResource(id = R.drawable.icon_youtube),
                                    color = YoutubeColor
                                )
                            }
                            if (university.facebook.isNotEmpty()) {
                                SocialMedia(
                                    modifier = Modifier.weight(weight = 1f),
                                    socialMedia = university.facebook,
                                    icon = painterResource(id = R.drawable.icon_facebook),
                                    color = FacebookColor
                                )
                            }
                            if (university.snapchat.isNotEmpty()) {
                                SocialMedia(
                                    modifier = Modifier.weight(weight = 1f),
                                    socialMedia = university.snapchat,
                                    icon = painterResource(id = R.drawable.icon_snapchat),
                                    color = SnapchatColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun UniversityInformationNavigate(action: NavDirections, message: Int, backgroundColor: Color) {
        Button(
            onClick = { findNavController().navigateTo(action, R.id.detailsFragment) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
        ) {
            Text(text = getString(message), color = Color.White)
        }
    }

    @Composable
    fun UniversityInformationIntent(action: String, message: Int, backgroundColor: Color) {
        Button(
            onClick = { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(action))) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
        ) {
            Text(text = getString(message), color = Color.White)
        }
    }

    @Composable
    private fun SocialMedia(modifier: Modifier, socialMedia: String, icon: Painter, color: Color) {
        Button(
            onClick = { openSocialMedia(socialMedia) },
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 4.dp),
        ) {
            Icon(painter = icon, contentDescription = null, tint = Color.White)
        }
    }

    private fun openSocialMedia(stringUri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringUri))
        val chooser = Intent.createChooser(intent, "Open app")
        startActivity(chooser)
    }
}