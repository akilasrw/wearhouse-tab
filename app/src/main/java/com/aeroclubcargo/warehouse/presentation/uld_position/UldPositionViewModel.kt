package com.aeroclubcargo.warehouse.presentation.uld_position

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.ServiceResponseStatus
import com.aeroclubcargo.warehouse.domain.model.CargoPositionVM
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.model.ULDCargoPositionMap
import com.aeroclubcargo.warehouse.domain.model.ULDCargoPositionRequest
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class UldPositionViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    private val _flightScheduleValue = MutableStateFlow<FlightScheduleModel?>(null)
    val flightScheduleValue = _flightScheduleValue.asStateFlow()


    fun setFlightSchedule(schedule: FlightScheduleModel?) {
        _flightScheduleValue.value = schedule
        getULDList()
    }

    private val _assignedULDListFlow = MutableStateFlow<List<ULDPalletVM>?>(null)
    var assignedUldListFlow = _assignedULDListFlow.asStateFlow()

    private val _cargoPositionListFlow = MutableStateFlow<List<CargoPositionVM>?>(null)
    var cargoPositionListFlow = _cargoPositionListFlow.asStateFlow()


    fun getCargoPositionList() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val response =
                    repository.getSummaryCargoPositionsBySector(flightScheduleSectorId = flightScheduleValue!!.value!!.id)
                if (response.isSuccessful) {
                    _cargoPositionListFlow.emit(response.body())
                    updateULDListValues()
                }
            } catch (e: Exception) {
                Log.e("ULDAssignment Model", e.message.toString())
            }
            setLoading(false)
        }
    }

    private fun updateULDListValues() {
        var assignList = _assignedULDListFlow.value
        assignList?.forEach { item ->
            if (item.cargoPositionID != null)
                item.cargoPositionVM =
                    _cargoPositionListFlow.value?.toList()?.first { it.id == item.cargoPositionID }

            cargoPositionListFlow.value?.first { it.id == item.cargoPositionID }?.isAssigned = true

        }
        setupContentCargoPackageDetails()
    }

    fun addPosition(uldPalletVM: ULDPalletVM, cargoPosition: CargoPositionVM) {
        viewModelScope.launch {
            if (uldPalletVM.cargoPositionVM != null) {
                val lastCargoPosition = uldPalletVM.cargoPositionVM
                cargoPositionListFlow.value?.first { it.id == lastCargoPosition!!.id }?.isAssigned =
                    false
            }
            _cargoPositionListFlow.value?.first { it.id == cargoPosition.id }?.isAssigned = true

            _assignedULDListFlow.value?.first { it.id == uldPalletVM.id }?.apply {
                cargoPositionVM = cargoPosition
            }
        }


    }

    fun clear(onComplete: (String?, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val request = mutableListOf<ULDCargoPositionRequest>()
                _assignedULDListFlow.value?.forEach {
                    if (it.cargoPositionVM != null)
                        request.add(ULDCargoPositionRequest(it.id, it.cargoPositionVM!!.id))
                }
                val response = repository.clearCargoPositions((request))
                if (response.isSuccessful) {
                    var responseModel = response.body()
                    if (responseModel?.statusCode == ServiceResponseStatus.Failed.ordinal) {
                        onComplete(null, responseModel.message)
                    } else {
                        onComplete("ULD Cleared Successfully", null)
                    }
                }

            } catch (e: Exception) {
                Log.e("ULDAssignment Model", e.message.toString())
            }
        }
    }

    fun saveALl(onComplete: (String?, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val request = mutableListOf<ULDCargoPositionRequest>()
                _assignedULDListFlow.value?.forEach {
                    if (it.cargoPositionVM != null)
                        request.add(ULDCargoPositionRequest(it.id, it.cargoPositionVM!!.id))
                }
                val response =
                    repository.addULDCargoPosition(
                        (request)
                    )
                if (response.isSuccessful) {
                    var responseModel = response.body()
                    if (responseModel?.statusCode == ServiceResponseStatus.Failed.ordinal) {
                        onComplete(null, responseModel.message)
                    } else {
                        onComplete("ULD Updated Successfully", null)
                    }
                }

            } catch (e: Exception) {
                Log.e("ULDAssignment Model", e.message.toString())
            }
        }
    }

