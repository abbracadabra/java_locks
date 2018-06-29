package org.abbracadabra;

import java.util.concurrent.TimeUnit;

public class ReentrantLock {

	private CommonLock handler = null;
	
	enum LockType{
		fair,
		unfair,
		race
	}
	
	private LockType type = null;
	
	public ReentrantLock(LockType type) {
		this.type = type;
		switch (type) {
		case fair:
			handler = new FairLock();
			break;
		case unfair:
			handler = new UnfairLock();
			break;
		case race:
			handler = new RaceLock();
			break;
		default:
			handler = new RaceLock();
			break;
		}
	}
	
	public ReentrantLock() {
		this(LockType.race);
	}

	public void Lock() {
		while (!tryLock()) {
			
		}
	}
	
	public Condition newCondition() {
		Condition c = null ;
		switch (type) {
		case fair:
			c = new QueueCondition((QueuedLock) handler);
			break;
		case unfair:
			c = new QueueCondition((QueuedLock) handler);
			break;
		case race:
			c = new ConditionC((RaceLock) handler);
			break;
		}
		return c;
	}

	public boolean tryLock() {
		return handler.tryLock();
	}

	public boolean tryLock(long timeout, TimeUnit unit) {
		return handler.tryLock(timeout, unit);
	}

	public void unLock() {}
	

}
