package com.example.team33.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.team33.R

/**
 * Represents the navigation paths in the application.
 *
 * The `NavigationPath` enum class defines the different screens or destinations in the application along with
 * their corresponding route, selected icon, and title text ID.
 *
 * @property route The route associated with the navigation path.
 * @property selectedIcon The icon to be displayed when the navigation path is selected.
 * @property titleTextId The resource ID of the title text associated with the navigation path.
 */
enum class NavigationPath(
    val route: String,
    val selectedIcon: ImageVector,
    val titleTextId: Int,
) {
    // Home screen element
    HOME(
        route = "home",
        selectedIcon = Icons.Rounded.Home,
        titleTextId = R.string.home,
    ),

    // Appliances screen element
    APPLIANCES(
        route = "appliances",
        selectedIcon = Icons.Rounded.Kitchen,
        titleTextId = R.string.appliances,
    ),

    // Settings screen element
    SETTINGS(
        route = "settings",
        selectedIcon = Icons.Rounded.Settings,
        titleTextId = R.string.settings,
    ),
}