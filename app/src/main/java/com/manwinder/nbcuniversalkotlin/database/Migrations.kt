package com.manwinder.nbcuniversalkotlin.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE news_items ADD COLUMN captionLinks TEXT")
    }
}