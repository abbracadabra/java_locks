package org.abbracadabra;

import java.util.concurrent.atomic.AtomicLong;

public class Semaphore {

	// default to zero
	private AtomicLong atomicOps = new AtomicLong(0L);

	public Semaphore(long num) {
		atomicOps.set(num);
	}

	public void acquire() {
		while (true) {
			long update = atomicOps.decrementAndGet();
			if(update<0) {
				atomicOps.incrementAndGet();
			}else {
				break;
			}
		}
		
	}
	
	public void acquire(long num) {
		if(num<0) {
			throw new RuntimeException("illegal argument");
		}
		while (true) {
			long update = atomicOps.addAndGet(-num);
			if(update<0) {
				atomicOps.addAndGet(num);
			}else {
				break;
			}
			try {
				Thread.currentThread().sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void release() {
		atomicOps.incrementAndGet();
	}
	
	public void release(long num) {
		if(num<0) {
			throw new RuntimeException("illegal argument");
		}
		atomicOps.addAndGet(num);
	}
}
