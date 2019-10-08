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