package org.greencubes.executor;

import java.util.concurrent.ExecutionException;

public interface IInvokeable {

	public void invoke();
	
	public boolean isDone();
	
	public boolean isCancelled();
	
	public boolean isDoneWell();
	
	public ExecutionException getException();
	
	public void await() throws InterruptedException;
}
