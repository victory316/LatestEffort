package com.choidev.domain.catalog

import com.choidev.domain.catalog.model.CatalogType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatalogUseCaseImpl @Inject constructor() : CatalogUseCase {

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
}
