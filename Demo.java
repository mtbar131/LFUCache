public class Demo {
    public static void main(String args[]) {
	LFUCache<Integer, Integer> cache = new LFUCache<Integer, Integer>(2);

	cache.set(1, 1);
	System.out.println(cache.get(1));       // returns 1
	cache.set(2, 2);
	cache.set(3, 3);    // evicts key 2
	System.out.println(cache.get(3));  
	System.out.println(cache.get(2));       // returns -1 (not found)
	System.out.println(cache.get(3));       // returns 3.
	cache.set(4, 4);    // evicts key 1.
	System.out.println(cache.get(1));       // returns -1 (not found)
	System.out.println(cache.get(3));       // returns 3
	System.out.println(cache.get(4));       // returns 4

    }
}
 
