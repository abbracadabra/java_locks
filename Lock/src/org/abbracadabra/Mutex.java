package org.abbracadabra;

import java.util.concurrent.atomic.AtomicBoolean;

public class Mutex {
	private AtomicBoolean atomicOps = new AtomicBoolean(true);
	
	public void Lock() {
		while(!atomicOps.compareAndSet(true, false)) {
			try {
				//keep the thread dormant for a while to prevent the cpu from wasting too much time doing noting
				Thread.currentThread().sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void unLock() {
		atomicOps.set(true);
	}
	
}
