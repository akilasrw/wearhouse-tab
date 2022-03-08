package com.aeroclubcargo.warehouse.domain.use_case.login

import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.toAuthenticateResponse
import com.aeroclubcargo.warehouse.domain.model.AuthenticateResponse
import com.aeroclubcargo.warehouse.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(private var repository: Repository) {
    operator fun invoke(username: String, password: String): Flow<Resource<AuthenticateResponse>> =
        flow {
            try {
                emit(Resource.Loading<AuthenticateResponse>())
                // mock
//                val auth =  AuthenticateResponse(id = "aasdasda", jwtToken =  "SDasdasdasdasdasd")
//                delay(2000)
//                emit(Resource.Success<AuthenticateResponse>(data = auth))
                val loginResponse = repository.authenticateUser(
                    authenticateRequestDto = AuthenticateRequestDto(
                        username = username,
                        password = password
                    )
                )
                emit(Resource.Success<AuthenticateResponse>(data = loginResponse.toAuthenticateResponse()))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<AuthenticateResponse>(
                        message = e.localizedMessage ?: "API connection failed!"
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error<AuthenticateResponse>(
                        message = e.localizedMessage ?: "Please check network!"
                    )
                )
            }
        }
}