//    fun getCargoPackageDetails(onComplete: (PackageDetails?, String?) -> Unit) {
//
//        viewModelScope.launch {
//            try {
//                var user = repository.getLoggedInUser().first()
//                val response =
//                    repository.getCargoLookupDetails(
//                        user!!.id!!,
//                        "99953729081", // flightScheduleValue replace from this model
//                        issIncludeFlightDetail = true,
//                        isIncludeAWBDetail = true,
//                        isIncludePackageDetail = true
//                    )
//                if (response.isSuccessful) {
//                    var responseModel = response.body()
//                    if (responseModel == null) {
//                        onComplete(null, "failed to get awb data")
//                    } else {
//                        onComplete(responseModel, null)
//                    }
//                }
//
//            } catch (e: Exception) {
//                Log.e("ULDAssignment Model", e.message.toString())
//            }
//        }
//    }

    fun getULDList() {
        viewModelScope.launch {
            setLoading(false)
            try {
                var response = repository.getPalletsByFlightScheduleId(
                    _flightScheduleValue!!.value!!.id!!,
                    null,
                    Constants.ULDLocateStatus.OnGround.ordinal
                )
                if (response.isSuccessful) {
                    var list = response.body()
                    if (list != null) {
                        var allULDPallets = (list)
                        var assignedList = allULDPallets.toList().filter { it.isAssigned }
                        _assignedULDListFlow.emit(assignedList)
                    }
                }
            } catch (e: Exception) {
                setLoading(false)
                Log.e("ULDAssignment Model", e.message.toString())
            }
            setLoading(false)
            getCargoPositionList()
        }
    }

    val _PDFHTMKDetails = MutableStateFlow<String?>(null)
    val PDFHTMKDetails = _PDFHTMKDetails.asStateFlow()

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading;
    }

    private fun setupContentCargoPackageDetails() {
        if (_PDFHTMKDetails.value != null) {
            return
        } else {
            viewModelScope.launch {
                setLoading(true)
                try {
                    setLoading(false)
                    _PDFHTMKDetails.value = generateContent()
                    Log.e("HTML CONTENT ::::", "${_PDFHTMKDetails.value}")
                } catch (e: Exception) {
                    setLoading(false)
                    Log.e("ULDAssignment Model", e.message.toString())
                }
            }
        }

    }

    private fun generateContent(): String {
        val uldPositionMap = mutableListOf<ULDCargoPositionMap>()
        var totalWeight = 0.0
        _assignedULDListFlow.value?.forEach { uld ->
            if (uld.cargoPositionID != null) {
//                var cargoPosition =
//                    _cargoPositionListFlow.value?.any { it.id == uld.cargoPositionID }
                totalWeight += uld.weight
                uldPositionMap.add(
                    ULDCargoPositionMap(
                        uldId = uld.cargoPositionVM!!.id,
                        cargoPositionName = uld.cargoPositionVM!!.name,
                        totalWeight = uld.weight,
                        maxWeight = uld.cargoPositionVM!!.maxWeight,
                        uldNumber = uld.serialNumber,
                    )
                )
            }
        }

        val tableContent = getTableContent(uldPositionMap)
        return """
          <!DOCTYPE html>
          <html lang="en">
          <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <title>Load Distribution</title>
              <style>
                  body {
                      font-family: Arial, sans-serif;
                  }

                  .lir-info {
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 10px;
                  }

                  .table-content {
                      margin: 10px 0;
                  }

                  table {
                      width: 100%;
                      border-collapse: collapse;
                      margin-bottom: 10px;
                  }

                  th, td {
                      border: 1px solid #ddd;
                      padding: 8px;
                      text-align: left;
                  }

                  th {
                      background-color: #f2f2f2;
                  }

                  .text-center {
                      text-align: center;
                      margin-top: 20px;
                  }

                  h2, h3 {
                      margin-bottom: 10px;
                  }

                  .col-md-5 {
                      width: 48%;
                      float: left;
                      margin-right: 2%;
                  }

                  .offset-md-2 {
                      margin-left: 52%;
                  }

                  .pt-3 {
                      margin-top: 30px;
                  }

                  .disclaimer {
                      margin-top: 20px;
                  }
              </style>
          </head>

          <body>
          <div class="text-center">
              <div style="display: flex; flex-direction: column; align-items: center">
                  <h1 style="margin-bottom: -1px;margin-top: 0px;">Loading Instruction Report</h1>
                  <h3 style="margin-top: 1px;">${
            Constants.AircraftTypes.getAirCraftType(
                flightScheduleValue?.value?.aircraftType
            )
        }</h3>
              </div>
          </div>
              <div class="lir-info">
                  <div class="table-content">
                      <table>
                          <tbody>
                              <tr>

                                  <td style="padding: 0;">
                                      <table>
                                          <tbody>
                                              <tr>
                                                  <td style="text-transform: uppercase;">Flight Number: ${flightScheduleValue.value?.flightNumber ?: "-"}</td>
                                                  <td style="text-transform: uppercase;">Date: ${
            flightScheduleValue.value?.scheduledDepartureDateTime?.split(
                "T"
            )?.last() ?: "-"
        }</td>
                                              </tr>
                                              <tr>
                                                  <td style="text-transform: uppercase;">Station: ${flightScheduleValue.value?.originAirportCode}</td>
                                                  <td style="text-transform: uppercase;">Destination: ${flightScheduleValue.value?.destinationAirportCode}</td>
                                              </tr>
                                          </tbody>
                                      </table>
                                  </td>
                                  <td style="padding-bottom: 30px;">
                                      <div style="text-transform: uppercase;">Prepared By: </div>
                                      <div>Signature of Load Master / Load Planner</div>
                                  </td>
                                  <td style="padding-bottom: 50px;">
                                      <div style="text-transform: uppercase;">Special Instruction / Report</div>
                                  </td>
                              </tr>
                          </tbody>
                      </table>
                  </div>
              </div>

              <div class="text-center">
                  <h2>Load Distribution Main Deck Compartment</h2>
              </div>
                $tableContent
              </div>
              <div class="text-center pt-5">
                  <h2>Load Distribution Lower Hold</h2>
              </div>

              <div class="col-md-5">
                  <div class="pt-3">
                      <h3>Forward Hold</h3>
                  </div>
                  <div class="table-content">
                      <table>
                          <thead>
                              <th></th>
                              <th>B1</th>
                              <th>B1B</th>
                              <th>B2</th>
                          </thead>
                          <tbody>
                              <tr>
                                  <td class="lable">Max Weight Limit</td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                              </tr>
                              <tr>
                                <td class="lable">CART NO</td>
                                <td></td>
                                <td></td>
                                <td></td>
                              </tr>
                              <tr>
                                <td class="lable">WEIGHT in Kgs</td>
                                <td></td>
                                <td></td>
                                <td></td>
                              </tr>
                              <tr>
                                <td class="lable">DESTINATION</td>
                                <td></td>
                                <td></td>
                                <td></td>
                              </tr>
                              <tr>
                                <td class="lable">REMARKS</td>
                                <td></td>
                                <td></td>
                                <td></td>
                             </tr>
                              <!-- Add more rows with forward hold data if needed -->
                          </tbody>
                      </table>
                  </div>
                  <div>
                      <div class="disclaimer">
                          <p style = "border: 1px solid #f2f2f2;padding: 10px;">
                              Aircraft has been loaded by excetute.  
                          </p>
                      </div>
                      <div class="disclaimer">
                            <p style = "border: 1px solid #f2f2f2;padding: 10px;">
                               Total Gross : ${totalWeight} kg
                            </p>
                       </div>
                  </div>
              </div>

              <div class="col-md-5">
                  <div class="pt-3">
                      <h3>AFT Hold</h3>
                  </div>
                  <div class="table-content">
                      <table>
                          <thead>
                              <th></th>
                              <th>B3</th>
                              <th>B4</th>
                              <th>B5</th>
                          </thead>
                          <tbody>
                              <tr>
                                  <td class="lable">Max Weight Limit</td>
                                  <td></td>
                                  <td></td>
                                  <td></td>
                              </tr>
                              <tr>
                                <td class="lable">CART NO</td>
                                <td></td>
                                <td></td>
                                <td></td>
                              </tr>
                              <tr>
                                <td class="lable">WEIGHT in Kgs</td>
                                <td></td>
                                <td></td>
                                <td></td>
                              </tr>
                              <tr>
                                <td class="lable">DESTINATION</td>
                                <td></td>
                                <td></td>
                                <td></td>
                              </tr>
                              <tr>
                                <td class="lable">REMARKS</td>
                                <td></td>
                                <td></td>
                                <td></td>
                             </tr>
                              <!-- Add more rows with AFT hold data if needed -->
                          </tbody>
                      </table>
                  </div>
                  <div>
                      <div class="disclaimer">
                          <p style = "border: 1px solid #f2f2f2;padding: 10px;">
                            Aircraft has been loaded by excetute.  
                          </p>
                      </div>
                  </div>
              </div>

              <!-- Add any additional content or scripts here -->

          </body>

          </html>
        """.trimIndent()
    }

}


