import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean restart = true;

        while (restart) {
            System.out.print("Enter the number of processes: ");
            int numOfProcess = sc.nextInt();

            // User input arrival times array
            int[] arrivalTimes = new int[numOfProcess];

            // User input burst times array
            int[] burstTimes = new int[numOfProcess];

            // Remaining burst times for Round Robin (copy of burst times)
            int[] remainingBurstTimes = new int[numOfProcess];

            // Computer generated wait time array
            int[] waitTimes = new int[numOfProcess];

            // Computer generated turnaround time array
            int[] turnAroundTimes = new int[numOfProcess];

            // Computer generated completion time array
            int[] completionTimes = new int[numOfProcess];

            // Initialization
            int quantum;
            int processCompleted = 0;
            int currentTime = 0;

            System.out.print("Enter the Quantum value: ");
            quantum = sc.nextInt();

            // User input getting for arrival and burst times
            for (int i = 0; i < numOfProcess; i++) {
                System.out.println("/// P" + (i + 1));
                System.out.print("Arrival Time: ");
                arrivalTimes[i] = sc.nextInt();
                System.out.print("Burst Time: ");
                burstTimes[i] = sc.nextInt();
                remainingBurstTimes[i] = burstTimes[i]; // Initialize remaining times
            }

            // Main loop for Round Robin scheduling
            while (processCompleted != numOfProcess) {
                for (int i = 0; i < numOfProcess; i++) {
                    // Check if process has arrived and remaining time is greater than 0
                    if (arrivalTimes[i] <= currentTime && remainingBurstTimes[i] > 0) {
                        // Execute process for minimum of remaining time and quantum
                        int timeToExecute = Math.min(remainingBurstTimes[i], quantum);
                        remainingBurstTimes[i] -= timeToExecute;
                        currentTime += timeToExecute;

                        // Process completed
                        if (remainingBurstTimes[i] == 0) {
                            processCompleted++;
                            completionTimes[i] = currentTime; // Set completion time
                            turnAroundTimes[i] = completionTimes[i] - arrivalTimes[i];
                            waitTimes[i] = turnAroundTimes[i] - burstTimes[i];

                            // Handle negative wait times (process arrived and executed immediately)
                            if (waitTimes[i] < 0) {
                                waitTimes[i] = 0;
                            }
                        }
                    }
                }
            }

            // Calculate averages
            double averageWaitTime = calculateAverage(waitTimes, numOfProcess);
            double averageTurnAroundTime = calculateAverage(turnAroundTimes, numOfProcess);

            // Calculate throughput (same as FCFS)
            double totalExecutionTime = currentTime - arrivalTimes[0];
            double throughput = (double) numOfProcess / totalExecutionTime;

            // For displaying the table
            System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tWaiting Time\tTurnaround Time");
            for (int i = 0; i < numOfProcess; i++) {
                System.out.println("P" + (i + 1) + "\t\t" + arrivalTimes[i] + "\t\t" + burstTimes[i] + "\t\t"
                        + completionTimes[i] + "\t\t" +turnAroundTimes[i] + "\t\t" + waitTimes[i] );
            }

            // Display calculations
            System.out.println("\n> Total Wait Time: " + calculateTotal(waitTimes));
            System.out.println("> Average Waiting Time: " + averageWaitTime);
            System.out.println("> Total Turnaround Time: " + calculateTotal(turnAroundTimes));
            System.out.println("> Average Turnaround Time: " + averageTurnAroundTime);
            System.out.println("> Throughput: " + throughput);

            System.out.println("\nRestart program?: (y/n)");
            char restartAgain = sc.next().charAt(0);
            if (restartAgain == 'y') {
                main(args);
            } else {
                restart = false;
            }
        }
        sc.close();
    }

    // Helper functions to calculate total and average
    public static double calculateTotal(int[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    public static double calculateAverage(int[] arr, int n) {
        return calculateTotal(arr) / n;
    }
}
