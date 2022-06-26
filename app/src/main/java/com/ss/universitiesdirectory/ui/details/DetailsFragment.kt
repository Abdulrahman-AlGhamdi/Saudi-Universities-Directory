package com.ss.universitiesdirectory.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailsScreen(navController: NavHostController, university: UniversityModel) {
    val context = LocalContext.current

    Scaffold(
        topBar = { DetailsTopBar(navController) },
        content = { DetailsContent(it, navController, context, university) }
    )
}

@Composable
private fun DetailsTopBar(navController: NavHostController) = CenterAlignedTopAppBar(
    title = { Text(text = "Details") },
    navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
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
private fun DetailsContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    context: Context,
    university: UniversityModel
) = Column(modifier = Modifier.padding(paddingValues)) {
    Image(
        painter = rememberAsyncImagePainter(model = university.logo),
        contentDescription = null,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(200.dp)
    )

    Column(Modifier.verticalScroll(state = rememberScrollState())) {
        Text(
            text = "About University:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            color = if (isSystemInDarkTheme()) White else Black
        )
        Text(
            text = university.about,
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            color = if (isSystemInDarkTheme()) White else Black
        )
        if (university.website.isNotEmpty()) UniversitiesButton(
            message = "Website",
            backgroundColor = WebsiteColor,
            onClickCallBack = {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("url", university.website)

                navController.navigate(route = "website")
            }
        )
        if (university.colleges.isNotEmpty()) UniversitiesButton(
            message = "Colleges",
            backgroundColor = CollegesColor,
            onClickCallBack = {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("url", university.colleges)

                navController.navigate(route = "website")
            }
        )
        if (university.news.isNotEmpty()) UniversitiesButton(
            message = "News",
            backgroundColor = NewsColor,
            onClickCallBack = {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("address", university.news)

                navController.navigate(route = "news")
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
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            color = if (isSystemInDarkTheme()) White else Black
        )
        Row(modifier = Modifier.padding(16.dp)) {
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

@Composable
fun UniversitiesButton(message: String, backgroundColor: Color, onClickCallBack: () -> Unit) =
    Button(
        onClick = onClickCallBack,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
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
    colors = ButtonDefaults.buttonColors(containerColor = color),
    shape = RoundedCornerShape(8.dp),
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