package com.aeroclubcargo.warehouse.domain.use_case.locale

import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.data.local.DataStorePreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetLocaleUseCase @Inject constructor(var datastore: DataStorePreferenceRepository) {

    operator fun invoke(): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            datastore.getLanguage.collect {
                emit(Resource.Success<Int>(data = it))
            }
        } catch (e: Exception) {
            emit(Resource.Error<Int>(message = "Something went wrong"))
        }
    }

}