package com.example.team33.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalLaundryService
import androidx.compose.material.icons.rounded.Shower
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.team33.R
import com.example.team33.ui.chart.PopulatedChartCard
import com.example.team33.ui.theme.md_theme_light_primaryContainer
import com.example.team33.ui.uistate.MainUiState

/**
 * Composable function that represents the UI of the appliances screen.
 *
 * @param mainUiState The [MainUiState] representing the UI state of the app.
 * @param modifier The optional [Modifier] to be applied to the appliances screen UI.
 * @param graphVisible Callback to change the visibility of the graph.
 * @param changeAppliance Callback to change the selected appliance.
 */
@Composable
fun AppliancesScreen(
    mainUiState: MainUiState, modifier: Modifier = Modifier,
    graphVisible: (Boolean) -> Unit,
    changeAppliance: (String) -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxSize(),
    ) {
        item {

            Spacer(modifier = Modifier.height(10.dp))

            // Displays Table and graph button, which changes the showGraph statement in the mainUiState.
            Row(modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(0.5f))
                Button(
                    onClick = { graphVisible(true) },
                    modifier = Modifier
                        .clip(shape = RectangleShape)
                        .weight(4f)
                ) {
                    Text(
                        text = stringResource(id = R.string.graph),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Spacer(Modifier.weight(0.5f))
                Button(
                    onClick = { graphVisible(false) },
                    modifier = Modifier
                        .clip(shape = RectangleShape)
                        .weight(4f)
                ) {
                    Text(
                        text = stringResource(id = R.string.table),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Spacer(Modifier.weight(0.5f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Result
            Box {
                Box(
                    Modifier
                        .align(Center)
                        .height(200.dp)
                        .width(
                            300.dp
                        )
                )
                // gets the electricty price for the day from the uistate.
                var liste = mainUiState.electricityPrices

                // Times the list with the kwh cost of the product
                if (liste != null) {
                    when (mainUiState.appliance) {
                        "Washing" -> liste = liste.map { it * 0.57 }
                        "Oven" -> liste = liste.map { it * 0.946 }
                        "Heater" -> liste = liste.map { it * 0.53 }
                        "Shower" -> liste = liste.map { it * 6 }

                    }
                }
                // Displays either a graph or table based on the showGraph value in mainUiState.
                if (liste != null) {
                    if (mainUiState.showGraph) {
                        ShowGraph(liste)
                    } else {
                        Box(modifier = Modifier.fillMaxHeight(0.5F)) {
                            ShowTable(liste)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Four button which represents the different applications, with a corresponding icon.
            // When you click the button it changes which appliance is chosen in the mainUiState
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { changeAppliance("Washing") },
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(100),
                    colors = ButtonDefaults.buttonColors(md_theme_light_primaryContainer)

                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocalLaundryService,
                        contentDescription = stringResource(R.string.washing_machine),
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.weight(0.12f))
                Button(
                    onClick = { changeAppliance("Oven") },
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(100),
                    colors = ButtonDefaults.buttonColors(md_theme_light_primaryContainer)

                ) {
                    Image(
                        painterResource(R.drawable.oven),
                        contentDescription = stringResource(R.string.oven),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.weight(0.12f))

                Button(
                    onClick = { changeAppliance("Heater") },
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(100),
                    colors = ButtonDefaults.buttonColors(md_theme_light_primaryContainer)

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Thermostat,
                        contentDescription = stringResource(R.string.heater),
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.weight(0.12f))

                Button(
                    onClick = { changeAppliance("Shower") },
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(100),
                    colors = ButtonDefaults.buttonColors(md_theme_light_primaryContainer)

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Shower,
                        contentDescription = stringResource(R.string.shower),
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

/**
 * Composable function that displays a graph based on the provided list of values.
 *
 * @param list The list of values to be displayed in the graph.
 * @param modifier The optional [Modifier] to be applied to the graph UI.
 */
@Composable
fun ShowGraph(list: List<Double>, modifier: Modifier = Modifier) {
    // calls upon PopulatedChrtCard from AppChart
    PopulatedChartCard(list = list, modifier, 2.7f)
}

/**
 * Composable function that displays a table based on the provided list of values.
 *
 * @param list The list of values to be displayed in the table.
 * @param modifier The optional [Modifier] to be applied to the table UI.
 */
@Composable
fun ShowTable(list: List<Double>, modifier: Modifier = Modifier) {
    // Creates a row wih lazycolumns, so you are able to scroll vertically trough the table.
    Row(modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(0.25F))

        LazyColumn(modifier = Modifier.height(250.dp)) {
            itemsIndexed(list) { index, element ->
                RowInTable(value = index, price = element)

            }
        }
        Spacer(modifier = Modifier.weight(0.25F))
    }
}

/**
 * Composable function that represents a row in a table.
 *
 * @param value The value representing the row index.
 * @param price The price value to be displayed in the row.
 * @param modifier The optional [Modifier] to be applied to the row UI.
 */
@Composable
fun RowInTable(
    value: Int,
    price: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(width = 2.dp, color = Color.Blue)
            .padding(1.dp)
            .height(60.dp)
            .fillMaxWidth(0.8F)
    ) {
        // turns the index to hour
        var time = " "
        time += if (value < 10) {
            "0$value:00-"
        } else {
            "$value:00-"
        }
        time += if ((value + 1) < 10) {
            "0${value + 1}:00"
        } else {
            "${value + 1}:00"
        }
        // The first cell contains the time, and the second contains the cost
        TableCell(data = time)

        // Makes the black line between the 2 cells
        Divider(
            color = Color.Blue,
            thickness = 16.dp,
            modifier = Modifier
                .fillMaxHeight(2F)
                .width(1.dp)
        )
        TableCell(data = "%.2f NOK".format(price))
    }
}

/**
 * Composable function that represents a cell in a table.
 *
 * @param data The text data to be displayed in the cell.
 * @param modifier The optional [Modifier] to be applied to the cell UI.
 */
@Composable
fun TableCell(
    data: String,
    modifier: Modifier = Modifier
) {
    Box {
        Text(
            text = data,
            modifier = modifier.padding(8.dp)
        )
    }
}

