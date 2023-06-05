package com.aeroclubcargo.warehouse.domain.use_case.accept_cargo

import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.Constants.Accepted
import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.domain.model.AuthenticateResponse
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AcceptCargoUseCase @Inject constructor(var repository: Repository) {

    operator fun invoke(
        packageDetail: PackageDetails
    ): Flow<Resource<Boolean>> =
        flow {
//            try {
//                emit(Resource.Loading())
//               val response = repository.acceptCargo(
//                    configType = Constants.AircraftConfigType.values()[packageDetail.aircraftConfigType],
//                    id = packageDetail.bookingId,
//                    bookingStatus = Accepted
//                )
//                emit(Resource.Success(data = response))
//            } catch (e: Exception) {
//                val error = e.localizedMessage
//                emit(
//                    Resource.Error(
//                        message = error ?: (e.localizedMessage ?: "API connection failed!")
//                    )
//                )
//            }
        }

}