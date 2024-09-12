package com.choidev.domain.catalog

import com.choidev.domain.catalog.model.CatalogMenuType
import com.choidev.domain.catalog.model.CatalogType
import kotlinx.coroutines.flow.Flow

interface CatalogUseCase {

    suspend fun getCatalogMenuType(): Flow<CatalogMenuType>
    suspend fun getCatalogList(): Flow<Result<List<CatalogType>>>

    suspend fun updateMenuMode(type: CatalogMenuType)
}
