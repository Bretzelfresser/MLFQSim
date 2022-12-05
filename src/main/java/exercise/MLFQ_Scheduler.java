package exercise;

import java.util.ArrayList;
import java.util.LinkedList;

import main.AScheduler;
import main.ComputerSystem;
import main.Process;

public class MLFQ_Scheduler extends AScheduler {

    public MLFQ_Scheduler(int[] quantums) {
        super();
        this.quantums = quantums;
        // initialize queues
        //TODO
        this.queues = new ArrayList<>(quantums.length);
        //for all quantums initialize a new Queue as a LinkedList
        for (int i = 0; i < quantums.length; i++) {
            this.queues.add(new LinkedList<>());
        }

    }

    public Process retrieveNextP() {
        //TODO
        for (int i = 0; i < this.queues.size(); i++) {
            if (!this.queues.get(i).isEmpty())
                return this.queues.get(i).pollFirst();
        }
        return null;
    }

    @Override
    public void schedule(ComputerSystem c) {
        if (c.getCurrentP() != null) {
            //add process in case it has a CPU burst longer than the quantum for its Prio
            if (c.getCurrentP().getState() == Process.pstate.READY) {
                c.getCurrentP().setPrio(Math.min(this.quantums.length, c.getCurrentP().getPrio() + 1));
                addProcess(c.getCurrentP());
            }
            //in case the Process has a IO work to do it gets added back in the same Priority
            else if (c.getCurrentP().getState() == Process.pstate.BLOCKED) {
                c.getCurrentP().setState(Process.pstate.READY);
                addProcess(c.getCurrentP());
            }
            //in case the process is terminated it gets yeeted and deleted
            else if (c.getCurrentP().getState() == Process.pstate.TERMINATED){
                c.setCurrentP(null);
                //this is just for debug so i can see how much prcesses r in the Scheduler right now
                System.out.println(queues.stream().mapToInt(LinkedList::size).sum());
            }
        }
        //get next process
        Process p = retrieveNextP();
        if (p != null) {
            //adds the next process to the System and schedule the Clock
            c.setCurrentP(p);
            c.scheduleClockInt(this.quantums[p.getPrio()]);
        }
        //TODO
    }

    /**
     * adds a Process with set priority and state
     * @param p
     */
    protected void addProcess(Process p){
        this.queues.get(p.getPrio()).addLast(p);
    }

    @Override
    public void admit(Process p) {
        p.setPrio(0);
        p.setState(Process.pstate.NEW);
        addProcess(p);
        //TODO
    }


}
