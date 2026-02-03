package com.example.piticocoin.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piticocoin.services.RetrofitClient
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {
    var brlRate by mutableStateOf(0.0)
        private set
    var eurRate by mutableStateOf(0.0)
        private set
    fun fetchRates() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.service.getLatestRates(
                    apiKey = "295d6f35dd3f98f585c5afdad57f8740",
                    //base = "USD",
                    symbols = "EUR,BRL"
                )

                if (response.success) {
                    eurRate = response.rates["EUR"] ?: 0.0
                    brlRate = response.rates["BRL"] ?: 0.0
                    println("EUR: $eurRate  BRL: $brlRate")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}