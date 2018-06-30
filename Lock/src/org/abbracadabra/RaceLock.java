package org.abbracadabra;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

import org.abbracadabra.CommonLock.Node;


public class RaceLock extends CommonLock {

	@Override
	public boolean tryLock() {
		Thread curr = Thread.currentThread();
		if (curr == getCurrOwnerThread()) {
			atomicOps.incrementAndGet();
			return true;
		} else if (atomicOps.compareAndSet(0, 1)) {
			setCurrOwnerThread(curr);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) {
		long duration = unit.toNanos(time);
		long deadLine = System.nanoTime() + duration;
		for (;;) {
			duration = deadLine - System.nanoTime();
			if (duration < 1) {
				return false;
			} else {
				if (tryLock()) {
					return true;
				}
			}
		}

	}

	@Override
	public void Lock() {
		while (!tryLock()) {

		}
	}

	@Override
	public void unLock() {
		Thread curr = Thread.currentThread();
		if (curr != getCurrOwnerThread()) {
			throw new RuntimeException();
		} else {
			if (atomicOps.get()>1) {
				atomicOps.decrementAndGet();
			} else {
				setCurrOwnerThread(null);
				clearCount();
			}
		}
	}

	@Override
	Condition newCondition() {
		return new $Condition();
	}
	
	class $Condition extends Condition{
		
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
			Lock();//signaled,now race for lock
		}
}

}
