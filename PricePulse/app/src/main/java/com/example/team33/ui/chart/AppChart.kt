package com.example.team33.ui.chart

import android.graphics.Typeface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.team33.R
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.edges.rememberFadingEdges
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlin.math.ceil

private val axisTitleHorizontalPaddingValue = 8.dp
private val axisTitleVerticalPaddingValue = 2.dp
private val axisTitlePadding =
    dimensionsOf(axisTitleHorizontalPaddingValue, axisTitleVerticalPaddingValue)
private val axisTitleMarginValue = 4.dp
private val startAxisTitleMargins = dimensionsOf(end = axisTitleMarginValue)
private val bottomAxisTitleMargins = dimensionsOf(top = axisTitleMarginValue)

private val thresholdLineLabelMarginValue = 4.dp
private val thresholdLineLabelHorizontalPaddingValue = 8.dp
private val thresholdLineLabelVerticalPaddingValue = 2.dp
private val thresholdLineThickness = 1.dp
private val thresholdLineLabelPadding =
    dimensionsOf(thresholdLineLabelHorizontalPaddingValue, thresholdLineLabelVerticalPaddingValue)
private val thresholdLineLabelMargins = dimensionsOf(thresholdLineLabelMarginValue)

/**
 * Composable function that displays a populated chart card.
 *
 * @param list The list of double values representing the data points of the chart.
 * @param modifier The optional modifier for styling the chart card.
 * @param thresholdValue The optional threshold value for displaying a threshold line on the chart.
 */
@Composable
fun PopulatedChartCard(
    list: List<Double>,
    modifier: Modifier = Modifier,
    thresholdValue: Float? = null
) {
    // Create a ChartEntryModel using the list of double values
    val model: ChartEntryModel =
        entryModelOf(List(list.size) { FloatEntry(it.toFloat(), list[it].toFloat()) })

    // Define an AxisValuesOverrider to set the minimum and maximum Y-axis values
    val maxY = ceil(list.max() + 0.25).toFloat()
    val axisValuesOverrider: AxisValuesOverrider<ChartEntryModel> =
        AxisValuesOverrider.fixed(minY = 0.0f, maxY = maxY)

    // Determine the decoration based on the threshold value
    val thresholdLine = rememberThresholdLine(thresholdValue)
    val decoration = when {
        thresholdLine == null || thresholdValue!! >= maxY || thresholdValue == 0.0F -> null
        else -> remember(thresholdLine) { listOf(thresholdLine) }
    }

    // Define the chart colors
    val chartColors: List<Color> = listOf(MaterialTheme.colorScheme.primary)

    // Display the chart using the ChartTemplate composable
    ChartTemplate(
        chartColors = chartColors,
        model = model,
        axisValuesOverrider = axisValuesOverrider,
        decoration = decoration,
        modifier = modifier,
        xAxisTitlemsg = stringResource(R.string.x_axis2)
    )
}

/**
 * Draws an empty chart with styling
 *@param size the length of X axis
 */
@Composable
fun EmptyAppChartCard(size: Int) {
    val model = entryModelOf(List(size) { FloatEntry(it.toFloat(), 0.0f) })
    ChartTemplate(chartColors = listOf(Color.Black), model = model)
}

/**
 * Composable function that defines the template for displaying a chart.
 *
 * @param chartColors The list of colors to be used for the chart.
 * @param model The ChartEntryModel containing the data points for the chart.
 * @param modifier The optional modifier for styling the chart template.
 * @param axisValuesOverrider The optional AxisValuesOverrider for customizing the axis values.
 * @param decoration The optional list of decorations to be applied to the chart.
 * @param xAxisTitlemsg The optional x-axis title for the chart.
 */
@Composable
private fun ChartTemplate(
    chartColors: List<Color>,
    model: ChartEntryModel,
    modifier: Modifier = Modifier,
    axisValuesOverrider: AxisValuesOverrider<ChartEntryModel>? = null,
    decoration: List<Decoration>? = null,
    xAxisTitlemsg: String? = null
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(),
        modifier = modifier
    ) {
        Box(Modifier.padding(16.dp)) {
            ProvideChartStyle(rememberChartStyle(chartColors)) {

                Chart(
                    chart = lineChart(
                        pointPosition = LineChart.PointPosition.Start,
                        axisValuesOverrider = axisValuesOverrider,
                        decorations = decoration
                    ),
                    model = model,
                    startAxis = startAxis(
                        guideline = null,
                        horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
                        titleComponent = textComponent(
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            background = shapeComponent(
                                Shapes.pillShape,
                                MaterialTheme.colorScheme.secondaryContainer
                            ),
                            padding = dimensionsOf(
                                axisTitleHorizontalPaddingValue, axisTitleVerticalPaddingValue
                            ),
                            margins = startAxisTitleMargins,
                            typeface = Typeface.SANS_SERIF
                        ),
                        title = stringResource(R.string.y_axis),
                        maxLabelCount = 5
                    ),
                    bottomAxis = bottomAxis(
                        titleComponent = textComponent(
                            background = shapeComponent(
                                Shapes.pillShape,
                                MaterialTheme.colorScheme.secondaryContainer
                            ),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            padding = axisTitlePadding,
                            margins = bottomAxisTitleMargins,
                            typeface = Typeface.MONOSPACE,
                        ),
                        title = xAxisTitlemsg,
                    ),
                    marker = rememberMarker(),
                    fadingEdges = rememberFadingEdges(),
                )
            }
        }
    }
}

/**
 * Composable function that remembers a threshold line based on the specified threshold value.
 *
 * @param thresholdValue The threshold value for the threshold line.
 * @return The ThresholdLine if a threshold value is provided, otherwise null.
 */
@Composable
private fun rememberThresholdLine(thresholdValue: Float?): ThresholdLine? {
    if (thresholdValue == null) {
        return null
    }

    // Create the line component for the threshold line
    val line = shapeComponent(
        strokeWidth = thresholdLineThickness,
        strokeColor = MaterialTheme.colorScheme.secondary.copy(alpha = 1f)
    )

    // Create the label component for the threshold line
    val label = textComponent(
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        background = shapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.secondaryContainer),
        padding = thresholdLineLabelPadding,
        margins = thresholdLineLabelMargins,
        typeface = Typeface.MONOSPACE,
    )

    // Remember the line and label components
    return remember(line, label) {
        ThresholdLine(
            thresholdValue = thresholdValue,
            thresholdLabel = "limit",
            lineComponent = line,
            labelComponent = label,
            labelHorizontalPosition = ThresholdLine.LabelHorizontalPosition.End
        )
    }
}
