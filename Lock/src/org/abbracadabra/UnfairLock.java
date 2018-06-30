package org.abbracadabra;

import java.util.concurrent.TimeUnit;

public class UnfairLock extends QueuedLock{

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
		if(!tryLock()) {
			Enqueue(new Node(1,0));
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
}
