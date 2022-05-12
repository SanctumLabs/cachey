package com.sanctumlabs.cachey.lru

import com.sanctumlabs.cachey.Cache

/**
 * [LRUCache] flushes items that are **Least Recently Used** and keeps at most [maximumSize] items
 */
class LRUCache<K, V>(private val cache: Cache<K, V>, private val maximumSize: Int = DEFAULT_SIZE) :
    Cache<K, V> by cache {

    private val keyMap = object : LinkedHashMap<K, Boolean>(maximumSize, 0.75f, true) {
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
        keyMap[key]
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
        const val DEFAULT_SIZE = 100
        const val PRESENT = true
    }
}
