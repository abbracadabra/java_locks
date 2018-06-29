package org.abbracadabra;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CommonLock {

	volatile long count = 0;
	
	protected AtomicBoolean atomicOps = new AtomicBoolean(true);
	
	protected transient Thread currOwnerThread;// current thread holding the lock
	
	public abstract boolean tryLock();
	
	public abstract boolean tryLock(long time,TimeUnit unit);
	
	public abstract void Lock();
	
	public abstract void unLock();
	
	public Thread getCurrOwnerThread() {
		return currOwnerThread;
	}

	protected void setCurrOwnerThread(Thread currOwnerThread) {
		this.currOwnerThread = currOwnerThread;
	}
	

}