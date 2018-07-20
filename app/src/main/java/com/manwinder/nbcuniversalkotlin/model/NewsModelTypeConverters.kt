package com.manwinder.nbcuniversalkotlin.model

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

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
