/**
 * Author      : Aaron May 
 * Date        : 25/10/2016
 *
 * Store data for a process such as ID, arrivalTime, execTime,turnAroundTime,waitTime, processStartTime and processFinishTime
 * A process is to symbolise a job that is passed to a processor for processing.
 * Also contains compareTo methods used to sort a list of processes by their member data such as processID, arrivalTime etc.
 */

import java.util.Comparator;
public class Process {
    private String processId;//id of a process
    private int arrivalTime;//arrival time that a process enters the system for processing
    private int execTime;//amount of time a process needs to execute for
    private int turnAroundTime;//total amount of time a process takes to completely execute from when it first enters the system to when it finishes processing
    private int waitTime;//total time a process is not executing and is idle, waiting for its turn to process
    private int processStartTime;//used to store the last arrival time for a process if it is executed mutliple times before it finishes processing
    private int processFinishTime;//used to store the last finish time for a process if it is executed mutliple times before it finishes processing

    /*
     * Takes as input a processID (String), arrivalTime (int) and execTime (int) and sets all other process attributes to default.
     */
    public Process(String processId, int arrivalTime, int execTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.execTime = execTime;
        int timeProcessed = 0;
        int waitTime = 0;
        int turnAroundTime = 0;
        processStartTime = 0;
        processFinishTime = 0;
    }

    /*
     * Return: a processes ID (String)
     */
    public String getProcessId() {
        return processId;
    }

    /*
     * Return: a processes arrival time (int)
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /*
     * Sets a processes exec time.
     * Input: exec time (int) as parameter 
     * Return: none
     */
    public void setExecTime(int execTime) {
        this.execTime = execTime;
    }

    /*
     * Return: a processes exec time (int)
     */
    public int getExecTime() {
        return execTime;
    }

    /*
     * Return: a processes wait time (int)
     */
    public int getWaitTime() {
        return waitTime;
    }

    /*
     * Sets a processes wait time
     * Input: wait time (int) as parameter
     * Return: none
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /*
     * Return: a processes turn around time (int)
     */
    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    /*
     * Sets a processes turn around time 
     * Input: turn around time (int) as parameter
     * Return: none
     */
    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    /*
     * Return: a processes start time (int)
     */
    public int getProcessStartTime() {
        return processStartTime;
    }

    /*
     * Sets a processes start time
     * Input: process start time (int)
     * Return: none
     */
    public void setProcessStartTime(int processStartTime) {
        this.processStartTime = processStartTime;
    }

    /*
     * Return: a processes finish time (int)
     */
    public int getProcessFinishTime() {
        return processFinishTime;
    }

    /*
     * Sets a processes finish time
     * Input: process finish time (int)
     * Return: none
     */
    public void setProcessFinishTime(int processFinishTime) {
        this.processFinishTime = processFinishTime;
    }

    /*
     * Used to sort a list of processes by arrival time.
     */
    public static Comparator<Process> arriveTimeComparator() 
    {
        return new ArrivalTimeComparator();
    }

    /*
     * Sorts a list of processes by arrival time in ascending order.
     * Compare method takes 2 processes as input and tests their arrivalTime member data.
     * Returns an int depending on which processes arrival time are lower or are equal
     */
    private static class ArrivalTimeComparator implements Comparator<Process>
    {
        public int compare(Process p1, Process p2)
        {
            if (p1.getArrivalTime() < p2.getArrivalTime())
            {
                return -1;//p1 has a lower arrival time
            }
            else if (p1.getArrivalTime() > p2.getArrivalTime())
            {
                return 1;//p2 has a lower arrival time
            }
            else
            {
                return 0;//both processes have the same arrival time
            }
        }
    }

    /*
     * Used to sort a list of processes by their process Id.
     */
    public static Comparator<Process> processIDComparator() 
    {
        return new ProcessIDComparator();
    }

