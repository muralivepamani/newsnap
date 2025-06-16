package com.newsnap.utils


import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtils {

    fun getRelativeTime(pubDate: String): String {
        return try {
            val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
            val publishedTime = format.parse(pubDate)?.time ?: return ""
            val now = System.currentTimeMillis()
            val diff = now - publishedTime

            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
            val hours = TimeUnit.MILLISECONDS.toHours(diff)
            val days = TimeUnit.MILLISECONDS.toDays(diff)

            when {
                diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
                diff < TimeUnit.HOURS.toMillis(1) -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
                diff < TimeUnit.DAYS.toMillis(1) -> "$hours hour${if (hours > 1) "s" else ""} ago"
                diff < TimeUnit.DAYS.toMillis(2) -> "Yesterday"
                else -> "$days day${if (days > 1) "s" else ""} ago"
            }
        } catch (e: Exception) {
            ""
        }
    }
}
