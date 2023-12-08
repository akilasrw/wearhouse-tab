package com.aeroclubcargo.warehouse.data.repository

import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.data.local.DataStorePreferenceRepository
import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.ApiInterface
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.*
import com.aeroclubcargo.warehouse.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private var datastore: DataStorePreferenceRepository
) : Repository {

    override suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto {
        return apiInterface.authenticateUser(authenticateRequestDto = authenticateRequestDto)
    }

    override suspend fun refreshToken(refreshToken: String): AuthenticateResponseDto {
        return apiInterface.refreshToken(refreshToken)
    }

    override suspend fun apiGetSectorsList(): String {
        return apiInterface.apiGetSectors()
    }

    override suspend fun getCargoBooking(
        pageSize: Int,
        pageIndex: Int
    ): Pagination<PackageListItem> {
        return apiInterface.getCargoPackageList(
            pageIndex = pageIndex,
            pageSize = pageSize,
            includeCargoBooking = true
        )
    }

    override suspend fun getPackageDetails(packageRefNumber: String): PackageDetails {
        return apiInterface.getPackageDetails(awbTrackingNumber = packageRefNumber)
    }

    override suspend fun acceptCargo(configType: Constants.AircraftConfigType,id: String,@Constants.BookingStatus bookingStatus: Int): Boolean {
        return when(configType){
            Constants.AircraftConfigType.None -> {
                TODO()
            }
            Constants.AircraftConfigType.P2C -> {
                apiInterface.acceptCargoBooking(BookingStatusUpdateRequest(id, bookingStatus))
            }
            Constants.AircraftConfigType.Freighter -> {
                apiInterface.updateULDCargoBooking(BookingStatusUpdateRequest(id, bookingStatus))
            }
        }

    }

    override suspend fun cargoBookingSummaryList(
        FlightNumber: String,
        FlightDate: String,
        PageIndex: Int,
        PageSize: Int
    ): Pagination<CutOffTimeModel> {
        return apiInterface.cargoBookingSummaryList(FlightNumber, FlightDate, PageIndex, PageSize)
    }

    override suspend fun updateCutOffTIme(id: String, body: CutOffTimeRequest): Boolean {
        return apiInterface.updateCutOffTIme(id = id, body = body)
    }

    override suspend fun updatePackageStatus(body: UpdatePackageStatus): Response<Boolean?> {
        return apiInterface.updatePackageStatus(body)
    }

    override suspend fun getUnitList(): Response<List<UnitVM>> {
        return apiInterface.getUnitList()
    }

    override suspend fun updatePackage(body: PackageLineItem): Response<Any> {
        return  apiInterface.updatePackage(body)
    }

    override suspend fun getFlightScheduleWithULDCount(
        scheduledDepartureStartDateTime: String,
        scheduledDepartureEndDateTime: String,
        excludeFinalizedSchedules : Boolean,
    ): Response<List<FlightScheduleModel>> {
        return apiInterface.getFlightScheduleWithULDCount(scheduledDepartureStartDateTime, scheduledDepartureEndDateTime,excludeFinalizedSchedules)
    }

    override suspend fun getULDFilteredList(
        pageIndex: Int,
        pageSize: Int
    ): Response<Pagination<ULDModel>> {
        return apiInterface.getULDFilteredList(pageIndex, pageSize)
    }

    override suspend fun getBookingListPerFlightSchedule(flightScheduleSectorId: String): Response<List<BookingModel>> {
        return  apiInterface.getBookingListPerFlightSchedule(flightScheduleSectorId,true)
    }

    override suspend fun getSummaryCargoPositions(aircraftLayoutId: String): Response<List<FlightScheduleSectorUldPositionVM>> {
        return  apiInterface.getSummaryCargoPositions(aircraftLayoutId)
    }

    override suspend fun getPalletsByFlightScheduleId(
        flightScheduleId: String,
        uldId : String?,
        uldLocateStatus: Int
    ): Response<List<ULDPalletVM>> {
        return  apiInterface.getPalletsByFlightScheduleId(flightScheduleId,uldId, uldLocateStatus)
    }

    override suspend fun addPalletListToFlight(flightSchedulePalletUpdateListRM: FlightScheduleSectorPalletCreateListRM): Response<Any> {
        return apiInterface.addPalletListToFlight(flightSchedulePalletUpdateListRM)
    }

    override suspend fun removeAssignedULDFromSchedule(flightScheduleSectorDeleteRM: FlightScheduleSectorPalletDeleteRM): Response<Any> {
        return apiInterface.removeAssignedULDFromSchedule(flightScheduleSectorDeleteRM)
    }

    override suspend fun updatePackageULDContainerRM(bookingAssignmentRM: BookingAssignmentRM): Response<Any> {
        return apiInterface.saveBookingAssignment(bookingAssignmentRM)
    }

    override suspend fun removeBookedAssignment(bookingAssignmentRM: BookingAssignmentRM): Response<Any> {
        return apiInterface.removeBookedAssignment(bookingAssignmentRM = bookingAssignmentRM)
    }

    override suspend fun getAssignedCargoList(
        flightScheduleSectorId: String,
        uldId: String
    ): Response<List<BookingModel>> {
        return apiInterface.getAssignedCargoList(flightScheduleSectorId, uldId)
    }

    override suspend fun getSummaryCargoPositionsBySector(flightScheduleSectorId: String): Response<List<CargoPositionVM>> {
        return apiInterface.getSummaryCargoPositionsBySector(flightScheduleSectorId)
    }

    override suspend fun addULDCargoPosition(uldCargoPositionList: List<ULDCargoPositionRequest>): Response<ULDCargoPositionResponse> {
        return apiInterface.addULDCargoPosition(uldCargoPositionList)
    }

    override suspend fun saveCredential(credentialDto: CredentialDto) {
        datastore.saveCredential(credentialDto = credentialDto)
    }

    override suspend fun getCredential(): Flow<CredentialDto?> {
        return datastore.getCredential
    }

    override suspend fun clearCredential() {
        return datastore.clearCredential()
    }

    override suspend fun removeLoginUserDetails() {
        return datastore.removeLoginUserDetails()
    }

    override suspend fun saveLoggedInUser(authenticateRequestDto: AuthenticateResponseDto) {
        return datastore.saveAuthenticatedLoggedInUser(authenticateRequestDto = authenticateRequestDto)
    }

    override suspend fun saveJwtToken(jwtToken: String) {
        return datastore.saveJwtToken(jwtToken)
    }

    override suspend fun getJwtToken(): Flow<String> {
        return datastore.getJwtToken
    }

    override suspend fun getLoggedInUser(): Flow<AuthenticateResponseDto?> {
        return datastore.authenticatedLoggedInUser
    }



}