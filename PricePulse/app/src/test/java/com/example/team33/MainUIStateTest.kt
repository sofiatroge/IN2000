package com.example.team33

import com.example.team33.network.ElectricityRegion
import com.example.team33.ui.viewmodel.MainViewModel
import org.junit.Assert
import org.junit.Test

class MainUIStateTest {

    val viewModel = MainViewModel()


    @Test
    fun stroem_isNull() {
        val data = viewModel.uiState.value.electricityPrices
        Assert.assertNull(data)
    }

    @Test
    fun currentRegion_Test() {
        Assert.assertNotNull(viewModel.uiState.value.currentRegion)
        assert(viewModel.uiState.value.currentRegion == ElectricityRegion.NO1)
    }

    @Test
    fun currentHour_Test() {
        Assert.assertNotNull(viewModel.uiState.value.currentHour)
        assert(viewModel.uiState.value.currentHour == 0)
    }

    @Test
    fun appliances_Test() {
        Assert.assertNotNull(viewModel.uiState.value.appliance)
        assert(viewModel.uiState.value.appliance == "Washing")
    }

    @Test
    fun maxPrice_Test() {
        Assert.assertNotNull(viewModel.uiState.value.maxPrice)
        assert(viewModel.uiState.value.maxPrice == 1.28f)
    }

    @Test
    fun showGraph_Test() {
        Assert.assertNotNull(viewModel.uiState.value.showGraph)
        assert(viewModel.uiState.value.showGraph)
    }


}
