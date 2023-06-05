package com.aeroclubcargo.warehouse.presentation.verify_booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.aeroclubcargo.warehouse.domain.use_case.accept_cargo.AcceptCargoUseCase
import com.aeroclubcargo.warehouse.domain.use_case.verify_booking.VerifyBookingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VerifyBookingViewModel @Inject constructor(
    var verifyBookingUseCase: VerifyBookingUseCase,
    var acceptCargoUseCase: AcceptCargoUseCase,
    var repository: Repository
) :
    ViewModel() {



    var isLoading : MutableLiveData<Boolean> = MutableLiveData()
    var packageDetail: MutableLiveData<PackageDetails?> = MutableLiveData()
    fun getPackageDetails() {
        viewModelScope.launch {
            val response = repository.getPackageDetails(packageRefNumber = "11591530001")
            packageDetail.postValue(response)
        }
    }


    fun acceptCargo(){
        if (packageDetail.value == null){
            return
        }
        viewModelScope.launch {
            acceptCargoUseCase.invoke(packageDetail = packageDetail.value!!).onEach {

            }
        }
    }


}