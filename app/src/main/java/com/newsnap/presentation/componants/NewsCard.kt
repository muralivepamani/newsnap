package com.newsnap.presentation.componants

import ReadMoreBox
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.newsnap.data.model.NewsItem
import com.newsnap.utils.TimeUtils

@Composable
fun NewsCard(news: NewsItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            if (!news.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = news.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(top = 36.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 12.dp)) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = news.description,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 6
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = TimeUtils.getRelativeTime(news.pubDate),
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        ReadMoreBox(news.link,news.imageUrl)
    }
}

