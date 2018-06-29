package org.abbracadabra;

import java.util.concurrent.TimeUnit;

public class UnfairLock extends QueuedLock{

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Lock() {
		// TODO Auto-generated method stub
	}

	@Override
	public void unLock() {
		// TODO Auto-generated method stub
	}

}
