package main;

public class Process {
	
	// program either utilizes cpu or io
	public enum code { CPU, IO}
	
	public enum pstate { NEW, READY, ACTIVE, BLOCKED, TERMINATED}
	
	
	private int process_id;
	
	/*
	 * program counter, which code (line) from the 
	 * program to execute
	 */
	private int pc = 0;
	
	/*
	 * priority in this case:
	 * which queue level is the process in,
	 * (with 0 being the highest priority)
	 */
	private int prio = 0;
	
	/*
	 * current state of the process
	 */
	private pstate state = Process.pstate.NEW;
	
	/*
	 * program code, what does the process do in 
	 * each step?
	 */
	public code[] program;

	public int getProcess_id() {
		return process_id;
	}

	public pstate getState() {
		return this.state;
	}
	
	public void setState(pstate s) {
		this.state = s;
	}
	
	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}
	
	public void setPrio(int prio) {
		this.prio = prio;
	}
	
	public int getPrio() {
		return this.prio;
	}
	
	
	
	public Process(int process_id, code[] program) {
		super();
		this.process_id = process_id;
		this.program = program;
	}
	
}
