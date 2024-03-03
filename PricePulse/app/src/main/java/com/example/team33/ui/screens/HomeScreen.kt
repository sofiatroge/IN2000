package com.example.team33.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.team33.R
import com.example.team33.network.ElectricityRegion
import com.example.team33.ui.chart.EmptyAppChartCard
import com.example.team33.ui.chart.PopulatedChartCard
import com.example.team33.ui.genSineWaveList
import com.example.team33.ui.theme.Team33Theme
import com.example.team33.ui.uistate.MainUiState
import java.util.*

/**
 * Returns the current hour as an integer value.
 *
 * @return The current hour as an [Int].
 */
private fun getCurrentHour(): Int {
    return SimpleDateFormat("HH", Locale.getDefault()).format(Date()).toInt()
}

/**
 * Composable function that represents the home screen of the app.
 *
 * @param mainUiState The [MainUiState] representing the UI state of the app.
 * @param modifier The optional [Modifier] to be applied to the home screen UI.
 */
@Composable
fun HomeScreen(mainUiState: MainUiState, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxSize(1f)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))

            val region = when (mainUiState.currentRegion) {
                ElectricityRegion.NO1 -> stringResource(id = R.string.east_norway)
                ElectricityRegion.NO2 -> stringResource(id = R.string.south_norway)
                ElectricityRegion.NO3 -> stringResource(id = R.string.mid_norway)
                ElectricityRegion.NO4 -> stringResource(id = R.string.north_norway)
                ElectricityRegion.NO5 -> stringResource(id = R.string.west_norway)
            }
            Text(text = region, fontSize = 30.sp, modifier = Modifier)
            Spacer(modifier = Modifier.height(40.dp))

            SpotpriceComposable(currentPrice = mainUiState.electricityPrices?.get(getCurrentHour()))

            Spacer(modifier = Modifier.height(50.dp))

            ShowElectricityGraph(
                priceList = mainUiState.electricityPrices, thresholdValue = mainUiState.maxPrice
            )
        }
    }
}

/**
 * Composable function that displays the spot price of electricity.
 *
 * @param currentPrice The current spot price of electricity.
 * @param modifier The optional [Modifier] to be applied to the spot price UI.
 */
@Composable
private fun SpotpriceComposable(
    currentPrice: Double?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            stringResource(id = R.string.spot_price),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 10.dp)
        )

        val spotPrice = when (currentPrice) {
            null -> stringResource(id = R.string.wait)
            else -> "%.2f NOK/kWh".format(currentPrice)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
                modifier = Modifier.size(width = 280.dp, height = 80.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        spotPrice,
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}

/**
 * Composable function that displays an electricity graph.
 *
 * @param modifier The optional [Modifier] to be applied to the electricity graph UI.
 * @param priceList The list of electricity prices to be displayed on the graph.
 * @param thresholdValue The threshold value for the graph.
 */
@Composable
private fun ShowElectricityGraph(
    modifier: Modifier = Modifier,
    priceList: List<Double>? = null,
    thresholdValue: Float? = null,
) {
    when (priceList) {
        null -> EmptyAppChartCard(24)
        else -> PopulatedChartCard(
            list = priceList,
            thresholdValue = thresholdValue,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun HomeScreenPreview() {
    Team33Theme(dynamicColor = false) {
        Surface {
            HomeScreen(mainUiState = MainUiState(electricityPrices = genSineWaveList(24)))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpotpricePreview() {
    Team33Theme(dynamicColor = false) {
        Surface {
            SpotpriceComposable(null)
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ElectricityGraphPreview() {
    Team33Theme(dynamicColor = false) {
        Surface {
            ShowElectricityGraph(
                priceList = genSineWaveList(24), thresholdValue = 1.0f
            )
        }
    }
}
