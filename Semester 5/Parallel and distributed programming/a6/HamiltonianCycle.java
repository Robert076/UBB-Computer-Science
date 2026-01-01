import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class HamiltonianCycle {
    private static final AtomicReference<List<Integer>> result = new AtomicReference<>(null);

    static class HamiltonianTask extends RecursiveTask<List<Integer>> {
        private final int[][] adj;
        private final List<Integer> path;
        private final boolean[] visited;
        private final int threads;

        HamiltonianTask(int[][] adj, List<Integer> path, boolean[] visited, int threads) {
            this.adj = adj;
            this.path = new ArrayList<>(path);
            this.visited = visited.clone();
            this.threads = threads;
        }

        @Override
        protected List<Integer> compute() {
            if (result.get() != null)
                return null;

            int current = path.get(path.size() - 1);

            // Base case: visited all vertices
            if (path.size() == adj.length) {
                for (int neighbor : adj[current]) {
                    if (neighbor == path.get(0)) { // Check if we can return to start
                        result.compareAndSet(null, path);
                        return path;
                    }
                }
                return null;
            }

            // Find valid next vertices
            List<Integer> neighbors = new ArrayList<>();
            for (int neighbor : adj[current]) {
                if (!visited[neighbor])
                    neighbors.add(neighbor);
            }

            if (neighbors.isEmpty())
                return null;

            if (threads > 1) {
                // Split threads according to requirements
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
                        HamiltonianTask task = new HamiltonianTask(adj, path, visited, assignedThreads);
                        tasks.add(task);
                        task.fork();
                        // Reset for next iteration/sequential part
                        path.remove(path.size() - 1);
                        visited[nextNode] = false;
                        neighborIdx++;
                    } else {
                        // If we run out of threads, the last thread takes all remaining neighbors
                        // sequentially
                        for (int i = neighborIdx; i < neighbors.size(); i++) {
                            checkNodeSequentially(neighbors.get(i));
                        }
                        break;
                    }
                }
                for (HamiltonianTask t : tasks)
                    t.join();
            } else {
                // Sequential search
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
            new HamiltonianTask(adj, path, visited, 1).compute();
            path.remove(path.size() - 1);
            visited[nextNode] = false;
        }
    }

    public static void main(String[] args) {
        // Example Graph (Adjacency List)
        int[][] graph = {
                { 1, 2, 3 }, { 2, 3, 0 }, { 3, 0, 1 }, { 0, 1, 2 } // A simple complete graph
        };

        ForkJoinPool pool = new ForkJoinPool();
        List<Integer> startPath = new ArrayList<>();
        startPath.add(0);
        boolean[] visited = new boolean[graph.length];
        visited[0] = true;

        pool.invoke(new HamiltonianTask(graph, startPath, visited, 8));
        System.out.println("Result: " + result.get());
    }
}