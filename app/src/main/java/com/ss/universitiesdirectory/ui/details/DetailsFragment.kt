package com.ss.universitiesdirectory.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.ui.theme.*

private lateinit var nc: NavHostController

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailsScreen(navController: NavHostController, university: UniversityModel) {
    nc = navController

    Scaffold(
        topBar = { DetailsTopBar() },
        content = { DetailsContent(it, university) }
    )
}

@Composable
private fun DetailsTopBar() = CenterAlignedTopAppBar(
    title = { Text(text = "Details") },
    navigationIcon = {
        IconButton(onClick = { nc.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = PrimaryColor,
        titleContentColor = White,
        navigationIconContentColor = White,
        actionIconContentColor = White
    )
)

@Composable
private fun DetailsContent(paddingValues: PaddingValues, university: UniversityModel) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(paddingValues)) {
        Image(
            painter = rememberAsyncImagePainter(model = university.logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Column(Modifier.verticalScroll(state = rememberScrollState())) {
            Text(
                text = "About University:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                color = if (isSystemInDarkTheme()) White else Black
            )
            Text(
                text = university.about,
                fontSize = 15.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                color = if (isSystemInDarkTheme()) White else Black
            )
            if (university.website.isNotEmpty()) UniversitiesButton(
                message = "Website",
                backgroundColor = WebsiteColor,
                onClickCallBack = {
                    nc.currentBackStackEntry?.savedStateHandle?.set("url", university.website)
                    nc.navigate(route = "website")
                }
            )
            if (university.colleges.isNotEmpty()) UniversitiesButton(
                message = "Colleges",
                backgroundColor = CollegesColor,
                onClickCallBack = {
                    nc.currentBackStackEntry?.savedStateHandle?.set("url", university.colleges)
                    nc.navigate(route = "website")
                }
            )
            if (university.news.isNotEmpty()) UniversitiesButton(
                message = "News",
                backgroundColor = NewsColor,
                onClickCallBack = {
                    nc.currentBackStackEntry?.savedStateHandle?.set("address", university.news)
                    nc.navigate(route = "news")
                }
            )
            if (university.application.isNotEmpty()) {
                UniversitiesButton(
                    message = "Application",
                    backgroundColor = ApplicationColor,
                    onClickCallBack = { openAppAsIntent(context, university.application) }
                )
            }
            if (university.location.isNotEmpty()) UniversitiesButton(
                message = "Location",
                backgroundColor = LocationColor,
                onClickCallBack = { openAppAsIntent(context, university.location) }
            )
            Text(
                text = "Communication:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp),
                color = if (isSystemInDarkTheme()) White else Black
            )
            Row {
                if (university.instagram.isNotEmpty()) SocialMedia(
                    modifier = Modifier.weight(weight = 1f),
                    icon = painterResource(id = R.drawable.icon_instagram),
                    color = InstagramColor,
                    onClickCallBack = { openAppAsIntent(context, university.instagram) }
                )
                if (university.twitter.isNotEmpty()) {
                    SocialMedia(
                        modifier = Modifier.weight(weight = 1f),
                        icon = painterResource(id = R.drawable.icon_twitter),
                        color = TwitterColor,
                        onClickCallBack = { openAppAsIntent(context, university.twitter) }
                    )
                }
                if (university.youtube.isNotEmpty()) {
                    SocialMedia(
                        modifier = Modifier.weight(weight = 1f),
                        icon = painterResource(id = R.drawable.icon_youtube),
                        color = YoutubeColor,
                        onClickCallBack = { openAppAsIntent(context, university.youtube) }
                    )
                }
                if (university.facebook.isNotEmpty()) {
                    SocialMedia(
                        modifier = Modifier.weight(weight = 1f),
                        icon = painterResource(id = R.drawable.icon_facebook),
                        color = FacebookColor,
                        onClickCallBack = { openAppAsIntent(context, university.facebook) }
                    )
                }
                if (university.snapchat.isNotEmpty()) {
                    SocialMedia(
                        modifier = Modifier.weight(weight = 1f),
                        icon = painterResource(id = R.drawable.icon_snapchat),
                        color = SnapchatColor,
                        onClickCallBack = { openAppAsIntent(context, university.snapchat) }
                    )
                }
            }
        }
    }
}

@Composable
fun UniversitiesButton(message: String, backgroundColor: Color, onClickCallBack: () -> Unit) =
    Button(
        onClick = onClickCallBack,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(contentColor = backgroundColor)
    ) {
        Text(text = message, color = Color.White)
    }

@Composable
private fun SocialMedia(
    modifier: Modifier,
    icon: Painter,
    color: Color,
    onClickCallBack: () -> Unit
) = Button(
    onClick = onClickCallBack,
    colors = ButtonDefaults.buttonColors(contentColor = color),
    modifier = modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(horizontal = 4.dp),
) {
    Icon(painter = icon, contentDescription = null, tint = Color.Unspecified)
}

private fun openAppAsIntent(context: Context, stringUri: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringUri))
    val chooser = Intent.createChooser(intent, "Open app")
    startActivity(context, chooser, null)
}