package com.example.team33.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.team33.R
import com.example.team33.navigation.NavigationPath
import com.example.team33.network.ElectricityRegion
import com.example.team33.ui.screens.*
import com.example.team33.ui.theme.Team33Theme
import com.example.team33.ui.uistate.MainUiState
import com.example.team33.ui.viewmodel.MainViewModel
import kotlin.math.sin

/**
 * Generates a list of sine wave values.
 *
 * @param size The size of the list to generate.
 * @return A list of sine wave values as [Double].
 */
fun genSineWaveList(size: Int): List<Double> {
    return List(size) { 1 + sin(3 * Math.PI * 5 * size / 4 * it / size) }
}

/**
 * Composable function that represents the main app screen.
 *
 * @param modifier The [Modifier] to be applied to the app screen.
 * @param viewModel The [MainViewModel] used to manage the app's state and logic.
 */
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    val mainUiState by viewModel.uiState.collectAsState()

    AppScreenUI(
        navController = navController,
        mainUiState = mainUiState,
        changeElectricityRegion = { viewModel.changeElectricityRegion(it) },
        changeLimitValue = { viewModel.changeLimitValue(it)},
        graphVisible = { viewModel.graphVisible(it) },
        changeAppliance = { viewModel.changeAppliance(it) },
        modifier = modifier
    )
}

/**
 * Composable function that represents the UI of the app screen.
 *
 * @param navController The [NavHostController] used for navigation.
 * @param mainUiState The [MainUiState] representing the UI state of the app.
 * @param changeElectricityRegion Callback to change the electricity region.
 * @param changeAppliance Callback to change the selected appliance.
 * @param graphVisible Callback to change the visibility of the graph.
 * @param modifier The [Modifier] to be applied to the app screen UI.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppScreenUI(
    navController: NavHostController,
    mainUiState: MainUiState,
    changeElectricityRegion: (ElectricityRegion) -> Unit,
    changeLimitValue: (Float) -> Unit,
    changeAppliance: (String) -> Unit,
    graphVisible: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var topBarScreenTitleId by remember { mutableStateOf(NavigationPath.HOME.titleTextId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = topBarScreenTitleId),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            )
        },
        bottomBar = { BottomBar(navController) },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = NavigationPath.HOME.route,
            Modifier.padding(innerPadding)
        ) {
            composable(NavigationPath.HOME.route) {
                topBarScreenTitleId = NavigationPath.HOME.titleTextId
                HomeScreen(mainUiState = mainUiState, modifier = Modifier.padding(start = 10.dp))
            }

            composable(NavigationPath.APPLIANCES.route) {
                topBarScreenTitleId = NavigationPath.APPLIANCES.titleTextId
                AppliancesScreen(
                    mainUiState = mainUiState,
                    graphVisible = { graphVisible(it) },
                    changeAppliance = { changeAppliance(it) }
                )
            }

            composable(NavigationPath.SETTINGS.route) {
                topBarScreenTitleId = NavigationPath.SETTINGS.titleTextId
                SettingsScreen(
                    mainUiState = mainUiState,
                    navController = navController,
                    changeElectricityRegion = { changeElectricityRegion(it) },
                    changeLimitValue = {changeLimitValue(it)}
                )
            }

            composable(route = "openSource") {
                topBarScreenTitleId = R.string.opensource
                OpenSource()
            }

            composable(route = "showAboutThisApp") {
                topBarScreenTitleId = R.string.about_this_app
                ShowAboutThisApp()
            }

            composable(route = "showPurpose") {
                topBarScreenTitleId = R.string.usage
                ShowPurpose()
            }
        }
    }
}

/**
 * Composable function that displays a bottom navigation bar.
 *
 * @param navController The [NavHostController] used for navigation.
 */
@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        NavigationPath.values().forEach { screen ->
            AddNavItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

/**
 * Composable function that adds a navigation item to a row scope within a navigation bar.
 *
 * @param screen The [NavigationPath] representing the screen associated with the navigation item.
 * @param currentDestination The current [NavDestination] in the navigation stack.
 * @param navController The [NavHostController] used for navigation.
 * @param modifier The [Modifier] to be applied to the navigation item.
 */
@Composable
fun RowScope.AddNavItem(
    screen: NavigationPath,
    currentDestination: NavDestination?,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBarItem(icon = {
        Icon(
            imageVector = screen.selectedIcon,
            contentDescription = stringResource(id = screen.titleTextId)
        )
    },
        label = { Text(stringResource(id = screen.titleTextId)) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = false
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = modifier
    )
}

@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AppScreenPreview() {
    Team33Theme(dynamicColor = false) {
        Surface {
            AppScreenUI(
                rememberNavController(),
                MainUiState(electricityPrices = genSineWaveList(24)),
                {},
                graphVisible = {},
                changeLimitValue = {},
                changeAppliance = {}
            )
        }
    }
}
