package com.example.team33.network

import android.icu.text.SimpleDateFormat
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.util.*

/**
 * Represents the electricity regions in Norway.
 *
 * This enum class defines the different electricity regions in Norway along with their corresponding
 * codes. Each region is identified by a unique code, and the codes are used to differentiate electricity
 * pricing and consumption data for different regions.
 *
 * The available regions are:
 * - NO1: Oslo / Øst-Norge
 * - NO2: Kristiansand / Sør-Norge
 * - NO3: Trondheim / Midt-Norge
 * - NO4: Tromsø / Nord-Norge
 * - NO5: Bergen / Vest-Norge
 */
enum class ElectricityRegion {
    NO1, NO2, NO3, NO4, NO5
}

/**
 * A utility object for interacting with the Stroempris API to retrieve electricity price data.
 *
 * The `StroemprisApi` object provides functions to fetch the current electricity prices from different
 * regions in Norway. It uses the Stroempris API to make HTTP requests and retrieve the price data in JSON format.
 *
 * @property jsonClient The HTTP client used for making API requests and handling JSON responses.
 * @property BASE_URL The base URL of the Stroempris API.
 */
object StroemprisApi {
    private val jsonClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            expectSuccess = true
        }
    }

    private const val BASE_URL = "https://www.hvakosterstrommen.no/api/v1/prices"

    /**
     * Retrieves the current electricity prices from the specified region.
     *
     * This function makes a HTTP GET request to the Stroempris API to fetch the electricity price data for
     * the specified region and the current date. The function constructs the API endpoint URL using the
     * region code and current date and parses the JSON response into a list of [StroemprisData] objects.
     *
     * @param region The electricity region to retrieve the prices from.
     * @return A list of [StroemprisData] objects representing the electricity prices.
     */
    suspend fun getCurrentPriceFromRegion(region: ElectricityRegion): List<StroemprisData> {
        // get current date (year, month, day)
        val today = SimpleDateFormat("yyyy/MM-dd", Locale.getDefault()).format(Date())

        val path = "${BASE_URL}/${today}_${region.name}.json"
        return jsonClient.get(path).body()
    }
}