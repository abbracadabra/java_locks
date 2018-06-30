package org.abbracadabra;

import java.util.concurrent.TimeUnit;

public class FairLock extends QueuedLock{

	@Override
	public boolean tryLock() {
		return false;//try lock runs contrary to the concept of a fair lock
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) {
		return false;//try lock runs contrary to the concept of a fair lock
	}

	@Override
	public void Lock() {
		Enqueue(new Node(1,0));
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
