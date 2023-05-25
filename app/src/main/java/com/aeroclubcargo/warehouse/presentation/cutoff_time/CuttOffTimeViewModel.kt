package com.aeroclubcargo.warehouse.presentation.cutoff_time

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.CutOffTimeModel
import com.aeroclubcargo.warehouse.domain.model.PackageListItem
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

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
//                    cutOffTimeList.postValue(paginatedList.data)
//                    todoList = mutableStateListOf<CutOffTimeModel>(paginatedList.data!!)
                    todoList.addAll(paginatedList.data!!)
//                    _todoListFlow.value = mutableStateListOf(paginatedList.data)
                }
                setLoading(false)
            }catch (e:Exception){
                setLoading(false)
                Log.d("CutOffTimeViewModel",e.toString())
            }
        }
    }


}