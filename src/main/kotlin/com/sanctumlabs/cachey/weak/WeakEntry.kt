package com.sanctumlabs.cachey.weak

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

internal class WeakEntry(val key: Any, value: Any, referenceQueue: ReferenceQueue<Any>) :
    WeakReference<Any>(value, referenceQueue)
