package com.example.latesteffort.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter
import com.example.latesteffort.ui.CatalogScreen

const val catalogRoute = "app_catalog"

fun NavGraphBuilder.catalogScreen(
    presenter: ActionPresenter,
) {
    composable(
        route = catalogRoute
    ) {
        CatalogScreen(presenter = presenter)
    }
}
