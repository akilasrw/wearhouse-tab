package com.aeroclubcargo.warehouse.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.remove
import androidx.datastore.preferences.createDataStore
import com.aeroclubcargo.warehouse.common.Constants.PREF_LANGUAGE
import com.aeroclubcargo.warehouse.common.Constants.PREF_REMEMBER_ME
import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferenceRepository(context: Context) {
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


}