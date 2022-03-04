package com.aeroclubcargo.warehouse.domain.use_case.locale

import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.data.local.DataStorePreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SaveLocaleUseCase @Inject constructor(var dataStore: DataStorePreferenceRepository) {

    operator fun invoke(index: Int): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            dataStore.setLanguage(index)
            emit(Resource.Success<Int>(data = index))
        }catch (e:Exception){
            emit(Resource.Error<Int>(message = "Something went wrong"))
        }
    }

}