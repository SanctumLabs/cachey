package com.sanctumlabs.cachey.soft

import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference

internal class SoftEntry(val key: Any, value: Any, referenceQueue: ReferenceQueue<Any>) :
    SoftReference<Any>(value, referenceQueue)
