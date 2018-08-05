package com.manwinder.nbcuniversalkotlin.api

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.manwinder.nbcuniversalkotlin.network.Status
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class NBCApiTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer

    private lateinit var nbcApi: NBCApi

    @Before
    @Throws(IOException::class)
    fun setupServer() {
        mockWebServer = MockWebServer()
        nbcApi = NBCApi.getNBCApi()
    }

    @After
    @Throws(IOException::class)
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, Collections.emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testNewsItemsRetrieval() {
        enqueueResponse("news_response.json")

        val newsItemsResponse = nbcApi.getNewsItems().blockingObserve()
        assertThat(newsItemsResponse?.status, `is`(Status.SUCCESS))

        // without the timeout this request sometimes hangs
        mockWebServer.takeRequest(5, TimeUnit.SECONDS)

        assertNotNull(newsItemsResponse)
        assertNotNull(newsItemsResponse?.data)
        assertNotNull(newsItemsResponse?.data?.data)

        val newsItemList = newsItemsResponse?.data?.data

        assertThat(newsItemList?.size, `is`(19))

        assertNull(newsItemList?.first()?.items)

        assertThat(newsItemList?.first()?.id, `is`("playlist/mmlsnnd_20381145"))
        assertThat(newsItemList?.first()?.type, `is`("Hero"))

        assertNotNull(newsItemList?.get(2)?.items)
        assertThat(newsItemList?.get(2)?.items?.size, `is`(6))
        assertThat(newsItemList?.get(2)?.id, `is`("curation/web/news/"))
        assertThat(newsItemList?.get(2)?.type, `is`("Section"))
        assertThat(newsItemList?.get(2)?.items?.get(0)?.id, `is`("ncna890251"))
        assertThat(newsItemList?.get(2)?.items?.get(0)?.type, `is`("article"))

        assertNull(newsItemList?.get(3)?.items)
        assertNotNull(newsItemList?.get(3)?.videos)
        assertThat(newsItemList?.get(3)?.videos?.size, `is`(10))
    }
}

// Extension

/**
 * This function is also declared in com(androidTest) aka instrumentation tests.
 * This package is unable to access that function
 */
fun <T> LiveData<T>.blockingObserve(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    observeForever(innerObserver)
    latch.await(2, TimeUnit.SECONDS)
    return value
}