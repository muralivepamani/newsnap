package com.newsnap.presentation.componants


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.*
import com.newsnap.presentation.viewmodel.NewsViewModel


@Composable
fun NewsScreen(modifier: Modifier) {
    val viewModel: NewsViewModel = viewModel()
    val newsList by viewModel.newsItems.collectAsState()

    if (newsList.isNotEmpty()) {
        val pagerState = rememberPagerState()

        VerticalPager(
            count = newsList.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val news = newsList[page]
            NewsCard(news)
        }

    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    }
}
