package com.manwinder.nbcuniversalkotlin.database

import android.arch.persistence.room.Room
import android.content.Context

object DatabaseCreator {

    /**
     * Create database instance when the singleton instance is created for the
     * first time.
     */
    fun database(context: Context): NewsItemDatabase {
        return Room.databaseBuilder(context, NewsItemDatabase::class.java, "news_items.db")
                .addMigrations(MIGRATION_1_2)
                .build()
    }

}