fun getTableContent(uldPositionMap: List<ULDCargoPositionMap>): String {
    val tableContent = StringBuilder()

    // Add table start
    tableContent.append("<table>\n<thead>\n<tr>\n<th></th>\n")

    // Add position headers
    for (uldPosition in uldPositionMap) {
        tableContent.append("<th>P${uldPosition.cargoPositionName}</th>\n")
    }

    // Add more position headers if needed
    tableContent.append("</tr>\n</thead>\n<tbody>\n")

    // Add Max Weight Limit row
    tableContent.append("<tr>\n<td class=\"label\">Max Weight Limit</td>\n")
    for (uldPosition in uldPositionMap) {
        tableContent.append("<td>${uldPosition.maxWeight} Kg</td>\n")
    }
    // Add more max weight data if needed
    tableContent.append("</tr>\n")

    // Add ULD Number row
    tableContent.append("<tr>\n<td>ULD Number</td>\n")
    for (uldPosition in uldPositionMap) {
        tableContent.append("<td> ${uldPosition.uldNumber}</td>\n")
    }
    // Add more ULD numbers if needed
    tableContent.append("</tr>\n")

    // Add ULD Number row
    tableContent.append("<tr>\n<td>GROSS WEIGHT</td>\n")
    for (uldPosition in uldPositionMap) {
        tableContent.append("<td> ${uldPosition.totalWeight} Kg</td>\n")
    }
    // Add more ULD numbers if needed
    tableContent.append("</tr>\n")

    // Add ULD Number row
    tableContent.append("<tr>\n<td>DESTINATION</td>\n")
    for (uldPosition in uldPositionMap) {
        tableContent.append("<td> ${uldPosition.destination ?: ""}</td>\n")
    }
    // Add more ULD numbers if needed
    tableContent.append("</tr>\n")

    // Add ULD Number row
    tableContent.append("<tr>\n<td>REMARKS</td>\n")
    for (uldPosition in uldPositionMap) {
        tableContent.append("<td></td>\n")
    }
    // Add more ULD numbers if needed
    tableContent.append("</tr>\n")

    // Add more rows with cargo data if needed

    // Add table end
    tableContent.append("</tbody>\n</table>")

    return tableContent.toString()
}
// TODO add
// add layout type

//ULD NUMBER
//GROSS WEIGHT
//DESTINATION
// REMARKS

// add separate box for total gross weight

//returns
//              <div class="table-content">
//                  <table>
//                      <thead>
//
//                          <th></th>
//                          <th>P1</th>
//                          <th>P2</th>
//                          <!-- Add more position headers if needed -->
//                      </thead>
//                      <tbody>
//                          <tr>
//                              <td class="lable">Max Weight Limit</td>
//                              <td>5000 Kgs</td>
//                              <td>7000 Kgs</td>
//                              <!-- Add more max weight data if needed -->
//                          </tr>
//                          <tr>
//                              <td>ULD Number</td>
//                              <td>ULD123</td>
//                              <td>ULD456</td>
//                              <!-- Add more ULD numbers if needed -->
//                          </tr>
//                          <!-- Add more rows with cargo data if needed -->
//                      </tbody>
//                  </table>

