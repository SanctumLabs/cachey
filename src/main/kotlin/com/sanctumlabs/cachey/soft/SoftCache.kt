package com.sanctumlabs.cachey.soft

import com.sanctumlabs.cachey.Cache
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference

/**
 * [SoftCache] caches items with a [SoftReference] wrapper
 */
class SoftCache(private val cache: Cache) : Cache by cache {
    private val referenceQueue = ReferenceQueue<Any>()

    override fun set(key: Any, value: Any) {
        removeUnreachableItems()
        val softEntry = SoftEntry(key, value, referenceQueue)
        cache[key] = softEntry
    }

    override fun remove(key: Any) {
        cache.remove(key)
        removeUnreachableItems()
    }

    override fun get(key: Any): Any? {
        val softEntry = cache[key] as SoftEntry?
        softEntry?.get()?.let { return it }
        cache.remove(key)
        return null
    }

    private fun removeUnreachableItems() {
        var softEntry = referenceQueue.poll() as SoftEntry?

        while (softEntry != null) {
            val key = softEntry.key
            cache.remove(key)
            softEntry = referenceQueue.poll() as SoftEntry?
        }
    }
}
