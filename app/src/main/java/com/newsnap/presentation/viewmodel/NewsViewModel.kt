package com.newsnap.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsnap.data.model.NewsItem
import com.newsnap.data.repo.RssRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = RssRepository()

    private val _newsItems = MutableStateFlow<List<NewsItem>>(emptyList())
    val newsItems: StateFlow<List<NewsItem>> = _newsItems

    private val rssFeeds = listOf(
       "https://www.siasat.com/news/india/feed/"
    )

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            val allItems = rssFeeds.flatMap { repository.fetchRssFeed(it) }
            _newsItems.value = allItems.sortedByDescending { it.pubDate }
        }
    }
}
