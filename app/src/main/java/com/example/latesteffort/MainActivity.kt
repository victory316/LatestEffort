package com.example.latesteffort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.choidev.latesteffort.feature.search_media.SearchMediaActivity
import com.example.latesteffort.ext.startNewActivity
import com.example.latesteffort.ui.CatalogScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CatalogScreen()
        }
        startNewActivity(SearchMediaActivity::class.java)
    }
}
