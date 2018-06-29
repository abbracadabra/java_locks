package org.abbracadabra;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class QueuedLock extends CommonLock{

	Queue waitingList = new ConcurrentLinkedQueue<Node>();
	
	public void QueuedLock() {
		Thread curr = Thread.currentThread();
		while(true) {
			if(curr == ((Node) waitingList.peek()).t && tryLock()) {
				waitingList.poll();
				break;
			}
		}
	}
	
	public void Enqueue(Node n) {
		waitingList.add(n);
	}

}
