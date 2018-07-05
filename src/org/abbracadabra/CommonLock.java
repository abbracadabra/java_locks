package org.abbracadabra;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public abstract class CommonLock {
	
	protected final AtomicLong atomicOps = new AtomicLong();
	
	protected transient Thread currOwnerThread;// current thread holding the lock
	
	public abstract boolean tryLock();
	
	public abstract boolean tryLock(long time,TimeUnit unit);
	
	public abstract void Lock();
	
	public abstract void unLock();
	
	public long getCount() {
		return atomicOps.get();
	}
	
	public void clearCount() {
		atomicOps.set(0L);
	}
	
	public Thread getCurrOwnerThread() {
		return currOwnerThread;
	}

	protected void setCurrOwnerThread(Thread currOwnerThread) {
		this.currOwnerThread = currOwnerThread;
	}
	
	public void checkCurrentThread() {
		Thread curr = Thread.currentThread();
		if (curr != getCurrOwnerThread()) {
			throw new RuntimeException();
		}
	}
	
	static class Node{
		Node(Thread t,long count,int status){
			this.t = t;
			this.status = status;
			this.count = count;
		}
		Node(long count,int status){
			this.t = Thread.currentThread();
			this.status = status;
			this.count = count;
		}
		Thread t;
		int status;
		long count;
	}

	abstract Condition newCondition();
	
	public static abstract class Condition {

		abstract void await();
		
		abstract void signal();
		
		abstract void signalAll();
		
		protected Node head;
		
		protected Node tail;
	}
}