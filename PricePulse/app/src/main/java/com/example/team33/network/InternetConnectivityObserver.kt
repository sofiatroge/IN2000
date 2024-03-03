package com.example.team33.network

import kotlinx.coroutines.flow.Flow

interface InternetConnectivityObserver {
    fun observe(): Flow<Status>

    enum class Status {
        Available,
        Unavailable,
        Losing,
        Lost
    }
}