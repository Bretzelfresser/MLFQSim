package exercise;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import main.AScheduler;
import main.ComputerSystem;
import main.Process;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MFLQTests {

    public static final int AMOUNT_PROCESS = 20, LENGTH_CODE = 100, SYSTEM_SIMULATION = 10000;

    @Test
    public void test() {
        Process.code[] program = new Process.code[5];
        Arrays.fill(program, 0, 5, Process.code.CPU);
        Process[] processes = new Process[AMOUNT_PROCESS];
        for (int i = 0; i < AMOUNT_PROCESS; i++) {
            processes[i] = new Process(i, generateRandom(LENGTH_CODE));

        }


        /*
         * test with 6 levels
         */
        AScheduler scheduler = new MLFQ_Scheduler(new int[]{2, 4, 16, 32, 128});
        ComputerSystem c = new ComputerSystem(scheduler);
        ArrayList<LinkedList<Process>> queues = getQueue(scheduler);

        Process p_current = null;
        for (int i = 0; i < SYSTEM_SIMULATION; i++) {
            if (i < processes.length) {
                c.start(processes[i]);
                assertEquals(0, processes[i].getPrio());
            }
            for (int k = 0; k < queues.size(); k++) {
                int finalK = k;
                for (Process p : queues.get(k)) {
                    assertEquals(finalK, p.getPrio(), "prio fro process: " + p.getProcess_id() + "is not Equals to the Queue is is in: [Queue:"+ finalK + "|" + "prio:" + p.getPrio() + "]");
                }
            }
            c.executeOneStep();
            p_current = c.getCurrentP();
            //assertEquals(p_current, processes[0]);
        }
    }

    protected ArrayList<LinkedList<Process>> getQueue(AScheduler sh) {
        for (Field f : AScheduler.class.getDeclaredFields()) {
            if (f.getName().equals("queues") && f.getType().equals(ArrayList.class)) {
                f.setAccessible(true);
                try {
                    return (ArrayList<LinkedList<Process>>) f.get(sh);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new IllegalArgumentException("the parameter sh doenst have a field named queues");
    }

    protected Process.code[] generateRandom(int maxLength) {
        Random rand = new Random();
        int length = rand.nextInt(maxLength - 5) + 5;
        Process.code[] code = new Process.code[length];
        for (int i = 0; i < code.length; i++) {
            code[i] = rand.nextDouble() <= 0.2 ? Process.code.IO : Process.code.CPU;
        }
        return code;
    }

}