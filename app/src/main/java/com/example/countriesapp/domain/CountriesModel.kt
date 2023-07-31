package com.example.countriesapp.domain

import com.example.countriesapp.modal_data.countries_data

data class CountriesModel(var name : String, var countryData: countries_data, var isSection: Boolean)
