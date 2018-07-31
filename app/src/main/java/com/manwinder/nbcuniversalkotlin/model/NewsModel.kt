package com.manwinder.nbcuniversalkotlin.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class NewsResponse (
    val data: List<NewsData>
)

class NewsData (
    val id: String,
    val type: String,
    val item: NewsItem? = null,
    val videos: List<NewsItem>? = null,
    val items: List<NewsItem>?,
    val showMore: Boolean?,
    val tease: String?,
    val header: String?,
    val subHeader: String?
)

@Entity(tableName = "news_items")
@TypeConverters(SlideShowImageTypeConverters::class, NewsCaptionTypeConverters::class)
data class NewsItem (
    @PrimaryKey
    val id: String,
    val type: String? = null,
    val url: String? = null,
    val headline: String? = null,
    val published: String? = null,
    val tease: String? = null,
    val summary: String? = null,
    val label: String? = null,
    val images: List<SlideShowImage>? = null,
    val mpxID: String? = null,
    val duration: String? = null,
    val preview: String? = null,
    val videoUrl: String? = null,
    val captionLinks: NewsCaptions? = null,
    val associatedPlaylist: String? = null
)

class NewsCaptions (
    val srt: String?,
    val webvtt: String?
)

@Parcelize
class SlideShowImage (
        val id: String?,
        val url: String?,
        val headline: String?,
        val published: String?,
        val caption: String?,
        val copyright: String?,
        val graphic: Boolean?
    ) : Parcelable
