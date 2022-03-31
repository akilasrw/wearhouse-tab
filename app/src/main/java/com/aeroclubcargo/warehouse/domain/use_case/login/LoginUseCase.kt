package com.aeroclubcargo.warehouse.domain.use_case.login

import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.local.dto.toRememberMe
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.toAuthenticateResponse
import com.aeroclubcargo.warehouse.domain.model.AuthenticateResponse
import com.aeroclubcargo.warehouse.domain.model.RememberMe
import com.aeroclubcargo.warehouse.domain.model.toAuthenticateResponseDto
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
                if (loginResponse.jwtToken != null) {
                    emit(Resource.Success<AuthenticateResponse>(data = loginResponse.toAuthenticateResponse()))
                } else {
                    emit(
                        Resource.Error<AuthenticateResponse>(
                            message = "User not secured!"
                        )
                    )
                }
            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.string()
                emit(
                    Resource.Error<AuthenticateResponse>(
                        message = error ?: (e.localizedMessage ?: "API connection failed!")
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

    suspend fun saveLoggedInUser(authenticateResponse: AuthenticateResponse) {
        authenticateResponse.jwtToken?.let { repository.saveJwtToken(it) }
        repository.saveLoggedInUser(authenticateResponse.toAuthenticateResponseDto())
    }

    fun silentLogin(): Flow<Resource<AuthenticateResponse>> =
    flow {
        try {
            val credentials : Flow<CredentialDto?> = repository.getCredential()
            credentials.collect(collector = {
                if(it != null && it.password.isNotEmpty()){
                    emit(Resource.Loading<AuthenticateResponse>())
                    val loginResponse = repository.authenticateUser(
                        authenticateRequestDto = AuthenticateRequestDto(
                            username = it.userName,
                            password = it.password
                        )
                    )
                    if (loginResponse.jwtToken != null) {
                        emit(Resource.Success<AuthenticateResponse>(data = loginResponse.toAuthenticateResponse()))
                    } else {
                        emit(
                            Resource.Error<AuthenticateResponse>(
                                message = "User not secured!"
                            )
                        )
                    }
                }else{
                    emit(Resource.Error<AuthenticateResponse>(message = "No Users"))
                }
            })
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            emit(
                Resource.Error<AuthenticateResponse>(
                    message = error ?: (e.localizedMessage ?: "API connection failed!")
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