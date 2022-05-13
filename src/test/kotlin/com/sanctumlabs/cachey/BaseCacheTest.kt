package com.sanctumlabs.cachey

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.DefaultAsserter.assertNotNull
import kotlin.test.DefaultAsserter.assertNull
import kotlin.test.DefaultAsserter.assertTrue

internal abstract class BaseCacheTest {
    protected lateinit var cache: GenericCache<Any, Any>

    @BeforeEach
    fun setUp() {
        for (i in 0..99) {
            cache[i] = i
        }
    }

    @AfterEach
    fun tearDown() {
        cache.clear()
    }

    @Test
    fun `Should clear all entries`() {
        assertTrue("Cache size should be greater than 0", cache.size > 0)
        cache.clear()
        assertTrue("cache size should be 0", cache.size == 0)
    }

    @Test
    open fun `Should remove entry`() {
        assertNotNull("value at key 1 should not be null", cache[1])

        cache.remove(1)

        assertNull("value at key 1 should return null", cache[1])
    }
}
