package com.aeroclubcargo.warehouse.presentation.verify_booking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.model.PackageLineItem
import com.aeroclubcargo.warehouse.domain.model.UnitVM
import com.aeroclubcargo.warehouse.domain.model.UpdatePackageStatus
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.aeroclubcargo.warehouse.domain.use_case.accept_cargo.AcceptCargoUseCase
import com.aeroclubcargo.warehouse.domain.use_case.verify_booking.VerifyBookingUseCase
import com.aeroclubcargo.warehouse.presentation.components.DropDownModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VerifyBookingViewModel @Inject constructor(
    var verifyBookingUseCase: VerifyBookingUseCase,
    var acceptCargoUseCase: AcceptCargoUseCase,
    var repository: Repository
) :
    ViewModel() {

    var bookingId :String? = null

    // master data
    var weightUnitListTypes : List<UnitVM> = listOf()
    var lengthUnitListTypes : List<UnitVM> = listOf()


    fun getWeightUnitList() : List<UnitVM> {
        return weightUnitListTypes
    }

    fun getLengthUnitList() : List<UnitVM> {
        return lengthUnitListTypes
    }

    private fun loadUnits(){
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                val response = repository.getUnitList()
                if(response.isSuccessful){
                    val unitList = response.body() ?: listOf()
                    weightUnitListTypes = unitList.filter { it.unitType == 2 }
                    lengthUnitListTypes = unitList.filter { it.unitType == 1 }
                }
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

    // Drop Downs -----
    private val _packageItemCategory = MutableStateFlow(0)
    val packageItemCategory = _packageItemCategory.asStateFlow()

    private val _selectedVolumeUnit = MutableStateFlow(0)
    val selectedVolumeUnit = _selectedVolumeUnit.asStateFlow()

    private val _selectedWeightUnit = MutableStateFlow(0)
    val selectedWeightUnit = _selectedWeightUnit.asStateFlow()

    fun getCargoPackageItemCategories() : List<DropDownModel<Constants.PackageItemCategory>>{
        return listOf(
            DropDownModel(Constants.PackageItemCategory.None,Constants.PackageItemCategory.None.name),
            DropDownModel(Constants.PackageItemCategory.General,Constants.PackageItemCategory.General.name),
            DropDownModel(Constants.PackageItemCategory.Animal,Constants.PackageItemCategory.Animal.name),
            DropDownModel(Constants.PackageItemCategory.Artwork,Constants.PackageItemCategory.Artwork.name),
            DropDownModel(Constants.PackageItemCategory.Dgr,Constants.PackageItemCategory.Dgr.name),
        )
    }

    fun setPackageItemCategory(value: Int){
        _packageItemCategory.value = value
    }

    fun setVolumeUnit(value: Int){
        _selectedVolumeUnit.value = value
    }

    fun setWeightUnit(value: Int){
        _selectedWeightUnit.value = value
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

    fun setWeight(value: Double){
        _weightValue.value = value
    }

    fun setNoOfPakcages(value: Double){
        _noOfPackagesValue.value = value
    }

    fun setLineItem(item : PackageLineItem){
        isLoading.value = true
        packageLineItem = item
        if(packageLineItem == null){
            return
        }
        setHeight(packageLineItem!!.height)
        setWidth(packageLineItem!!.width)
        setLength(packageLineItem!!.length)
        setWeight(packageLineItem!!.weight)
        // TODO verify the data
        //_noOfPackagesValue.value = item.

        _packageItemCategory.value = packageLineItem!!.packageItemCategory
        _selectedVolumeUnit.value = getLengthUnitList().indexOfFirst { it.id == packageLineItem!!.volumeUnitId }
        _selectedWeightUnit.value = getWeightUnitList().indexOfFirst { it.id == packageLineItem!!.weightUnitId }
        isLoading.value = false

    }

    fun updatePackageItem(onComplete : () -> Unit){
        packageLineItem!!.height = _heightValue.value
        packageLineItem!!.weight = _weightValue.value
        packageLineItem!!.width = _widthValue.value
        packageLineItem!!.length = _lengthValue.value
        packageLineItem!!.packageItemCategory = _packageItemCategory.value

        try{
            packageLineItem!!.volumeUnitId = getLengthUnitList()[_selectedVolumeUnit.value].id
            packageLineItem!!.weightUnitId = getWeightUnitList()[_selectedWeightUnit.value].id
        }catch (e:Exception){
            print(e)
        }
        viewModelScope.launch {
            isLoading.value = true
            try {
                var response = repository.updatePackage(packageLineItem!!)
                if(response.isSuccessful){
                    onComplete()
                    getPackageDetails(bookingId!!)
                }
            }catch (e:Exception){
                e.localizedMessage?.let { Log.e("verifyBooking", it) }
            }
        }.invokeOnCompletion {  isLoading.value = false }

    }

    fun getPackageDetails(value : String) {
        bookingId = value
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getPackageDetails(packageRefNumber = value) //11591530001
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
                        packageItemStatus = Constants.PackageItemStatus.CargoReceived.ordinal)
                )
                isLoading.value = false
                getPackageDetails(bookingId!!)
            }catch (e:Exception){
                isLoading.value = false
                e.localizedMessage?.let { Log.e("verifyBooking", it) }
            }
        }
    }


}