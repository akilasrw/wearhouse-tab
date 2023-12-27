package com.aeroclubcargo.warehouse.presentation.pdf_viewer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroclubcargo.warehouse.common.Constants.getCargoType
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PDFViewModel @Inject constructor(private var repository: Repository) : ViewModel() {


    val _packageDetails = MutableStateFlow<PackageDetails?>(null)
    val packageDetails = _packageDetails.asStateFlow()

    val _PDFHTMKDetails = MutableStateFlow<String?>(null)
    val PDFHTMKDetails = _PDFHTMKDetails.asStateFlow()

    val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading;
    }

    fun getCargoPackageDetails(awbNumber: String, onError: (String?) -> Unit) {
        if(_PDFHTMKDetails.value != null){
            return
        }else{
            viewModelScope.launch {
                setLoading(true)
                try {
                    var user = repository.getLoggedInUser().first()
                    val response =
                        repository.getCargoLookupDetails(
                            user!!.id,
                            awbNumber,
                            issIncludeFlightDetail = true,
                            isIncludeAWBDetail = true,
                            isIncludePackageDetail = true
                        )
                    setLoading(false)
                    if (response.isSuccessful) {
                        var responseModel = response.body()
                        if (responseModel == null) {
                            onError("failed to get awb data")
                        } else {
                            _packageDetails.value = responseModel
                            _PDFHTMKDetails.value = generateContent()
                        }
                    }

                } catch (e: Exception) {
                    setLoading(false)
                    Log.e("ULDAssignment Model", e.message.toString())
                }
            }
        }

    }

    private fun generateContent(): String {
        var packageModel  = packageDetails.value!!
        var grossWeight = 0.0
        var chargeableWeight = 0.0
        var totalCharge = 0.0
        packageModel.packageItems?.forEach {
            grossWeight += it.weight
            chargeableWeight += it.chargeableWeight
        }

        return """
            <!DOCTYPE html>
            <div *ngIf="awbPrintData" style="max-width: 1000px; border: 1px solid black; margin: 0 auto; border-bottom: 0;" id="aws" #aws>
        <div style="display: flex;  height: 110px; border-bottom: 1px solid black;">
            <div style="width: 50%; display: flex; border-right: 1px solid black; justify-content: space-between;">
                <div style="padding: 5px;">
                    <label>Shipper's Name and Address</label>
                    <div>
                        <label>${packageModel.awbInformation?.shipperName}<br /> ${packageModel.awbInformation?.shipperAddress}</label>
                    </div>
                </div>
                <div style="padding: 5px; border-left: 1px solid black; height: 50px; border-bottom: 1px solid black; width: 200px;">
                    <label>Shipper's Account Number</label>
                    <div>
                        <label>${packageModel.awbInformation?.shipperAccountNumber}</label>
                    </div>
                </div>
            </div>
            <div style="width: 50%;">
                <div style="padding: 0 0 5px 5px;">
                    <label>Not Negotiable</label>
                </div>
                <div style="padding: 0 0 5px 5px;">
                    <label style="font-size: 20px;">Air Waybill</label>
                    <label style="font-size: 20px;margin-left: 10px;">${packageModel.awbInformation?.awbTrackingNumber}</label>
                </div>
                <div style="padding: 0 0 5px 5px;">
                    <label>Issued by</label>
                </div>
                <div style="border-top: 1px solid black; padding: 0 5px;">
                    <label>Copies 1, 2 and 3 of this Air Waybill are originals and have the same validity</label>
                </div>
            </div>
        </div>
        <div style="display: flex;  height: 170px; border-bottom: 1px solid black;">
            <div style="width: 50%; display: flex; border-right: 1px solid black; justify-content: space-between;">
                <div style="padding: 5px;">
                    <label>Consignee's Name and Address</label>
                    <div>
                        <label>${packageModel.awbInformation?.consigneeName}<br /> ${packageModel.awbInformation?.consigneeAddress}</label>
                    </div>
                </div>
                <div style="padding: 5px; border-left: 1px solid black; height: 50px; border-bottom: 1px solid black; width: 200px; background-color: #e4e4e4;">
                    <label>Consignee's Account Number</label>
                    <div>
                        <label>${packageModel.awbInformation?.consigneeAccountNumber}</label>
                    </div>
                </div>
            </div>
            <div style="width: 50%;">
                <div style="padding: 5px;">
                    <label>It is agreed that the goods described herein are accepted in apparent good order and condition (except as noted) for carriage
                        SUBJECT TO THE CONDITIONS OF CONTRACT ON THE REVERSE HEREOF. ALL GOODS MAY BE CARRIED BY ANY OTHER MEANS INCLUDING ROAD OR ANY OTHER
                        CARRIER UNLESS SPECIFIC CONTRARY INSTRUCTIONS ARE GIVEN HEREON BY THE SHIPPER, AND SHIPPER AGREES THAT THE SHIPMENT MAY BE CARRIED VIA
                        INTERMEDIATE STOPPING PLACES WHICH THE CARRIER DEEMS APPROPRIATE. THE SHIPPER'S ATTENTION IS DRAWN TO THE NOTICE CONCERNING CARRIERS'
                        LIMITATION OF LIABILITY. Shipper may increase such limitation of liability by declaring a higher value for carriage and paying a
                        supplemental charge if requiered.</label>
                </div>
            </div>
        </div>
        <div style="display: flex;  height: 170px; border-bottom: 1px solid black;">
            <div style="width: 50%; border-right: 1px solid black;">
                <div style="padding: 5px; border-bottom: 1px solid black; height: 50px;">
                    <label>Issuing Carrier's Agent Name and City</label>
                    <div>
                        <label>${packageModel.awbInformation?.agentName}<br /> ${packageModel.awbInformation?.agentCity}</label>
                    </div>
                </div>
                <div style="border-bottom: 1px solid black; height: 40px; display: flex;">
                    <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                        <label>Agent's IATA Code</label>
                        <div>
                            <label>${packageModel.awbInformation?.agentAITACode}</label>
                        </div>
                    </div>
                    <div style="width: 50%; padding: 0 0 0 5px;">
                        <label>Account No.</label>
                        <div>
                            <label>${packageModel.awbInformation?.agentAccountNumber}</label>
                        </div>
                    </div>
                </div>
                <div style="padding: 5px; height: 50px;">
                    <label>Airport of Departure (Addr. of First Carrier) and Requested Routing</label>
                    <div>
                        <label>${packageModel?.destinationAirportCode}<br /> ${packageModel.awbInformation?.requestedRouting}</label>
                    </div>
                </div>
            </div>
            <div style="width: 50%;">
                <div style="height: 130px; padding: 0 5px;">
                    <label>Accounting Information</label>
                </div>
                <div>
                    <label>${packageModel.awbInformation?.agentAccountInformation}</label>
                </div>
                <div style="border-top: 1px solid black;">
        
                </div>
            </div>
        </div>
        <div style="display: flex;  height: 70px; border-bottom: 1px solid black;">
            <div style="width: 50%; border-right: 1px solid black; display: flex;">
                <div style="width: 10%; background-color: #e4e4e4; border-right: 1px solid black; padding-left: 5px;">
                    <label>To</label>
                </div>
                <div style="width: 50%; border-right: 1px solid black; display: flex; justify-content: space-between;">
                    <div style="padding: 0 5px;">
                        <label>By first Carrier</label>
                    </div>
                    <div style="height: 20px; border: 1px solid black; border-right: 0; border-top: 0; padding: 0 5px; background-color: #e4e4e4;">
                        <label>Routing And Destination</label>
                    </div>
                </div>
                <div style="width: 10%; background-color: #e4e4e4; border-right: 1px solid black; padding-left: 5px;">
                    <label>to</label>
                    <div>
                        <label>${packageModel.awbInformation?.routingAndDestinationTo}</label>
                    </div>
                </div>
                <div style="width: 10%; background-color: #e4e4e4; border-right: 1px solid black; padding-left: 5px;">
                    <label>by</label>
                    <div>
                        <label>${packageModel.awbInformation?.routingAndDestinationBy}</label>
                    </div>
                </div>
                <div style="width: 10%; background-color: #e4e4e4; border-right: 1px solid black; padding-left: 5px;">
                    <label>to</label>
                </div>
                <div style="width: 10%; background-color: #e4e4e4; padding-left: 5px;">
                    <label>by</label>
                </div>
            </div>
            <div style="width: 50%; display: flex;">
                <div style="width: 15%; border-right: 1px solid black; padding: 0 5px;">
                    <label>Currency</label>
                    <label>${packageModel.awbInformation?.currency?: "USD"}</label>
                </div>
                <div style="width: 10%; border-right: 1px solid black; padding: 0 5px; background-color: #e4e4e4;">
                    <label>CHGS Code</label>
                </div>
                <div style="display: flex; border-right: 1px solid black; width: 40%;">
                    <div style="width: 50%; border-right: 1px solid black; border-bottom: 1px solid black; height: 20px; padding: 0 0 0 5px;">
                        <label>WT/VAL</label>
                        <div style="display: flex; height: 48px;">
                            <div style="width: 50%; border-right: 1px solid black;">
                                <label>PPD</label>
                            </div>
                            <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                                <label>COLL</label>
                            </div>
                        </div>
                    </div>
                    <div style="width: 50%; border-bottom: 1px solid black; height: 20px; padding: 0 0 0 5px;">
                        <label>Other</label>
                        <div style="display: flex; height: 48px;">
                            <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                                <label>PPD</label>
                            </div>
                            <div style="padding: 0 5px;">
                                <label>COLL</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; width: 20%; padding: 0 5px;">
                    <label>Declared Value for Carriage</label>
                    <label>${packageModel.awbInformation?.declaredValueForCarriage}</label>
                </div>
                <div style="width: 10%; width: 20%; padding: 0 5px;">
                    <label>Declared Value for Customers</label>
                    <label>${packageModel.awbInformation?.declaredValueForCustomer}</label>
                </div>
            </div>
        </div>
        <div style="display: flex;  height: 79px; border-bottom: 1px solid black;">
            <div style="width: 50%; border-right: 1px solid black;">
                <div style="display: flex;">
                    <div style="width: 38%; border-right: 1px solid black; padding: 0 5px; height: 79px;">
                        <label>Airport of Destination</label>
                    </div>
                    <div style="display: flex; width: 80%;">
                        <div style="width: 33%; padding: 0 5px;">
                            <label>Flight/Date</label>
                        </div>
                        <div style="width: 45%; border: 1px solid black; border-top: 0; background-color: #e4e4e4; padding: 0 5px; height: 25px;">
                            <label>For Carrier Use only</label>
                        </div>
                        <div style="width: 33%; padding: 0 5px;">
                            <label>Flight/Date</label>
                            <label>${packageModel.awbInformation?.requestedFlightDate}</label>
                        </div>
                    </div>
                </div>
            </div>
            <div style="width: 50%;">
                <div style="display: flex;">
                    <div style="width: 50%; padding: 0 5px; border-right: 1px solid black;">
                        <label>Amount of Insurance</label>
                        <label>${packageModel.awbInformation?.amountOfInsurance}</label>
                    </div>
                    <div style="width: 50%; padding: 0 5px 5px 5px;">
                        <label>INSURANCE: If carrier offers insurance and such insurance is requested in accordance with the conditions thereof,
                            indicate amount to be insured in figures in box marked "Amount of Insurance". </label>
                    </div>
                </div>
            </div>
        </div>
        <div style="height: 100px; position: relative;">
            <div style="padding: 0 5px;">
                <label>Handling Information</label>
            </div>
            <div style="position: absolute; right: 0;width: 150px; text-align: center; border: 1px solid black; height: 50px; top: 50px;border-bottom: 0; border-right: 0;">
                <label>SCI</label>
            </div>
        </div>
        <div style="height: 280px; border-top: 1px solid black;">
            <div style="display: flex; height: 280px;">
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>No. of Pieces RCP</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>${packageModel.packageItems!!.size}</label></div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Gross Weight</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>${grossWeight}</label></div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>kg lb</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label></label></div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Rate Class</label>
                        <div style="border: 1px solid black; width: 80px; margin-left: 20px; margin-top: 13px; height: 247px;">
                            <label>Commodity Item No.</label>
                        </div>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div style="display: flex;">
                            <div style="width: 38%;">
                                <label></label>
                            </div>
                            <div style="padding: 0 5px;">
                                <label>${getCargoType(packageModel.packageItems?.first()?.packageItemCategory)}</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Chargeable Weight</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>${chargeableWeight}</label></div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Rate / Charge</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>${packageModel.awbInformation?.rateCharge}</label></div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Total</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>${totalCharge}</label></div>
                    </div>
                </div>
                <div style="width: 30%; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Nature and Quantity of Goods (incl.Dimensions or Volume)</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>${packageModel.awbInformation?.natureAndQualityOfGoods}</label></div>
                    </div>
                </div>
            </div>
        </div>
        <div style="display: flex; border-bottom: 1px solid black;">
            <div style="width: 50%; border-right: 1px solid black;">
                <div style="display: flex; height: 20px; border-bottom: 1px solid black; border-top: 1px solid black;">
                    <div style="width: 50%; border-right: 1px solid black; padding: 0 5px; background-color: #e4e4e4;">
                        <label>Prepaid</label>
                    </div>
                    <div style="width: 50%; padding: 0 5px; background-color: #e4e4e4;">
                        <label>Collect</label>
                    </div>
                </div>
                <div>
                    <div style="text-align: center; border: 1px solid black; width: fit-content; margin: 0 auto; padding: 0 20px; border-top: 0; background-color: #e4e4e4;">
                        <label>Weight Charge</label>
                    </div>
                    <div style="display: flex; height: 25px; border-bottom: 1px solid black; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                            <label></label>
                        </div>
                        <div style="width: 50%; padding: 0 5px;">
                            <label></label>
                        </div>
                    </div>
                </div>
                <div>
                    <div style="text-align: center; border: 1px solid black; width: fit-content; margin: 0 auto; padding: 0 20px; border-top: 0; background-color: #e4e4e4;">
                        <label>Valuation Charge</label>
                    </div>
                    <div style="display: flex; height: 25px; border-bottom: 1px solid black; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                            <label></label>
                        </div>
                        <div style="width: 50%; padding: 0 5px;">
                            <label></label>
                        </div>
                    </div>
                </div>
                <div>
                    <div style="text-align: center; border: 1px solid black; width: fit-content; margin: 0 auto; padding: 0 20px; border-top: 0; background-color: #e4e4e4;">
                        <label>Tax</label>
                    </div>
                    <div style="display: flex; height: 25px; border-bottom: 1px solid black; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                            <label></label>
                        </div>
                        <div style="width: 50%; padding: 0 5px;">
                            <label></label>
                        </div>
                    </div>
                </div>
                <div>
                    <div style="text-align: center; border: 1px solid black; width: fit-content; margin: 0 auto; padding: 0 20px; border-top: 0; background-color: #e4e4e4;">
                        <label>Total Other Charges Due Agent</label>
                    </div>
                    <div style="display: flex; height: 25px; border-bottom: 1px solid black; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                            <label></label>
                        </div>
                        <div style="width: 50%; padding: 0 5px;">
                            <label></label>
                        </div>
                    </div>
                </div>
                <div>
                    <div style="text-align: center; border: 1px solid black; width: fit-content; margin: 0 auto; padding: 0 20px; border-top: 0; background-color: #e4e4e4;">
                        <label>Total Other Charges Due Carrier</label>
                    </div>
                    <div style="display: flex; height: 25px; border-bottom: 1px solid black; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                            <label></label>
                        </div>
                        <div style="width: 50%; padding: 0 5px;">
                            <label></label>
                        </div>
                    </div>
                </div>
                <div style="display: flex; height: 20px; border-bottom: 1px solid black;">
                    <div style="width: 50%; border-right: 1px solid black; padding: 0 5px; background-color: #e4e4e4;"></div>
                    <div style="width: 50%; padding: 0 5px; background-color: #e4e4e4;"></div>
                </div>
                <div>
                    <div style="display: flex; height: 25px; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black;">
                            <div style="width: 110px; border: 1px solid black; border-top: 0; border-left: 0; padding: 0 5px; height: 25px; background-color: #e4e4e4;">
                                <label>Total Prepaid</label>
                            </div>
                        </div>
                        <div style="width: 50%; display: flex; justify-content: end;">
                            <div style="width: 110px; border: 1px solid black; border-top: 0; border-right: 0; padding: 0 5px; height: 25px; background-color: #e4e4e4;">
                                <label>Total Collect</label>
                            </div>
        
                        </div>
                    </div>
                    <div style="display: flex; height: 25px; border-bottom: 1px solid black; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                            <label></label>
                        </div>
                        <div style="width: 50%; padding: 0 5px;">
                            <label></label>
                        </div>
                    </div>
                </div>
                <div>
                    <div style="display: flex; height: 25px; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; display: flex; justify-content: center;">
                            <div style="width: fit-content; border: 1px solid black; border-top: 0; padding: 0 5px; height: 25px; background-color: #e4e4e4;">
                                <label>Currency Conversion Rates</label>
                            </div>
                        </div>
                        <div style="width: 50%; display: flex; justify-content: center;">
                            <div style="width: fit-content; border: 1px solid black; border-top: 0; padding: 0 5px; height: 25px; background-color: #e4e4e4;">
                                <label>CC Charges in Dest. Currency</label>
                            </div>
        
                        </div>
                    </div>
                    <div style="display: flex; height: 25px; border-bottom: 1px solid black; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                            <label></label>
                        </div>
                        <div style="width: 50%; padding: 0 5px;">
                            <label></label>
                        </div>
                    </div>
                </div>
                <div style="background-color: #e4e4e4;">
                    <div style="display: flex; height: 25px; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; display: flex; background-color: #e4e4e4;">
                            <div style="padding: 0 5px;">
                                <label>For Carriers Use only at Destination</label>
                            </div>
                        </div>
                        <div style="width: 50%; display: flex; justify-content: center; background-color: #e4e4e4;">
                            <div style="width: fit-content; border: 1px solid black; border-top: 0; padding: 0 5px; height: 25px; background-color: #e4e4e4;">
                                <label>Charges at Destination</label>
                            </div>
                        </div>
                    </div>
                    <div style="display: flex; height: 25px; border-bottom: 1px solid black; position: relative;">
                        <div style="width: 50%; border-right: 1px solid black; padding: 0 5px; background-color: #e4e4e4;">
                            <div></div>
                        </div>
                        <div style="width: 50%; padding: 0 5px;">
                            <label></label>
                        </div>
                    </div>
                </div>
            </div>
            <div style="width: 50%;">
                <div style="padding: 0 5px; border-bottom: 1px solid black; height: 84px;">
                    <label>Other Charges</label>
                </div>
                <div style="padding: 10px 5px 0 5px; border-bottom: 1px solid black;">
                    <label style="margin-bottom: 50px">
                        I hereby certify that the particulars on the face hereof are correct and that insofar as any part of the
                        consignment contains dangerous goods, I hereby certify that the contents of this consignment are fully and
                        accurately described above by proper shipping name and are classified, packaged, marked and labeled, and
                        in proper condition for carriage by air according to the applicable national governmental regulations.
                    </label>
                    <div style="text-align: center; border-top: 1px solid black; width: 80%; margin: 0 auto; padding-bottom: 20px;">
                        <label>Signature of Shipper or his Agent</label>
                    </div>
                </div>
                <div style="padding: 10px 5px 0 5px; border-bottom: 1px solid black;">
                    <div style="text-align: center; padding-top: 40px;">
                        <label></label>
                    </div>
                    <div style="text-align: center; border-top: 1px solid black; width: 95%; margin: 0 auto; padding-bottom: 20px;">
                        <label>Executed on (Date) at (Place) Signature of Issuing Carrier or its Agent</label>
                    </div>
                </div>
                <div style="width: 50%; background-color: #e4e4e4; border-right: 1px solid black; border-bottom: 1px solid black;">
                    <div style="display: flex; justify-content: center;">
                        <div style="border: 1px solid black; padding: 0 15px; border-top: 0; height: 25px;">
                            <label>Total Collect Charges</label>
                        </div>
                    </div>
                    <div style="padding: 5px 5px 0;">
                        <label></label>
                    </div>
                </div>
            </div>
        </div>
        </div>
    """
    }


}