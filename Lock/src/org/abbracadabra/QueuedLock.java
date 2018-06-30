package org.abbracadabra;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

import org.abbracadabra.CommonLock.Node;

abstract class QueuedLock extends CommonLock{

	Queue waitingList = new ConcurrentLinkedQueue<Node>();
	
	public void Enqueue(Node node) {
		waitingList.add(node);
		Thread curr = Thread.currentThread();
		while(true) {
			//only the first in the queue can acquire the lock
			//recover lock count
			if(curr == ((Node) waitingList.peek()).t && atomicOps.compareAndSet(0, node.count)) {
				waitingList.poll();
				break;
			}
		}
	}
	
	@Override
	Condition newCondition() {
		return new $Condition();
	}
	
	class $Condition extends Condition {
		
		Queue conditionWaitingList = new ConcurrentLinkedQueue<Node>();
		
		@Override
		void signalAll() {
			Node n;
			while((n=(Node) conditionWaitingList.poll())!=null) {
				LockSupport.unpark(head.t);
			}
		}
		
		@Override
		void signal() {
			Node head = (Node) conditionWaitingList.poll();//poll the first
			if(head != null) {
				LockSupport.unpark(head.t);
			}
		}
		
		@Override
		void await() {
			// TODO Auto-generated method stub
			Thread curr = Thread.currentThread();
			if (curr != getCurrOwnerThread()) {
				throw new RuntimeException();
			}
			//long count = lock.count;//a copy of lock count
			Node n = new Node(curr,getCount(),0);
			clearCount();//surrender the lock before parked
			conditionWaitingList.add(n);
			LockSupport.park();//block this thread until signaled
			Enqueue(n);//signaled,now race for the lock
		}
}
}
