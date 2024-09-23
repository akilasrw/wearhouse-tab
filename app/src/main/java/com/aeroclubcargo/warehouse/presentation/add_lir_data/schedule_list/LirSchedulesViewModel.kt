package com.aeroclubcargo.warehouse.presentation.add_lir_data.schedule_list

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.aeroclubcargo.warehouse.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LirSchedulesViewModel  @Inject constructor(private var repository: Repository) : ViewModel()  {

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
    private var todoList = mutableStateListOf<FlightScheduleModel>()
    private val _todoListFlow = MutableStateFlow(todoList)

    val flightScheduleListFlow: StateFlow<List<FlightScheduleModel>> get() = _todoListFlow

    init {
        getScheduleList()
        _dateFromValue.value = Utils.getDateBeforeOneMonth()
        _dateToValue.value = Utils.getTodayDate()
    }

    fun setLoading( isLoading: Boolean){
        _isLoading.value= isLoading;
    }

    fun getScheduleList() {
        viewModelScope.launch {
            setLoading(true)
            delay(timeMillis = 1500)
            try{
                val paginatedList = repository.getFlightScheduleWithULDCount( scheduledDepartureStartDateTime = flightDateFromValue.value,scheduledDepartureEndDateTime = flightDateToValue.value, excludeFinalizedSchedules = false)
                if(paginatedList.isSuccessful &&
                    paginatedList.body() != null) {
                    todoList.clear()
                    todoList.addAll(paginatedList.body()!!)
                }
                setLoading(false)
            }catch (e:Exception){
                setLoading(false)
                Log.d("FlightScheduleViewModel",e.toString())
            }
        }
    }

}