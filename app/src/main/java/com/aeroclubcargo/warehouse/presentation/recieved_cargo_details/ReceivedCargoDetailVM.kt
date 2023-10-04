package com.aeroclubcargo.warehouse.presentation.recieved_cargo_details

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReceivedCargoDetailVM @Inject constructor(var repository: Repository) : ViewModel() {
    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setLoading( isLoading: Boolean){
        _isLoading.value= isLoading;
    }

    var pageIndex = 1
    val pageSize = 10

    private val _listOfPackages = MutableStateFlow<List<String>?>(listOf("as","Aasg","ahe4"))
    val listOfPackages = _listOfPackages.asStateFlow()


}