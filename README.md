# LFU cache


A data structure for Least Frequently Used (LFU) cache. Supports following operations: 
  get and set.

get(key) - Get the value of the key if the key exists in the cache, otherwise return null

set(key, value) - Set or insert the value if the key is not already present. 
When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. 
when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

// Get procedure
// 1. Get LFUCacheEntry reference from HashMap
// 2. Find Frequency Node from the Entry Node
// 3. Remove Entry Node from Frequency Node list and add it to next freuquency Node
//    list
//    a. If New frequency Node doesn't exist, create a new one and add its entry
//       into frequency Node set
//    b. If Previous Frequency Node list is now empty remove that frequency Node

// Set procedure
// 1. If same key already exists 
//    Remove that LFUCacheEntry from Frequency Node & remove frequnecy Node if its
//    Empty
//    Remove that key & corresponding LFUCacheEntry from HashMap
//    If Cache is full remove very first entry from very first frequency Node in list
   
// 2. Add New LFUCacheEntry into HashMap
// 3. Add this new Entry into Frequency Node of frequency 1
//    a. Create if such a node doesn't already exist
