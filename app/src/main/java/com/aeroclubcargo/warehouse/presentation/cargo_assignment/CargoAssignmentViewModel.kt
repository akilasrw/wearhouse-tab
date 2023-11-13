package com.aeroclubcargo.warehouse.presentation.cargo_assignment

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CargoAssignmentViewModel @Inject constructor(private var repository: Repository) : ViewModel(){

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _isLoading.value= isLoading
    }

    private val _uldPalletVMValue = MutableStateFlow<ULDPalletVM?>(null)
    val uldPalletVMValue = _uldPalletVMValue.asStateFlow()
    fun setULDPalletVM (value: ULDPalletVM) {
        _uldPalletVMValue.value = value
    }



}