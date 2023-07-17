package com.aeroclubcargo.warehouse.presentation.uld_master

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.aeroclubcargo.warehouse.domain.use_case.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class ULDMasterVIewModel @Inject constructor(var repository: Repository) : ViewModel() {


    private val _uldNumberValue = MutableStateFlow("")
    val uldNumber = _uldNumberValue.asStateFlow()


}