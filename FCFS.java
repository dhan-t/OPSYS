import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean restart = true;

        // Will restart while users don't want to end the program.
        while (restart) {
            System.out.print("Enter the number of processes: ");
            int numOfProcess = sc.nextInt();

            // User input arrival times array
            int[] arrivalTimes = new int[numOfProcess];

            // User input burst times array
            int[] burstTimes = new int[numOfProcess];

            // Computer generated wait time array
            int[] waitTimes = new int[numOfProcess];

            // Computer generated turnaround time array
            int[] turnAroundTimes = new int[numOfProcess];

            // Initialization
            int previousFinishTime = 0;
            int totalWaitTime = 0;
            int totalTurnAroundTime = 0;
            int nonZeroProcessCount = 0;

            // Main loop and user input getting.
            for (int i = 0; i < numOfProcess; i++) {

                // Process counter
                System.out.println("/// P" + (i + 1));

                // Getting of arrival time
                System.out.print("Arrival Time: ");
                arrivalTimes[i] = sc.nextInt();

                // Getting of burst time
                System.out.print("Burst Time: ");
                burstTimes[i] = sc.nextInt();

                if (i == 0) {
                    waitTimes[i] = 0; // First process has no waiting time
                } else {
                    waitTimes[i] = previousFinishTime - arrivalTimes[i];
                    if (waitTimes[i] < 0) {
                        waitTimes[i] = 0;
                    }
                }

                turnAroundTimes[i] = waitTimes[i] + burstTimes[i];

                previousFinishTime += burstTimes[i];
                totalWaitTime += waitTimes[i];
                totalTurnAroundTime += turnAroundTimes[i];
                nonZeroProcessCount++;
            }

            // Calculate averages
            double averageWaitTime = (double) totalWaitTime / nonZeroProcessCount;
            double averageTurnAroundTime = (double) totalTurnAroundTime / nonZeroProcessCount;

            // Calculate throughput
            double totalExecutionTime = previousFinishTime - arrivalTimes[0];
            double throughput = (double) numOfProcess / totalExecutionTime;

            // For displaying the table
            System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
            for (int i = 0; i < numOfProcess; i++) {
                System.out.println("P" + (i + 1) + "\t\t" + arrivalTimes[i] + "\t\t" + burstTimes[i] + "\t\t"
                        + waitTimes[i] + "\t\t" + turnAroundTimes[i]);
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
