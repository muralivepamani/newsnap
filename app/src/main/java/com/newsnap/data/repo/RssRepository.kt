package com.newsnap.data.repo

import android.util.Log
import com.newsnap.data.model.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class RssRepository {
    suspend fun fetchRssFeed(rssUrl: String): List<NewsItem> = withContext(Dispatchers.IO) {
        val newsList = mutableListOf<NewsItem>()

        try {
            val doc = Jsoup.connect(rssUrl).get()
            val items = doc.select("item")

            for (item in items) {
                val title = item.selectFirst("title")?.text().orEmpty()
                val rawDescription = item.selectFirst("description")?.text().orEmpty()
                val link = item.selectFirst("link")?.text().orEmpty()
                val pubDate = item.selectFirst("pubDate")?.text().orEmpty()

                val cleanedDescription = cleanDescription(rawDescription)

                val imageUrl = extractImageUrl(item, rawDescription)

                newsList.add(
                    NewsItem(
                        title = title,
                        description = cleanedDescription,
                        link = link,
                        imageUrl = imageUrl,
                        pubDate = pubDate,
                        source = rssUrl
                    )
                )
            }

            Log.d("NewsSnap", "Fetched ${newsList.size} items from RSS")

        } catch (e: Exception) {
            Log.e("NewsSnap", "RSS Fetch Failed", e)
        }

        newsList
    }

    private fun cleanDescription(html: String): String {
        val doc = Jsoup.parse(html)

        // Remove ads, aside, HR, Telegram promotions
        doc.select("aside, hr, script, style, .also_read_post").remove()

        doc.select("p").forEach { p ->
            val text = p.text().lowercase()
            if (text.contains("get the latest updates") ||
                text.contains("telegram") ||
                text.contains("whatsapp") ||
                text.contains("download our app")
            ) {
                p.remove()
            }
        }

        return doc.select("p").joinToString("\n\n") { it.text() }.trim()
    }

    private fun extractImageUrl(item: Element, descriptionHtml: String): String? {
        // Try <media:content> or <image>
        item.selectFirst("media|content")?.attr("url")?.let { return it }
        item.selectFirst("image")?.text()?.let { return it }

        // Fallback: parse <img> from HTML
        val imgRegex = Regex("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"]")
        return imgRegex.find(descriptionHtml)?.groupValues?.get(1)
    }
}
