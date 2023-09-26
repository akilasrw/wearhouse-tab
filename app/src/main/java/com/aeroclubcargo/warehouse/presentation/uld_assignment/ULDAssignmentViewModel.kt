package com.aeroclubcargo.warehouse.presentation.uld_assignment

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.CutOffTimeModel
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ULDAssignmentViewModel  @Inject constructor(private var repository: Repository) : ViewModel()  {

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    private val _flightScheduleValue = MutableStateFlow<FlightScheduleModel?>(null)
    val flightScheduleValue = _flightScheduleValue.asStateFlow()

    private val _flightULDvalue = MutableStateFlow<String>("")
    val flightULDValue = _flightULDvalue.asStateFlow()

    private val _todoListFlow = MutableStateFlow<List<String>>(value = listOf("asd","asda","asd","dasd","sada","ada","asd","asda","asd","dasd","sada","ada"))
    var todoListFlow = _todoListFlow.asStateFlow()

    fun setFlightULDValue (value : String){
        _flightULDvalue.value = value
    }

    fun setFlightSchedule(schedule: FlightScheduleModel?){
        _flightScheduleValue.value = schedule
    }

    fun setLoading( isLoading: Boolean){
        _isLoading.value= isLoading;
    }


}