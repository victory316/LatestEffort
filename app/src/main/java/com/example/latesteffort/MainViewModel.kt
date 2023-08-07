package com.example.latesteffort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.domain.catalog.CatalogUseCase
import com.choidev.domain.catalog.model.CatalogMenuType
import com.choidev.domain.catalog.model.CatalogType
import com.example.latesteffort.state.CatalogScreenState
import com.example.latesteffort.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val catalogUseCase: CatalogUseCase
) : ViewModel() {

    private val _catalogMenuType = MutableStateFlow<CatalogMenuType?>(null)
    private val _catalogs = MutableStateFlow<Result<List<CatalogType>>?>(null)

    val catalogScreenState = combine(_catalogMenuType, _catalogs) { menuType, catalogs ->
        when {
            catalogs?.isSuccess == true && menuType != null -> {
                CatalogScreenState(
                    catalogs = catalogs.getOrNull(),
                    menuType = menuType,
                    uiState = UiState.SUCCESS
                )
            }

            else -> CatalogScreenState()
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = CatalogScreenState(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    init {
        viewModelScope.launch {
            catalogUseCase.getCatalogList().collect { result ->
                _catalogs.value = result
            }

            catalogUseCase.getCatalogMenuType().collect { result ->
                _catalogMenuType.value = result
            }
        }
    }

    fun updateMenuType(setToGrid: Boolean) = viewModelScope.launch {
        val menuMode = if (setToGrid) {
            CatalogMenuType.TYPE_GRID
        } else {
            CatalogMenuType.TYPE_LIST
        }

        catalogUseCase.updateMenuMode(menuMode)
    }
}
