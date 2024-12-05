package com.choidev.latesteffort.feature.compose.ui.image

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.choidev.core.actions.presenter.ActionPresenter
import com.supergene.loki.feature.motion.R

const val imageViewerRoute = "feature_compose_image_viewer"

fun NavGraphBuilder.imageViewerScreen(presenter: ActionPresenter) {
    composable(
        route = imageViewerRoute
    ) {
        DownloadableImageViewer(
            pageItems = listOf(R.drawable.img_sample)
        )
    }
}