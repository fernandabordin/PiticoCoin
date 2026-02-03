package com.example.piticocoin.services

data class LatestRatesResponse(
    val success: Boolean,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
