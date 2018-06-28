package org.abbracadabra;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReentrantLock {
	
	private AtomicBoolean atomicOps = new AtomicBoolean(true);

	private transient Thread currOwnerThread;// current thread holding the lock

	private volatile long count = 0;// lock count
	
	public ReentrantLock(boolean fair) {

	}
	
	public ReentrantLock() {
		this(false);
	}

	public void Lock() {
		while (!tryLock()) {

		}
	}

	public boolean tryLock() {
		Thread curr = Thread.currentThread();
		if (curr == getCurrOwnerThread()) {
			count++;
			return true;
		} else if (atomicOps.compareAndSet(true, false)) {
			setCurrOwnerThread(curr);
			count++;
			return true;
		} else {
			return false;
		}
	}

	public boolean tryLock(long timeout, TimeUnit unit) {
		long duration = unit.toNanos(timeout);
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

	public void unLock() {
		Thread curr = Thread.currentThread();
		if (curr != getCurrOwnerThread()) {
			throw new RuntimeException();
		} else {
			if (count > 1) {
				count--;
				return;
			} else {
				count = 0;
				setCurrOwnerThread(null);
				atomicOps.set(true);
			}
		}
	}

	public Thread getCurrOwnerThread() {
		return currOwnerThread;
	}

	public void setCurrOwnerThread(Thread currOwnerThread) {
		this.currOwnerThread = currOwnerThread;
	}

}
