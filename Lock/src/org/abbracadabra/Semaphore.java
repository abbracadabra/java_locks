package org.abbracadabra;

import java.util.concurrent.atomic.AtomicLong;

public class Semaphore {

	// default to zero
	private AtomicLong atomicOps = new AtomicLong(0L);

	public Semaphore(long num) {
		this(num, false);
	}
	
	public Semaphore(long num,boolean fair) {
		atomicOps.set(num);
	}

	public boolean tryAcquire(long num) {
		if(num<0) {
			throw new RuntimeException("illegal argument");
		}
		long expect = atomicOps.get();
		long update = expect-num;
		if(update>=0 && atomicOps.compareAndSet(expect, update)) {
			return true;
		}else {
			return false;
		}
	}
	
	public void acquire(long num) {
		if(num<0) {
			throw new RuntimeException("illegal argument");
		}
		while (!tryAcquire(num)) {
			
		}
	}
	
	public void release(long num) {
		if(num<0) {
			throw new RuntimeException("illegal argument");
		}
		atomicOps.addAndGet(num);
	}
}
