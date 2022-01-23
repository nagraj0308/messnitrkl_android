package com.nagraj.messnitrkl.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreference(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)
        private val PREF_KEY_HOSTEL = stringPreferencesKey(Constants.PREF_HOSTEL)
        private val PREF_KEY_ROLL_NO = stringPreferencesKey(Constants.PREF_ROLL_NO)
        private val PREF_KEY_IS_LOGGED_IN = booleanPreferencesKey(Constants.PREF_IS_LOGGED_IN)
    }

    val getHostel: Flow<String>
        get() = context.dataStore.data.map {
            it[PREF_KEY_HOSTEL] ?: ""
        }

    suspend fun setHostel(value: String) {
        context.dataStore.edit { it[PREF_KEY_HOSTEL] = value }
    }

    val getRollNo: Flow<String>
        get() = context.dataStore.data.map {
            it[PREF_KEY_ROLL_NO] ?: ""
        }

    suspend fun setRollNo(value: String) {
        context.dataStore.edit { it[PREF_KEY_ROLL_NO] = value }
    }

    val isLoggedIn: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[PREF_KEY_IS_LOGGED_IN] ?: false
        }

    suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { it[PREF_KEY_IS_LOGGED_IN] = value }
    }

    suspend fun setLogout() {
        context.dataStore.edit {
            it.clear()
        }
    }

}