package com.ss.universitiesdirectory.ui.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsCard(@StringRes title: Int, content: @Composable ColumnScope.() -> Unit) = Card(
    shape = RoundedCornerShape(8.dp),
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
) {
    Text(
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        text = stringResource(id = title),
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp)
    )

    content()
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsItem(@StringRes title: Int, icon: ImageVector, onClick: () -> Unit) = ListItem(
    headlineText = { Text(text = stringResource(id = title)) },
    leadingContent = { Icon(imageVector = icon, contentDescription = null) },
    colors = ListItemDefaults.colors(
        containerColor = Color.Unspecified,
        leadingIconColor = MaterialTheme.colorScheme.primary
    ),
    modifier = Modifier.clickable(onClick = onClick)
)