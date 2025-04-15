package com.project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.data.GitHubUserRepoDetails

@Composable
fun GithubRepositoryList(
    repositoryList: List<GitHubUserRepoDetails>,
    onItemClick: (user: GitHubUserRepoDetails) -> Unit
) {
    LazyColumn {
        items(repositoryList) { item ->
            GithubRepositoryItem(item = item, onClick = { onItemClick(item) })
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
                text = "Name: ${item.name.orEmpty()}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Description: ${item.description.orEmpty()}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Programming Language: ${item.language.orEmpty()}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Number of stars: ${item.starsCount}",
                style = MaterialTheme.typography.bodySmall
            )
        }

    }


}