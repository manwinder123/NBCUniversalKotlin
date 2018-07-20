package com.manwinder.nbcuniversalkotlin.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class NewsResponse (
    val data: List<NewsData>
)

class NewsData (
    val id: String,	//"playlist/mmlsnnd_20381145"
    val type: String, //"Hero"
    val items: List<NewsItem>?,
    val showMore: Boolean?,
    val tease: String?,
    val header: String?,
    val subHeader: String?
)

@Entity(tableName = "news_items")
@TypeConverters(SlideShowImageTypeConverters::class)
data class NewsItem (
    @PrimaryKey
    val id: String,//	"mmvo1273710147636"
    val type: String?, //	"video"
    val url: String?, //	"https://www.nbcnews.com/…-thai-cave-1273710147636"
    val headline: String?, //	"Mission accomplished: Al… rescued from Thai cave"
    val published: String?, //	"2018-07-10T12:02:59Z"
    val tease: String?, //	"https://media2.s-nbcnews…deo/201807/995168902.jpg"
    val summary: String?, //	"Thailand’s navy SEALs co…C’s Bill Neely reports."
    val label: String?, //	"WORLD"
    val images: List<SlideShowImage>?, //	"WORLD"
    val mpxID: String?, //	"1273710147636"
    val duration: String?, //	"00:03:32"
    val preview: String?, //	"http://public.vilynx.com…db80b/pro23.viwindow.mp4"
    val videoUrl: String?, //	"http://link.theplatform.…nifest=m3u&metafile=none"
    /*val captionLinks: List<NewsCapitions>,*/
    val associatedPlaylist: String? //	"playlist/mmlsnnd_21426473"
)

class NewsCapitions (
        val srt: String?, //	"https://nbcnewsdigital-s…ec_thai_neely_180710.srt"
        val webvtt: String? //	"https://nbcnewsdigital-s…ec_thai_neely_180710.vtt"
)

class SlideShowImage (
    val id: String?,
    val url: String?,
    val headline: String?,
    val published: String?,
    val caption: String?,
    val copyright: String?,
    val graphic: Boolean?
)


class SlideShowImageTypeConverters {
    val gson = Gson()

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