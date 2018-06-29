package org.abbracadabra;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.LockSupport;

import org.abbracadabra.CommonLock.Node;


public class QueueCondition extends Condition{
	
	Queue waitingList = new ConcurrentLinkedQueue<Node>();
	
	final QueuedLock lock ;
	
	public QueueCondition(QueuedLock lock) {
		this.lock = lock;
	}
	
	@Override
	void signalAll() {
		Node n;
		while((n=(Node) waitingList.poll())!=null) {
			lock.Enqueue(head);
			LockSupport.unpark(head.t);
		}
	}
	
	@Override
	void signal() {
		Node head = (Node) waitingList.poll();//poll the first
		if(head != null) {
			lock.Enqueue(head);
			LockSupport.unpark(head.t);
		}
	}
	
	@Override
	void await() {
		// TODO Auto-generated method stub
		Thread curr = Thread.currentThread();
		if (curr != lock.getCurrOwnerThread()) {
			throw new RuntimeException();
		}
		//long count = lock.count;//a copy of lock count
		this.lock.unLock();//surrender the lock before parked
		waitingList.add(new Node(curr,0));
		LockSupport.park();//block this thread until signaled
		this.lock.QueuedLock();//signaled,now race for the lock
	}
};