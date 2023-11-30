package com.aeroclubcargo.warehouse.presentation.uld_assignment

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleSectorPalletCreateListRM
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleSectorPalletCreateRM
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleSectorPalletDeleteRM
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ULDAssignmentViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _flightScheduleValue = MutableStateFlow<FlightScheduleModel?>(null)
    val flightScheduleValue = _flightScheduleValue.asStateFlow()

    private val _flightULDvalue = MutableStateFlow<String>("")
    val flightULDValue = _flightULDvalue.asStateFlow()

    // keeps all the ULD lists here
    private var allULDPallets: List<ULDPalletVM> = listOf()

    private val _assignedULDListFlow = MutableStateFlow<List<ULDPalletVM>?>(null)
    var assignedUldListFlow = _assignedULDListFlow.asStateFlow()

    private val _allULDListFlow = MutableStateFlow<List<ULDPalletVM>?>(null)
    var allUldListFlow = _allULDListFlow.asStateFlow()

    val selectedRows = mutableStateListOf<ULDPalletVM>()

    var flightScheduleSectorPalletRMs = mutableListOf<FlightScheduleSectorPalletCreateRM>()

    suspend fun refreshAllULDList() {
        viewModelScope.launch {
            _allULDListFlow.emit(allULDPallets)
            allULDPallets.forEach { uldPalletVM ->
                if (!selectedRows.any { it.id == uldPalletVM.id } && uldPalletVM.isAssigned) {
                    selectedRows.add(uldPalletVM)
                }

            }
        }
    }

    fun setFlightULDValue(value: String) {
        _flightULDvalue.value = value
        viewModelScope.launch {
            if (value.isNotEmpty()) {
                val assignedList = allULDPallets.filter { it.serialNumber.contains(value) }
                _allULDListFlow.emit(assignedList)
            } else {
                refreshAllULDList()
            }
        }
    }

    fun updateData(onComplete: ()-> Unit) {
        viewModelScope.launch {
            if (flightScheduleSectorPalletRMs.isNotEmpty()) (flightScheduleSectorPalletRMs as ArrayList).clear()

            _allULDListFlow.value?.forEach { item ->
                if (selectedRows.any { it.id == item.id }) {
                    // assigned item
                    Log.e("#######", "assigned item -> ${item.serialNumber}")
                    flightScheduleSectorPalletRMs.add(
                        FlightScheduleSectorPalletCreateRM(
                            isAdded = true,
                            flightScheduleSectorId = _flightScheduleValue.value!!.id,
                            uldId = item.id,
                        )
                    )
                } else {
                    // unassigned item
                    Log.e("#######", "unassigned item -> ${item.serialNumber}")
                    flightScheduleSectorPalletRMs.add(
                        FlightScheduleSectorPalletCreateRM(
                            isAdded = false,
                            flightScheduleSectorId = _flightScheduleValue.value!!.id,
                            uldId = item.id,
                        )
                    )
                }

            }
            if (flightScheduleSectorPalletRMs.isNotEmpty()) {
                var response = repository.addPalletListToFlight(
                    FlightScheduleSectorPalletCreateListRM(
                        flightScheduleSectorPalletRMs = flightScheduleSectorPalletRMs
                    )
                )
                Log.e("#######", "Updated Items -> ${response.body()}")
                getULDList()
                onComplete()
            }

        }

    }

    fun setFlightSchedule(schedule: FlightScheduleModel?) {
        _flightScheduleValue.value = schedule
        getULDList()
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading;
    }


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
                        allULDPallets = (list)
                        refreshAllULDList()
                        var assignedList = allULDPallets.toList().filter { it.isAssigned }
                        _assignedULDListFlow.emit(assignedList)
                    }
                }
            } catch (e: Exception) {
                Log.e("ULDAssignment Model", e.message.toString())
            }
        }
    }

    fun deleteAssignedPallet(uldId:String){
        viewModelScope.launch {
            try {
                setLoading(true)
                val response = repository.removeAssignedULDFromSchedule(
                    FlightScheduleSectorPalletDeleteRM(flightScheduleSectorId = _flightScheduleValue!!.value!!.id!!, uldId = uldId)
                )
                if(response.isSuccessful){
                    getULDList()
                }
                setLoading(false)
            }   catch (e:Exception){
                print(e)
                setLoading(false)
            }
        }
    }


}