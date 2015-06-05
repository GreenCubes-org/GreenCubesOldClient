package org.greencubes.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Task implements IInvokeable {
	
	private static final Logger logger = Logger.getLogger(Task.class.getName());
	private final Runnable runnable;
	private ExecutionException exception;
	private boolean finished;
	private final ReentrantLock stateLock = new ReentrantLock();
	private final Condition finishedCondition = this.stateLock.newCondition();

	public Task(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public void invoke() {
		try {
			if(runnable != null)
				runnable.run();
			this.stateLock.lock();
			try {
				this.finished = true;
				this.finishedCondition.signalAll();
			} finally {
				this.stateLock.unlock();
			}
		} catch(Exception e) {
			logger.logp(Level.SEVERE, getClass().toString(), "invoke()", "Exception", e);
			this.stateLock.lock();
			try {
				this.exception = new ExecutionException(e);
				this.finishedCondition.signalAll();
			} finally {
				this.stateLock.unlock();
			}
		}
	}

	@Override
	public boolean isDoneWell() {
		this.stateLock.lock();
		try {
			return finished;
		} finally {
			this.stateLock.unlock();
		}
	}

	@Override
	public ExecutionException getException() {
		this.stateLock.lock();
		try {
			return exception;
		} finally {
			this.stateLock.unlock();
		}
	}


	@Override
	public boolean isDone() {
		this.stateLock.lock();
		try {
			return finished || exception != null;
		} finally {
			this.stateLock.unlock();
		}
	}


	@Override
	public boolean isCancelled() {
		return false;
	}
	
	@Override
	public void await() throws InterruptedException {
		this.stateLock.lock();
		try {
			while(!isDone())
				this.finishedCondition.await();
		} finally {
			this.stateLock.unlock();
		}
	}
}