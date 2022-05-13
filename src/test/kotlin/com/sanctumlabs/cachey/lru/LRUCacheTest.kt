package com.sanctumlabs.cachey.lru

import com.sanctumlabs.cachey.BaseCacheTest
import com.sanctumlabs.cachey.perpetual.PerpetualCache
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.Random

internal class LRUCacheTest : BaseCacheTest() {
    init {
        cache = LRUCache(PerpetualCache(), 31)
    }

    @Test
    fun `should only cache the most recent 31 items`() {
        for (i in 0..99) {
            cache[i] = i
        }

        assertEquals(31, cache.size)
    }

    @Test
    fun `should cache items with access order`() {
        for (i in 0..30) {
            cache[i] = i
        }

        val random = Random()
        val indices = IntArray(31)

        for (i in 0..30) {
            indices[i] = random.nextInt(31)
        }

        for (index in indices) {
            cache[index]
        }

        for (index in indices) {
            assertEquals(index, cache[index])
        }
    }

    @Test
    override fun `Should remove entry`() {
        assertNotNull(cache[69])
        cache.remove(69)
        assertNull(cache[69])
    }
}
