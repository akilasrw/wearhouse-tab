package com.aeroclubcargo.warehouse.presentation.scan_cargo

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanCargoViewModel @Inject constructor(private var repository: Repository) : ViewModel() {



}