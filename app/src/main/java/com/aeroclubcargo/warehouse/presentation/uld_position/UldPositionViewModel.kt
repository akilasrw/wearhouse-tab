package com.aeroclubcargo.warehouse.presentation.uld_position

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.ServiceResponseStatus
import com.aeroclubcargo.warehouse.domain.model.CargoPositionVM
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.ULDCargoPositionRequest
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UldPositionViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    private val _flightScheduleValue = MutableStateFlow<FlightScheduleModel?>(null)
    val flightScheduleValue = _flightScheduleValue.asStateFlow()

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setLoading( isLoading: Boolean){
        _isLoading.value= isLoading;
    }

    fun setFlightSchedule(schedule: FlightScheduleModel?) {
        _flightScheduleValue.value = schedule
        getULDList()
    }

    private val _assignedULDListFlow = MutableStateFlow<List<ULDPalletVM>?>(null)
    var assignedUldListFlow = _assignedULDListFlow.asStateFlow()

    private val _cargoPositionListFlow = MutableStateFlow<List<CargoPositionVM>?>(null)
    var cargoPositionListFlow = _cargoPositionListFlow.asStateFlow()


    fun getCargoPositionList() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response =
                    repository.getSummaryCargoPositionsBySector(flightScheduleSectorId = flightScheduleValue!!.value!!.id)
                if (response.isSuccessful) {
                    _cargoPositionListFlow.emit(response.body())
                    updateULDListValues()
                }
            } catch (e: Exception) {
                Log.e("ULDAssignment Model", e.message.toString())
            }
            setLoading(false)
        }
    }

    private fun updateULDListValues() {
        var assignList = _assignedULDListFlow.value
        assignList?.forEach { item ->
            if(item.cargoPositionID != null)
                item.cargoPositionVM = _cargoPositionListFlow.value?.toList()?.filter { it.id == item.cargoPositionID }?.first()
        }

    }

    fun addULDCargoPosition(uldId: String, cargoPositionId: String,onComplete :(String?,String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response =
                    repository.addULDCargoPosition(ULDCargoPositionRequest(uldId, cargoPositionId))
                if (response.isSuccessful) {
                    var responseModel = response.body()
                    if(responseModel?.statusCode == ServiceResponseStatus.Failed.ordinal){
                        onComplete(null,responseModel.message)
                    }else{
                        onComplete("ULD Updated Successfully", null)
                    }
                }

            } catch (e: Exception) {
                Log.e("ULDAssignment Model", e.message.toString())
            }
        }

    }

    fun getULDList() {
        viewModelScope.launch {
            setLoading(false)
            try {
                var response = repository.getPalletsByFlightScheduleId(
                    _flightScheduleValue!!.value!!.id!!,
                    null,
                    Constants.ULDLocateStatus.OnGround.ordinal
                )
                if (response.isSuccessful) {
                    var list = response.body()
                    if (list != null) {
                        var allULDPallets = (list)
                        var assignedList = allULDPallets.toList().filter { it.isAssigned }
                        _assignedULDListFlow.emit(assignedList)
                    }
                }
            } catch (e: Exception) {
                setLoading(false)
                Log.e("ULDAssignment Model", e.message.toString())
            }
            setLoading(false)
            getCargoPositionList()
        }
    }

}