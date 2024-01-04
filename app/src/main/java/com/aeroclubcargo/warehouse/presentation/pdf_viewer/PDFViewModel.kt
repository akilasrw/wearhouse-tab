//package com.aeroclubcargo.warehouse.presentation.pdf_viewer
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.aeroclubcargo.warehouse.common.Constants.getCargoType
//import com.aeroclubcargo.warehouse.domain.model.PackageDetails
//import com.aeroclubcargo.warehouse.domain.repository.Repository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class PDFViewModel @Inject constructor(private var repository: Repository) : ViewModel() {
//
//    val _packageDetails = MutableStateFlow<PackageDetails?>(null)
//    val packageDetails = _packageDetails.asStateFlow()
//
//    val _PDFHTMKDetails = MutableStateFlow<String?>(null)
//    val PDFHTMKDetails = _PDFHTMKDetails.asStateFlow()
//
//    val _isLoading = MutableStateFlow(false)
//    val isLoading = _isLoading.asStateFlow()
//
//    fun setLoading(isLoading: Boolean) {
//        _isLoading.value = isLoading;
//    }
//
//    fun getCargoPackageDetails(awbNumber: String, onError: (String?) -> Unit) {
//        if(_PDFHTMKDetails.value != null){
//            return
//        }else{
//            viewModelScope.launch {
//                setLoading(true)
//                try {
//                    var user = repository.getLoggedInUser().first()
//                    val response =
//                        repository.getCargoLookupDetails(
//                            user!!.id,
//                            awbNumber,
//                            issIncludeFlightDetail = true,
//                            isIncludeAWBDetail = true,
//                            isIncludePackageDetail = true
//                        )
//                    setLoading(false)
//                    if (response.isSuccessful) {
//                        var responseModel = response.body()
//                        if (responseModel == null) {
//                            onError("failed to get awb data")
//                        } else {
//                            _packageDetails.value = responseModel
//                            _PDFHTMKDetails.value = generateContent()
//                        }
//                    }
//
//                } catch (e: Exception) {
//                    setLoading(false)
//                    Log.e("ULDAssignment Model", e.message.toString())
//                }
//            }
//        }
//
//    }
//
//    private fun generateContent(): String {
//        var packageModel  = packageDetails.value!!
//        var grossWeight = 0.0
//        var chargeableWeight = 0.0
//        var totalCharge = 0.0
//        packageModel.packageItems?.forEach {
//            grossWeight += it.weight
//            chargeableWeight += it.chargeableWeight
//        }
//
//        return """
//          <!DOCTYPE html>
//          <html lang="en">
//          <head>
//              <meta charset="UTF-8">
//              <meta name="viewport" content="width=device-width, initial-scale=1.0">
//              <title>Load Distribution</title>
//              <style>
//                  body {
//                      font-family: Arial, sans-serif;
//                  }
//
//                  .lir-info {
//                      display: flex;
//                      justify-content: space-between;
//                      margin-bottom: 10px;
//                  }
//
//                  .table-content {
//                      margin: 10px 0;
//                  }
//
//                  table {
//                      width: 100%;
//                      border-collapse: collapse;
//                      margin-bottom: 10px;
//                  }
//
//                  th, td {
//                      border: 1px solid #ddd;
//                      padding: 8px;
//                      text-align: left;
//                  }
//
//                  th {
//                      background-color: #f2f2f2;
//                  }
//
//                  .text-center {
//                      text-align: center;
//                      margin-top: 20px;
//                  }
//
//                  h2, h3 {
//                      margin-bottom: 10px;
//                  }
//
//                  .col-md-5 {
//                      width: 48%;
//                      float: left;
//                      margin-right: 2%;
//                  }
//
//                  .offset-md-2 {
//                      margin-left: 52%;
//                  }
//
//                  .pt-3 {
//                      margin-top: 30px;
//                  }
//
//                  .disclaimer {
//                      margin-top: 20px;
//                  }
//              </style>
//          </head>
//
//          <body>
//              <div class="lir-info">
//                  <div class="table-content">
//                      <div style="margin-left: 42%; display: flex; flex-direction: column; align-items: center">
//                          <h1 style="margin-bottom: -1px;margin-top: 0px;">Loading Instruction Report</h1>
//                          <h3 style="margin-top: 1px;">Boeing 737-400 / PK-MYJ</h3>
//                      </div>
//
//                      <table>
//                          <tbody>
//                              <tr>
//
//                                  <td style="padding: 0;">
//                                      <table>
//                                          <tbody>
//                                              <tr>
//                                                  <td style="text-transform: uppercase;">Flight Number: FL123</td>
//                                                  <td style="text-transform: uppercase;">Date: 2023-12-28</td>
//                                              </tr>
//                                              <tr>
//                                                  <td style="text-transform: uppercase;">Station: ABC</td>
//                                                  <td style="text-transform: uppercase;">Destination: XYZ</td>
//                                              </tr>
//                                          </tbody>
//                                      </table>
//                                  </td>
//                                  <td style="padding-bottom: 30px;">
//                                      <div style="text-transform: uppercase;">Prepared By: John Doe</div>
//                                      <div>Signature of Load Master / Load Planner</div>
//                                  </td>
//                                  <td style="padding-bottom: 50px;">
//                                      <div style="text-transform: uppercase;">Special Instruction / Report</div>
//                                  </td>
//                              </tr>
//                          </tbody>
//                      </table>
//                  </div>
//              </div>
//
//              <div class="text-center">
//                  <h2>Load Distribution Main Deck Compartment</h2>
//              </div>
//
//              <div class="table-content">
//                  <table>
//                      <thead>
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
//              </div>
//
//              <div class="text-center pt-5">
//                  <h2>Load Distribution Lower Hold</h2>
//              </div>
//
//              <div class="col-md-5">
//                  <div class="pt-3">
//                      <h3>Forward Hold</h3>
//                  </div>
//                  <div class="table-content">
//                      <table>
//                          <thead>
//                              <th></th>
//                              <th>B1</th>
//                              <th>B1B</th>
//                              <th>B2</th>
//                          </thead>
//                          <tbody>
//                              <tr>
//                                  <td class="lable">Max Weight Limit</td>
//                                  <td>762 Kgs</td>
//                                  <td>483 Kgs</td>
//                                  <td>2059 Kgs</td>
//                              </tr>
//                              <!-- Add more rows with forward hold data if needed -->
//                          </tbody>
//                      </table>
//                  </div>
//                  <div>
//                      <div class="disclaimer">
//                          <p>
//                              Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the.
//                          </p>
//                      </div>
//                  </div>
//              </div>
//
//              <div class="col-md-5">
//                  <div class="pt-3">
//                      <h3>AFT Hold</h3>
//                  </div>
//                  <div class="table-content">
//                      <table>
//                          <thead>
//                              <th></th>
//                              <th>B3</th>
//                              <th>B4</th>
//                              <th>B5</th>
//                          </thead>
//                          <tbody>
//                              <tr>
//                                  <td class="lable">Max Weight Limit</td>
//                                  <td>3062 Kgs</td>
//                                  <td>414 Kgs</td>
//                                  <td>735 Kgs</td>
//                              </tr>
//                              <!-- Add more rows with AFT hold data if needed -->
//                          </tbody>
//                      </table>
//                  </div>
//                  <div>
//                      <div class="disclaimer">
//                          <p>
//                              Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the.
//                          </p>
//                      </div>
//                  </div>
//              </div>
//
//              <!-- Add any additional content or scripts here -->
//
//          </body>
//
//          </html>
//        """.trimIndent()
//    }
//
//
//}