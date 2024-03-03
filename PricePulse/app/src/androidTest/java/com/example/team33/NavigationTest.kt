package com.example.team33

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.team33.ui.AppScreen
import com.example.team33.ui.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    val viewModel = MainViewModel()

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            AppScreen(Modifier.fillMaxSize(), viewModel)
        }
    }

    @Test
    fun bottomNavigation_clickStartDestination() {
        composeTestRule.onAllNodesWithText("Home").onLast().performClick()

    }

    @Test
    fun bottomNavigation_verifyStartDestination() {
        composeTestRule.onAllNodesWithText("Home").onFirst().assertIsDisplayed()
    }

    @Test
    fun bottomNavigation_clickAppliances() {
        composeTestRule.onAllNodesWithText("Appliances").onLast().performClick()
        composeTestRule.onAllNodesWithText("Appliances").onFirst().assertIsDisplayed()
    }

    @Test
    fun bottomNavigation_clickSetting() {
        composeTestRule.onAllNodesWithText( "Settings").onLast().performClick()
        composeTestRule.onAllNodesWithText("Settings").onFirst().assertIsDisplayed()
    }

    @Test
    fun bottomNavigation_backToHome() {
        composeTestRule.onAllNodesWithText("Home").onLast().performClick()
        composeTestRule.onAllNodesWithText("Home").onFirst().assertIsDisplayed()
    }
}