package com.manwinder.nbcuniversalkotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.manwinder.nbcuniversalkotlin.model.NewsItem

@Database(entities = [(NewsItem::class)], version = 2)
abstract class NewsItemDatabase : RoomDatabase() {
    abstract fun newsItemDao() : NewsItemDao
}