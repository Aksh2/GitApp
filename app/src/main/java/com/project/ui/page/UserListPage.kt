package com.project.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.project.R
import com.project.data.GithubUser
import com.project.data.UserListState
import com.project.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GithubUserItem(item: GithubUser, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.avatar_url,
                contentDescription = null, // Provide a meaningful description if needed
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = item.login,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun GithubUserList(
    onItemClick: (user: GithubUser) -> Unit
) {
    val viewModel: UserViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }
    val userListState by viewModel.userListState.collectAsStateWithLifecycle(initialValue = UserListState.Loading)
    when (val state = userListState) {
        is UserListState.Loading -> {}
        is UserListState.Success -> {
            val data = state.users
            LazyColumn {
                items(data) { item ->
                    GithubUserItem(item = item, onClick = { onItemClick(item) })
                }
            }

        }

        is UserListState.Error -> {}
    }

}