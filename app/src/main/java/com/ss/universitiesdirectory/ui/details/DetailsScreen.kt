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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.ui.main.DefaultTopAppBar
import com.ss.universitiesdirectory.ui.theme.*

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailsScreen(
    navController: NavHostController,
    university: UniversityModel?,
    context: Context = LocalContext.current,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = R.string.details_fragment,
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = { navController.popBackStack() }
            )
        },
        content = { paddingValues ->
            university?.let {
                Column(modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = university.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
                        DetailsContent(navController, context, university)
                        SocialMediaContent(university, context)
                    }
                }
            }
        }
    )
}

@Composable
fun SocialMediaContent(university: UniversityModel, context: Context) {
    Text(
        text = stringResource(id = R.string.details_communication),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        color = if (isSystemInDarkTheme()) White else Black
    )

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        if (university.instagram.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_instagram),
            color = InstagramColor,
            onClickCallBack = { openAppAsIntent(context, university.instagram) }
        )
        if (university.twitter.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_twitter),
            color = TwitterColor,
            onClickCallBack = { openAppAsIntent(context, university.twitter) }
        )
        if (university.youtube.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_youtube),
            color = YoutubeColor,
            onClickCallBack = { openAppAsIntent(context, university.youtube) }
        )
        if (university.facebook.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_facebook),
            color = FacebookColor,
            onClickCallBack = { openAppAsIntent(context, university.facebook) }
        )
        if (university.snapchat.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_snapchat),
            color = SnapchatColor,
            onClickCallBack = { openAppAsIntent(context, university.snapchat) }
        )
    }
}

@Composable
private fun DetailsContent(
    navController: NavHostController,
    context: Context,
    university: UniversityModel,
) {
    Text(
        text = stringResource(id = R.string.details_about),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
    )
    Text(
        text = university.about,
        fontSize = 15.sp,
        modifier = Modifier.padding(bottom = 16.dp),
    )
    if (university.website.isNotEmpty()) UniversitiesButton(
        message = stringResource(id = R.string.details_website),
        onClickCallBack = {
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("url", university.website)

            navController.navigate(route = "website")
        }
    )
    if (university.colleges.isNotEmpty()) UniversitiesButton(
        message = stringResource(id = R.string.details_colleges),
        onClickCallBack = {
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("url", university.colleges)

            navController.navigate(route = "website")
        }
    )
    if (university.application.isNotEmpty()) UniversitiesButton(
        message = stringResource(id = R.string.details_application),
        onClickCallBack = { openAppAsIntent(context, university.application) }
    )
    if (university.location.isNotEmpty()) UniversitiesButton(
        message = stringResource(id = R.string.details_location),
        onClickCallBack = { openAppAsIntent(context, university.location) }
    )
}

@Composable
fun UniversitiesButton(message: String, onClickCallBack: () -> Unit) = Button(
    onClick = onClickCallBack,
    shape = RoundedCornerShape(8.dp),
    modifier = Modifier.fillMaxWidth()
) {
    Text(text = message, color = Color.White)
}

@Composable
private fun SocialMedia(
    modifier: Modifier,
    icon: Painter,
    color: Color,
    onClickCallBack: () -> Unit,
) = Button(
    onClick = onClickCallBack,
    colors = ButtonDefaults.buttonColors(containerColor = color),
    shape = RoundedCornerShape(8.dp),
    modifier = modifier.height(40.dp)
) {
    Icon(
        painter = icon,
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier.size(30.dp)
    )
}

private fun openAppAsIntent(context: Context, stringUri: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringUri))
    val chooser = Intent.createChooser(intent, "Open app")
    startActivity(context, chooser, null)
}