package com.aeroclubcargo.warehouse.presentation.cutoff_time

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CutOffTimeViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    private val _flightNameValue = MutableStateFlow("")
    val flightNameValue = _flightNameValue.asStateFlow()

    fun onFlightNameChange(flightName: String){
        _flightNameValue.value = flightName
    }
}