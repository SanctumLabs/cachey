package com.sanctumlabs.cachey.expirable

import com.sanctumlabs.cachey.Cache
import com.sanctumlabs.cachey.GenericCache
import java.util.concurrent.TimeUnit

/**
 * [ExpirableCache] is a [Cache] that automatically expires entries after a given amount of time.
 */
class ExpirableCache<K, V>(
    private val delegate: GenericCache<K, V>,
    private val flushInterval: Long = TimeUnit.MINUTES.toMillis(1)
) : GenericCache<K, V> by delegate {
    private var lastFlushTime = System.nanoTime()

    override val size: Int
        get() {
            recycle()
            return delegate.size
        }

    override fun remove(key: K): V? {
        recycle()
        return delegate.remove(key)
    }

    override fun get(key: K): V? {
        recycle()
        return delegate[key]
    }

    private fun recycle() {
        val shouldRecycle = System.nanoTime() - lastFlushTime >= TimeUnit.MILLISECONDS.toNanos(flushInterval)
        if (shouldRecycle) {
            delegate.clear()
            lastFlushTime = System.nanoTime()
        }
    }
}
