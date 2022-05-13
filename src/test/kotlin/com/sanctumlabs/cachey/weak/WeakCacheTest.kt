package com.sanctumlabs.cachey.weak

import com.sanctumlabs.cachey.BaseCacheTest
import com.sanctumlabs.cachey.perpetual.PerpetualCache
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class WeakCacheTest : BaseCacheTest() {
    init {
        cache = WeakCache(PerpetualCache())
    }

    companion object {
        private const val ONE_MEGABYTE = 1024 * 1024
    }

    @Test
    fun `Should remove unreachable items`() {
        val size = 2048
        for (i in 0 until size) {
            cache[i] = ByteArray(ONE_MEGABYTE)
        }

        System.gc()
        assertTrue {
            cache.size < size
        }
    }
}
