package com.example.newsfeed.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsfeed.viewmodel.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel = viewModel()) {

    val news by viewModel.filteredNews.collectAsState(initial = emptyList())
    val readCount by viewModel.readCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Read News: $readCount")

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = { viewModel.setCategory(null) }) {
                Text("All")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { viewModel.setCategory("Tech") }) {
                Text("Tech")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { viewModel.setCategory("Sports") }) {
                Text("Sports")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { viewModel.setCategory("Finance") }) {
                Text("Finance")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(news) { item ->
                NewsItem(
                    news = item,
                    viewModel = viewModel
                )
            }
        }
    }
}