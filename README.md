# LFU cache


A data structure for Least Frequently Used (LFU) cache. Supports following operations: 
  get and set.

get(key) - Get the value of the key if the key exists in the cache, otherwise return null

set(key, value) - Set or insert the value if the key is not already present. 
When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. 
when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.