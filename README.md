# schedulingAlgorithms
This program simulates FCFS, RR (Round Robin), SRT and FB constant scheduling algorithms. 

For each algorithm the program lists the order of the jobs being processed and computes the waiting time and 
turnaround time for every job as well as the average waiting time and average turnaround time. 

The average values are consolidated in a table for easy comparison.

The time quantum for pre-emptive short-term scheduling policy is 4 milliseconds. For FB constant the highest priority is 0 and the lowest priority is 5. 

Whenever multiple processes meet the criteria for being selected as the next process to be run (e.g. if multiple processes have the same arrival time for FCFS), the program selects the process that comes first at input.

TO RUN:
Use a command line argument to read a datafile e.g. java Main datafile1.txt
Example format within datafile1.txt and datafile2.txt
