package com.example.team33.ui.uistate

import com.example.team33.network.ElectricityRegion

/**
 * Data class representing the UI state of the app.
 *
 * @param electricityPrices The list of electricity prices. Defaults to null.
 * @param currentRegion The current electricity region. Defaults to NO1.
 * @param currentHour The current hour. Defaults to 0.
 * @param maxPrice The maximum price. Defaults to 1.28.
 * @param showGraph Flag indicating whether the graph is visible. Defaults to true.
 * @param appliance The selected appliance. Defaults to "Washing".
 */
data class MainUiState(
    var electricityPrices: List<Double>? = null,
    var currentRegion: ElectricityRegion = ElectricityRegion.NO1,
    var currentHour: Int = 0,
    var maxPrice: Float = 1.28f,
    var showGraph: Boolean = true,
    var appliance: String = "Washing"
)