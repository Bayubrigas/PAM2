package com.example.newsfeed.data

import com.example.newsfeed.model.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class NewsRepository {

    private val categories = listOf("Tech", "Sports", "Finance")

    fun getNewsStream(): Flow<News> = flow {
        var id = 1
        while (true) {
            delay(2000)

            val news = News(
                id = id,
                title = "Breaking News #$id",
                category = categories.random(),
                content = "Short content preview for news #$id"
            )

            emit(news)
            id++
        }
    }

    suspend fun getNewsDetail(news: News): News {
        delay(1000) // simulate network delay
        return news.copy(
            content = news.content + "\n\n[Full detailed content loaded]"
        )
    }
}