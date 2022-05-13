package com.sanctumlabs.cachey.weak

import com.sanctumlabs.cachey.Cache
import java.lang.ref.ReferenceQueue

/**
 * A [WeakCache] caches items with a [WeakReference] wrapper
 */
class WeakCache(private val cache: Cache) : Cache by cache {
    private val referenceQueue = ReferenceQueue<Any>()

    override fun set(key: Any, value: Any) {
        removeUnreachableItems()
        val weakEntry = WeakEntry(key, value, referenceQueue)
        cache[key] = weakEntry
    }

    override fun get(key: Any): Any? {
        val weakEntry = cache[key] as? WeakEntry
        weakEntry?.get()?.let { return it }
        cache.remove(key)
        return null
    }

    override fun remove(key: Any) {
        cache.remove(key)
        removeUnreachableItems()
    }

    private fun removeUnreachableItems() {
        var weakEntry = referenceQueue.poll() as WeakEntry?

        while (weakEntry != null) {
            val key = weakEntry.key
            cache.remove(key)
            weakEntry = referenceQueue.poll() as WeakEntry?
        }
    }
}
