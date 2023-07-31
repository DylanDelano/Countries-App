package com.example.countriesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapp.modal_data.GetCountriesUseCase
import com.example.countriesapp.modal_data.Resource
import com.example.countriesapp.modal_data.countries_data
import com.example.countriesapp.util.CountryListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(private val getCountriesUseCase: GetCountriesUseCase):ViewModel() {
    private val _state = MutableStateFlow(CountryListState())
    val state: StateFlow<CountryListState> = _state

    init {
        getCountries()
    }


    private fun getCountries() {
        getCountriesUseCase().onEach { result ->
            when (result) {

                is Resource.Success -> {
                    _state.value = CountryListState(country = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = CountryListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CountryListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}




