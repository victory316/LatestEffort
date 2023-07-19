package com.example.latesteffort.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Welcome to my latest effort.") }) }
    ) { paddingValues ->
        Row(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(text = "d")
            Text(text = "d")
            Text(text = "d")
        }
    }
}
