package com.example.team33

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import com.example.team33.network.ElectricityRegion
import com.example.team33.ui.AppScreen
import com.example.team33.ui.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
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
    }

    @Test
    fun screenTitle_isDisplayed() {
        context?.let {
            composeTestRule.onAllNodesWithText(it.getString(R.string.home)).onFirst()
                .assertIsDisplayed()
        }
    }

    // Test`s if title is displayed
    @Test
    fun screenRegion_isDisplayed() {
        val region = when (viewModel.uiState.value.currentRegion) {
            ElectricityRegion.NO1 -> context?.getString(R.string.east_norway)
            ElectricityRegion.NO2 -> context?.getString(R.string.south_norway)
            ElectricityRegion.NO3 -> context?.getString(R.string.mid_norway)
            ElectricityRegion.NO4 -> context?.getString(R.string.north_norway)
            ElectricityRegion.NO5 -> context?.getString(R.string.west_norway)
        }

        if (region != null) {
            composeTestRule.onNodeWithText(region).assertIsDisplayed()
        } else {
            error("region not found")
        }
    }

}