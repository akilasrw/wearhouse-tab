package com.aeroclubcargo.warehouse.presentation.uld_position

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
import javax.inject.Inject

@HiltViewModel
class UldPositionViewModel @Inject constructor(private var repository: Repository) : ViewModel(){

    private val _flightScheduleValue = MutableStateFlow<FlightScheduleModel?>(null)
    val flightScheduleValue = _flightScheduleValue.asStateFlow()

    fun setFlightSchedule(schedule: FlightScheduleModel?) {
        _flightScheduleValue.value = schedule
        getULDList()
    }

    private val _assignedULDListFlow = MutableStateFlow<List<ULDPalletVM>?>(null)
    var assignedUldListFlow = _assignedULDListFlow.asStateFlow()

    fun getULDList() {
        viewModelScope.launch {
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
                Log.e("ULDAssignment Model", e.message.toString())
            }
        }
    }

}