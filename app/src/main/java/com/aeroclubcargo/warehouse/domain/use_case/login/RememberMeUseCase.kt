package com.aeroclubcargo.warehouse.domain.use_case.login

import com.aeroclubcargo.warehouse.data.local.dto.RememberMeDto
import com.aeroclubcargo.warehouse.data.local.dto.toRememberMe
import com.aeroclubcargo.warehouse.domain.model.RememberMe
import com.aeroclubcargo.warehouse.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RememberMeUseCase @Inject constructor(var repository: Repository) {

    operator fun invoke(): Flow<RememberMe> = flow {
        try {
            val credentials = repository.getRememberMeCredential()
            credentials.collect {
                emit(value = it?.toRememberMe() ?: RememberMe(isRememberMe = false))
            }
        } catch (e: Exception) {
            emit(value = RememberMe(isRememberMe = false))
        }
    }

    suspend fun saveRememberMeCredentials(userName: String, password: String) {
        repository.saveRememberMeCredential(
            rememberMeDto = RememberMeDto(
                userName = userName,
                password = password,
            )
        )
    }

    suspend fun clearUserDataOnRememberMe(){
        repository.clearCredential()
    }

}