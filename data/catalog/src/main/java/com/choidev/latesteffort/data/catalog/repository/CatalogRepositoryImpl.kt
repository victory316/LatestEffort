package com.choidev.latesteffort.data.catalog.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.choidev.domain.catalog.CatalogRepository
import com.choidev.domain.catalog.model.CatalogMenuType
import com.choidev.latesteffort.data.core.di.PREF_KEY_MENU_TYPE_INDEX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CatalogRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : CatalogRepository {

    override suspend fun getCurrentMenuMode(): Flow<CatalogMenuType> =
        datastore.data.map { preferences ->
            val index = preferences[PREF_KEY_MENU_TYPE_INDEX] ?: CatalogMenuType.TYPE_LIST.ordinal

            CatalogMenuType.values()[index]
        }

    override suspend fun updateMenuMode(type: CatalogMenuType) {
        datastore.edit { preferences ->
            preferences[PREF_KEY_MENU_TYPE_INDEX] = type.ordinal
        }
    }
}