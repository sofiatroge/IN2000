package com.example.team33

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.team33.network.ElectricityRegion
import com.example.team33.ui.AppScreen
import com.example.team33.ui.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

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
        context?.let { composeTestRule.onAllNodesWithText(it.getString(R.string.settings)).onFirst().performClick() }
    }

    // Testing that text is displayed
    @Test
    fun screenTitle_isDisplayed() {
        context?.let { composeTestRule.onAllNodesWithText(it.getString(R.string.settings)).onFirst().assertIsDisplayed() }
    }

    @Test
    fun language_isDisplayed() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.language)).assertIsDisplayed() }
    }

    @Test
    fun change_Language_isDisplayed() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.change_language)).assertIsDisplayed() }
    }

    @Test
    fun select_Region_isDisplayed() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.select_region)).assertIsDisplayed() }
    }

    // Testing select Region button click
    @Test
    fun select_Region_click() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.select_region)).performClick() }
    }

    // Testing that radio button click actually change region below
    @Test
    fun radio_Buttons_East_Test() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.select_region)).performClick() }

        context?.let { composeTestRule.onAllNodesWithText(it.getString(R.string.east_norway)).onFirst().performClick() }

        composeTestRule.onAllNodesWithText("Ok").onFirst().performClick()

        assert(ElectricityRegion.NO1 == viewModel.uiState.value.currentRegion)

    }

    @Test
    fun radio_Buttons_South_Test() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.select_region)).performClick() }

        context?.let { composeTestRule.onAllNodesWithText(it.getString(R.string.south_norway)).onFirst().performClick() }

        composeTestRule.onAllNodesWithText("Ok").onFirst().performClick()

        assert(ElectricityRegion.NO2 == viewModel.uiState.value.currentRegion)

    }

    @Test
    fun radio_Buttons_Mid_Test() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.select_region)).performClick() }

        context?.let { composeTestRule.onAllNodesWithText(it.getString(R.string.mid_norway)).onFirst().performClick() }

        composeTestRule.onAllNodesWithText("Ok").onFirst().performClick()


        assert(ElectricityRegion.NO3 == viewModel.uiState.value.currentRegion)

    }

    @Test
    fun radio_Buttons_North_Test() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.select_region)).performClick() }

        context?.let { composeTestRule.onAllNodesWithText(it.getString(R.string.north_norway)).onFirst().performClick() }

        composeTestRule.onAllNodesWithText("Ok").onFirst().performClick()


        assert(ElectricityRegion.NO4 == viewModel.uiState.value.currentRegion)

    }

    @Test
    fun radio_Buttons_West_Test() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.select_region)).performClick() }

        context?.let { composeTestRule.onAllNodesWithText(it.getString(R.string.west_norway)).onFirst().performClick() }

        composeTestRule.onAllNodesWithText("Ok").onFirst().performClick()


        assert(ElectricityRegion.NO5 == viewModel.uiState.value.currentRegion)

    }


    // Testing if button is displayed below and clicking on it
    @Test
    fun about_This_App_isDisplayed() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.about_this_app)).assertIsDisplayed() }
    }

    @Test
    fun about_This_App_click() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.about_this_app)).performClick() }
    }

    @Test
    fun usage_isDisplayed() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.usage)).assertIsDisplayed() }
    }

    @Test
    fun usage_click() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.usage)).performClick() }
    }

    @Test
    fun opensource_isDisplayed() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.opensource)).assertIsDisplayed() }
    }

    @Test
    fun opensource_click() {
        context?.let { composeTestRule.onNodeWithText(it.getString(R.string.opensource)).performClick() }
    }
}
