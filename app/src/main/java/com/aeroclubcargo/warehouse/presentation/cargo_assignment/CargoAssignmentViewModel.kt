package com.aeroclubcargo.warehouse.presentation.cargo_assignment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.BookingModel
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.stream.Stream
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
    }

    private val _packageNameValue = MutableStateFlow<String>("")
    val packageNameValue = _packageNameValue.asStateFlow()

    fun setFlightULDValue(value: String) {
        _packageNameValue.value = value
    }

    fun getBookingListForFlightScheduleSector(){
        setLoading(true)
        viewModelScope.launch {
            var responsePackageList = repository.getBookingListPerFlightSchedule(_flightSectorId)
            if(responsePackageList.isSuccessful){
                _listOfPackages.value = responsePackageList.body()
            }
            setLoading(false)
        }
    }


}