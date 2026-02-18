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

    var eurValue by mutableStateOf("")

    var brlValue by mutableStateOf("")
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
                    val eur = response.rates["EUR"] ?: 0.0
                    eurValue = eur.toString()
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
                brlValue = buildInput(brlValue, key)
                convertBrlToEur()
            }
            SelectedField.TOP -> {
                eurValue = buildInput(eurValue, key)
                convertEurToBrl()
            }
        }
    }

    private fun buildInput(current: String, key: String): String {
        return when {
            key == "." && current.contains(".") -> current
            key == "." && current.isEmpty() -> "0."
            current == "0" && key != "." -> key
            else -> current + key
        }
    }

    private fun clear() {
        brlValue = ""
        eurValue = ""
        /*when (selectedField) {
            SelectedField.BOTTOM -> {
                brlRate = ""
                eurRate = ""
            }
            SelectedField.TOP -> {
                eurRate = ""
                brlRate = ""
            }
        }*/
    }

    private fun convertBrlToEur() {
        val brl = brlValue.toDoubleOrNull() ?: return
        val rate = eurValue.toDoubleOrNull() ?: return

        val eur = brl / rate
        eurValue = String.format("%.2f", eur)
    }

    private fun convertEurToBrl() {
        val eur = eurValue.toDoubleOrNull() ?: return
        val rate = eurRate.toDoubleOrNull() ?: return

        val brl = eur * rate
        brlValue = String.format("%.2f", brl)
    }
}