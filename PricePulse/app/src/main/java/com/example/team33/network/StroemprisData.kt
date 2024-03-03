package com.example.team33.network

import kotlinx.serialization.Serializable

/**
 * Represents data from Strompris API.
 * This class is used to serialize data from Strompris API.
 *
 * @param NOK_per_kWh The price per kilowatt-hour in Norwegian Kroner (NOK).
 * @param time_end The end time of the period for which the price is applicable.
 * @param time_start The start time of the period for which the price is applicable.
 */
@Serializable
data class StroemprisData(
    val NOK_per_kWh: Double,
    val time_end: String,
    val time_start: String
)