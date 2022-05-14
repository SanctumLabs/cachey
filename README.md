cachey
=================================================================

[![codecov](https://codecov.io/gh/SanctumLabs/cachey/branch/main/graph/badge.svg?token=QaXhB4f8Fk)](https://codecov.io/gh/SanctumLabs/cachey)
[![Lint, Test & Build](https://github.com/SanctumLabs/cachey/actions/workflows/lint_test_build.yml/badge.svg)](https://github.com/SanctumLabs/cachey/actions/workflows/lint_test_build.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/011ee06ae46e468eadbda6d604a944d2)](https://www.codacy.com/gh/SanctumLabs/cachey/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SanctumLabs/cachey&amp;utm_campaign=Badge_Grade)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.6.21-blue.svg)](https://kotlinlang.org)

A feather weight cache implementation in Kotlin

## Usage

```gradle
implementation("com.sanctumlabs:cachey:1.0.0")
```

> with gradle

```xml
<dependency>
    <groupId>com.sanctumlabs</groupId>
    <artifactId>cachey</artifactId>
    <version>1.0.0</version>
</dependency>
```

> with maven


## Background

Caching is a critical technology in many high performance scalable applications. There are many choices in caching
framework, including (but not limited to) [Memcache](https://memcached.org/)
, [cache2k](https://cache2k.org/) etc.

## LRU Cache

LRU cache uses **[Least Recently Used](https://en.wikipedia.org/wiki/Cache_replacement_policies#LRU)** strategy to
evict the least recently used items. This keeps only a certain number of entries that are recently used and removes
others.

We only keep `maximumSize` entries at most. Here we leverage the class `java.util.LinkedHashMap` to trace the usage of
entries, and `oldestKeyToRemove` is the one to be removed, which is the eldest entry ordered by used frequency.
Method `cycleKeyMap` is responsible for removing entries that are too old and less used. Simple and straightforward.

## Expirable Cache

This uses a `flushInterval`, and will clear the cache every `flushInterval` milliseconds. It's typically a **
scheduled task**, we can use a background thread to do the task, but again, to make it simple, we just `recycle` before
every operation in our cache.

## Other Implementations

Besides the three implementations above, here are several implementations such as `FIFOCache`, `SoftCache`
and `WeakCache`, implemented
with **[First-in-first-out algorithm](https://en.wikipedia.org/wiki/FIFO_%28computing_and_electronics%29)**
, **[Soft Reference](https://en.wikipedia.org/wiki/Soft_reference)**,
and **[Weak Reference](https://en.wikipedia.org/wiki/Weak_reference)** respectively.
