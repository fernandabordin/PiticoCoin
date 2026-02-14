package com.example.piticocoin.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piticocoin.services.RetrofitClient
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    enum class SelectedField {
        TOP,
        BOTTOM
    }

    var eurRate by mutableStateOf("")
        private set

    var brlRate: String by mutableStateOf(0.0)
        private set

    private var selectedField = SelectedField.BOTTOM

    fun fetchRates() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.service.getLatestRates(
                    apiKey = "295d6f35dd3f98f585c5afdad57f8740",
                    symbols = "EUR"
                )

                if (response.success) {
                    val eur = (response.rates["EUR"]as? Double) ?: 0.0
                    eurRate = String.format("%.4f", eur)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectTop() {
        selectedField = SelectedField.TOP
    }

    fun selectBottom() {
        selectedField = SelectedField.BOTTOM
    }

    fun onKeyPressed(key: String) {
        when (key) {
            "C" -> clear()
            else -> appendKey(key)
        }
    }

    private fun appendKey(key: String) {
        when (selectedField) {
            SelectedField.BOTTOM -> {
                brlRate += key
                convertBrlToEur()
            }
            SelectedField.TOP -> {
                eurRate += key
                convertEurToBrl()
            }
        }
    }

    private fun clear() {
        when (selectedField) {
            SelectedField.BOTTOM -> {
                brlRate = ""
                eurRate = ""
            }
            SelectedField.TOP -> {
                eurRate = ""
                brlRate = ""
            }
        }
    }

    private fun convertBrlToEur() {
        val Brl = brlRate.toDoubleOrNull() ?: return
        val rate = eurRate.toDoubleOrNull() ?: return

        val Eur = Brl / rate
        eurRate = String.format("%.2f", Eur)
    }

    private fun convertEurToBrl() {
        val eur = eurRate.toDoubleOrNull() ?: return

        val Brl = eur * eurRate
        brlRate = String.format("%.2f", Brl)
    }
}