package com.manwinder.nbcuniversalkotlin.database

import android.arch.persistence.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.utils.blockingObserve
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestNewsItemDao {

    private lateinit var newsItemDatabase: NewsItemDatabase
    private lateinit var newsItemDao: NewsItemDao

    @Before
    fun initDb() {
        newsItemDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), NewsItemDatabase::class.java).build()
        newsItemDao = newsItemDatabase.newsItemDao()
    }

    @After
    fun closeDb() {
        newsItemDatabase.close()
    }

    @Test
    fun isDatabaseEmpty() {
        assertThat(newsItemDao.getNumOfNewsItems(), `is`(0))
    }

    @Test
    fun insertNewsItem() {
        newsItemDao.insert(NewsItem("0"))

        val newsItemList = newsItemDao.getNewsItems()
        assert(newsItemList.isNotEmpty())
        assertThat(newsItemList.first().id, `is`("0"))
    }

    @Test
    fun insertNewsItems() {
        newsItemDao.insert(NewsItem("0"))
        newsItemDao.insert(NewsItem("1"))
        newsItemDao.insert(NewsItem("2"))

        val newsItemList = newsItemDao.getNewsItems()
        assert(newsItemList.isNotEmpty())

        var i = 0
        newsItemList.forEach { newsItem ->
            assertThat(newsItem.id, `is`("$i"))
            i += 1
        }
    }

    @Test
    fun checkNewsItemsDateOrder() {
        newsItemDao.insert(NewsItem("0", published = "2018-07-30T14:00:00Z"))
        newsItemDao.insert(NewsItem("1", published = "2018-07-30T15:00:00Z"))
        newsItemDao.insert(NewsItem("2", published = "2018-07-30T16:00:00Z"))

        val newsItemList = newsItemDao.getNewsItemsInOrder()
        assert(newsItemList.isNotEmpty())

        assertThat(newsItemList.first().id, `is`("2"))
        assertThat(newsItemList.first().published, `is`("2018-07-30T16:00:00Z"))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertNewsItemLiveData() {
        newsItemDao.insert(NewsItem("0"))

        val newsItemList = newsItemDao.getNewsItemsLiveData().blockingObserve()
        assertNotNull(newsItemList)
        assert(newsItemList!!.isNotEmpty())
        assertThat(newsItemList.first().id, `is`("0"))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertNewsItemsLiveData() {
        newsItemDao.insert(NewsItem("0"))
        newsItemDao.insert(NewsItem("1"))
        newsItemDao.insert(NewsItem("2"))

        val newsItemList = newsItemDao.getNewsItemsLiveData().blockingObserve()
        assertNotNull(newsItemList)
        assert(newsItemList!!.isNotEmpty())

        var i = 0
        newsItemList.forEach { newsItem ->
            assertThat(newsItem.id, `is`("$i"))
            i += 1
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun checkNewsItemsDateOrderLiveData() {
        newsItemDao.insert(NewsItem("0", published = "2018-07-30T14:00:00Z"))
        newsItemDao.insert(NewsItem("1", published = "2018-07-30T15:00:00Z"))
        newsItemDao.insert(NewsItem("2", published = "2018-07-30T16:00:00Z"))

        val newsItemList = newsItemDao.getNewsItemsInOrderLiveData().blockingObserve()
        assertNotNull(newsItemList)
        assert(newsItemList!!.isNotEmpty())
        assertThat(newsItemList.first().id, `is`("2"))
        assertThat(newsItemList.first().published, `is`("2018-07-30T16:00:00Z"))
    }

}