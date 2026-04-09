#include <mpi.h>
#include <vector>
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <algorithm>

std::vector<int> karatsuba_serial(const std::vector<int> &A, const std::vector<int> &B)
{
    int n = A.size();
    std::vector<int> res(2 * n - 1, 0);

    // base case: small size, switch to naive
    if (n <= 4)
    {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                res[i + j] += A[i] * B[j];
        return res;
    }

    int k = n / 2;

    std::vector<int> A0(A.begin(), A.begin() + k);
    std::vector<int> A1(A.begin() + k, A.end());
    std::vector<int> B0(B.begin(), B.begin() + k);
    std::vector<int> B1(B.begin() + k, B.end());

    std::vector<int> A0A1(k);
    std::vector<int> B0B1(k);

    for (int i = 0; i < k; i++)
    {
        A0A1[i] = A0[i] + A1[i];
        B0B1[i] = B0[i] + B1[i];
    }

    std::vector<int> P0 = karatsuba_serial(A0, B0);
    std::vector<int> P1 = karatsuba_serial(A1, B1);
    std::vector<int> P2 = karatsuba_serial(A0A1, B0B1);

    for (int i = 0; i < P0.size(); i++)
        res[i] += P0[i];
    for (int i = 0; i < P1.size(); i++)
        res[i + 2 * k] += P1[i];
    for (int i = 0; i < P2.size(); i++)
        res[i + k] += (P2[i] - P0[i] - P1[i]);

    return res;
}

void generate_random(std::vector<int> &v, int n)
{
    for (int i = 0; i < n; i++)
        v[i] = rand() % 10;
}

int main(int argc, char **argv)
{
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    int N = 0;
    if (argc > 1)
        N = atoi(argv[1]);
    else
    {
        if (rank == 0)
            std::cout << "Usage: mpirun -np 3 ./karatsuba <N>" << std::endl;
        MPI_Finalize();
        return 0;
    }

    std::vector<int> A, B;

    if (rank == 0)
    {
        A.resize(N);
        B.resize(N);
        srand(time(0));
        generate_random(A, N);
        generate_random(B, N);
    }

    if (size < 3)
    {
        if (rank == 0)
            std::cout << "Error: Karatsuba MPI demo requires at least 3 processes." << std::endl;
        MPI_Finalize();
        return 0;
    }

    double start_time = MPI_Wtime();

    if (rank == 0)
    {
        int k = N / 2;

        std::vector<int> A0(A.begin(), A.begin() + k);
        std::vector<int> A1(A.begin() + k, A.end());
        std::vector<int> B0(B.begin(), B.begin() + k);
        std::vector<int> B1(B.begin() + k, B.end());

        std::vector<int> A0A1(k), B0B1(k);
        for (int i = 0; i < k; i++)
        {
            A0A1[i] = A0[i] + A1[i];
            B0B1[i] = B0[i] + B1[i];
        }

        MPI_Send(A0.data(), k, MPI_INT, 1, 0, MPI_COMM_WORLD);
        MPI_Send(B0.data(), k, MPI_INT, 1, 0, MPI_COMM_WORLD);

        MPI_Send(A1.data(), k, MPI_INT, 2, 0, MPI_COMM_WORLD);
        MPI_Send(B1.data(), k, MPI_INT, 2, 0, MPI_COMM_WORLD);

        std::vector<int> P2 = karatsuba_serial(A0A1, B0B1);

        std::vector<int> P0(2 * k - 1);
        std::vector<int> P1(2 * k - 1);

        MPI_Recv(P0.data(), 2 * k - 1, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(P1.data(), 2 * k - 1, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        std::vector<int> final_res(2 * N - 1, 0);
        for (int i = 0; i < P0.size(); i++)
            final_res[i] += P0[i];
        for (int i = 0; i < P1.size(); i++)
            final_res[i + 2 * k] += P1[i];
        for (int i = 0; i < P2.size(); i++)
            final_res[i + k] += (P2[i] - P0[i] - P1[i]);

        double end_time = MPI_Wtime();
        std::cout << "Karatsuba MPI finished in: " << (end_time - start_time) << " seconds." << std::endl;

        if (N < 10)
        {
            std::cout << "Result: ";
            for (auto x : final_res)
                std::cout << x << " ";
            std::cout << std::endl;
        }
    }
    else if (rank == 1 || rank == 2)
    {
        int k = N / 2;
        std::vector<int> local_A(k), local_B(k);

        MPI_Recv(local_A.data(), k, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(local_B.data(), k, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        std::vector<int> local_res = karatsuba_serial(local_A, local_B);

        MPI_Send(local_res.data(), local_res.size(), MPI_INT, 0, 0, MPI_COMM_WORLD);
    }

    MPI_Finalize();
    return 0;
}
