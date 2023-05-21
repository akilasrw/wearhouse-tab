package com.aeroclubcargo.warehouse.presentation.cutoff_time

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.model.CutOffTimeModel
import com.aeroclubcargo.warehouse.domain.model.PackageListItem
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CutOffTimeViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    private val _flightNameValue = MutableStateFlow("")
    val flightNameValue = _flightNameValue.asStateFlow()

    fun onFlightNameChange(flightName: String){
        _flightNameValue.value = flightName
    }

    var cutOffTimeList: MutableLiveData<List<CutOffTimeModel>?> = MutableLiveData()

    init {
      var cuttOffTime =  CutOffTimeModel(
            flightNo = "23AADA", departureDate = "20/12/34", departureTime = "4:23 PM", cutOffTime = "2h", origin = "UAE", dest = "AAT", airCraftType = "AE234",
            totalBookVolume = 23.0, totalBookWeight = 23.0)
        var list = listOf<CutOffTimeModel>(cuttOffTime)
        cutOffTimeList.postValue(list)

    }


}