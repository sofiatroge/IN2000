package com.example.team33.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team33.network.ElectricityRegion
import com.example.team33.network.StroemprisApi
import com.example.team33.network.StroemprisData
import com.example.team33.ui.uistate.MainUiState
import io.ktor.client.plugins.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

/**
 * ViewModel class responsible for managing the main UI state and logic of the app.
 */
class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    /**
     * Initializes the view model by fetching the initial electricity price.
     */
    init {
        getElectricityPrice()
    }

    /**
     * Changes the electricity region and updates the UI state accordingly.
     *
     * @param region The new electricity region.
     */
    fun changeElectricityRegion(region: ElectricityRegion) {
        if (_uiState.value.currentRegion != region) {
            _uiState.update { currentState ->
                currentState.copy(currentRegion = region)
            }
            getElectricityPrice()
        }
    }

    /**
     * Fetches electricity prices from the 'strømpris API'.
     * Updates the UI state with the fetched data.
     */
    private fun getElectricityPrice() {
        viewModelScope.launch(Dispatchers.IO) {
            var data = emptyList<StroemprisData>()
            try {
                data = StroemprisApi.getCurrentPriceFromRegion(_uiState.value.currentRegion)
            } catch (e: RedirectResponseException) {
                // Handle 3xx redirection response error
                val errorMsg = "${e.response.status}: ${e.response.call.request.url}"
                Log.d(TAG, errorMsg)
            } catch (e: ClientRequestException) {
                // Handle 4xx client request response error
                val errorMsg = "${e.response.status}: ${e.response.call.request.url}"
                Log.e(TAG, errorMsg)
            } catch (e: ServerResponseException) {
                // Handle 5xx server response error
                val errorMsg = "${e.response.status}: ${e.response.call.request.url}"
                Log.e(TAG, errorMsg)
            } catch (e: Exception) {
                // Handle other exceptions
                val errorMsg = "Something terrible went wrong because:"
                Log.e(TAG, "$errorMsg $e")
            }

            // Update the UI state with the fetched electricity price data
            _uiState.update { currentState ->
                currentState.copy(
                    // remove all other unnecessary properties for fetched data
                    electricityPrices = data.map { it.NOK_per_kWh }
                )
            }
        }
    }

    /**
     * Updates the UI state to control the visibility of the graph in the ApplianceScreen.
     *
     * @param statement The visibility status of the graph.
     */
    fun graphVisible(statement: Boolean) {
        // Changes whether or not the graph is visible in ApplianceScreen
        _uiState.update { currentState ->
            currentState.copy(
                // remove all other unnecessary properties for fetched data
                showGraph = statement
            )
        }
    }

    /**
     * Changes the selected appliance and updates the UI state accordingly.
     *
     * @param chosenAppliance The chosen appliance.
     */
    fun changeAppliance(chosenAppliance: String) {
        // Changes which appliance's price that should be displayed
        _uiState.update { currentState ->
            currentState.copy(
                // remove all other unnecessary properties for fetched data
                appliance = chosenAppliance
            )
        }
    }

    fun changeLimitValue(newLimitValue: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                maxPrice = newLimitValue
            )
        }
    }
}