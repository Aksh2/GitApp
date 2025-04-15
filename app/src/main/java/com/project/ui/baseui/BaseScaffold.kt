package com.project.ui.baseui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    title: String,
    content: @Composable (modifier: Modifier) -> Unit
) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(title) })
            },
            content = { paddingValues ->
                content(Modifier.padding(paddingValues))
            }
        )
    }

}