    /*
     * Sorts a list of processes by process ID in ascending order.
     * Compare method takes 2 processes as input and tests their processId member data.
     * Returns an int depending on which processes id is lower or are equal
     */
    private static class ProcessIDComparator implements Comparator<Process>
    {
        public int compare(Process p1, Process p2)
        {
            String string1 = p1.getProcessId();//store process1's id into a temp String 
            string1 = string1.replaceAll("\\D+","");//Replace all non digits with an empty character in a string
            int s1 = Integer.parseInt(string1);//convert string into a int which is used for testing which process should be ordered first in a list of processes

            String string2 = p2.getProcessId();//store process2's id into a temp string
            string2 = string2.replaceAll("\\D+","");//Replace all non digits with an empty character in a string
            int s2 = Integer.parseInt(string2);//convert string into a int which is used for testing which process should be ordered first in a list of processes

            if (s1 < s2)
            {
                return -1;//p1 has a lower processId
            }
            else if (s1 > s2)
            {
                return 1;//p2 has a lower processId
            }
            else
            {
                return 0;//both processes have the same processId
            }
        }

    }

    /*
     * Used to sort a list of processes by exec time
     */
    public static Comparator<Process> execTimeComparator() 
    {
        return new ExecTimeComparator();
    }

    /*
     * Sorts a list of processes by execution time in ascending order.
     * Compare method takes 2 processes as input and tests their exec member data.
     * Returns an int depending on which processes exec time is lower or are equal
     */
    private static class ExecTimeComparator implements Comparator<Process>
    {
        public int compare(Process p1, Process p2)
        {
            if (p1.getExecTime() < p2.getExecTime())
            {
                return -1;//p1 has a lower execTime
            }
            else if (p1.getExecTime() > p2.getExecTime())
            {
                return 1;//p2 has a lower execTime
            }
            else
            {
                return 0;//both processes have the same execTime
            }
        }
    }

    /*
     * Used to sort a list of processes by arrival time and then exec time if the arrival times are equal.
     */
    public static Comparator<Process> arrivalExecTimeComparator() 
    {
        return new ArrivalExecTimeComparator();
    }

    /*
     * Sorts a list of processes by arrival time then by exec time if the arrival time is equal in ascending order.
     * Compare method takes 2 processes as input and tests their arrivalTime and exec member data if needed.
     * Returns an int depending on which processes arrival time is lower or if equal which process has a lower exec time
     */
    private static class ArrivalExecTimeComparator implements Comparator<Process>
    {
        public int compare(Process p1, Process p2) {
            if (p1.getArrivalTime() < p2.getArrivalTime())
            {
                return -1;//p1 has a lower arrivalTime
            }
            else if (p1.getArrivalTime() > p2.getArrivalTime())
            {
                return 1;//p2 has a lower arrivalTime
            }
            else
            {//both processes have the same arrivalTime so a check on their exec times must then be performed
                if (p1.getExecTime() < p2.getExecTime())
                {
                    return -1;//p1 has a lower execTime
                }
                else if (p1.getExecTime() > p2.getExecTime())
                {
                    return 1;//p2 has a lower execTime
                }
                else
                {
                    return 0;//both processes have the same execTime
                }
            }
        }
    }

    /*
     * Used to sort a list of processes by exec time and then by process Id if the exec times are equal.
     */
    public static Comparator<Process> execIDTimeComparator() 
    {
        return new ExecIDTimeComparator();
    }

    /*
     * Sorts a list of processes by exec time then by processId if the exec time is equal in ascending order.
     * Compare method takes 2 processes as input and tests their exec and id member data if needed.
     * Returns an int depending on which processes exec time is lower or if equal which process has a lower id.
     */
    private static class ExecIDTimeComparator implements Comparator<Process>
    {
        public int compare(Process p1, Process p2) {
            if (p1.getExecTime() < p2.getExecTime())
            {
                return -1;//p1 has a lower execTime
            }
            else if (p1.getExecTime() > p2.getExecTime())
            {
                return 1;//p2 has a lower execTime
            }
            else
            {//both processes have the same execTime so a check on their id must then be performed
                String string1 = p1.getProcessId();//store process1's id into a temp String 
                string1 = string1.replaceAll("\\D+","");//Replace all non digits with an empty character in a string
                int s1 = Integer.parseInt(string1);//convert string into a int which is used for testing which process should be ordered first in a list of processes

                String string2 = p2.getProcessId();//store process2's id into a temp string
                string2 = string2.replaceAll("\\D+","");//Replace all non digits with an empty character in a string
                int s2 = Integer.parseInt(string2);//convert string into a int which is used for testing which process should be ordered first in a list of processes

                if (s1 < s2)
                {
                    return -1;//p1 has a lower processId
                }
                else if (s1 > s2)
                {
                    return 1;//p2 has a higher processId
                }
                else
                {
                    return 0;//both processes have the same processId
                }
            }
        }
    }
}
