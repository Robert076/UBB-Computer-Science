import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class HamiltonianCycle {
    private static final AtomicReference<List<Integer>> result = new AtomicReference<>(null);

    static class HamiltonianTask extends RecursiveTask<List<Integer>> {
        private final List<Integer> path;
        public static int[][] adj;
        private final boolean[] visited;
        private final int threads;

        HamiltonianTask(List<Integer> path, boolean[] visited, int threads) {
            this.path = new ArrayList<>(path);
            this.visited = visited.clone();
            this.threads = threads;
        }

        @Override
        protected List<Integer> compute() {
            if (result.get() != null)
                return null;

            int current = path.get(path.size() - 1);

            if (path.size() == adj.length) {
                for (int neighbor : adj[current]) {
                    if (neighbor == path.get(0)) {
                        result.compareAndSet(null, path);
                        return path;
                    }
                }
                return null;
            }

            List<Integer> neighbors = new ArrayList<>();
            for (int neighbor : adj[current]) {
                if (!visited[neighbor])
                    neighbors.add(neighbor);
            }

            if (neighbors.isEmpty())
                return null;

            if (path.size() < 2 && threads > 1) {
                int threadsPerNeighbor = threads / neighbors.size();
                int extraThreads = threads % neighbors.size();
                List<HamiltonianTask> tasks = new ArrayList<>();

                int neighborIdx = 0;
                while (neighborIdx < neighbors.size()) {
                    int assignedThreads = threadsPerNeighbor + (extraThreads-- > 0 ? 1 : 0);

                    if (assignedThreads > 0) {
                        int nextNode = neighbors.get(neighborIdx);
                        visited[nextNode] = true;
                        path.add(nextNode);
                        HamiltonianTask task = new HamiltonianTask(path, visited, assignedThreads);
                        tasks.add(task);
                        task.fork();
                        path.remove(path.size() - 1);
                        visited[nextNode] = false;
                        neighborIdx++;
                    } else {
                        for (int i = neighborIdx; i < neighbors.size(); i++) {
                            checkNodeSequentially(neighbors.get(i));
                        }
                        break;
                    }
                }
                for (HamiltonianTask t : tasks)
                    t.join();
            } else {
                for (int neighbor : neighbors)
                    checkNodeSequentially(neighbor);
            }
            return result.get();
        }

        private void checkNodeSequentially(int nextNode) {
            if (result.get() != null)
                return;
            visited[nextNode] = true;
            path.add(nextNode);
            new HamiltonianTask(path, visited, 1).compute();
            path.remove(path.size() - 1);
            visited[nextNode] = false;
        }
    }

    public static void main(String[] args) {
        // int[][] graph = {
        // { 1, 2, 3 }, { 2, 3, 0 }, { 3, 0, 1 }, { 0, 1, 2 }
        // };

        int n = 40;
        HamiltonianTask.adj = new int[n][n - 1];
        for (int i = 0; i < n; i++) {
            int idx = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && Math.random() > 0.69)
                    HamiltonianTask.adj[i][idx++] = j;
            }
        }

        ForkJoinPool pool = new ForkJoinPool();
        List<Integer> startPath = new ArrayList<>();
        startPath.add(0);
        boolean[] visited = new boolean[HamiltonianTask.adj.length];
        visited[0] = true;

        long startTime = System.nanoTime();
        pool.invoke(new HamiltonianTask(startPath, visited, 2));
        long endTime = System.nanoTime();

        System.out.println("Result: " + result.get());
        System.out.println("Execution time (ms): " + (endTime - startTime) / 1_000_000.0);
    }
}