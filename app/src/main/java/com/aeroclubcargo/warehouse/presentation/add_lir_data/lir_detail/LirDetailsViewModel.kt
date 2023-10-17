package com.aeroclubcargo.warehouse.presentation.add_lir_data.lir_detail

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LirDetailsViewModel  @Inject constructor(private var repository: Repository) : ViewModel()  {



}