package com.example.latesteffort.state

import com.choidev.domain.catalog.model.CatalogMenuType
import com.choidev.domain.catalog.model.CatalogType

data class CatalogScreenState(
    val catalogs: List<CatalogType>? = null,
    val menuType: CatalogMenuType? = null,
    val uiState: UiState = UiState.LOADING
)
