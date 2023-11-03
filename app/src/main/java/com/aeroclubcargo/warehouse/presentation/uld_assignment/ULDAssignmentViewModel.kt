package com.aeroclubcargo.warehouse.presentation.uld_assignment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ULDAssignmentViewModel  @Inject constructor(private var repository: Repository) : ViewModel()  {

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    var pageIndex = 1
    val pageSize = 10

    private val _flightScheduleValue = MutableStateFlow<FlightScheduleModel?>(null)
    val flightScheduleValue = _flightScheduleValue.asStateFlow()

    private val _flightULDvalue = MutableStateFlow<String>("")
    val flightULDValue = _flightULDvalue.asStateFlow()

    private var allULDPallets: List<ULDPalletVM> = listOf()

    private val _assignedULDListFlow = MutableStateFlow<List<ULDPalletVM>?>(null)
    var assignedUldListFlow = _assignedULDListFlow.asStateFlow()

    private val _allULDListFlow = MutableStateFlow<List<ULDPalletVM>?>(null)
    var allUldListFlow = _allULDListFlow.asStateFlow()

    fun refreshAllULDList(){
        viewModelScope.launch {
            _allULDListFlow.emit(allULDPallets)
        }
    }

    fun setFlightULDValue (value : String){
        _flightULDvalue.value = value
    }

    fun setFlightSchedule(schedule: FlightScheduleModel?){
        _flightScheduleValue.value = schedule
        getULDList()
    }

    fun setLoading( isLoading: Boolean){
        _isLoading.value= isLoading;
    }


    fun getULDList(){
        viewModelScope.launch {
            try {
                var response =  repository.getPalletsByFlightScheduleId(_flightScheduleValue!!.value!!.id!!,Constants.ULDLocateStatus.OnGround.ordinal)
                if(response.isSuccessful){
                    var list = response.body()
                    if(list != null) {
                        allULDPallets = (list)
                        var assignedList = allULDPallets.toList().filter { it.isAssigned }
                        _assignedULDListFlow.emit(assignedList)
                    }
                }
            }catch (e: Exception){
                Log.e("ULDAssignment Model",e.message.toString())
            }
        }
    }
// TODO remove
//    fun getULDList(){
//        viewModelScope.launch {
//            try {
//                var response =  repository.getULDFilteredList(pageSize = pageSize, pageIndex = pageIndex)
//                if(response.isSuccessful){
//                    var list = response.body()
//                    if(list?.data != null) {
//                        _todoListFlow.emit(list.data!!)
//                    }
//                }
//            }catch (e: Exception){
//                Log.e("ULDAssignment Model",e.message.toString())
//            }
//        }
//    }


}