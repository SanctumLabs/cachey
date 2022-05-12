package com.sanctumlabs.cachey.fifo

import com.sanctumlabs.cachey.BaseCacheTest
import com.sanctumlabs.cachey.perpetual.PerpetualCache
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class FifoCacheTest : BaseCacheTest() {
    init {
        cache = FifoCache(PerpetualCache(), 31)
    }

    @Test
    fun `should only cache number of items set`() {
        assertEquals(31, cache.size)
    }

    @Test
    fun `should only cache latest items`() {
        for (i in 0..99) {
            if (i < 100 - 31) {
                // only last 31 items should be cached
                assertNull(cache[i])
            } else {
                assertNotNull(cache[i])
            }
        }
    }

    @Test
    fun `should remove entry`() {
        assertNotNull(cache[69])
        cache.remove(69)
        assertNull(cache[69])
    }
}
