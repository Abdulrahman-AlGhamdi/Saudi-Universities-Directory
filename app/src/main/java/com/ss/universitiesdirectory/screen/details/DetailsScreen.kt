package com.ss.universitiesdirectory.screen.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.ui.common.DefaultButton
import com.ss.universitiesdirectory.ui.common.DefaultCenterTopAppBar
import com.ss.universitiesdirectory.ui.common.DefaultIcon
import com.ss.universitiesdirectory.ui.common.DefaultIconButton
import com.ss.universitiesdirectory.ui.common.DefaultScaffold
import com.ss.universitiesdirectory.ui.common.DefaultText
import com.ss.universitiesdirectory.ui.theme.FacebookColor
import com.ss.universitiesdirectory.ui.theme.InstagramColor
import com.ss.universitiesdirectory.ui.theme.SaudiUniversitiesComposeTheme
import com.ss.universitiesdirectory.ui.theme.SnapchatColor
import com.ss.universitiesdirectory.ui.theme.TwitterColor
import com.ss.universitiesdirectory.ui.theme.YoutubeColor

@Composable
fun DetailsScreen(
    university: UniversityModel?,
    onWebsiteClick: (String) -> Unit,
    onAppClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    DefaultScaffold(topBar = { DetailsTopAppBar(onBackClick = onBackClick) }) {
        university?.let {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                model = university.logo,
                contentDescription = null,
            )

            UniversityDetails(
                university = university,
                onWebsiteClick = onWebsiteClick,
                openAppClick = onAppClick
            )

            UniversitySocialMedia(
                university = university,
                openAppClick = onAppClick
            )
        }
    }
}

@Composable
private fun DetailsTopAppBar(onBackClick: () -> Unit) = DefaultCenterTopAppBar(
    title = R.string.details_screen,
    navigationIcon = { DefaultIconButton(onClick = onBackClick, icon = Icons.Default.ArrowBack) }
)

@Composable
private fun UniversityDetails(
    university: UniversityModel,
    onWebsiteClick: (String) -> Unit,
    openAppClick: (String) -> Unit
) {
    DefaultText(
        text = R.string.details_about,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 24.dp, start = 16.dp)
    )
    Text(
        fontSize = 15.sp,
        text = university.about,
        textAlign = TextAlign.Justify,
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
    )
    if (university.website.isNotEmpty()) DefaultButton(
        text = R.string.details_website,
        onClick = { onWebsiteClick(university.website) },
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    if (university.colleges.isNotEmpty()) DefaultButton(
        text = R.string.details_colleges,
        onClick = { onWebsiteClick(university.website) },
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    if (university.application.isNotEmpty()) DefaultButton(
        text = R.string.details_application,
        onClick = { openAppClick(university.application) },
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
    if (university.location.isNotEmpty()) DefaultButton(
        text = R.string.details_location,
        onClick = { openAppClick(university.location) },
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun UniversitySocialMedia(university: UniversityModel, openAppClick: (String) -> Unit) {
    DefaultText(
        text = R.string.details_communication,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        if (university.instagram.isNotEmpty()) IconButton(
            modifier = Modifier.weight(weight = 1f),
            colors = IconButtonDefaults.iconButtonColors(containerColor = InstagramColor),
            onClick = { openAppClick(university.instagram) },
            content = { SocialMediaIcon(painter = R.drawable.icon_instagram) }
        )
        if (university.twitter.isNotEmpty()) IconButton(
            modifier = Modifier.weight(weight = 1f),
            colors = IconButtonDefaults.iconButtonColors(containerColor = TwitterColor),
            onClick = { openAppClick(university.twitter) },
            content = { SocialMediaIcon(painter = R.drawable.icon_twitter) }
        )
        if (university.youtube.isNotEmpty()) IconButton(
            modifier = Modifier.weight(weight = 1f),
            colors = IconButtonDefaults.iconButtonColors(containerColor = YoutubeColor),
            onClick = { openAppClick(university.youtube) },
            content = { SocialMediaIcon(painter = R.drawable.icon_youtube) }
        )
        if (university.facebook.isNotEmpty()) IconButton(
            modifier = Modifier.weight(weight = 1f),
            colors = IconButtonDefaults.iconButtonColors(containerColor = FacebookColor),
            onClick = { openAppClick(university.facebook) },
            content = { SocialMediaIcon(painter = R.drawable.icon_facebook) }
        )
        if (university.snapchat.isNotEmpty()) IconButton(
            modifier = Modifier.weight(weight = 1f),
            colors = IconButtonDefaults.iconButtonColors(containerColor = SnapchatColor),
            onClick = { openAppClick(university.snapchat) },
            content = { SocialMediaIcon(painter = R.drawable.icon_snapchat) }
        )
    }
}

@Composable
private fun SocialMediaIcon(painter: Int) = DefaultIcon(
    painter = painter,
    contentDescription = null,
    tint = Color.Unspecified,
    modifier = Modifier.size(30.dp)
)

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
private fun DetailsScreenPreview() = SaudiUniversitiesComposeTheme {
    DetailsScreen(
        university = null,
        onWebsiteClick = {},
        onAppClick = {},
        onBackClick = {}
    )
}