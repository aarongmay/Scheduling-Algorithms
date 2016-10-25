/**
 * Author      : Aaron May 
 * Date        : 25/10/2016
 *
 * Processor class used to provide execution time for a process.
 * Process wait and turnaround time are recorded here.
 * The schedule algorithms of FF, RR, SRT and FB (constant) all have their own methods, which are custom to that particular algorthim.
 */
public class Processor {
    private final int timeQuantum = 4;//interval timer to allow a process to run for a specific time interval

    /*
     * No member data required
     */
    public Processor() { //do nothing
    }

    /*
     * Used for FCFS schedule algorithm.
     * Pass in a process (Process) and the current time (int) as parameter and then calculate the wait and turn around time of the process.
     * The timeQuantum var is not used as FCFS simiply executes a process until it has no more execution time.
     * Input: process (Process) and current time (int)
     * Return: process that has been provided with execution time
     */
    public Process provideCPUTimeFCFS(Process process, int currentTime) {
        process.setProcessStartTime(currentTime);//when the process starts execution
        process.setWaitTime(currentTime - process.getArrivalTime());//how long the process has had to wait to execute since it first entered the system
        process.setTurnAroundTime(process.getWaitTime() + process.getExecTime());//the total time a process takes to completely execute since it first entered the system
        return process;
    }

    /*
     * Used for RR schedule algorithm.
     * Pass in a process (Process) as parameter and then calculate the wait and turn around time of the process.
     * The timeQuantum var is used as RR simiply executes a process for only the max amount of the time Quantum or less.
     * Input: process (Process)
     * Return: process that has been provided with execution time
     */
    public Process provideCPUTimeRR(Process process) {
        process.setProcessStartTime(RR.timer);//when the process starts execution

        if(process.getExecTime() - timeQuantum <= 0) {//if a process has less than 4 exec time, stops the exec time from being a negative number
            RR.timer += process.getExecTime();//increment the processor timer
            process.setExecTime(0);//set the processes exec time to 0 as it has no more exec time left
        }
        else { //process has more than 4 exec time
            RR.timer += timeQuantum;//increment the processor timer by the processor quantum time
            process.setExecTime(process.getExecTime() - timeQuantum);//set the process exec time to the previous exec time minus the time quantum
        }

        int lastProcessFinishTime = 0;//reset the last process finish time to 0 which is used to calculate the wait time if the process needs to enter the processor a number of times
        if(process.getProcessFinishTime() != 0) { //for processes that have already been processed once before
            lastProcessFinishTime = process.getProcessFinishTime();//store than the process has last finished execution
            process.setProcessFinishTime(RR.timer);//set the new process finish time for the current process being executed
            process.setWaitTime(process.getWaitTime() + (process.getProcessStartTime() - lastProcessFinishTime));//set the process wait time which adds the previous waittime plus the current start and finish time
        }
        else{//for processes that have not been run before in the processor
            process.setProcessFinishTime(RR.timer);//set the process finish time to the processor timer
            process.setWaitTime(process.getProcessStartTime() - process.getArrivalTime());//set the process wait time which takes the process start time minus the process arrival time into the system
        }
        process.setTurnAroundTime(process.getProcessFinishTime() - process.getArrivalTime());//set the turn around time for the process, which is the process finish time minus the process arrival time

        return process;
    }

    /*
     * Used for SRT schedule algorithm.
     * Pass in a process (Process) as parameter and then calculate the wait and turn around time of the process.
     * The timeQuantum var is not used as SRT will simiply executes a process for only 1 execution at a time as it needs to check after every execution that another process in its list has a smaller
     * execution time or not.
     * Input: process (Process)
     * Return: process that has been provided with execution time
     */
    public Process provideCPUTimeSRT(Process process) {
        process.setProcessStartTime(SRT.timer);//when the process starts execution
        SRT.timer += 1;//increment the processor timer by 1 execution time
        process.setExecTime(process.getExecTime() - 1);//set the process exec time to the previous exec time minus the 1 execution time provided

        int lastProcessFinishTime = 0;//reset the last process finish time to 0 which is used to calculate the wait time if the process needs to enter the processor a number of times
        if(process.getProcessFinishTime() != 0) { //for processes that have already been processed once before
            lastProcessFinishTime = process.getProcessFinishTime();//store than the process has last finished execution
            process.setProcessFinishTime(SRT.timer);//set the new process finish time for the current process being executed
            process.setWaitTime(process.getWaitTime() + (process.getProcessStartTime() - lastProcessFinishTime));//set the process wait time which adds the previous waittime plus the current start and finish time
        }
        else{//for processes that have not been run before in the processor
            process.setProcessFinishTime(SRT.timer);//set the process finish time to the processor timer 
            process.setWaitTime(process.getProcessStartTime() - process.getArrivalTime());//set the process wait time which takes the process start time minus the process arrival time into the system
        }
        process.setTurnAroundTime(process.getProcessFinishTime() - process.getArrivalTime());//set the turn around time for the process, which is the process finish time minus the process arrival time

        return process;
    }

    /*
     * Used for FB (constant) schedule algorithm.
     * Pass in a process (Process) as parameter and then calculate the wait and turn around time of the process.
     * The timeQuantum var is used as FB simiply executes a process for only the max amount of the time Quantum or less.
     * Input: process (Process)
     * Return: process that has been provided with execution time
     */
    public Process provideCPUTimeFB(Process process) {
        process.setProcessStartTime(FB.timer);//when the process starts execution

        if(process.getExecTime() - timeQuantum <= 0) {//if a process has less than 4 exec time, stops the exec time from being a negative number
            FB.timer += process.getExecTime();//increment the processor timer
            process.setExecTime(0);//set the processes exec time to 0 as it has no more exec time left
        }
        else { 
            FB.timer += timeQuantum;//increment the processor timer by the processor quantum time
            process.setExecTime(process.getExecTime() - timeQuantum);//set the process exec time to the previous exec time minus the time quantum
        }

        int lastProcessFinishTime = 0;//reset the last process finish time to 0 which is used to calculate the wait time if the process needs to enter the processor a number of times
        if(process.getProcessFinishTime() != 0) { //for processes that have already been processed once before
            lastProcessFinishTime = process.getProcessFinishTime();//store than the process has last finished execution
            process.setProcessFinishTime(FB.timer);//set the new process finish time for the current process being executed
            process.setWaitTime(process.getWaitTime() + (process.getProcessStartTime() - lastProcessFinishTime));//set the process wait time which adds the previous waittime plus the current start and finish time
        }
        else{//for processes that have not been run before in the processor
            process.setProcessFinishTime(FB.timer);//set the process finish time to the processor timer
            process.setWaitTime(process.getProcessStartTime() - process.getArrivalTime());//set the process wait time which takes the process start time minus the process arrival time into the system
        }
        process.setTurnAroundTime(process.getProcessFinishTime() - process.getArrivalTime());//set the turn around time for the process, which is the process finish time minus the process arrival time

        return process;
    }
}

