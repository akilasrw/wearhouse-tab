package com.aeroclubcargo.warehouse.presentation.recieved_cargo_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.BookingModel
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceivedCargoDetailVM @Inject constructor(var repository: Repository) : ViewModel() {
    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setLoading( isLoading: Boolean){
        _isLoading.value= isLoading;
    }

    var pageIndex = 1
    val pageSize = 10

    private val _listOfPackages = MutableStateFlow<List<BookingModel>?>(null)
    val listOfPackages = _listOfPackages.asStateFlow()

    private val _flightScheduleValue = MutableStateFlow<FlightScheduleModel?>(null)
    val flightScheduleValue = _flightScheduleValue.asStateFlow()
    fun setFlightSchedule(schedule: FlightScheduleModel?){
        _flightScheduleValue.value = schedule
        getBookingListForFlightScheduleSector()
    }

    private fun getBookingListForFlightScheduleSector(){
        setLoading(true)
      viewModelScope.launch {
          var responsePackageList = repository.getBookingListPerFlightSchedule(_flightScheduleValue.value!!.id)
          if(responsePackageList.isSuccessful){
              _listOfPackages.value = responsePackageList.body()
          }
          setLoading(false)
      }
    }

}