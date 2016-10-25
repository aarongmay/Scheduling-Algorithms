/**
 * Author      : Aaron May 
 * Date        : 25/10/2016
 *
 * Takes as input, a command line argument of the data file name (String) to open which contains a list of processes. 
 * FCFS, SRT, RR and FB (constant) schedule algorithms are performed on stored processes and waiting time and turnaround results are output.
 * Average wait time and turnaround time for each algorithm are also output.
 */

import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[]args) {
        Scanner inputStream = null;
        ArrayList<Process> inputList = new ArrayList<Process>();//stores processes from datafile which is used to pass processes to schedule algorithms 
        double fcfsAverageWait = 0.0;//used to calculate and store average wait time for FCFS schedule algorithm
        double fcfsAverageTurnaround = 0.0;//used to calculate and store the average turnaround time for FCFS schedule algorithm
        double rrAverageWait = 0.0;//used to calculate and store average wait time for RR schedule algorithm
        double rrAverageTurnaround = 0.0;//used to calculate and store the average turnaround time for RR schedule algorithm
        double srtAverageWait = 0.0;//used to calculate and store average wait time for SRT schedule algorithm
        double srtAverageTurnaround = 0.0;//used to calculate and store the average turnaround time for SRT schedule algorithm
        double fbAverageWait = 0.0;//used to calculate and store average wait time for FB schedule algorithm
        double fbAverageTurnaround = 0.0;//used to calculate and store the average turnaround time for FB schedule algorithm

        try
        {
            if(args.length == 0) {//check if user has passed a datafile name (String) as command line argument
                throw new FileNotFoundException();
            }

            inputStream = new Scanner (new File(args[0]));
            while (inputStream.hasNextLine())
            {     
                inputStream.nextLine();//ignore line
                inputStream.nextLine();//ignore line

                String processId = inputStream.nextLine();//store processID as string
                if(processId.equals("EOF")){//test for end of file
                    //do nothing, loop will break on next pass
                }
                else{
                    processId = processId.substring(4);//extract only p# from string

                    String tempArrive = inputStream.nextLine();//store arriveTime as string
                    tempArrive = tempArrive.replaceAll("\\D+","");//extract only ints from string
                    int arrivalTime = Integer.parseInt(tempArrive);//convert string to int

                    String tempExec = inputStream.nextLine();//store execTime as string
                    tempExec = tempExec.replaceAll("\\D+","");//extract only ints from string
                    int execSize = Integer.parseInt(tempExec);//convert string to int

                    Process newProcess = new Process(processId, arrivalTime, execSize);//create new process
                    inputList.add(newProcess);                    
                }
            }
            inputStream.close ();//close file
        } 
        catch (FileNotFoundException e) 
        { 
            System.out.println("Error occured. File failed to open.");
            System.exit(0);//exit program as no input file has been loaded in
        }
        Process process;//temp Process object used to store each process each output list
        Comparator<Process> arrivalComparator = Process.arriveTimeComparator();//used to sort processes in list by arrival time
        Collections.sort(inputList, arrivalComparator);//sorts inputList by arrival time

        /*---------------FCFS---------------*/
        System.out.println("FCFS:");
        FCFS fcfs = new FCFS(inputList);//pass the list of processes into FCFS schedule algorithm
        ArrayList<Process> outputFCFS = fcfs.start();//run FCFS algorithm and store processed processes in array used for outputing FCFS data

        for(int i = 0; i < outputFCFS.size(); i++) {
            System.out.println("T" + outputFCFS.get(i).getProcessStartTime() + ":  " + outputFCFS.get(i).getProcessId());
        }

        Comparator<Process> nameComparatorFCFS = Process.processIDComparator();//used to sort processes in list by processID
        Collections.sort(outputFCFS, nameComparatorFCFS);//sorts inputList by processID

        System.out.format("\n%-10s%-15s%-20s", "Process", "Waiting Time", "Turnaround Time");
        for(int i = 0; i < outputFCFS.size(); i++) {
            process = outputFCFS.get(i);//store each process in FCFS output list
            System.out.format("\n%-10s%-15s%-20s",process.getProcessId(), process.getWaitTime(), process.getTurnAroundTime());
            fcfsAverageWait += process.getWaitTime();//accumulate FCFS process wait times used to calculate FCFS average wait time
            fcfsAverageTurnaround += process.getTurnAroundTime();//accumulate FCFS process wait times used to calculate FCFS average turnaround time 
        }

        /*---------------RR---------------*/

        Comparator<Process> arrivalComparatorRR = Process.arriveTimeComparator();//used to sort processes in list by arrival time
        Collections.sort(inputList, arrivalComparatorRR); //sorts inputList by arrival time

        System.out.println("\n\nRR:");
        RR rr =  new RR(inputList);//pass the list of processes into RR schedule algorithm
        ArrayList<Process> outputRR = rr.start();//run RR algorithm and store processed processes in array used for outputing RR data

        Comparator<Process> nameComparatorRR = Process.processIDComparator();//used to sort processes in list by processID
        Collections.sort(outputRR, nameComparatorRR);//sorts inputList by name

        System.out.format("\n%-10s%-15s%-20s", "Process", "Waiting Time", "Turnaround Time");
        for(int i = 0; i < outputRR.size(); i++) { //format this!!!!!!!!!!!!!!!!!!!!!!!
            process = outputRR.get(i);//store each process in RR output list
            System.out.format("\n%-10s%-15s%-20s",process.getProcessId(), process.getWaitTime(), process.getTurnAroundTime());
            rrAverageWait += process.getWaitTime();//accumulate RR process wait times used to calculate FCFS average wait time
            rrAverageTurnaround += process.getTurnAroundTime();//accumulate RR process wait times used to calculate FCFS average turnaround time 
        }

        /*---------------SRT---------------*/

        Comparator<Process> arrivalExecComparatorSRT = Process.arrivalExecTimeComparator();//used to sort processes in list by arrival time then by exec time
        Collections.sort(inputList, arrivalExecComparatorSRT); //sorts inputList by arrival time then exec time

        System.out.println("\n\nSRT:");
        SRT srt = new SRT(inputList);//pass the list of processes into SRT schedule algorithm
        ArrayList<Process> outputSRT = srt.start();//run SRT algorithm and store processed processes in array used for outputing SRT data

        Comparator<Process> nameComparatorSRT = Process.processIDComparator();//used to sort processes in list by processID
        Collections.sort(outputSRT, nameComparatorSRT); //sorts inputList by name

        System.out.format("\n%-10s%-15s%-20s", "Process", "Waiting Time", "Turnaround Time");
        for(int i = 0; i < outputSRT.size(); i++) { //format this!!!!!!!!!!!!!!!!!!!!!!!
            process = outputSRT.get(i);//store each process in SRT output list
            System.out.format("\n%-10s%-15s%-20s",process.getProcessId(), process.getWaitTime(), process.getTurnAroundTime());
            srtAverageWait += process.getWaitTime();//accumulate SRT process wait times used to calculate SRT average wait time
            srtAverageTurnaround += process.getTurnAroundTime();//accumulate SRT process wait times used to calculate SRT average turnaround time 
        }

        /*---------------FB---------------*/
        Comparator<Process> nameComparatorFB = Process.processIDComparator();//used to sort processes in list by processID
        Collections.sort(inputList, nameComparatorFB); //sorts inputList by name

        Comparator<Process> arrivalComparatorFB = Process.arriveTimeComparator();//used to sort processes in list by arrivalTime
        Collections.sort(inputList, arrivalComparatorFB); //sorts inputList by arrival time

        System.out.println("\n\nFB (constant):");
        FB fb = new FB(inputList);//pass the list of processes into FB (constant) schedule algorithm
        ArrayList<Process> outputFB = fb.start();//run FB algorithm and store processed processes in array used for outputing FB data

        Collections.sort(outputFB, nameComparatorFB); //sorts inputList by name

        System.out.format("\n%-10s%-15s%-20s", "Process", "Waiting Time", "Turnaround Time");
        for(int i = 0; i < outputFB.size(); i++) { //format this!!!!!!!!!!!!!!!!!!!!!!!
            process = outputFB.get(i);//store each process in FB (constant) output list
            System.out.format("\n%-10s%-15s%-20s",process.getProcessId(), process.getWaitTime(), process.getTurnAroundTime());
            fbAverageWait += process.getWaitTime();//accumulate FB (constant) process wait times used to calculate FB (constant) average wait time
            fbAverageTurnaround += process.getTurnAroundTime();//accumulate FB (constant) process wait times used to calculate FB (constant) average turnaround time 
        }

        /*---------------Summary output---------------*/
        System.out.println("\n\nSummary");
        System.out.format("%-15s%-25s%-10s"," ","Average Waiting Time", "Average Turnaround Time");
        System.out.format("\n%-15s%-25.2f%-10.2f","FCFS", fcfsAverageWait/outputFCFS.size(), fcfsAverageTurnaround/outputFCFS.size());
        System.out.format("\n%-15s%-25.2f%-10.2f","RR", rrAverageWait/outputRR.size(), rrAverageTurnaround/outputRR.size());
        System.out.format("\n%-15s%-25.2f%-10.2f","SRT", srtAverageWait/outputSRT.size(), srtAverageTurnaround/outputSRT.size());
        System.out.format("\n%-15s%-25.2f%-10.2f","FB (constant)", fbAverageWait/outputFB.size(), fbAverageTurnaround/outputFB.size());
    }        
}
