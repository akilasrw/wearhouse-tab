package com.aeroclubcargo.warehouse.presentation.cargo_assignment

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CargoAssignmentViewModel @Inject constructor(private var repository: Repository) : ViewModel(){



}