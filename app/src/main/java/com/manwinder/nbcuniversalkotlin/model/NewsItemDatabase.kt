package com.manwinder.nbcuniversalkotlin.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(NewsItem::class)], version = 1)
abstract class NewsItemDatabase : RoomDatabase() {
    abstract fun newsItemDao() : NewsItemDao
}