package com.ss.universitiesdirectory.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.ui.common.DefaultText

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DefaultCenterTopAppBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) = CenterAlignedTopAppBar(
    title = { DefaultText(text = title) },
    modifier = modifier,
    navigationIcon = navigationIcon,
    actions = actions
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DefaultCenterTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) = CenterAlignedTopAppBar(
    title = { Text(text = title, overflow = TextOverflow.Ellipsis, maxLines = 1) },
    modifier = modifier,
    navigationIcon = navigationIcon,
    actions = actions
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchTopAppBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClose: () -> Unit
) = TopAppBar(
    title = {
        val focusRequester = remember { FocusRequester() }

        TextField(
            singleLine = true,
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            placeholder = { DefaultText(text = R.string.universities_search) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            ),
            modifier = Modifier.focusRequester(focusRequester).padding(0.dp)
        )

        LaunchedEffect(key1 = focusRequester) { focusRequester.requestFocus() }
    },
    navigationIcon = { DefaultIconButton(onClick = {}, icon = Icons.Default.Search) },
    actions = { DefaultIconButton(onClick = onClose, icon = Icons.Default.Close) },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.background)
)