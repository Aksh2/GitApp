package com.project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.ui.screens.GithubUserDetailsView
import com.project.ui.screens.GithubUserDetailscreen
import com.project.ui.screens.WebviewScreen
import java.net.URLEncoder

@Composable
fun GitAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "userList") {
        // User List Screen will be the base screen
        composable("userList") {
            GithubUserDetailsView {
                navController.navigate("userDetails/${it.login}")
            }
        }
        // User Details and list of Repositories screen
        composable("userDetails/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            GithubUserDetailscreen(
                login = username,
                onBackPress = { navController.popBackStack() },
                onRepositoryClick = {
                    val encodedUrl = URLEncoder.encode(it, "UTF-8")
                    navController.navigate("repositoryDetails/$encodedUrl")
                })
        }
        // Webview to open the user repository
        composable("repositoryDetails/{url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url").orEmpty()
            WebviewScreen(url = url, onBackPress = { navController.popBackStack() })
        }
    }
}