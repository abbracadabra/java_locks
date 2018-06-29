package org.abbracadabra;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class RaceLock extends CommonLock {

	protected final AtomicBoolean atomicOps = new AtomicBoolean(true);
	
	@Override
	public boolean tryLock() {
		Thread curr = Thread.currentThread();
		if (curr == getCurrOwnerThread()) {
			count++;
			return true;
		} else if (atomicOps.compareAndSet(true, false)) {
			setCurrOwnerThread(curr);
			count=1;
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
			if (count > 1) {
				count--;
			} else {
				count = 0;
				setCurrOwnerThread(null);
				atomicOps.set(true);
			}
		}
	}

	@Override
	Condition newCondition() {
		return new ConditionR(this);
	}

}
