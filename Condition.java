package org.abbracadabra;

import org.abbracadabra.CommonLock.Node;

abstract class Condition {

	abstract void await();
	
	abstract void signal();
	
	abstract void signalAll();
	
	protected Node head;
	
	protected Node tail;
	

}
