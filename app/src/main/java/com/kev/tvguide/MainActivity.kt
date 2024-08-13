package com.kev.tvguide

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kev.tvguide.presentation.ShowsViewModel
import com.kev.tvguide.presentation.screens.MainScreen
import com.kev.tvguide.presentation.screens.ShowsDemo
import com.kev.tvguide.presentation.theme.TvGuideTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvGuideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val viewModel = hiltViewModel<ShowsViewModel>()
//                    viewModel.popularShows
//                    MainScreen(viewModel)
                    ShowsDemo(viewModel = viewModel)
                }
            }
        }
    }
}