package com.manwinder.nbcuniversalkotlin

import android.arch.persistence.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.manwinder.nbcuniversalkotlin.database.NewsItemDao
import com.manwinder.nbcuniversalkotlin.database.NewsItemDatabase
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Was unable to get the test to run, error that I got was:
 * Class not found: "com.manwinder.nbcuniversalkotlin.NewsItemDaoTest"Empty test suite.
 */

@RunWith(AndroidJUnit4::class)
class NewsItemDaoTest {

    private lateinit var newsItemDatabase: NewsItemDatabase
    private lateinit var newsItemDao: NewsItemDao

    @Before
    fun initDb() {
        newsItemDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), NewsItemDatabase::class.java)
                .build()
        newsItemDao = newsItemDatabase.newsItemDao()
    }

    @After
    fun closeDb() {
        newsItemDatabase.close()
    }

    @Test
    fun insertNewsItem() {
        val newsItem = NewsItem("1",null,null,null,null,null,null,null,null,null,null,null,null,null,null)

        newsItemDao.insert(newsItem)

        val newsItemList = newsItemDao.getNewsItems()
        val list = newsItemList.value
        list?.let {
            assert(it.isNotEmpty())
            val newsItemDb = it.get(0)
            assertThat(newsItemDb.id, `is`("1"))
        }
    }
}