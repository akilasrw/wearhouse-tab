package com.aeroclubcargo.warehouse.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.remove
import androidx.datastore.preferences.createDataStore
import com.aeroclubcargo.warehouse.common.Constants.PREF_JWT_TOKEN
import com.aeroclubcargo.warehouse.common.Constants.PREF_LANGUAGE
import com.aeroclubcargo.warehouse.common.Constants.PREF_LOGIN_USER
import com.aeroclubcargo.warehouse.common.Constants.PREF_REMEMBER_ME
import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStorePreferenceRepository(var context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "localeData")

    private val defaultLanguage = 0


    suspend fun setLanguage(language: Int) {
        dataStore.edit { preferences ->
            preferences[PREF_LANGUAGE] = language
        }
    }

    val getLanguage: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[PREF_LANGUAGE] ?: defaultLanguage
        }

    suspend fun saveCredential(credentialDto: CredentialDto) {
        val json = Gson().toJson(credentialDto)
        dataStore.edit { preferences ->
            preferences[PREF_REMEMBER_ME] = json
        }
    }

    suspend fun clearCredential() {
        dataStore.edit { preferences ->
            preferences.remove(PREF_REMEMBER_ME)
        }
    }

    val getCredential : Flow<CredentialDto?> = dataStore.data.map {
        preferences ->
        Gson().fromJson(preferences[PREF_REMEMBER_ME],CredentialDto::class.java)
    }

    suspend fun saveAuthenticatedLoggedInUser(authenticateRequestDto: AuthenticateResponseDto){
        val json = Gson().toJson(authenticateRequestDto)
        dataStore.edit { preferences ->
            preferences[PREF_LOGIN_USER] = json
        }
    }

    var authenticatedLoggedInUser : Flow<AuthenticateResponseDto?> = dataStore.data.map {
            preferences ->
        Gson().fromJson(preferences[PREF_LOGIN_USER],AuthenticateResponseDto::class.java)
    }


    suspend fun getRefreshToken() : String? {
        val preference = dataStore.data.first()
        return try{
            val json = preference[PREF_LOGIN_USER]
            val authModel = Gson().fromJson(json, AuthenticateResponseDto::class.java)
            authModel.refreshToken
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun removeLoginUserDetails(){
        dataStore.edit { preferences ->
            preferences.remove(PREF_LOGIN_USER)
        }
    }

    suspend fun saveJwtToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PREF_JWT_TOKEN] = token
        }
    }


    val getJwtToken: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PREF_JWT_TOKEN] ?: ""
        }

    suspend fun getToken() : String? {
        val preference = dataStore.data.first()
        return preference[PREF_JWT_TOKEN]
    }


}