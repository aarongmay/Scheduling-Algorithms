/**
 * Author      : Aaron May 
 * Date        : 25/10/2016
 *
 * For the Feedback (FB) (constant) policy, I use a set of queues, one for each priority level and also a main queue that stores each priority queue in 1 queue.
 * When a process enters the system it enters the high priority queue which is priority queue 0.
 * The process is executed for a constant amount of time and then is moved down a priority level.
 * The lowest priority level in this program is priority 5.
 * Within each queue, an FCFS mechanism is used; except for the lowest priority queue, which is round-robin.
 */
import java.util.*;
public class FB {
    private Processor newProcessor;//used to pass a process to the processor which provides execution time to the process
    private ArrayList<ArrayList<Process>> allPriorities;//stores all priority lists into one arraylist
    private ArrayList<Process> priority0;//store all processes at priority0 into this list
    private ArrayList<Process> priority1;//store all processes at priority1 into this list
    private ArrayList<Process> priority2;//store all processes at priority2 into this list
    private ArrayList<Process> priority3;//store all processes at priority3 into this list
    private ArrayList<Process> priority4;//store all processes at priority4 into this list
    private ArrayList<Process> priority5;//store all processes at priority5 into this list
    private ArrayList<Process> processes;//the list of process that are to be executed from have not arrived into the system yet
    private ArrayList<Process> completed;//the list of processes that have completely finished processing and have no further execution time
    public static int timer;//the current time at any point within the execution of all processes
    private int processQueueSize;//used to store the size of the list that is passed in at the constructor.
    private Process previousProcess;//used to store the last process that was sent to the processor. Used so if the same process is sent to processor twice in a row the output will not be affected.

    /*
     * Takes an ArrayList of processes as parameter and performs a deep copy of this list, which will be worked on rather than the input list.
     * Input: ArryayList of processes
     */
    public FB(ArrayList<Process> list) {
        allPriorities = new ArrayList<ArrayList<Process>>();//stores all priority lists into one arraylist
        priority0 = new ArrayList<Process>();//store all processes at priority0 into this list
        priority1 = new ArrayList<Process>();//store all processes at priority1 into this list
        priority2 = new ArrayList<Process>();//store all processes at priority2 into this list
        priority3 = new ArrayList<Process>();//store all processes at priority3 into this list
        priority4 = new ArrayList<Process>();//store all processes at priority4 into this list
        priority5 = new ArrayList<Process>();//store all processes at priority5 into this list
        processes = new ArrayList<Process>();//the list of process that are to be executed from have not arrived into the system yet
        completed =  new ArrayList<Process>();//the list of processes that have completely finished processing and have no further execution time
        newProcessor = new Processor();//used to pass a process to the processor which provides execution time to the process
        for(int i = 0; i < list.size();i++) { //create a deep copy of list
            processes.add(new Process(list.get(i).getProcessId(),list.get(i).getArrivalTime(),list.get(i).getExecTime()));
        }
        timer = 0;//set timer to 0 as this is the start of the algorithm
        processQueueSize = list.size();//store the size of the list that is passed in at the constructor.

        //add all priority levels to 1 queue
        allPriorities.add(priority0);
        allPriorities.add(priority1);
        allPriorities.add(priority2);
        allPriorities.add(priority3);
        allPriorities.add(priority4);
        allPriorities.add(priority5);
    }

    /*
     * Send the highest priority process to the processor. If no processes are found at highest priority search down one step until a process is found.
     * Output the time where each process is sent to the processor. If the same processor is sent twice in a row (or more) only the first time will be output.
     * Returns a list of processes that have sall been executed.
     * Input: none
     * Output: completed arraylist of processes
     */
    public ArrayList<Process> start() {
        Process process = null;//temp process used to store the processes from the readyQueue
        ArrayList<Process> tempArray = null;//used to store the priority array which the current process is apart of. Needed to be able to move a process down a priority
        do{
            storeNextProcess();//store process into priority 0            
            if(priority0.size() != 0) {//always attempt to get from the highest priority first
                process = priority0.get(0);//store process from priority0
                tempArray = priority0;//store priority0 into tempArray.
            }
            else {//no processes are in priority0 and begin search for process in next lower priorities
                for(int i = 1; i < allPriorities.size(); i++) {
                    tempArray = allPriorities.get(i);//store the next highest priority list into tempArray
                    if(tempArray.size() != 0) {//check the size of the next highest priority list
                        process = tempArray.get(0);//store process from list
                        break; //exit for loop as process has been found
                    }
                }

                if(tempArray.size() == 0) {//if all priorities are empty, get process directly from queue
                    process = processes.get(0);
                    tempArray = priority0;//store priority0 into tempArray.
                }
            }

            if(timer < process.getArrivalTime()) { //increments time when there are no processes to process, handles idle time
                timer = process.getArrivalTime();
            }

            if(previousProcess != process) { //ensures if the same process is processed directly after it self the output does not duplicate
                System.out.println("T" + timer + ": " + process.getProcessId());
            }   

            process = newProcessor.provideCPUTimeFB(process);//send current process to processor and then store back into process var
            storeNextProcess();//store process into priority 0  

            if(process.getExecTime() > 0) {//process still has more processing time
                sendProcessDownPriority(process,tempArray);
            }
            else{
                completed.add(tempArray.remove(0)); //process has completed processing
            }
            previousProcess = process;//store current process into previousProcess to handle if the process is processed next and will cause a duplicate output

        }
        while(completed.size() != processQueueSize);//continue until the readyQueue is empty or the completed queue reaches the size of the original process list
        return completed;//return the processes that have all been processed
    }

    /*
     * Stores the next process from priority0 to be executed into the ready queue.
     * Input: none
     * Output: none
     */
    public void storeNextProcess() {
        while(processes.size() != 0) {
            if(processes.get(0).getArrivalTime() <= timer) {
                priority0.add(processes.remove(0));//always store into the highest priority
            }
            else { //list is in arrival time order - can assume that any processes after i not due yet
                break;
            }
        }
    }

    /*
     * Send a process down a priority level. Takes as input a process and the priority level it is currently at.
     * Input: current process (Process) and the ArrayList<Process> that the process is in
     * Return: none
     */
    public void sendProcessDownPriority(Process process, ArrayList<Process> list) {
        //remove process from higher priority to store down one priority
        if(list == priority0) {
            priority1.add(priority0.remove(0));
        }
        else if(list == priority1) {
            priority2.add(priority1.remove(0));
        }
        else if(list == priority2) {
            priority3.add(priority2.remove(0));
        }
        else if(list ==  priority3) {
            priority4.add(priority3.remove(0));
        }
        else if(list == priority4) {
            priority5.add(priority4.remove(0));
        }
        else { //list is priority 5 and at lowest priority, cannot be moved down any more
            priority5.remove(0);//remove first process
            priority5.add(process);//add process back into priority queue
        }
    }
}
