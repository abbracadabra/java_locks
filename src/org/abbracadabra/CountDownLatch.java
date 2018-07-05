package org.abbracadabra;

import java.util.concurrent.atomic.AtomicLong;

public class CountDownLatch {

		private AtomicLong atomicOps = new AtomicLong(0L);
		
		public CountDownLatch(long num) {
			atomicOps.set(num);
		}
		
		public void countDown() {
			atomicOps.decrementAndGet();
		}
		
		public void await() {
			while (atomicOps.get()>0) {
				
			}
		}
}
