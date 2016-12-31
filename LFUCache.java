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

    public void delete(K key, V value) {
	if (!kvStore.containsKey(key)) 
	    return;

	LFUCacheEntry<K, V> entry = kvStore.remove(key);
	FrequencyNode frequencyNode = entry.frequencyNode;
	frequencyNode.lfuCacheEntryList.remove(entry);
	if (frequencyNode.lfuCacheEntryList.length <= 0) {
	    frequencyMap.remove(frequencyNode.frequency);
	    freqList.remove(frequencyNode);
	}
	size--;
    }
    
    public void delete(LFUCacheEntry<K, V> entry) {
	delete(entry.key, entry.value);
    }

    
    public void insert(K key, V value, int frequency) {
	/* Create freq Node with freq 1 if doesn't exist - Every new entry
	   has frequency 1 */
	FrequencyNode frequencyNode = null;

	if (!frequencyMap.containsKey(frequency)) {
	    frequencyNode = new FrequencyNode(frequency);
	    if (frequency == 1) {
		freqList.prepend(frequencyNode);
	    } else {
		freqList.insertAfter(frequencyMap.get(frequency - 1), frequencyNode);
	    }
	    frequencyMap.put(frequency, frequencyNode);
	} else {
	    frequencyNode = frequencyMap.get(frequency);
	}

	/* Create a new entry node - and insert into freqList of freq 1 */	
	LFUCacheEntry<K, V> entry = new LFUCacheEntry<K,V>(key, value, frequencyNode);
	frequencyNode.lfuCacheEntryList.append(entry);
	kvStore.put(key, entry);
	size++;
    }

    public void set(K key, V value) {

	if (kvStore.containsKey(key)) {
	    /* Remove old key if exists */
	    delete(key, value);
	} else if (size == capacity) {
	    /* If cache size if full remove first element from freq list */
	    FrequencyNode fNode = (FrequencyNode) freqList.head;
	    LFUCacheEntry<K, V> entry = (LFUCacheEntry<K, V>) fNode.lfuCacheEntryList.head;
	    delete(entry);
	}
	
	insert(key, value, 1);
    }


    public V get(K key) {
	if (!kvStore.containsKey(key))
	    return null;

	LFUCacheEntry<K, V> entry = kvStore.get(key);
	delete(entry);
	insert(entry.key, entry.value, entry.frequencyNode.frequency + 1);
	return entry.value;
    }
    
}






