package com.project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.project.data.BaseState
import com.project.data.Constants.APP_NAME
import com.project.data.Constants.NO_RESULTS
import com.project.data.Constants.SEARCH_HINT
import com.project.data.Constants.SOMETHING_WENT_WRONG
import com.project.data.Constants.UNKNOWN
import com.project.data.model.GitHubUserDetails
import com.project.ui.baseui.BaseScaffold
import com.project.ui.baseui.ErrorOverlay
import com.project.ui.baseui.Loader
import com.project.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Composable function that displays a screen listing GitHub users.
 *
 * The screen consists of a [BaseScaffold] that provides a common layout structure
 * and a [GithubUserList] composable that displays the actual list of users.
 *
 * @param onItemClick A lambda function to be executed when a user item in the list is clicked.  It takes
 *                    a [GitHubUserDetails] object representing the clicked user as input.
 */
@Composable
fun GithubUserDetailsView(
    onItemClick: (user: GitHubUserDetails) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    BaseScaffold(APP_NAME, false) {
        Column(modifier = it.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(SEARCH_HINT) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(align = Alignment.Center)
                    .fillMaxWidth()
            )

            GithubUserList(
                onItemClick = onItemClick,
                searchQuery = searchQuery
            )
        }
    }
}


/**
 * Displays a list of Github users.
 *
 * Fetches user data using [UserViewModel] and displays it in a [LazyColumn].
 * Handles different states (Loading, Success, Error) of the data fetching process.
 *
 * @param onItemClick Callback function to be invoked when a user item is clicked.
 *                  It receives the clicked [GitHubUserDetails] as a parameter.
 *
 */
@Composable
fun GithubUserList(
    onItemClick: (user: GitHubUserDetails) -> Unit,
    searchQuery: String
) {
    val viewModel: UserViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }

    val userListState by viewModel.userListState.collectAsStateWithLifecycle(initialValue = BaseState.Loading())
    when (val state = userListState) {
        is BaseState.Loading -> {
            Loader()
        }

        is BaseState.Success -> {
            val data = state.response
            val filteredItems = remember(searchQuery, data) {
                if (searchQuery.isBlank()) {
                    data
                } else {
                    data.filter { it.login?.contains(searchQuery, ignoreCase = true) ?: false }
                }
            }
            if (filteredItems.isEmpty() && searchQuery.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("$NO_RESULTS '$searchQuery'")
                }
            } else {
                LazyColumn {
                    items(filteredItems) { item ->
                        GithubUserItem(item = item, onClick = { onItemClick(item) })
                    }
                }
            }

        }

        is BaseState.Error -> {
            ErrorOverlay(SOMETHING_WENT_WRONG)
        }
    }

}


/**
 * Composable function to display a single GitHub user item in a card format.
 *
 * @param item The [GitHubUserDetails] object containing the user's information.  Specifically, it uses
 *             `item.avatar_url` for the profile image and `item.login` for the user's login name.  If
 *             `item.login` is null, it will display an Unkown string.
 * @param onClick A lambda function to be executed when the user clicks on the card.  This typically
 *                navigates to the detailed view of the selected user.
 */
@Composable
fun GithubUserItem(item: GitHubUserDetails, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
    ) {
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
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = item.login ?: UNKNOWN,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}



