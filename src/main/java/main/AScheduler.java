package main;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AScheduler {
	
	/*
	 * list of the queues
	 *  0 -> queue (implemented as LinkedList) for processes with prio 0
	 *  1 -> queue for processes with prio 1
	 *  ...
	 */
	protected ArrayList<LinkedList<Process>> queues;
		
	/*
	 * how long can a process utilize the CPU in each 
	 * queue level?
	 */
	protected int[] quantums;
	
	/*
	 * add a new process to the scheduler queue
	 */
	public abstract void admit(Process p);

	/*
	 * schedule processes on the computersystem
	 */
	public abstract void schedule(ComputerSystem c);
	
}
