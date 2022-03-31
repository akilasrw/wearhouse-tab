package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import kotlinx.coroutines.flow.Flow

interface Repository : Remote,Local{

}

