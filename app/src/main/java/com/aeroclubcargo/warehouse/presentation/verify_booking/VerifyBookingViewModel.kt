package com.aeroclubcargo.warehouse.presentation.verify_booking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.model.PackageLineItem
import com.aeroclubcargo.warehouse.domain.model.UpdatePackageStatus
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.aeroclubcargo.warehouse.domain.use_case.accept_cargo.AcceptCargoUseCase
import com.aeroclubcargo.warehouse.domain.use_case.verify_booking.VerifyBookingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class VerifyBookingViewModel @Inject constructor(
    var verifyBookingUseCase: VerifyBookingUseCase,
    var acceptCargoUseCase: AcceptCargoUseCase,
    var repository: Repository
) :
    ViewModel() {


    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var packageDetail: MutableLiveData<PackageDetails?> = MutableLiveData()

    fun getPackageDetails() {
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getPackageDetails(packageRefNumber = "11591530001")
            packageDetail.postValue(response)
            isLoading.value = false
        }
    }


    fun acceptPackageItem(packageDetail: PackageLineItem) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = repository.updatePackageStatus(
                    UpdatePackageStatus(id = packageDetail.id,
                        packageItemStatus = Constants.PackageItemStatus.Accepted.ordinal)
                )
                isLoading.value = false
            }catch (e:Exception){
                isLoading.value = false
                Log.e("verifyBooking",e.localizedMessage)
            }


//            acceptCargoUseCase.invoke(packageDetail = packageDetail).onEach { result ->
//                when (result) {
//                    is Resource.Error -> result.let {
//                        isLoading.value = false
//                        Log.e("LoginViewModel", "${result.message}")
//                    }
//                    is Resource.Loading -> isLoading.value = true
//                    is Resource.Success -> {
//                        isLoading.value = false
//                        getPackageDetails()
//                    }
//                }
//            }
        }
    }


}