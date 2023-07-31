package com.example.countriesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.lottie.L
import com.example.countriesapp.domain.FilterChildData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor():ViewModel() {
    private val _filter = MutableLiveData<List<FilterChildData>>()
    val filter: LiveData<List<FilterChildData>> get() = _filter
    init {
        _filter.value= emptyList()
    }
    fun filterWords(list:List<FilterChildData>){
        _filter.value=list
    }
}
