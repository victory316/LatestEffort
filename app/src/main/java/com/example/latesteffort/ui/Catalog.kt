package com.example.latesteffort.ui

import LeTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.NavigateAction
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.domain.catalog.model.CatalogType
import com.choidev.latesteffort.R
import com.choidev.latesteffort.core.design.compose.LazyColumnPaddingVertical
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.latesteffort.feature.notification_test.navigation.notificationRoute
import com.choidev.latesteffort.feature.search_media.SearchMediaActivity
import com.choidev.vibration.navigation.vibrationRoute
import com.example.latesteffort.MainViewModel
import com.example.latesteffort.state.CatalogItemState
import com.example.latesteffort.util.CatalogScreenHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    presenter: ActionPresenter,
    mainViewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val menus by mainViewModel.catalogs.collectAsStateWithLifecycle()

    var gridMode by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Welcome to my latest effort.") },
                actions = {
                    IconToggleButton(checked = gridMode, onCheckedChange = { gridMode = it }) {
                        if (gridMode) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_apps_24),
                                contentDescription = null
                            )
                        }
                    }
                })
        },
        modifier = modifier
            .padding(horizontal = ScreenPaddingHorizontal(), vertical = 12.dp)
    ) { paddingValues ->
        when {
            menus.isSuccess -> {
                menus.mapCatching { data ->
                    data.map {
                        when (it) {
                            CatalogType.SEARCH_MEDIA -> {
                                CatalogItemState(
                                    title = "미디어 검색하기",
                                    icon = Icons.Rounded.Search,
                                    backgroundColor = CatalogScreenHelper.getNextBackgroundColor(),
                                    action = NavigateAction.StartActivity(SearchMediaActivity::class.java),
                                )
                            }

                            CatalogType.VIBRATION -> {
                                CatalogItemState(
                                    title = "진동 테스트",
                                    icon = Icons.Rounded.MoreVert,
                                    backgroundColor = CatalogScreenHelper.getNextBackgroundColor(),
                                    action = NavigateAction.NavGraphDestination(vibrationRoute)
                                )
                            }

                            CatalogType.NOTIFICATION -> {
                                CatalogItemState(
                                    title = "알림 테스트",
                                    icon = Icons.Rounded.Notifications,
                                    backgroundColor = CatalogScreenHelper.getNextBackgroundColor(),
                                    action = NavigateAction.NavGraphDestination(notificationRoute)
                                )
                            }
                        }
                    }
                }.also { result ->
                    Catalogs(
                        itemState = result.getOrDefault(emptyList()),
                        isGridMode = gridMode,
                        modifier = Modifier.padding(paddingValues),
                        presenter = presenter
                    )
                }
            }

            menus.isFailure -> {
                // TODO show failure screen
            }
        }
    }
}

@Composable
fun Catalogs(
    itemState: List<CatalogItemState>,
    isGridMode: Boolean,
    presenter: ActionPresenter,
    modifier: Modifier = Modifier
) {
    if (!isGridMode) {
        CatalogByListsUi(catalogs = itemState, presenter = presenter, modifier = modifier)
    } else {
        CatalogsByGridUi(catalogs = itemState, presenter = presenter, modifier = modifier)
    }
}

@Composable
fun CatalogByListsUi(
    catalogs: List<CatalogItemState>,
    presenter: ActionPresenter,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(LazyColumnPaddingVertical())
    ) {
        items(catalogs) { state ->
            with(state) {
                CatalogListItem(
                    icon = icon,
                    title = title,
                    backgroundColor = backgroundColor,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .clickable { presenter.onClick(state.action) }
                )
            }
        }
    }
}

@Composable
fun CatalogsByGridUi(
    catalogs: List<CatalogItemState>,
    modifier: Modifier = Modifier,
    presenter: ActionPresenter
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        items(catalogs) { state ->

            with(state) {
                CatalogGridItem(
                    icon = icon,
                    title = title,
                    backgroundColor = backgroundColor,
                    modifier = Modifier
                        .clickable { presenter.onClick(action) }
                )
            }
        }
    }
}

@Composable
fun CatalogListItem(
    icon: ImageVector,
    title: String,
    backgroundColor: Color = Color.LightGray,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(icon, contentDescription = null)
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
    }
}

@Composable
fun CatalogGridItem(
    icon: ImageVector,
    title: String,
    backgroundColor: Color = Color.LightGray,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = modifier
            .aspectRatio(1f)
            .padding(6.dp)
    ) {
        Icon(
            icon, contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxHeight()
        )
    }
}

@Preview
@Composable
fun PreviewCatalogListItem() {
    LeTheme {
        CatalogListItem(
            icon = Icons.Default.Notifications,
            title = "알림 테스트",
        )
    }
}

@Preview
@Composable
fun PreviewCatalogGridItem() {
    LeTheme {
        CatalogGridItem(
            icon = Icons.Default.Notifications,
            title = "알림 테스트"
        )
    }
}
