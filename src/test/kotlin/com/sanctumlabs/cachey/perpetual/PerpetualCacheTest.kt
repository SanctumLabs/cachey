package com.sanctumlabs.cachey.perpetual

import com.sanctumlabs.cachey.BaseCacheTest
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class PerpetualCacheTest : BaseCacheTest() {
    init {
        cache = PerpetualCache()
    }

    @Test
    fun `should keep all entries`() {
        for (i in 0..99) {
            assertNotNull(cache[i])
        }
    }
}
