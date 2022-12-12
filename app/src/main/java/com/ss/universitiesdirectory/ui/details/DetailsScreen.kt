package com.ss.universitiesdirectory.ui.details

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.ui.main.DefaultTopAppBar
import com.ss.universitiesdirectory.ui.main.Screen
import com.ss.universitiesdirectory.ui.theme.*

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailsScreen(
    navController: NavHostController,
    viewModel: DetailsViewModel = hiltViewModel(),
    university: UniversityModel?,
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
                        DetailsContent(
                            university = university,
                            onNavigation = {
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("url", it)

                                navController.navigate(route = Screen.WebsiteScreen.route)
                            },
                            openApp = { viewModel.openApp(it) }
                        )
                        SocialMediaContent(
                            university = university,
                            openApp = { viewModel.openApp(it) }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun SocialMediaContent(university: UniversityModel, openApp: (String) -> Unit) {
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
            onClickCallBack = { openApp(university.instagram) }
        )
        if (university.twitter.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_twitter),
            color = TwitterColor,
            onClickCallBack = { openApp(university.twitter) }
        )
        if (university.youtube.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_youtube),
            color = YoutubeColor,
            onClickCallBack = { openApp(university.youtube) }
        )
        if (university.facebook.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_facebook),
            color = FacebookColor,
            onClickCallBack = { openApp(university.facebook) }
        )
        if (university.snapchat.isNotEmpty()) SocialMedia(
            modifier = Modifier.weight(weight = 1f),
            icon = painterResource(id = R.drawable.icon_snapchat),
            color = SnapchatColor,
            onClickCallBack = { openApp(university.snapchat) }
        )
    }
}

@Composable
private fun DetailsContent(
    university: UniversityModel,
    onNavigation: (String) -> Unit,
    openApp: (String) -> Unit
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
        onClickCallBack = { onNavigation(university.website) }
    )
    if (university.colleges.isNotEmpty()) UniversitiesButton(
        message = stringResource(id = R.string.details_colleges),
        onClickCallBack = { onNavigation(university.website) }
    )
    if (university.application.isNotEmpty()) UniversitiesButton(
        message = stringResource(id = R.string.details_application),
        onClickCallBack = { openApp(university.application) }
    )
    if (university.location.isNotEmpty()) UniversitiesButton(
        message = stringResource(id = R.string.details_location),
        onClickCallBack = { openApp(university.location) }
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