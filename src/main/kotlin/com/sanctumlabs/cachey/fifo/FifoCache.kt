package com.sanctumlabs.cachey.fifo

import com.sanctumlabs.cachey.GenericCache

/**
 * [FifoCache] caches at most [maximumSize] items that are recently [set]
 */
class FifoCache<K, V>(private val cache: GenericCache<K, V>, private val maximumSize: Int = DEFAULT_SIZE) :
    GenericCache<K, V> by cache {

    private val keyMap = object : LinkedHashMap<K, Boolean>(maximumSize, LOAD_FACTOR) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, Boolean>): Boolean {
            val hasTooManyCachedItems = size > maximumSize
            if (hasTooManyCachedItems) {
                oldestKeyToRemove = eldest.key
            }
            return hasTooManyCachedItems
        }
    }

    private var oldestKeyToRemove: K? = null

    override fun set(key: K, value: V) {
        cache[key] = value
        cycleKeyMap(key)
    }

    override fun get(key: K): V? {
        keyMap[key] = true
        return cache[key]
    }

    override fun clear() {
        keyMap.clear()
        cache.clear()
    }

    private fun cycleKeyMap(key: K) {
        keyMap[key] = PRESENT
        oldestKeyToRemove?.let { cache.remove(it) }
        oldestKeyToRemove = null
    }

    companion object {
        private const val DEFAULT_SIZE = 100
        private const val PRESENT = true
    }
}
