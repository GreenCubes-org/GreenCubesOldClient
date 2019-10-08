/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.greencubes.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskWithResult<V> implements Future<V>, IInvokeable {
	
	private static final Logger logger = Logger.getLogger(TaskWithResult.class.getName());
	private final Callable<V> callable;
	private V result;
	private ExecutionException exception;
	private boolean cancelled;
	private boolean finished;
	private final ReentrantLock stateLock = new ReentrantLock();
	private final Condition finishedCondition = this.stateLock.newCondition();

	public TaskWithResult(Callable<V> callable) {
		this.callable = callable;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		this.stateLock.lock();
		try {
			if(this.result != null) {
				return false;
			}
			this.cancelled = true;

			this.finishedCondition.signalAll();

			return true;
		} finally {
			this.stateLock.unlock();
		}
	}
	
	public V getWellDoneResult() {
		this.stateLock.lock();
		try {
			if(!finished) {
				if(exception != null)
					throw new IllegalStateException("Exception occur while executing", exception);
				throw new IllegalStateException("Task is not completed yet!");
			}
			return this.result;
		} finally {
			this.stateLock.unlock();
		}
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		this.stateLock.lock();
		try {
			while(!isDone()) {
				this.finishedCondition.await();
			}
			if(this.exception != null) {
				throw this.exception;
			}
			return this.result;
		} finally {
			this.stateLock.unlock();
		}
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		this.stateLock.lock();
		try {
			if(!isDone())
				this.finishedCondition.await(timeout, unit);
			if(this.exception != null)
				throw this.exception;
			if(this.result == null)
				throw new TimeoutException("Object not returned in time allocated.");
			return this.result;
		} finally {
			this.stateLock.unlock();
		}
	}

	@Override
	public boolean isCancelled() {
		this.stateLock.lock();
		try {
			return this.cancelled;
		} finally {
			this.stateLock.unlock();
		}
	}

	@Override
	public boolean isDone() {
		this.stateLock.lock();
		try {
			return (this.finished) || (this.cancelled) || (this.exception != null);
		} finally {
			this.stateLock.unlock();
		}
	}

	public Callable<V> getCallable() {
		return this.callable;
	}

	@Override
	public void invoke() {
		try {
			V tmpResult = this.callable.call();

			this.stateLock.lock();
			try {
				this.result = tmpResult;
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
			return this.finished;
		} finally {
			this.stateLock.unlock();
		}
	}

	@Override
	public ExecutionException getException() {
		return exception;
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