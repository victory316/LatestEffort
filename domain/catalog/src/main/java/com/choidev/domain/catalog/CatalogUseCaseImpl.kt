package com.choidev.domain.catalog

import com.choidev.domain.catalog.model.CatalogMenuType
import com.choidev.domain.catalog.model.CatalogType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatalogUseCaseImpl @Inject constructor(
    private val catalogRepository: CatalogRepository
) : CatalogUseCase {

    override suspend fun getCurrentMenuMode(): Flow<CatalogMenuType> =
        catalogRepository.getCurrentMenuMode()

    override suspend fun getCatalogList(): Flow<Result<List<CatalogType>>> = flow {
        emit(
            runCatching {
                listOf(
                    CatalogType.SEARCH_MEDIA,
                    CatalogType.VIBRATION,
                    CatalogType.NOTIFICATION,
                    CatalogType.MOTION
                )
            }
        )
    }

    override suspend fun updateMenuMode(type: CatalogMenuType) {
        catalogRepository.updateMenuMode(type)
    }
}
