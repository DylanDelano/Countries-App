package com.example.countriesapp.modal_data

import com.google.gson.annotations.SerializedName

data class UICountry(val name: String,
val flags: String,val capital:List<String>? = emptyList(),                                                                                                                                                                                                       )
