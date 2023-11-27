package com.aeroclubcargo.warehouse.presentation.cargo_assignment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.BookingModel
import com.aeroclubcargo.warehouse.domain.model.BookingAssignmentRM
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CargoAssignmentViewModel @Inject constructor(private var repository: Repository) : ViewModel(){

    // loading information
    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _isLoading.value= isLoading
    }

    // container packages

    private val _assignedBookingModels = MutableStateFlow<List<BookingModel>?>(null)
    val assignedBookingModels = _assignedBookingModels.asStateFlow()

    private val _listOfPackages = MutableStateFlow<List<BookingModel>?>(null)
    val listOfPackages = _listOfPackages.asStateFlow()

    // uld pallet data
    private val _uldPalletVMValue = MutableStateFlow<ULDPalletVM?>(null)
    val uldPalletVMValue = _uldPalletVMValue.asStateFlow()

    fun setULDPalletVM (value: ULDPalletVM) {
        _uldPalletVMValue.value = value
    }
    // flight sector data
    private var _flightSectorId :String = ""

    fun setFlightSectorId(flightSectorId : String){
        _flightSectorId  = flightSectorId
        getCargoBookingAssignedCargoList()
    }

    private val _packageNameValue = MutableStateFlow<String>("")
    val packageNameValue = _packageNameValue.asStateFlow()

    fun setFlightULDValue(value: String) {
        _packageNameValue.value = value
    }

    fun getCargoBookingAssignedCargoList() {
        setLoading(true)
        viewModelScope.launch {
           var response = repository.getAssignedCargoList(flightScheduleSectorId = _flightSectorId, uldId = uldPalletVMValue.value!!.id)
            if(response.isSuccessful){
                _assignedBookingModels.value = response.body()
            }
            setLoading(false)
        }
    }

    fun getBookingListForFlightScheduleSector(){
        setLoading(true)
        viewModelScope.launch {
            var responsePackageList = repository.getBookingListPerFlightSchedule(_flightSectorId)
            if(responsePackageList.isSuccessful){
                _listOfPackages.value = responsePackageList.body()
//                _listOfPackages.value?.forEach { it.packageItems?.forEach { it.isAddedToCargo = true  } }
            }
            setLoading(false)
        }
    }

    fun assignCargoToUld(packageItemId: String){
        if(uldPalletVMValue.value != null) {
            setLoading(true)
            viewModelScope.launch {
                var response = repository.updatePackageULDContainerRM(BookingAssignmentRM(packageId = packageItemId, uldId = uldPalletVMValue.value!!.id))
                if(response.isSuccessful){
                    setLoading(false)
                    Log.e("assignCargoToUld() => ","${response.body()}")
                }
                setLoading(false)
            }.invokeOnCompletion {
                getBookingListForFlightScheduleSector()
            }
        }
    }

    fun removeCargoFromUld(packageItemId: String){
        if(uldPalletVMValue.value != null) {
            setLoading(true)
            viewModelScope.launch {
                var response = repository.removeBookedAssignment(BookingAssignmentRM(packageId = packageItemId, uldId = uldPalletVMValue.value!!.id))
                if(response.isSuccessful){
                    setLoading(false)
                    Log.e("assignCargoToUld() => ","${response.body()}")
                }
                setLoading(false)
            }.invokeOnCompletion {
                getBookingListForFlightScheduleSector()
            }
        }
    }




}