package com.arthur.rukiasvet.core.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionRepository {

    private val Context.dataStore by preferencesDataStore(name = "session")

    private val KEY_TOKEN = stringPreferencesKey("token")
    private val KEY_USER_ID = intPreferencesKey("user_id")

    override suspend fun getToken(): String? =
        context.dataStore.data.first()[KEY_TOKEN]

    override suspend fun getCurrentUserId(): Int? =
        context.dataStore.data.first()[KEY_USER_ID]

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { it[KEY_TOKEN] = token }
    }

    override suspend fun clearToken() {
        context.dataStore.edit {
            it.remove(KEY_TOKEN)
            it.remove(KEY_USER_ID)
        }
    }

    override suspend fun saveUserId(userId: Int) {
        context.dataStore.edit { it[KEY_USER_ID] = userId }
    }
}