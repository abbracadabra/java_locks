package org.abbracadabra;

import java.util.concurrent.atomic.AtomicBoolean;

public class Mutex {
	private AtomicBoolean atomicOps = new AtomicBoolean(true);
	
	public void Lock() {
		while(!atomicOps.compareAndSet(true, false)) {
			try {
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
