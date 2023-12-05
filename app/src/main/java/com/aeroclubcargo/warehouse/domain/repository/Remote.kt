package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query

interface Remote {
    suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto
    suspend fun refreshToken(refreshToken: String): AuthenticateResponseDto
    suspend fun apiGetSectorsList(): String
    suspend fun getCargoBooking(pageSize: Int, pageIndex: Int): Pagination<PackageListItem>
    suspend fun getPackageDetails(packageRefNumber: String): PackageDetails
    suspend fun acceptCargo(
        configType: Constants.AircraftConfigType,
        id: String,
        @Body @Constants.BookingStatus bookingStatus: Int
    ): Boolean
    suspend fun cargoBookingSummaryList(
        FlightNumber: String,
        FlightDate: String,
        PageIndex: Int,
        PageSize: Int,
    ): Pagination<CutOffTimeModel>

    suspend fun updateCutOffTIme(id: String,body: CutOffTimeRequest): Boolean
    suspend fun updatePackageStatus(@Body body: UpdatePackageStatus): Response<Boolean?>

    suspend fun getFlightScheduleWithULDCount(scheduledDepartureStartDateTime : String, scheduledDepartureEndDateTime : String,excludeFinalizedSchedules : Boolean,): Response<List<FlightScheduleModel>>
    suspend fun getULDFilteredList( pageIndex:Int, pageSize:Int): Response<Pagination<ULDModel>>
    suspend fun getBookingListPerFlightSchedule(flightScheduleSectorId:String) : Response<List<BookingModel>> // Replace the parameter to flightScheduleSectorId
    suspend fun getSummaryCargoPositions(aircraftLayoutId : String) : Response<List<FlightScheduleSectorUldPositionVM>>
    suspend fun getPalletsByFlightScheduleId(flightScheduleId : String,uldId : String?, uldLocateStatus: Int) : Response<List<ULDPalletVM>>
    suspend fun addPalletListToFlight(flightSchedulePalletUpdateListRM: FlightScheduleSectorPalletCreateListRM) : Response<Any>
    suspend fun removeAssignedULDFromSchedule(flightScheduleSectorDeleteRM:FlightScheduleSectorPalletDeleteRM) : Response<Any>
    suspend fun updatePackageULDContainerRM(bookingAssignmentRM: BookingAssignmentRM) : Response<Any>
    suspend fun removeBookedAssignment(bookingAssignmentRM: BookingAssignmentRM) : Response<Any>
    suspend fun getAssignedCargoList(flightScheduleSectorId:String,uldId : String) : Response<List<BookingModel>>
    suspend fun getSummaryCargoPositionsBySector(flightScheduleSectorId: String) : Response<List<CargoPositionVM>>
    suspend fun addULDCargoPosition(uldCargoPositionRequest: ULDCargoPositionRequest) : Response<ULDCargoPositionResponse>


}