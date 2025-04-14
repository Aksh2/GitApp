package com.project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.ui.page.GithubUserListScreen

@Composable
fun GitAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "userList") {
        // User List Screen will be the base screen
        composable("userList") {
            GithubUserListScreen(onItemClick = { user ->
                navController.navigate("userDetails/${user.login}")
            })
        }
        composable("userDetails/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            // UserDetailsScreen(username = username)
        }
    }
}