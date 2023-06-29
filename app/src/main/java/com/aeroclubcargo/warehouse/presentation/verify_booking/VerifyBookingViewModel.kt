package com.aeroclubcargo.warehouse.presentation.verify_booking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.model.PackageLineItem
import com.aeroclubcargo.warehouse.domain.model.UnitVM
import com.aeroclubcargo.warehouse.domain.model.UpdatePackageStatus
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.aeroclubcargo.warehouse.domain.use_case.accept_cargo.AcceptCargoUseCase
import com.aeroclubcargo.warehouse.domain.use_case.verify_booking.VerifyBookingUseCase
import com.aeroclubcargo.warehouse.presentation.components.DropDownModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    // master data
     var unitList : List<UnitVM> = listOf()


    fun getWeightUnitList() : List<UnitVM> {
        return unitList.filter { it.unitType == 2 }
    }

    fun getLengthUnitList() : List<UnitVM> {
        return unitList.filter { it.unitType == 1 }
    }

    private fun loadUnits(){
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                val response = repository.getUnitList()
                if(response.isSuccessful)
                    unitList = response.body() ?: listOf()
            }catch (e:Exception){
                Log.e("VerifyBookingViewModel",e.toString())
            }
            isLoading.postValue(false)
        }
    }


    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var packageDetail: MutableLiveData<PackageDetails?> = MutableLiveData()

    var packageLineItem : PackageLineItem? = null

    private val _heightValue = MutableStateFlow(0.0)
    val heightValue = _heightValue.asStateFlow()

    private val _widthValue = MutableStateFlow(0.0)
    val widthValue = _widthValue.asStateFlow()

    private val _lengthValue = MutableStateFlow(0.0)
    val lengthValue = _lengthValue.asStateFlow()

    private val _weightValue = MutableStateFlow(0.0)
    val weightValue = _weightValue.asStateFlow()

    private val _noOfPackagesValue = MutableStateFlow(0.0)
    val noOfPackagesValue = _noOfPackagesValue.asStateFlow()

    fun getCargoPackageItemCategories() : List<DropDownModel<Constants.PackageItemCategory>>{
        return listOf(
            DropDownModel(Constants.PackageItemCategory.None,Constants.PackageItemCategory.None.name),
            DropDownModel(Constants.PackageItemCategory.General,Constants.PackageItemCategory.General.name),
            DropDownModel(Constants.PackageItemCategory.Animal,Constants.PackageItemCategory.Animal.name),
            DropDownModel(Constants.PackageItemCategory.Artwork,Constants.PackageItemCategory.Artwork.name),
            DropDownModel(Constants.PackageItemCategory.Dgr,Constants.PackageItemCategory.Dgr.name),
        )
    }

    fun setHeight(value: Double){
        _heightValue.value = value
    }
    fun setWidth(value: Double){
        _widthValue.value = value
    }
    fun setLength(value: Double){
        _lengthValue.value = value
    }

    fun setweight(value: Double){
        _weightValue.value = value
    }

    fun setNoOfPakcages(value: Double){
        _noOfPackagesValue.value = value
    }

    fun setLineItem(item : PackageLineItem){
        packageLineItem = item
        setHeight(packageLineItem!!.height)
        setWidth(packageLineItem!!.width)
        setLength(packageLineItem!!.length)
        setweight(packageLineItem!!.weight)
    }

    fun getPackageDetails() {
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getPackageDetails(packageRefNumber = "11591530001")
            packageDetail.postValue(response)
            isLoading.value = false
            loadUnits()
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