package com.manwinder.nbcuniversalkotlin.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.util.*

class NewsResponse (
    val data: List<NewsData>
)

class NewsData (
    val id: String,
    val type: String,
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
    val type: String?,
    val url: String?,
    val headline: String?,
    val published: String?,
    val tease: String?,
    val summary: String?,
    val label: String?,
    val images: List<SlideShowImage>?,
    val mpxID: String?,
    val duration: String?,
    val preview: String?,
    val videoUrl: String?,
    val captionLinks: List<NewsCaptions>?,
    val associatedPlaylist: String?
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


class SlideShowImageTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun slideShowToList(slideShowData: String?) : List<SlideShowImage>? {
        slideShowData ?: run {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<SlideShowImage>>() {}.type

        return gson.fromJson(slideShowData, listType)
    }

    @TypeConverter
    fun slideShowListToString(slideShowList: List<SlideShowImage>?): String? {
        return gson.toJson(slideShowList)
    }
}

class NewsCaptionTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun newsCaptionToList(newsCaptionData: String?) : List<NewsCaptions>? {
        newsCaptionData ?: run {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<NewsCaptions>>() {}.type

        return gson.fromJson(newsCaptionData, listType)
    }

    @TypeConverter
    fun newsCaptionListToString(newsCaptionList: List<NewsCaptions>?): String? {
        return gson.toJson(newsCaptionList)
    }
}
