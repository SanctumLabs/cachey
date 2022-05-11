package com.sanctumlabs.cachey.expirable

import com.sanctumlabs.cachey.BaseCacheTest
import com.sanctumlabs.cachey.perpetual.PerpetualCache
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

internal class ExpirableCacheTest : BaseCacheTest() {
    init {
        cache = ExpirableCache(PerpetualCache(), TimeUnit.SECONDS.toMillis(5))
    }

    @Test
    fun `should expire cache`() {
        Thread.sleep(TimeUnit.SECONDS.toMillis(5))
        assertEquals(0, cache.size)
    }

    @Test
    fun `should expire multiple times`() {
        Thread.sleep(TimeUnit.SECONDS.toMillis(5))
        assertEquals(0, cache.size)
        cache[1] = 1
        assertEquals(1, cache.size)
        Thread.sleep(TimeUnit.SECONDS.toMillis(5))
        assertEquals(0, cache.size)
    }
}
