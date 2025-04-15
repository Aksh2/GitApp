package com.project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.project.data.BaseState
import com.project.data.Constants.SOMETHING_WENT_WRONG
import com.project.data.model.GitHubUserDetails
import com.project.ui.baseui.BaseScaffold
import com.project.ui.baseui.ErrorOverlay
import com.project.ui.baseui.Loader
import com.project.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GithubUserDetailscreen(
    login: String, onBackPress: () -> Unit, onRepositoryClick: (repoUrl: String) -> Unit
) {
    val viewModel: UserViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        viewModel.getUserDetails(login)
    }

    LaunchedEffect(Unit) {
        viewModel.getUserRepositoryList(login)
    }

    BaseScaffold("$login details", onBackPress) {
        Column(modifier = it.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            GithubUserDetailsView(viewModel)
            GithubRepositoryList(viewModel, onRepositoryClick)
        }
    }
}

@Composable
fun GithubUserDetailsView(viewModel: UserViewModel) {
    // Fire the API Once to get the user details

    val userDetailsState =
        viewModel.userDetails.collectAsStateWithLifecycle(initialValue = BaseState.Loading())
    when (val state = userDetailsState.value) {
        is BaseState.Loading -> {
            Loader()
        }

        is BaseState.Success -> {
            val data = state.response
            GithubUserDetailsItem(item = data)
        }

        is BaseState.Error -> {
            ErrorOverlay(SOMETHING_WENT_WRONG)
        }
    }


}

@Composable
fun GithubRepositoryList(
    viewModel: UserViewModel, onRepositoryClick: (repoUrl: String) -> Unit
) {

    val gitRepositoryListState =
        viewModel.userRepositoryListState.collectAsStateWithLifecycle(
            initialValue = BaseState.Loading()
        )
    when (val state = gitRepositoryListState.value) {
        is BaseState.Loading -> {
            Loader()
        }

        is BaseState.Success -> {
            val data = state.response.filter { it.fork == false }
            GithubRepositoryList(repositoryList = data) {
                onRepositoryClick.invoke(it.htmlUrl.orEmpty())
            }
        }

        is BaseState.Error -> {
            ErrorOverlay(SOMETHING_WENT_WRONG)
        }
    }
}

@Composable
fun GithubUserDetailsItem(item: GitHubUserDetails) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Username: ${item.name.orEmpty()}",
            style = MaterialTheme.typography.bodyLarge
        )
        Column {
            Text(
                text = "Name: ${item.name.orEmpty()}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Followers: ${item.followers}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Following: ${item.following}",
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}