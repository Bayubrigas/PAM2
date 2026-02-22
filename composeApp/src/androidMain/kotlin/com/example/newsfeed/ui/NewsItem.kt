package com.example.newsfeed.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsfeed.model.News
import com.example.newsfeed.viewmodel.NewsViewModel

@Composable
fun NewsItem(
    news: News,
    viewModel: NewsViewModel
) {
    var detail by remember { mutableStateOf<News?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                viewModel.markAsRead(news)
                viewModel.loadDetail(news) {
                    detail = it
                }
            }
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(text = news.title)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Category: ${news.category}")

            if (news.isRead) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "✓ Sudah dibaca")
            }

            detail?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it.content)
            }
        }
    }
}