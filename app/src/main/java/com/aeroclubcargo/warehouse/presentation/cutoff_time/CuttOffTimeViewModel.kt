package com.aeroclubcargo.warehouse.presentation.cutoff_time

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.CutOffTimeModel
import com.aeroclubcargo.warehouse.domain.model.CutOffTimeRequest
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.aeroclubcargo.warehouse.utils.updateTimeOnly
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class CutOffTimeViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    private val _flightNameValue = MutableStateFlow("")
    val flightNameValue = _flightNameValue.asStateFlow()

    fun onFlightNameChange(flightName: String){
        _flightNameValue.value = flightName

    }


    private val _dateValue = MutableStateFlow("")
    val flightDateValue = _dateValue.asStateFlow()

    fun onFlightDateChange(date: String){
        _dateValue.value = date
    }

    fun updateCutOffTime(hours:Int, minutes:Int,cutOffTimeModel: CutOffTimeModel){
        viewModelScope.launch {
            setLoading(true)
            try {
                var scheduledDepartureTime = cutOffTimeModel.scheduledDepartureDateTime
                repository.updateCutOffTIme(cutOffTimeModel.id,
                    CutOffTimeRequest(id = cutOffTimeModel.id, cutOffTime = scheduledDepartureTime!!.updateTimeOnly(hours = hours,minutes = minutes)))

            }catch (e:Exception){
                e.localizedMessage?.let { Log.e("CutOffTimeViewModel", it) }
            }finally {
                setLoading(false)
                getScheduleList()
            }
        }
    }



    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


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
                val paginatedList = repository.cargoBookingSummaryList(FlightNumber = flightNameValue.value, FlightDate = flightDateValue.value,1,11)
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