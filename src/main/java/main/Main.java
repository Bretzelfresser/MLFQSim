package main;

import java.util.Arrays;

import exercise.MLFQ_Scheduler;

public class Main {

	
	
	public static void main(String[] args) {
		Process.code[] program1 = new Process.code[100];
		Arrays.fill(program1, 0, 100, Process.code.CPU);
		Process p1 = new Process(1, program1);
		
		Process.code[] program2 = {Process.code.CPU, Process.code.IO, Process.code.CPU, Process.code.IO, Process.code.CPU, Process.code.IO}; 
		Process p2 = new Process(2, program2);
		
		Process.code[] program3 = {Process.code.CPU, Process.code.CPU, Process.code.CPU, Process.code.CPU, Process.code.CPU, Process.code.CPU};
		Process p3 = new Process(3, program3);
		
		Process.code[] program4 = new Process.code[50];
		Arrays.fill(program4, 0, 50, Process.code.CPU);
		Process p4 = new Process(4, program4);
		
		Process.code[] program5 = new Process.code[20];
		Arrays.fill(program5, 0, 5, Process.code.CPU);
		Arrays.fill(program5, 5, 6, Process.code.IO);
		Arrays.fill(program5, 6, 10, Process.code.CPU);
		Arrays.fill(program5, 10, 11, Process.code.IO);
		Arrays.fill(program5, 11, 15, Process.code.CPU);
		Arrays.fill(program5, 15, 16, Process.code.IO);
		Arrays.fill(program5, 16, 20, Process.code.CPU);
		Process p5 = new Process(5, program5);
		
		
		AScheduler scheduler = new MLFQ_Scheduler(new int[] {2, 4, 16, 32, 128});
		ComputerSystem c = new ComputerSystem(scheduler);
		
		
		for (int i =0 ; i<200; i++) {
			if (i==0) {
				c.start(p1);
			}
			if (i==10) {
				c.start(p2);
			}
			if (i==11) {
				c.start(p3);
			}
			if (i==12) {
				c.start(p4);
				c.start(p5);
			}
			
			c.executeOneStep();
		}

	}

}
