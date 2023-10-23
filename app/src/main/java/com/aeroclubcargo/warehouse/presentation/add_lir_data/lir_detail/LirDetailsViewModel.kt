package com.aeroclubcargo.warehouse.presentation.add_lir_data.lir_detail

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class LirDetailsViewModel  @Inject constructor(private var repository: Repository) : ViewModel()  {

    // TODO
    private var todoList = mutableStateListOf<Object>()
    private val _todoListFlow = MutableStateFlow(todoList)

    val flightScheduleListFlow: StateFlow<List<Object>> get() = _todoListFlow


}