package com.aeroclubcargo.warehouse.data.repository

import com.aeroclubcargo.warehouse.data.remote.ApiInterface
import com.aeroclubcargo.warehouse.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private  val apiInterface: ApiInterface
) : Repository {



}