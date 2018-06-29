package org.abbracadabra;

abstract class Condition {

	abstract void await();
	
	abstract void signal();
	
	abstract void signalAll();
	
	protected Node head;
	
	protected Node tail;
	
	static class Node{
		Thread t;
		Node prev;
		Node next;
	}
}
