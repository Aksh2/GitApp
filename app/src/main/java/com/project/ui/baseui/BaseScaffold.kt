package com.project.ui.baseui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BaseScaffold(
    title: String,
    showBackNavigation: Boolean = true,
    onBackPress: () -> Unit = {},
    content: @Composable (modifier: Modifier) -> Unit
) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopBar(title, showBackNavigation, onBackPress)
            },
            content = { paddingValues ->
                content(Modifier.padding(paddingValues))
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String, showBackNavigation: Boolean, onBackPress: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(title) },
        navigationIcon = {
            if (showBackNavigation) {
                IconButton(onClick = onBackPress) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )

}