/**
 * Author      : Aaron May 
 * Date        : 25/10/2016
 *
 * The First-Come-First-Served (FCFS) algorithm is a non-preemptive policy.
 * It will execute a process until there is no more execution time, then execute the next process in the list.
 */
import java.util.ArrayList;

public class FCFS {
    private Processor newProcessor;//used to pass a process to the processor which provides execution time to the process
    private ArrayList<Process> readyQueue;//the list of processes that are waiting to execute
    private int timer;//the current time at any point within the execution of all processes
    
    /*
     * Takes an ArrayList of processes as parameter and performs a deep copy of this list, which will be worked on rather than the input list.
     * Input: ArryayList of processes
     */
    public FCFS(ArrayList<Process> list) {
        readyQueue = new ArrayList<Process>();//the list of processes that are waiting to execute
        newProcessor = new Processor();//used to pass a process to the processor which provides execution time to the process
        for(int i = 0; i < list.size();i++) { //create a deep copy of list
            readyQueue.add(new Process(list.get(i).getProcessId(),list.get(i).getArrivalTime(),list.get(i).getExecTime()));
        }
        timer = 0;//set timer to 0 as this is the start of the algorithm
    }

    /*
     * Iterate through the list of processes from the readyQueue and provide execution time to each of them.
     * Input: none
     * Return: the ArrayList<Process> of processes that have all been processed already
     */
    public ArrayList<Process> start() {
        Process process;//temp process used to store the processes from the readyQueue
        for(int i = 0; i < readyQueue.size(); i++) {//iterate through the entire list of processes
            process = readyQueue.get(i);//get the next process in the list
            if(timer < process.getArrivalTime()) { //increments time when there are no processes to process, caters for idle time
                timer = process.getArrivalTime();
            }
            process = newProcessor.provideCPUTimeFCFS(process, timer);//pass the process to the processor for processing
            timer += process.getExecTime();//increment the schedule timer
        }
        return readyQueue;
    }
}
