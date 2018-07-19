package com.manwinder.nbcuniversalkotlin.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface NewsItemDao {
    @Insert(onConflict = REPLACE)
    fun insertAll(newsItems: List<NewsItem>)

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem)

    @Update
    fun update(newsItem: NewsItem)

    @Query("SELECT * FROM news_items")
    fun getNewsItems() : LiveData<List<NewsItem>>

    @Query("SELECT * FROM news_items ORDER BY published DESC")
    fun getNewsItemsInOrder() : LiveData<List<NewsItem>>

    @Query("SELECT Count(*) FROM news_items")
    fun getNumOfNewsItems() : Int
}