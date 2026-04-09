#include <mpi.h>
#include <vector>
#include <iostream>
#include <cstdlib>
#include <ctime>

void generate_random(std::vector<int> &v, int n)
{
    for (int i = 0; i < n; i++)
        v[i] = rand() % 10;
}

int main(int argc, char **argv)
{
    MPI_Init(&argc, &argv);

    int world_rank, world_size;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);

    int N = 0;

    if (argc > 1)
    {
        N = atoi(argv[1]);
    }
    else
    {
        if (world_rank == 0)
            std::cout << "Usage: mpirun -np <p> ./naive <N>" << std::endl;
        MPI_Finalize();
        return 0;
    }

    if (N % world_size != 0)
    {
        if (world_rank == 0)
            std::cout << "Please ensure N is divisible by number of processes for this simple demo." << std::endl;
        MPI_Finalize();
        return 0;
    }

    std::vector<int> A(N), B(N);
    std::vector<int> final_result(2 * N - 1, 0);

    if (world_rank == 0)
    {
        srand(time(0));
        generate_random(A, N);
        generate_random(B, N);
        // std::cout << "Generated polynomials of size " << N << std::endl;
    }

    MPI_Bcast(B.data(), N, MPI_INT, 0, MPI_COMM_WORLD);

    int chunk_size = N / world_size;
    std::vector<int> local_A(chunk_size);
    MPI_Scatter(A.data(), chunk_size, MPI_INT, local_A.data(), chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    std::vector<int> local_result(2 * N - 1, 0);
    int offset = world_rank * chunk_size;

    double start_time = MPI_Wtime();

    for (int i = 0; i < chunk_size; i++)
    {
        for (int j = 0; j < N; j++)
        {
            local_result[offset + i + j] += local_A[i] * B[j];
        }
    }

    MPI_Reduce(local_result.data(), final_result.data(), 2 * N - 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

    double end_time = MPI_Wtime();

    if (world_rank == 0)
    {
        std::cout << "Naive MPI finished in: " << (end_time - start_time) << " seconds." << std::endl;
        if (N < 10)
        {
            std::cout << "Result coefficients: ";
            for (int x : final_result)
                std::cout << x << " ";
            std::cout << std::endl;
        }
    }

    MPI_Finalize();
    return 0;
}
