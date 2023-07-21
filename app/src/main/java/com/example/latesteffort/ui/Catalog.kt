package com.example.latesteffort.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.domain.catalog.model.CatalogType
import com.choidev.latesteffort.feature.search_media.SearchMediaActivity
import com.example.latesteffort.MainViewModel
import com.example.latesteffort.action.NavigateAction
import com.example.latesteffort.action.presenter.ActionPresenter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    presenter: ActionPresenter,
    mainViewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val menus by mainViewModel.catalogs.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Welcome to my latest effort.") }) },
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
    ) { paddingValues ->
        when {
            menus.isSuccess -> {
                CatalogListsUi(
                    catalogs = menus.getOrDefault(emptyList()),
                    modifier = Modifier.padding(paddingValues),
                    presenter = presenter
                )
            }

            menus.isFailure -> {
                // TODO show failure screen
            }
        }
    }
}

@Composable
fun CatalogListsUi(
    catalogs: List<CatalogType>,
    modifier: Modifier = Modifier,
    presenter: ActionPresenter
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(catalogs) { type ->
            when (type) {
                CatalogType.SEARCH_MEDIA -> {
                    CatalogItem(
                        icon = Icons.Rounded.Search,
                        title = "미디어 검색하기",
                        presenter = presenter,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }

                CatalogType.VIBRATION -> {
                    CatalogItem(
                        icon = Icons.Default.MoreVert,
                        title = "진동 테스트",
                        presenter = presenter,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun CatalogItem(
    icon: ImageVector,
    title: String,
    presenter: ActionPresenter,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .clickable {
                presenter.onClick(
                    NavigateAction.StartActivity(SearchMediaActivity::class.java)
                )
            }
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(icon, contentDescription = null)
            Text(text = title)
        }
    }
}
