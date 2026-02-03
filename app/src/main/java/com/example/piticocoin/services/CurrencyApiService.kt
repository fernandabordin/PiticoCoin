package com.example.piticocoin.services
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") apiKey: String,
        @Query("base") base: String? = "",
        @Query("symbols") symbols: String
    ): LatestRatesResponse
}