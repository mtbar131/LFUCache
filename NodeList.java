import java.util.*;

public class NodeList {
    Node head;
    Node tail;
    int length;

    public NodeList() {
	head = null;
	tail = null;
	length = 0;
    }

    public void prepend(Node node) {
	if (head == null) {
	    tail = node;
	    node.next = null;
	} else {
	    node.next = head;
	    head.prev = node;
	}
	head = node;
	node.prev = null;
	length++;
    }

    public void append(Node node) {
	if (tail == null) {
	    prepend(node);
	} else {
	    tail.next = node;
	    node.next = null;
	    node.prev = tail;
	    tail = node;
	}
	length++;
    }

    public void insertAfter(Node position, Node node) {
	if (position == tail) {
	    append(node);
	} else {
	    node.next = position.next;
	    node.prev = position;
	    position.next = node;
	    node.next.prev = node;
	    length++;
	}
    }

    public void remove(Node node) {
	if (node == tail && node == head) { /* single node in LinkedList */
	    head = null;
	    tail = null;
	} else if (node == tail) {
	    tail = tail.prev;
	    tail.next = null;
	} else if (node == head) {
	    head = head.next;
	    head.prev = null;
	} else {
	    node.next.prev = node.prev;
	    node.prev.next = node.next;
	}
	node.next = null;
	node.prev = null;
    }


    public void printList() {
	Node walk = head;
	while (walk != null) {
	    System.out.print("[" + walk + "] -> "); 
	    walk = walk.next;
	}
	System.out.println();
    }

    
    // public void test() {
    // 	NodeList<String> list = new NodeList<String>();
    // 	ArrayList<Node<String>> alist = new ArrayList<Node<String>>();
    // 	alist.add(new Node("a0"));
    // 	alist.add(new Node("a1"));
    // 	alist.add(new Node("a2"));
    // 	alist.add(new Node("a3"));
    // 	alist.add(new Node("a4"));
    // 	alist.add(new Node("a5"));
    // 	alist.add(new Node("a6"));
    // 	list.append(alist.get(0));
    // 	list.append(alist.get(1));
    // 	list.prepend(alist.get(2));
    // 	list.prepend(alist.get(3));
    // 	list.printList();
    // 	list.insertAfter(alist.get(3), alist.get(4));
    // 	list.printList();
    // 	list.insertAfter(alist.get(2), alist.get(5));
    // 	list.printList();
    // 	list.insertAfter(alist.get(1), alist.get(6));
    // 	list.printList();
    // 	list.remove(alist.get(1));
    // 	list.remove(alist.get(6));
    // 	list.remove(alist.get(3));
    // 	list.printList();

    // 	/* Output should be */
    // 	/*
    // 	[a3] -> [a2] -> [a0] -> [a1] -> 
    // 	[a3] -> [a4] -> [a2] -> [a0] -> [a1] -> 
    // 	[a3] -> [a4] -> [a2] -> [a5] -> [a0] -> [a1] -> 
    // 	[a3] -> [a4] -> [a2] -> [a5] -> [a0] -> [a1] -> [a6] -> 
    // 	[a4] -> [a2] -> [a5] -> [a0] -> 
    // 	*/
    // }
}

