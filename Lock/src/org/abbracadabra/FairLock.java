package org.abbracadabra;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class FairLock extends QueuedLock{

	protected final AtomicBoolean atomicOps = new AtomicBoolean(true);
	
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
		QueuedLock(new Node(0));
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
