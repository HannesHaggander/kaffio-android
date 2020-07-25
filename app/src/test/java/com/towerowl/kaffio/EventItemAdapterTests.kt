package com.towerowl.kaffio

import com.towerowl.kaffio.adapters.EventItemAdapter
import com.towerowl.kaffio.adapters.EventItemData
import io.mockk.mockkClass
import org.junit.Assert
import org.junit.Test
import java.util.*

class EventItemAdapterTests {

    @Test
    fun `assure items updated correctly`() {
        EventItemAdapter { }.run {
            Assert.assertEquals(listOf<EventItemData>(), data)
            val testData = mutableListOf<EventItemData>().apply {
                for (i in 0..10) add(mockkClass(EventItemData::class))
            }
            updateData(testData, false)
            Assert.assertEquals(testData, data)
        }
    }

    @Test
    fun `filter items by title`() {
        val testItem = EventItemData("test1", UUID.randomUUID())

        EventItemAdapter { }.run {
            updateData(mutableListOf<EventItemData>().apply {
                add(testItem)
                add(EventItemData("test2", UUID.randomUUID()))
                add(EventItemData("test3", UUID.randomUUID()))
            }, false)
            filter(onFilter = { it.title.contains("test1", true) }, notify = false)
            Assert.assertEquals(data.size, 1)
            Assert.assertTrue(data.contains(testItem))
        }
    }

    @Test
    fun `verify filter reset`() {
        EventItemAdapter {}.run {
            updateData(mutableListOf<EventItemData>().apply {
                add(EventItemData("test1", UUID.randomUUID()))
                add(EventItemData("test2", UUID.randomUUID()))
            }, false)
            filter(onFilter = { false }, notify = false)
            Assert.assertEquals(data.size, 0)
            resetFilter(false)
            Assert.assertEquals(data.size, 2)
        }
    }

}