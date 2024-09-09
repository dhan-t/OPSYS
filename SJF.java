import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SJF {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean restart = true;

        while (restart) {
            System.out.print("Enter the number of processes: ");
            int numOfProcess = sc.nextInt();

            ArrayList<Process> processes = new ArrayList<>();

            // User input getting loop
            for (int i = 0; i < numOfProcess; i++) {

                System.out.println("/// P" + (i + 1));

                System.out.print("Arrival Time: ");
                int arrivalTime = sc.nextInt();

                System.out.print("Burst Time: ");
                int burstTime = sc.nextInt();

                processes.add(new Process(i + 1, arrivalTime, burstTime));
            }

            // Sort processes by burst time (ascending)
            Collections.sort(processes, (p1, p2) -> p1.burstTime - p2.burstTime);

            // Calculate waiting and turnaround times
            int previousFinishTime = 0;
            int totalWaitTime = 0;
            int totalTurnAroundTime = 0;

            for (Process process : processes) {
                if (process.arrivalTime > previousFinishTime) {
                    process.waitingTime = 0;
                } else {
                    process.waitingTime = previousFinishTime - process.arrivalTime;
                }
                process.turnaroundTime = process.waitingTime + process.burstTime;
                previousFinishTime = process.turnaroundTime;
                totalWaitTime += process.waitingTime;
                totalTurnAroundTime += process.turnaroundTime;
            }

            // Calculate exit time
            for (Process process : processes) {
                process.exitTime = process.arrivalTime + process.turnaroundTime;
            }

            // Calculate averages
            double averageWaitTime = (double) totalWaitTime / numOfProcess;
            double averageTurnAroundTime = (double) totalTurnAroundTime / numOfProcess;

            // Calculate throughput
            double totalExecutionTime = previousFinishTime - processes.get(0).arrivalTime;
            double throughput = (double) numOfProcess / totalExecutionTime;

            // Print table headers
            System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time\tExit Time");

            // Print process information
            for (Process process : processes) {
                System.out.println("P" + process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t"
                        + process.waitingTime + "\t\t" + process.turnaroundTime + "\t\t" + process.exitTime);
            }

            // Display calculations
            System.out.println("\n> Total Wait Time: " + totalWaitTime);
            System.out.println("> Average Waiting Time: " + averageWaitTime);
            System.out.println("> Total Turnaround Time: " + totalTurnAroundTime);
            System.out.println("> Average Turnaround Time: " + averageTurnAroundTime);
            System.out.println("> Throughput: " + throughput);

            System.out.println("\nRestart program?: (y/n)");
            char restartAgain = sc.next().charAt(0);
            if (restartAgain == 'y') {
                main(args);
            } else {
                restart = false;
            }
            sc.close();
        }
    }
}

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnaroundTime;
    int exitTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}
