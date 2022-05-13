package com.sanctumlabs.cachey.soft

import com.sanctumlabs.cachey.BaseCacheTest
import com.sanctumlabs.cachey.perpetual.PerpetualCache
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class SoftCacheTest : BaseCacheTest() {
    init {
        cache = SoftCache(PerpetualCache())
    }

    companion object {
        private const val ONE_MEGABYTE = 1024 * 1024
    }

    @Test
    fun `should clear unreachable items`() {
        val size = 2048 * 2
        for (i in 0 until size) {
            cache[i] = ByteArray(ONE_MEGABYTE)
        }
        System.gc()
        assertTrue { cache.size < size }
    }
}
