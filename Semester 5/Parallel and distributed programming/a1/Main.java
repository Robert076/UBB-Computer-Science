import java.util.*;

public class Main {
    public static void main(String[] args) {

        Map<Integer, Integer> setupBalances = new LinkedHashMap<>();
        setupBalances.put(1, 10000);
        setupBalances.put(2, 20000);
        setupBalances.put(3, 15000);
        setupBalances.put(4, 25000);

        Bank bankSystem = new Bank(setupBalances);

        System.out.println("Initial Account Summary:");
        bankSystem.displayBalances();
        System.out.printf("Combined Total: $%d\n\n", bankSystem.getOverallBalance());

        bankSystem.verifyIntegrity();
        System.out.println();

        List<TransferThread> workers = new ArrayList<>();
        workers.add(new TransferThread(bankSystem, 1, 2, 100, 25));
        workers.add(new TransferThread(bankSystem, 2, 3, 200, 20));
        workers.add(new TransferThread(bankSystem, 3, 4, 150, 30));
        workers.add(new TransferThread(bankSystem, 4, 1, 250, 15));
        workers.add(new TransferThread(bankSystem, 2, 4, 300, 35));
        workers.add(new TransferThread(bankSystem, 4, 3, 275, 50));

        AuditorThread auditor = new AuditorThread(bankSystem, 300);
        auditor.start();

        System.out.println("Transfers in progress...\n");

        long start = System.currentTimeMillis();
        for (TransferThread t : workers)
            t.start();

        for (TransferThread t : workers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        auditor.stopAuditing();

        long end = System.currentTimeMillis();
        System.out.println("\nAll transactions finished in " + (end - start) + " ms\n");

        System.out.println("Final Account Summary:");
        bankSystem.displayBalances();
        System.out.printf("Final Total: $%d\n\n", bankSystem.getOverallBalance());

        System.out.println("Transfer Results:");
        for (int i = 0; i < workers.size(); i++) {
            TransferThread t = workers.get(i);
            System.out.printf("Thread %d: %d succeeded, %d failed%n",
                    i + 1, t.getSuccessCount(), t.getFailureCount());
        }

        bankSystem.verifyIntegrity();
        System.out.println("\nSimulation finished successfully!");
    }
}
