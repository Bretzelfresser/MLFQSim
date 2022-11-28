package main;

public class ComputerSystem {
		
	private AScheduler scheduler;
	
	// global time in the computer system
	private long time = 0;
	
	/*
	 *  at this time, a clock interrupt will be executed,
	 *  first interrupt right at the start
	 */
	private long clockInterrupt = 0;
	
	// reference to current process
	private Process currentP=null;

	
	/**
	 * simulate one cpu time slot
	 * (scheduling can be done in zero time)
	 */
	public void executeOneStep() {
		
		if (time == clockInterrupt || null == currentP || Process.pstate.TERMINATED == currentP.getState()) {
			/*
			 * if a process has used its quantum, it's time to
			 * look for another process that may want to run
			 */
			if (null != currentP && Process.pstate.BLOCKED != currentP.getState() && Process.pstate.TERMINATED != currentP.getState()) {
				currentP.setState(Process.pstate.READY);
				clockInterrupt = -1;
				System.out.println("time: " + time + ";\t clock interrupt for process: \t" + currentP.getProcess_id());
			}
			
			// try to schedule another process.
			scheduler.schedule(this);
		}
			
		
		if (null != currentP) {
			
			if (Process.pstate.BLOCKED == currentP.getState()) {
				/*
				 * if process did IO, it is now in blocked state,
				 * call scheduler
				 */
				scheduler.schedule(this);
			}
			
			/*
			 * execute the current process
			 */
			currentP.setState(Process.pstate.ACTIVE);
			Process.code currentAction = currentP.program[currentP.getPc()];
			System.out.println("time: " + time + ";\t executing line: " + currentP.getPc() + "/" + currentAction + "\t of process " + currentP.getProcess_id() + " with prio " + currentP.getPrio());
			/*
			 * pc pointing already to next line
			 */
			currentP.setPc(currentP.getPc()+1);
			
			
			if (currentP.getPc() >= currentP.program.length) {
				/*
				 * oh, we ran out of program,
				 */
				currentP.setState(Process.pstate.TERMINATED);
				clockInterrupt = -1;
			}
			else if (currentAction == Process.code.IO) {
				/*
				 * we do EA, therefore process is blocked
				 */
				currentP.setState(Process.pstate.BLOCKED);
			}
			
		}
		else {
			/*
			 * No Process can be executed
			 */
			//System.out.println("time: " + time +"\t No process to execute.");
		}
		time = time+1;
	}
	
	
	public void scheduleClockInt(long t) {
		clockInterrupt = time + t;
	}
	
	public Process getCurrentP() {
		return this.currentP;
	}
	
	public void setCurrentP(Process p) {
		this.currentP = p;
	}
	
	/**
	 * just passes process to the scheduler
	 * @param p - the process
	 */
	public void start(Process p) {
		scheduler.admit(p);
	}

	public ComputerSystem(AScheduler scheduler) {
		super();
		this.scheduler = scheduler;
	}
	
	
	
}
