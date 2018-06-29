package org.abbracadabra;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class FairLock extends QueuedLock{

	protected final AtomicBoolean atomicOps = new AtomicBoolean(true);
	
	@Override
	public boolean tryLock() {
		Thread curr = Thread.currentThread();
		if(waitingList.peek()==null && )
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Lock() {
		Thread curr = Thread.currentThread();
		Enqueue(new Node(curr,0));
		QueuedLock();
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
