package com.example.latesteffort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.domain.catalog.CatalogUseCase
import com.choidev.domain.catalog.model.CatalogMenuType
import com.choidev.domain.catalog.model.CatalogType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val catalogUseCase: CatalogUseCase
) : ViewModel() {

    private val _menuIsGridType = MutableStateFlow<Boolean?>(null)

    private val _catalogs = MutableStateFlow<Result<List<CatalogType>>>(Result.success(emptyList()))

    val catalogs = _catalogs.asStateFlow()

    val menuIsListType = _menuIsGridType.stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    init {
        viewModelScope.launch {
            catalogUseCase.getCatalogList().collect { result ->
                _catalogs.value = result
            }

            catalogUseCase.getCurrentMenuMode().collect { result ->
                _menuIsGridType.value = result == CatalogMenuType.TYPE_GRID
            }
        }
    }

    fun updateMenuType(isGridType: Boolean) = viewModelScope.launch {
        val menuMode = if (isGridType) {
            CatalogMenuType.TYPE_GRID
        } else {
            CatalogMenuType.TYPE_LIST
        }

        catalogUseCase.updateMenuMode(menuMode)
    }
}
