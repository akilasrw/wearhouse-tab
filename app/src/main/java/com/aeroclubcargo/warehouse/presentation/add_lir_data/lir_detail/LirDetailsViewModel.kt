package com.aeroclubcargo.warehouse.presentation.add_lir_data.lir_detail

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleSectorUldPositionVM
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LirDetailsViewModel  @Inject constructor(private var repository: Repository) : ViewModel()  {

    private var cargoPositionList = mutableStateListOf<FlightScheduleSectorUldPositionVM>()
    private val _cargoPositionListFlow = MutableStateFlow(cargoPositionList)

    val cargoPositionsDetailListFlow: StateFlow<List<FlightScheduleSectorUldPositionVM>> get() = _cargoPositionListFlow

    fun getCargoPositionsDetails(flightScheduleModel: FlightScheduleModel?){
        if(flightScheduleModel?.aircraftLayoutId == null){
            return;
        }
        viewModelScope.launch {
           var response =  repository.getSummaryCargoPositions(aircraftLayoutId = flightScheduleModel.aircraftLayoutId!!)
            if(response.isSuccessful){
                if(!response.body().isNullOrEmpty()){
                    cargoPositionList.addAll(response.body()!!)
                }
            }
        }
    }


}