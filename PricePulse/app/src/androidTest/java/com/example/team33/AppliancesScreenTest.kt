package com.example.team33

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.team33.ui.AppScreen
import com.example.team33.ui.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppliancesScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    val viewModel = MainViewModel()
    var context: Context? = null


    @Before
    fun setUp() {
        composeTestRule.setContent {
            AppScreen(Modifier.fillMaxSize(), viewModel)
            context = LocalContext.current
        }
        context?.let {
            composeTestRule.onAllNodesWithText(it.getString(R.string.appliances)).onFirst()
                .performClick()
        }
    }

    // Test`s if title is displayed
    @Test
    fun screenTitle_isDisplayed() {
        context?.let {
            composeTestRule.onAllNodesWithText(it.getString(R.string.appliances)).onFirst()
                .assertIsDisplayed()
        }
    }

    @Test
    fun graph_isDisplayed() {
        context?.let {
            composeTestRule.onAllNodesWithText(it.getString(R.string.graph)).onFirst()
                .assertIsDisplayed()
        }
    }

    @Test
    fun graph_Click() {
        context?.let {
            composeTestRule.onAllNodesWithText(it.getString(R.string.graph)).onFirst()
                .performClick()
        }
    }

    @Test
    fun table_isDisplayed() {
        context?.let {
            composeTestRule.onAllNodesWithText(it.getString(R.string.table)).onFirst()
                .assertIsDisplayed()
        }
    }

    @Test
    fun table_Click() {
        context?.let {
            composeTestRule.onAllNodesWithText(it.getString(R.string.table)).onFirst()
                .performClick()
        }
    }

    //Testing that appliance button is displayed and clicking through
    // them to check if they change the current appliance below
    @Test
    fun washingButton_isDisplayed() {
        context?.let {
            composeTestRule.onNodeWithContentDescription(it.getString(R.string.washing_machine))
                .assertIsDisplayed()
        }
    }

    @Test
    fun washingButton_click() {
        context?.let {
            composeTestRule.onNodeWithContentDescription(it.getString(R.string.washing_machine))
                .performClick()
        }

        assert(viewModel.uiState.value.appliance == "Washing")

    }

    @Test
    fun ovenButton_isDisplayed() {
        context?.let {
            composeTestRule.onNodeWithContentDescription(it.getString(R.string.oven))
                .assertIsDisplayed()
        }


    }

    @Test
    fun ovenButton_click() {
        context?.let {
            composeTestRule.onNodeWithContentDescription(it.getString(R.string.oven)).performClick()
        }

        assert(viewModel.uiState.value.appliance == "Oven")

    }

    @Test
    fun heaterButton_isDisplayed() {
        context?.let {
            composeTestRule.onNodeWithContentDescription(it.getString(R.string.heater))
                .assertIsDisplayed()
        }
    }

    @Test
    fun heaterButton_click() {
        context?.let {
            composeTestRule.onNodeWithContentDescription(it.getString(R.string.heater))
                .performClick()
        }

        assert(viewModel.uiState.value.appliance == "Heater")

    }

    @Test
    fun showerButton_isDisplayed() {
        context?.let {
            composeTestRule.onNodeWithContentDescription(it.getString(R.string.shower))
                .assertIsDisplayed()
        }
    }

    @Test
    fun showerButton_click() {
        context?.let {
            composeTestRule.onNodeWithContentDescription(it.getString(R.string.shower))
                .performClick()
        }

        assert(viewModel.uiState.value.appliance == "Shower")

    }


}
