package com.example.newsfeed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.data.NewsRepository
import com.example.newsfeed.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getNewsStream()
                .onEach { println("New article: ${it.title}") }
                .catch { e -> println("Error: ${e.message}") }
                .collect { news ->
                    _newsList.value = _newsList.value + news
                }
        }
    }

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    val filteredNews: Flow<List<News>> =
        combine(newsList, selectedCategory) { list, category ->
            list
                .filter { category == null || it.category == category }
                .map { news ->
                    news.copy(title = news.title.uppercase())
                }
        }

    fun markAsRead(news: News) {
        _newsList.value = _newsList.value.map {
            if (it.id == news.id) {
                _readCount.value++
                it.copy(isRead = true)
            } else it
        }
    }

    fun loadDetail(news: News, onResult: (News) -> Unit) {
        viewModelScope.launch {
            val detail = async(Dispatchers.IO) {
                repository.getNewsDetail(news)
            }.await()

            onResult(detail)
        }
    }
}