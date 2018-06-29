package org.abbracadabra;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class UnfairLock extends QueuedLock{

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
		if(!tryLock()) {
			Thread curr = Thread.currentThread();
			Enqueue(new Node(curr, 0));
			QueuedLock();
		}
	}

	@Override
	public void unLock() {
		waitingList.poll();
	}

	@Override
	Condition newCondition() {
		return new QueueCondition(this);
	}

}
