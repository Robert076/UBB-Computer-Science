import java.util.*;

public class Main {
    public static void main(String[] args) {

        Map<Long, Long> setupBalances = new LinkedHashMap<>();
        setupBalances.put(1L, 10000L);
        setupBalances.put(2L, 20000L);
        setupBalances.put(3L, 15000L);
        setupBalances.put(4L, 25000L);

        Bank bankSystem = new Bank(setupBalances);

        System.out.println("Initial Account Summary:");
        bankSystem.displayBalances();
        System.out.printf("Combined Total: $%d\n\n", bankSystem.getOverallBalance());

        bankSystem.verifyIntegrity();
        System.out.println();

        List<TransferThread> workers = new ArrayList<>();

        TransferThread w1 = new TransferThread(bankSystem, 1L, 2L, 100L, 25);
        workers.add(w1);

        TransferThread w2 = new TransferThread(bankSystem, 2L, 3L, 200L, 20);
        workers.add(w2);

        TransferThread w3 = new TransferThread(bankSystem, 3L, 4L, 150L, 30);
        workers.add(w3);

        TransferThread w4 = new TransferThread(bankSystem, 4L, 1L, 250L, 15);
        workers.add(w4);

        TransferThread w5 = new TransferThread(bankSystem, 2L, 4L, 300L, 35);
        workers.add(w5);

        TransferThread w6 = new TransferThread(bankSystem, 4L, 3L, 275L, 50);
        workers.add(w6);

        System.out.println("Transfers in progress...\n");

        long start = System.currentTimeMillis();
        for (TransferThread t : workers) {
            t.start();
        }

        for (TransferThread t : workers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Main process interrupted");
                Thread.currentThread().interrupt();
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("\nAll transactions finished in " + (end - start) + " ms\n");

        System.out.println("Final Account Summary:");
        bankSystem.displayBalances();
        System.out.printf("Final Total: $%d\n\n", bankSystem.getOverallBalance());

        System.out.println("Transfer Results:");
        for (int i = 0; i < workers.size(); i++) {
            TransferThread t = workers.get(i);
            System.out.printf("Thread %d: %d succeeded, %d failed\n",
                    i + 1,
                    t.getSuccessCount(),
                    t.getFailureCount());
        }
        System.out.println();

        bankSystem.verifyIntegrity();
        System.out.println("\nSimulation finished successfully!");
    }
}
