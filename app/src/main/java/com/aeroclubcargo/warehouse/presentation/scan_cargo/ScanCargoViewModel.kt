package com.aeroclubcargo.warehouse.presentation.scan_cargo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanCargoViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun setLoading( isLoading: Boolean){
        _isLoading.value= isLoading;
    }

    private val _awbNameValue = MutableStateFlow("")
    val awbNameValue = _awbNameValue.asStateFlow()

    fun setAWBNumber(awbNumber:String){
        _awbNameValue.value = awbNumber
    }

    fun findAWBNumber(){
        viewModelScope.launch {
            // TODO implement API
//            setLoading(true)
//
//            setLoading(false)
        }
    }


}