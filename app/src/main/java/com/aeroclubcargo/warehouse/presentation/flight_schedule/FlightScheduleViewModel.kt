package com.aeroclubcargo.warehouse.presentation.flight_schedule

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.CutOffTimeModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightScheduleViewModel  @Inject constructor(private var repository: Repository) : ViewModel()  {

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _dateFromValue = MutableStateFlow("")
    val flightDateFromValue = _dateFromValue.asStateFlow()


    fun onFlightFromDateChange(date: String){
        _dateFromValue.value = date
    }

    private val _dateToValue = MutableStateFlow("")
    val flightDateToValue = _dateToValue.asStateFlow()


    fun onFlightToDateChange(date: String){
        _dateToValue.value = date
    }
    private var todoList = mutableStateListOf<CutOffTimeModel>()
    private val _todoListFlow = MutableStateFlow(todoList)

    val todoListFlow: StateFlow<List<CutOffTimeModel>> get() = _todoListFlow

    init {
        getScheduleList()

    }

    fun setLoading( isLoading: Boolean){
        _isLoading.value= isLoading;
    }

    fun getScheduleList() {
        viewModelScope.launch {
            setLoading(true)
            delay(timeMillis = 1000)
            try{
                val paginatedList = repository.cargoBookingSummaryList(FlightNumber = "", FlightDate = flightDateFromValue.value,1,11)
                if(paginatedList.data!= null){
                    todoList.clear()
                    todoList.addAll(paginatedList.data!!)
                }
                setLoading(false)
            }catch (e:Exception){
                setLoading(false)
                Log.d("CutOffTimeViewModel",e.toString())
            }
        }
    }

}