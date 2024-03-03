package com.example.team33

import com.example.team33.network.ElectricityRegion
import com.example.team33.network.StroemprisApi
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class StroemprisDataTest {

    // Testing that all API-calls returns a non-null array
    @Test
    fun stroemData_NotNull() {
        runBlocking {
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO1)[0])

            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO2)[0])

            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO3)[0])

            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO4)[0])

            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO5)[0])

        }
    }
    @Test
    fun NOK_per_kWh_NotNull() {
        runBlocking {
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO1)[0].NOK_per_kWh)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO2)[0].NOK_per_kWh)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO3)[0].NOK_per_kWh)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO4)[0].NOK_per_kWh)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO5)[0].NOK_per_kWh)

        }
    }


    @Test
    fun time_start_NotNull() {
        runBlocking {
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO1)[0].time_start)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO2)[0].time_start)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO3)[0].time_start)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO4)[0].time_start)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO5)[0].time_start)

        }
    }


    @Test
    fun time_end_NotNull() {
        runBlocking {
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO1)[0].time_end)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO2)[0].time_end)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO3)[0].time_end)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO4)[0].time_end)
            assertNotNull(StroemprisApi.getCurrentPriceFromRegion(ElectricityRegion.NO5)[0].time_end)

        }
    }

}
