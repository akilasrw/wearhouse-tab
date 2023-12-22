package com.aeroclubcargo.warehouse.presentation.pdf_viewer

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.presentation.uld_position.GetULTMasterUI
import com.aeroclubcargo.warehouse.theme.BlueLight
import com.aeroclubcargo.warehouse.theme.BlueLight3

const val htmlContent = """
            <!DOCTYPE html>
            <div *ngIf="awbPrintData" style="max-width: 1000px; border: 1px solid black; margin: 0 auto; border-bottom: 0;" id="aws" #aws>
        <div style="display: flex;  height: 110px; border-bottom: 1px solid black;">
            <div style="width: 50%; display: flex; border-right: 1px solid black; justify-content: space-between;">
                <div style="padding: 5px;">
                    <label>Shipper's Name and Address</label>
                    <div>
                        <label>{{awbPrintData?.shipperName}}<br /> {{awbPrintData?.shipperAddress}}</label>
                    </div>
                </div>
                <div style="padding: 5px; border-left: 1px solid black; height: 50px; border-bottom: 1px solid black; width: 200px;">
                    <label>Shipper's Account Number</label>
                    <div>
                        <label>{{awbPrintData?.shipperAccountNumber}}</label>
                    </div>
                </div>
            </div>
            <div style="width: 50%;">
                <div style="padding: 0 0 5px 5px;">
                    <label>Not Negotiable</label>
                </div>
                <div style="padding: 0 0 5px 5px;">
                    <label style="font-size: 20px;">Air Waybill</label>
                    <label style="font-size: 20px;margin-left: 10px;">{{awbPrintData?.awbTrackingNumber}}</label>
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
                        <label>{{awbPrintData?.consigneeName}}<br /> {{awbPrintData?.consigneeAddress}}</label>
                    </div>
                </div>
                <div style="padding: 5px; border-left: 1px solid black; height: 50px; border-bottom: 1px solid black; width: 200px; background-color: #e4e4e4;">
                    <label>Consignee's Account Number</label>
                    <div>
                        <label>{{awbPrintData?.consigneeAccountNumber}}</label>
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
                        <label>{{awbPrintData?.agentName}}<br /> {{awbPrintData?.agentCity}}</label>
                    </div>
                </div>
                <div style="border-bottom: 1px solid black; height: 40px; display: flex;">
                    <div style="width: 50%; border-right: 1px solid black; padding: 0 5px;">
                        <label>Agent's IATA Code</label>
                        <div>
                            <label>{{awbPrintData?.agentAITACode}}</label>
                        </div>
                    </div>
                    <div style="width: 50%; padding: 0 0 0 5px;">
                        <label>Account No.</label>
                        <div>
                            <label>{{awbPrintData?.agentAccountNumber}}</label>
                        </div>
                    </div>
                </div>
                <div style="padding: 5px; height: 50px;">
                    <label>Airport of Departure (Addr. of First Carrier) and Requested Routing</label>
                    <div>
                        <label>{{awbPrintData?.destinationAirportCode}}<br /> {{awbPrintData?.requestedRouting}}</label>
                    </div>
                </div>
            </div>
            <div style="width: 50%;">
                <div style="height: 130px; padding: 0 5px;">
                    <label>Accounting Information</label>
                </div>
                <div>
                    <label>{{awbPrintData?.agentAccountInformation}}</label>
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
                        <label>{{awbPrintData?.routingAndDestinationTo}}</label>
                    </div>
                </div>
                <div style="width: 10%; background-color: #e4e4e4; border-right: 1px solid black; padding-left: 5px;">
                    <label>by</label>
                    <div>
                        <label>{{awbPrintData?.routingAndDestinationBy}}</label>
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
                    <label>{{awbPrintData?.Currency}}</label>
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
                    <label>{{awbPrintData?.declaredValueForCarriage}}</label>
                </div>
                <div style="width: 10%; width: 20%; padding: 0 5px;">
                    <label>Declared Value for Customers</label>
                    <label>{{awbPrintData?.declaredValueForCustomer}}</label>
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
                            <label>{{awbPrintData.requestedFlightDate | date : 'M/d/yyyy'}}</label>
                        </div>
                    </div>
                </div>
            </div>
            <div style="width: 50%;">
                <div style="display: flex;">
                    <div style="width: 50%; padding: 0 5px; border-right: 1px solid black;">
                        <label>Amount of Insurance</label>
                        <label>{{awbPrintData?.amountOfInsurance}}</label>
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
                        <div><label>{{awbPrintData?.noOfPieces}}</label></div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Gross Weight</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>{{awbPrintData?.grossWeight}}</label></div>
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
                                <label>{{getCargoType(awbPrintData?.packageItemCategory)}}</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Chargeable Weight</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>{{awbPrintData?.chargeableWeight}}</label></div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Rate / Charge</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>{{awbPrintData?.rateCharge}}</label></div>
                    </div>
                </div>
                <div style="width: 10%; border-right: 1px solid black; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Total</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>{{awbPrintData?.totalCharge}}</label></div>
                    </div>
                </div>
                <div style="width: 30%; border-bottom: 1px solid black; text-align: center;">
                    <div style="border-bottom: 1px solid black; height: 70px;">
                        <label>Nature and Quantity of Goods (incl.Dimensions or Volume)</label>
                    </div>
                    <div style="text-align: left; padding: 0 5px;">
                        <div><label>{{awbPrintData?.natureAndQualityOfGoods}}</label></div>
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

@Composable
fun RenderHTMLInWebView(navController: NavController,) {
    val context = LocalContext.current
    var webView: WebView? = null

    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                exportAsPdf(webView = webView, context = context)
            },
            modifier = Modifier.padding(16.dp),
            backgroundColor = BlueLight
        ) {
            Icon(
                painter = painterResource(  R.drawable.twotone_local_printshop_24),
                contentDescription = "delete",
                tint = BlueLight3
            )
        }
    }) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
            ) {
                AndroidView(
                    factory = { context ->
                        WebView(context)
                            .apply {
                                webViewClient = WebViewClient()
                                loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
                            }
                    },
                ) {
                    webView = it
                    it.webViewClient = WebViewClient()
                    it.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
//            Button(
//                onClick = {
//                    exportAsPdf(webView = webView, context = context)
//                }) {
//                Text("Export As PDF")
//            }
        }
    }
}

fun exportAsPdf(webView: WebView?, context: Context) {
    if (webView != null) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter =
            webView.createPrintDocumentAdapter("TestPDF")
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .build()
        printManager.print(
            "TestPDF",
            printAdapter,
            printAttributes
        )
    }
}