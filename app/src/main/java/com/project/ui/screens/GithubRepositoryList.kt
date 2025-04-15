package com.project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.project.data.Constants.NOT_AVAILABLE
import com.project.data.Constants.UNKNOWN
import com.project.data.GitHubUserRepoDetails
import com.project.ui.baseui.ChipText

@Composable
fun GithubRepositoryList(
    repositoryList: List<GitHubUserRepoDetails>,
    onItemClick: (user: GitHubUserRepoDetails) -> Unit
) {
    Column {
        Text(
            text = "Repositories:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            items(repositoryList) { item ->
                GithubRepositoryItem(item = item, onClick = { onItemClick(item) })
            }
        }
    }

}

@Composable
fun GithubRepositoryItem(item: GitHubUserRepoDetails, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.name ?: UNKNOWN,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.description ?: NOT_AVAILABLE,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
            Row {
                ChipText(
                    text = item.language ?: UNKNOWN,
                    icon = Icons.Filled.Settings

                )
                Spacer(Modifier.size(4.dp))
                ChipText(
                    text = (item.starsCount ?: "0").toString(),
                    icon = Icons.Filled.Star
                )
            }
        }

    }


}