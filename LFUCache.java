import java.util.*;

public class LFUCache<K, V> {
    /* Key value store for LFU cache
       The key is normal key and value is a reference to 
       a node in frequency list. This node will point to
       its parent which is head of linkedlist for particular
       frequency */
    HashMap<K, LFUCacheEntry<K, V>> kvStore;
    
    /* A Doubly linked list of frequency nodes */
    NodeList freqList;
    
    /* HashMap for storing frequencyNode entries */
    HashMap<Integer, FrequencyNode> frequencyMap;

    /* Capacity of cache */
    int capacity;

    /* current size of Cache */
    int size;

    public LFUCache(int capacity) {
	this.capacity = capacity;
	size = 0;
	kvStore = new HashMap<K, LFUCacheEntry<K, V>>();
	freqList = new NodeList();
	frequencyMap = new HashMap<Integer, FrequencyNode>();
    }

    public void delete(LFUCacheEntry<K, V> entry) {
    	if (!kvStore.containsKey(entry.key)) 
    	    return;

    	kvStore.remove(entry.key);
    	entry.frequencyNode.lfuCacheEntryList.remove(entry);
    	if (entry.frequencyNode.lfuCacheEntryList.length <= 0) {
    	    frequencyMap.remove(entry.frequencyNode.frequency);
    	    freqList.remove(entry.frequencyNode);
    	}
	size--;
    }
    
    
    public FrequencyNode getFrequencyNode(int frequency) {
	if (!frequencyMap.containsKey(frequency - 1) &&
	    !frequencyMap.containsKey(frequency) &&
	    frequency != 1) {
	    System.out.println("Request for Frequency Node " + frequency +
			       " But " + frequency + " or " + (frequency - 1) + 
			       " Doesn't exist");
	    return null;
 
	}

	if (!frequencyMap.containsKey(frequency)) {
	    FrequencyNode newFrequencyNode = new FrequencyNode(frequency);
	    if (frequency != 1)
		freqList.insertAfter(frequencyMap.get(frequency - 1), 
				 newFrequencyNode);
	    else
		freqList.prepend(newFrequencyNode);
	    frequencyMap.put(frequency, newFrequencyNode);
	}

	return frequencyMap.get(frequency);
    }

    // public void insert(K key, V value, int frequency) {
    // 	/* Create freq Node with freq 1 if doesn't exist - Every new entry
    // 	   has frequency 1 */
    // 	FrequencyNode frequencyNode = null;
    // 	System.out.println("Inserting new key " + key +  " for frequency " + frequency);
    // 	if (!frequencyMap.containsKey(frequency)) {
    // 	    frequencyNode = new FrequencyNode(frequency);
    // 	    if (frequency == 1) {
    // 		freqList.prepend(frequencyNode);
    // 	    } else {
    // 		freqList.insertAfter(frequencyMap.get(frequency - 1), frequencyNode);
    // 	    }
    // 	    frequencyMap.put(frequency, frequencyNode);
    // 	} else {
    // 	    frequencyNode = frequencyMap.get(frequency);
    // 	}

    // 	/* Create a new entry node - and insert into freqList of freq 1 */	
    // 	LFUCacheEntry<K, V> entry = new LFUCacheEntry<K,V>(key, value, frequencyNode);
    // 	frequencyNode.lfuCacheEntryList.append(entry);
    // 	kvStore.put(key, entry);
    // 	size++;
    // }

    public void set(K key, V value) {

	if (kvStore.containsKey(key)) {
	    /* Remove old key if exists */
	    delete(kvStore.get(key));
	} else if (size == capacity) {
	    /* If cache size if full remove first element from freq list */
	    FrequencyNode fNode = (FrequencyNode) freqList.head;
	    LFUCacheEntry<K, V> entry = (LFUCacheEntry<K, V>) fNode.lfuCacheEntryList.head;
	    delete(entry);
	    System.out.println("Cache full. Removed entry " + entry);
	}
	FrequencyNode newFrequencyNode = getFrequencyNode(1);
	LFUCacheEntry<K, V> entry = new LFUCacheEntry<K, V>(key, value, 
							    newFrequencyNode);
	kvStore.put(key, entry);
	newFrequencyNode.lfuCacheEntryList.append(entry);
	size++;
    }


    public V get(K key) {
	if (!kvStore.containsKey(key))
	    return null;

	LFUCacheEntry<K, V> entry = kvStore.get(key);
	FrequencyNode newFrequencyNode = 
	    getFrequencyNode(entry.frequencyNode.frequency + 1);
	entry.frequencyNode.lfuCacheEntryList.remove(entry);
	newFrequencyNode.lfuCacheEntryList.append(entry);
	if (entry.frequencyNode.lfuCacheEntryList.length <= 0) {
	    frequencyMap.remove(entry.frequencyNode.frequency);
	    freqList.remove(entry.frequencyNode);
	}
	entry.frequencyNode = newFrequencyNode;

	return entry.value;
    }
}





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
