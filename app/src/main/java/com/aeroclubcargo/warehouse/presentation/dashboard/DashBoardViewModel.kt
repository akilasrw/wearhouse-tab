package com.aeroclubcargo.warehouse.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.model.CargoBooking
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private var repository: Repository) : ViewModel() {


    var cargoList: MutableLiveData<List<CargoBooking>?> = MutableLiveData()


    init {
        getCargoBookingList(1,10)
    }

    fun getCargoBookingList(pageIndex: Int, pageSize: Int) {
        viewModelScope.launch {
            var paginateResponse =
                repository.getCargoBooking(pageSize = pageSize, pageIndex = pageIndex)
            cargoList.postValue(paginateResponse.data)
        }
    }


}