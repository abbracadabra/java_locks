package org.abbracadabra;

import java.util.concurrent.atomic.AtomicLong;

public class Semaphore {

	// default to zero
	private AtomicLong atomicOps = new AtomicLong(0L);

	public Semaphore(long num) {
		atomicOps.set(num);
	}

	public void acquire() {
		atomicOps.decrementAndGet();
		while (atomicOps.get()<1) {
			//keep the thread dormant for a while to prevent the cpu from wasting too much time doing noting
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
}
