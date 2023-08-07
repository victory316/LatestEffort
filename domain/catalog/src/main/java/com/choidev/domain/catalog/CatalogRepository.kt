package com.choidev.domain.catalog

import com.choidev.domain.catalog.model.CatalogMenuType
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    suspend fun getCurrentMenuMode(): Flow<CatalogMenuType>

    suspend fun updateMenuMode(type: CatalogMenuType)
}