package com.ss.universitiesdirectory.ui.main

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ss.universitiesdirectory.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DefaultTopAppBar(
    title: Int,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) = CenterAlignedTopAppBar(
    title = {
        Text(text = stringResource(id = title), overflow = TextOverflow.Ellipsis)
    },
    navigationIcon = {
        if (navigationIcon != null) IconButton(onClick = onNavigationClick) {
            Icon(imageVector = navigationIcon, contentDescription = null)
        }
    },
    actions = actions,
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.background)
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchTopAppBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    isSearching: (Boolean) -> Unit,
) = TopAppBar(
    title = {
        val focusRequester = remember { FocusRequester() }

        TextField(
            singleLine = true,
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            placeholder = { Text(text = stringResource(id = R.string.universities_search)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            ),
            modifier = Modifier.focusRequester(focusRequester).padding(0.dp)
        )

        LaunchedEffect(key1 = focusRequester) { focusRequester.requestFocus() }
    },
    navigationIcon = {
        IconButton(onClick = {  }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    },
    actions = {
        IconButton(onClick = { isSearching(false) }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.background)
)