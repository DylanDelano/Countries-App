package com.example.countriesapp.util

import com.example.countriesapp.modal_data.countries_data

data class CountryListState(
    val isLoading: Boolean = false,
    val country:List<countries_data> = emptyList(),
    val error: String = ""
)

