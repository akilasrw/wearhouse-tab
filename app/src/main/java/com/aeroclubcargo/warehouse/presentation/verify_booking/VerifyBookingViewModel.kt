package com.aeroclubcargo.warehouse.presentation.verify_booking

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.use_case.verify_booking.VerifyBookingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class VerifyBookingViewModel @Inject constructor(var verifyBookingUseCase: VerifyBookingUseCase) :
    ViewModel() {


}