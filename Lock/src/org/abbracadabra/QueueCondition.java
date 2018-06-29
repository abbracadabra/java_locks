package org.abbracadabra;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.abbracadabra.Condition.Node;

public class QueueCondition extends Condition{
	
	List waitingList = new CopyOnWriteArrayList<Node>();
	
	CommonLock lock = null;
	
	public QueueCondition(QueuedLock lock) {
		this.lock = lock;
	}
	
	private QueueCondition() {}
	
	@Override
	void signalAll() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	void signal() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	void await() {
		// TODO Auto-generated method stub
		Thread curr = Thread.currentThread();
		if(curr!=lock.getCurrOwnerThread()) {
			throw new RuntimeException();
		}
	}
};
