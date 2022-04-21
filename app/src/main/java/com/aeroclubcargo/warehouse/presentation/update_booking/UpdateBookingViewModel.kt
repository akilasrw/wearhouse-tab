package com.aeroclubcargo.warehouse.presentation.update_booking

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateBookingViewModel @Inject constructor(var repository: Repository) : ViewModel() {


}